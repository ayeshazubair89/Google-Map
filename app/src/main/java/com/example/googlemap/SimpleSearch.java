package com.example.googlemap;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

    public class SimpleSearch extends FragmentActivity implements OnMapReadyCallback {

        private GoogleMap mMap;

        // creating a variable
        // for search view.
        SearchView searchView;
Button btn;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_simple_search);
            btn=findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "AutoComplete Search", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(SimpleSearch.this,Search.class);
                    startActivity(intent);
                    finish();
                }
            });

            // initializing our search view.
            searchView = findViewById(R.id.idSearch);

            // Obtain the SupportMapFragment and get notified
            // when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // adding on query listener for our search view.
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // on below line we are getting the
                    // location name from search view.
                    String location = searchView.getQuery().toString();

                    // below line is to create a list of address
                    // where we will store the list of all address.
                    List<Address> addressList = null;

                    // checking if the entered location is null or not.
                    if (location != null || location.equals("")) {
                        // on below line we are creating and initializing a geo coder.
                        Geocoder geocoder = new Geocoder(SimpleSearch.this);
                        try {
                            // on below line we are getting location from the
                            // location name and adding that location to address list.
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // on below line we are getting the location
                        // from our list a first position.
                        Address address = addressList.get(0);

                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        // on below line we are adding marker to that position.
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                        // below line is to animate camera to that position.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            // at last we calling our map fragment to update.
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
        }
        @Override
        public void onBackPressed() {
            // Add your custom logic here
            // For example, you can show a dialog box to confirm the user's action
            // or navigate to a different activity
            Intent intent= new Intent(SimpleSearch.this,Splash.class);
            startActivity(intent);
            finish();
            // To navigate back to the previous activity, you can call the super method
            super.onBackPressed();
        }



    }

