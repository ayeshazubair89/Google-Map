package com.example.googlemap;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AsyncRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.maps.android.PolyUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class Route extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "Route";
    private GoogleMap mMap;
    private LatLng mOriginLatLng;
    private LatLng mDestinationLatLng;

    EditText sourceEdt, destinationEdt;
    Button trackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sourceEdt = findViewById(R.id.source);
        destinationEdt = findViewById(R.id.destination);
        trackBtn = findViewById(R.id.track);

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = sourceEdt.getText().toString().trim();
                String destination = destinationEdt.getText().toString().trim();

                if (!source.equals("") && !destination.equals("")) {
                    try {
                        String url = "https://www.google.com/maps/dir/?api=1&";
                        String origin = URLEncoder.encode(source, "utf-8");
                        String dest = URLEncoder.encode(destination, "utf-8");
                        url += "origin=" + origin + "&destination=" + dest + "&travelmode=driving";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    } catch (UnsupportedEncodingException | ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Show error message if either source or destination is not entered.
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      //  mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private void requestDirections(LatLng origin, LatLng destination) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                origin.latitude + "," + origin.longitude + "&destination=" +
                destination.latitude + "," + destination.longitude + "&mode=driving&key=YOUR_API_KEY";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray routesArray = response.getJSONArray("routes");
                            JSONObject routeObject = routesArray.getJSONObject(0);
                            JSONObject polyObject = routeObject.getJSONObject("overview_polyline");
                            String polylineString = polyObject.getString("points");

                            List<LatLng> polylineList = PolyUtil.decode(polylineString);
                            PolylineOptions polylineOptions = new PolylineOptions()
                                    .addAll(polylineList)
                                    .color(getResources().getColor(com.google.android.material.R.color.design_default_color_primary_dark))
                                    .width(10f);

                            mMap.addPolyline(polylineOptions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error getting directions: " + error.getMessage());
            }
        });

        /*AsyncRequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);*/
    }

}







