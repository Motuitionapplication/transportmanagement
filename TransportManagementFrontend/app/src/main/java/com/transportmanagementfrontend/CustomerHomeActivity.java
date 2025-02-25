package com.transportmanagementfrontend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.codebyashish.googledirectionapi.AbstractRouting;
import com.codebyashish.googledirectionapi.ErrorHandling;
import com.codebyashish.googledirectionapi.RouteDrawing;
import com.codebyashish.googledirectionapi.RouteInfoModel;
import com.codebyashish.googledirectionapi.RouteListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerHomeActivity extends AppCompatActivity implements OnMapReadyCallback, RouteListener {

    private GoogleMap mMap;
    private ProgressDialog dialog;

    // UI elements
    private EditText pickupLocation, destinationLocation;
    private TextView welcomeText, distanceText;

    private static final int AUTOCOMPLETE_PICKUP_REQUEST = 1002;
    private static final int AUTOCOMPLETE_DEST_REQUEST = 1003;

    private LatLng pickupLatLng, destinationLatLng;
    private Marker pickupMarker, destinationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        welcomeText = findViewById(R.id.welcomeText);
        distanceText = findViewById(R.id.distanceText);
        pickupLocation = findViewById(R.id.pickupLocation);
        destinationLocation = findViewById(R.id.destinationLocation);

        String firstName = getIntent().getStringExtra("FIRST_NAME");
        welcomeText.setText(firstName != null && !firstName.isEmpty() ? "Hello, " + firstName + "!" : "Hello, Customer!");

        pickupLocation.setOnClickListener(view -> startPlaceAutocomplete(AUTOCOMPLETE_PICKUP_REQUEST));
        destinationLocation.setOnClickListener(view -> startPlaceAutocomplete(AUTOCOMPLETE_DEST_REQUEST));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        dialog = new ProgressDialog(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng india = new LatLng(20.5937, 78.9629);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india, 5f));
        mMap.addMarker(new MarkerOptions().position(india).title("India"));
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
        if (resultCode == RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            LatLng latLng = place.getLatLng();
            if (latLng == null) {
                Log.e("MAP_ERROR", "Selected place has no LatLng.");
                return;
            }

            Log.d("MAP_DEBUG", "Selected location: " + place.getName() + " Lat: " + latLng.latitude + " Lng: " + latLng.longitude);

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

            if (pickupLatLng != null && destinationLatLng != null) {
                com.google.android.gms.maps.model.LatLngBounds.Builder builder = new com.google.android.gms.maps.model.LatLngBounds.Builder();
                builder.include(pickupLatLng);
                builder.include(destinationLatLng);
                com.google.android.gms.maps.model.LatLngBounds bounds = builder.build();
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));

                calculateDistance();
                getRoute(pickupLatLng, destinationLatLng);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Marker updateMarker(Marker marker, LatLng latLng, String title) {
        if (mMap == null) return null;
        if (marker != null) marker.remove();
        return mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(setIcon(this, R.drawable.baseline_location_on_24)));
    }

    public BitmapDescriptor setIcon(Activity context, int drawableID) {
        Drawable drawable = ActivityCompat.getDrawable(context, drawableID);
        if (drawable == null) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void calculateDistance() {
        if (pickupLatLng == null || destinationLatLng == null) {
            distanceText.setText("Select both locations to calculate distance.");
            return;
        }
        float[] results = new float[1];
        Location.distanceBetween(pickupLatLng.latitude, pickupLatLng.longitude,
                destinationLatLng.latitude, destinationLatLng.longitude, results);
        float distanceKm = results[0] / 1000;
        distanceText.setText("Distance: " + String.format("%.2f", distanceKm) + " km");
    }

    private void getRoute(LatLng origin, LatLng destination) {
        dialog.setMessage("Route is generating, please wait");
        dialog.show();
        RouteDrawing routeDrawing = new RouteDrawing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(origin, destination)
                .build();
        routeDrawing.execute();
    }

    private boolean isWithinIndia(LatLng latLng) {
        double minLat = 6.0, maxLat = 38.0;
        double minLng = 68.0, maxLng = 98.0;
        return latLng.latitude >= minLat && latLng.latitude <= maxLat &&
                latLng.longitude >= minLng && latLng.longitude <= maxLng;
    }

    @Override
    public void onRouteFailure(ErrorHandling e) {
        dialog.dismiss();
        Toast.makeText(this, "Route Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteStart() {
        Toast.makeText(this, "Route Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteSuccess(ArrayList<RouteInfoModel> list, int indexing) {
        Toast.makeText(this, "Route Success", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void onRouteCancelled() {
        dialog.dismiss();
        Toast.makeText(this, "Route Canceled", Toast.LENGTH_SHORT).show();
    }
}
