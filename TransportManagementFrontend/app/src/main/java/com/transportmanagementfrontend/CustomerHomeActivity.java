package com.transportmanagementfrontend;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class CustomerHomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private Marker pickupMarker, destinationMarker;
    private LatLng pickupLatLng, destinationLatLng;
    private LocationCallback locationCallback;

    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private static final int AUTOCOMPLETE_PICKUP_REQUEST = 1002;
    private static final int AUTOCOMPLETE_DEST_REQUEST = 1003;

    private TextView welcomeText, distanceText;
    private EditText pickupLocation, destinationLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        // Initialize Google Places API (Move key to local properties or AndroidManifest.xml)
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        // Initialize UI elements
        welcomeText = findViewById(R.id.welcomeText);
        distanceText = findViewById(R.id.distanceText);
        pickupLocation = findViewById(R.id.pickupLocation);
        destinationLocation = findViewById(R.id.destinationLocation);

        // Welcome message
        Intent intent = getIntent();
        String firstName = intent.getStringExtra("FIRST_NAME");
        welcomeText.setText(firstName != null && !firstName.isEmpty() ? "Hello, " + firstName + "!" : "Hello, Customer!");

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Autocomplete listeners
        pickupLocation.setOnClickListener(view -> startPlaceAutocomplete(AUTOCOMPLETE_PICKUP_REQUEST));
        destinationLocation.setOnClickListener(view -> startPlaceAutocomplete(AUTOCOMPLETE_DEST_REQUEST));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng india = new LatLng(20.5937, 78.9629);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 5f));

        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (checkLocationPermission()) {
            enableUserLocation();
        }
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return false;
        }
        return true;
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void startPlaceAutocomplete(int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            LatLng latLng = place.getLatLng();
            if (latLng == null) return;

            if (!isWithinIndia(latLng)) {
                Toast.makeText(this, "Please select a location within India.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (requestCode == AUTOCOMPLETE_PICKUP_REQUEST) {
                pickupLatLng = latLng;
                pickupLocation.setText(place.getName());
                pickupMarker = updateMarker(pickupMarker, pickupLatLng, "Pickup Location");
            } else if (requestCode == AUTOCOMPLETE_DEST_REQUEST) {
                destinationLatLng = latLng;
                destinationLocation.setText(place.getName());
                destinationMarker = updateMarker(destinationMarker, destinationLatLng, "Destination Location");
            }

            // If both locations are selected, adjust the camera to show both markers.
            // Otherwise, move the camera to the selected location.
            if (pickupLatLng != null && destinationLatLng != null) {
                com.google.android.gms.maps.model.LatLngBounds.Builder builder = new com.google.android.gms.maps.model.LatLngBounds.Builder();
                builder.include(pickupLatLng);
                builder.include(destinationLatLng);
                com.google.android.gms.maps.model.LatLngBounds bounds = builder.build();
                int padding = 100; // offset in pixels from the edges of the map
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            } else {
                if (requestCode == AUTOCOMPLETE_PICKUP_REQUEST) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickupLatLng, 15));
                } else if (requestCode == AUTOCOMPLETE_DEST_REQUEST) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 15));
                }
            }

            if (pickupLatLng != null && destinationLatLng != null) {
                calculateDistance();
            }
        }
    }

    private Marker updateMarker(Marker marker, LatLng latLng, String title) {
        if (mMap == null) return null;
        if (marker != null) marker.remove();
        return mMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void calculateDistance() {
        if (pickupLatLng == null || destinationLatLng == null) {
            distanceText.setText("Select both locations to calculate distance.");
            return;
        }
        float[] results = new float[1];
        Location.distanceBetween(
                pickupLatLng.latitude, pickupLatLng.longitude,
                destinationLatLng.latitude, destinationLatLng.longitude,
                results
        );

        float distanceKm = results[0] / 1000;
        distanceText.setText("Distance: " + String.format("%.2f", distanceKm) + " km");
    }

    private boolean isWithinIndia(LatLng latLng) {
        double minLat = 6.0, maxLat = 38.0;
        double minLng = 68.0, maxLng = 98.0;
        return latLng.latitude >= minLat && latLng.latitude <= maxLat &&
                latLng.longitude >= minLng && latLng.longitude <= maxLng;
    }
}
