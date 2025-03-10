package com.transportmanagementfrontend;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomerHomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "CustomerHomeActivity";
    private GoogleMap mMap;
    private ProgressDialog dialog;

    // UI elements
    private EditText pickupLocation, destinationLocation;
    private TextView welcomeText, distanceText;

    private LatLng pickupLatLng, destinationLatLng;
    private Marker pickupMarker, destinationMarker;

    // Background thread for Geocoder and network calls
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        welcomeText = findViewById(R.id.welcomeText);
        distanceText = findViewById(R.id.distanceText);
        pickupLocation = findViewById(R.id.pickupLocation);
        destinationLocation = findViewById(R.id.destinationLocation);

        // Get user details (if any)
        String firstName = getIntent().getStringExtra("FIRST_NAME");
        welcomeText.setText(firstName != null && !firstName.isEmpty() ? "Hello, " + firstName + "!" : "");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        dialog = new ProgressDialog(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        // Listen for action on pickup and destination EditTexts
        pickupLocation.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getLocationFromAddress(pickupLocation.getText().toString().trim(), true);
                return true;
            }
            return false;
        });

        destinationLocation.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getLocationFromAddress(destinationLocation.getText().toString().trim(), false);
                return true;
            }
            return false;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        // Move camera to a default location
        LatLng india = new LatLng(20.5937, 78.9629);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(india, 5f));
        mMap.addMarker(new MarkerOptions().position(india).title("India"));
    }

    private void getLocationFromAddress(String cityName, boolean isPickup) {
        if (cityName.isEmpty()) return;

        Log.d(TAG, "Searching for: " + cityName);
        executorService.execute(() -> {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocationName(cityName, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address location = addresses.get(0);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    runOnUiThread(() -> {
                        if (isPickup) {
                            pickupLatLng = latLng;
                            pickupMarker = updateMarker(pickupMarker, pickupLatLng, "Pickup: " + cityName);
                        } else {
                            destinationLatLng = latLng;
                            destinationMarker = updateMarker(destinationMarker, destinationLatLng, "Destination: " + cityName);
                        }
                        // Smooth camera movement
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f), 1000, null);
                        // Calculate distance and draw route if both locations are available
                        if (pickupLatLng != null && destinationLatLng != null) {
                            calculateDistance();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "City not found: " + cityName, Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                Log.e(TAG, "Geocoder failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private Marker updateMarker(Marker marker, LatLng latLng, String title) {
        if (mMap == null || latLng == null) return null;
        if (marker == null) {
            return mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        } else {
            marker.setPosition(latLng);
            marker.setTitle(title);
            return marker;
        }
    }

    private void calculateDistance() {
        if (pickupLatLng != null && destinationLatLng != null) {
            float[] results = new float[1];
            Location.distanceBetween(
                    pickupLatLng.latitude, pickupLatLng.longitude,
                    destinationLatLng.latitude, destinationLatLng.longitude,
                    results);
            float distanceInKm = results[0] / 1000; // Convert meters to kilometers
            distanceText.setText(String.format("Distance: %.2f km", distanceInKm));

            // Draw the route using the Directions API
            drawRoute(pickupLatLng, destinationLatLng);
        }
    }

    /**
     * Draws the route using the legacy Google Directions API.
     */
    private void drawRoute(LatLng origin, LatLng destination) {
        // Build the URL for the Directions API request.
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + origin.latitude + "," + origin.longitude
                + "&destination=" + destination.latitude + "," + destination.longitude
                + "&mode=driving&key=" + getString(R.string.google_maps_key);

        executorService.execute(() -> {
            try {
                URL urlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Read the response
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder responseStr = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseStr.append(line);
                }
                reader.close();
                connection.disconnect();

                Log.d(TAG, "Directions API Response: " + responseStr.toString());

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(responseStr.toString());
                JSONArray routes = jsonResponse.getJSONArray("routes");
                if (routes.length() > 0) {
                    JSONObject route = routes.getJSONObject(0);
                    JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                    String encodedPolyline = overviewPolyline.getString("points");
                    if (encodedPolyline == null || encodedPolyline.isEmpty()) {
                        Log.e(TAG, "Empty encoded polyline received");
                        return;
                    }
                    List<LatLng> polylinePoints = decodePoly(encodedPolyline);
                    runOnUiThread(() -> {
                        mMap.addPolyline(new PolylineOptions()
                                .addAll(polylinePoints)
                                .width(10)
                                .color(Color.RED));
                    });
                } else {
                    Log.e(TAG, "No routes found in the response.");
                }
            } catch (Exception e) {
                Log.e(TAG, "Error in drawRoute: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Decodes an encoded polyline string into a list of LatLng points.
     */
    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            poly.add(new LatLng(lat / 1E5, lng / 1E5));
        }
        return poly;
    }

    /**
     * Utility method to convert a drawable resource to a BitmapDescriptor.
     */
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
}
