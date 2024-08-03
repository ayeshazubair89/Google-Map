package com.example.googlemap;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Fetching API_KEY which we wrapped
        ApplicationInfo ai;
        try {
            ai = getApplicationContext().getPackageManager()
                    .getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            String apiKey = "AIzaSyAMBZDLUlR7B1xUWBz39GANnS6cuuoqN0g";

            // Initializing the Places API
            // with the help of our API_KEY
            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), apiKey);
            }

            // Initialize Autocomplete Fragments
            // from the main activity layout file
            AutocompleteSupportFragment autocompleteSupportFragment1 =
                    (AutocompleteSupportFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.autocomplete_fragment1);

            // Information that we wish to fetch after typing
            // the location and clicking on one of the options
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.PHONE_NUMBER,
                    Place.Field.LAT_LNG,
                    Place.Field.OPENING_HOURS,
                    Place.Field.RATING,
                    Place.Field.USER_RATINGS_TOTAL);
            autocompleteSupportFragment1.setPlaceFields(placeFields);

            // Display the fetched information after clicking on one of the options
            autocompleteSupportFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {

                    // Text view where we will
                    // append the information that we fetch
                    TextView textView = findViewById(R.id.tv1);

                    // Information about the place
                    String name = place.getName();
                    String address = place.getAddress();
                    String phone = place.getPhoneNumber();
                    String latitude = String.valueOf(place.getLatLng().latitude);
                    String longitude = String.valueOf(place.getLatLng().longitude);

                    String isOpenStatus = place.isOpen() ? "Open" : "Closed";

                    String rating = String.valueOf(place.getRating());
                    String userRatings = String.valueOf(place.getUserRatingsTotal());

                    textView.setText("Name: " + name + "\nAddress: " + address + "\nPhone Number: " + phone + "\n" +
                            "Latitude, Longitude: " + latitude + ", " + longitude + "\nIs open: " + isOpenStatus + "\n" +
                            "Rating: " + rating + "\nUser ratings: " + userRatings);
                }

                @Override
                public void onError(@NonNull Status status) {
                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        // Add your custom logic here
        // For example, you can show a dialog box to confirm the user's action
        // or navigate to a different activity
        Intent intent= new Intent(Search.this,SimpleSearch.class);
        startActivity(intent);
        finish();
        // To navigate back to the previous activity, you can call the super method
        super.onBackPressed();
    }

}
