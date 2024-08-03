package com.example.googlemap;

import static android.provider.ContactsContract.*;
import static android.provider.ContactsContract.CommonDataKinds.*;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.googlemap.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapsActivity extends FragmentActivity  {

    SupportMapFragment smf;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getmylocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }


    public void getmylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptions=new MarkerOptions().position(latLng).title("You are here...!!");

                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                       /* googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(@NonNull Marker marker) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                                builder.setTitle("Add note");
                                builder.setMessage("Enter your note:");

                                // Add an EditText to the dialog box to allow the user to enter the note
                                final EditText input = new EditText(MapsActivity.this);
                                builder.setView(input);

                                // Add a "Save" button to the dialog box to allow the user to save the note
                                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Create a new Note object with the note text and the marker's location
                                        Note note = new Note(input.getText().toString(), marker.getPosition().latitude, marker.getPosition().longitude);

                                        // Add the note to the database
                                        NoteDatabaseHelper dbHelper = new NoteDatabaseHelper(MapsActivity.this);
                                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                                        ContentValues values = new ContentValues();
                                        values.put(NoteContract.NoteEntry.COLUMN_NAME_NOTE_TEXT, note.gettext());
                                        values.put(NoteContract.NoteEntry.COLUMN_NAME_LATITUDE, note.getLatitude());
                                        values.put(NoteContract.NoteEntry.COLUMN_NAME_LONGITUDE, note.getLongitude());
                                        long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);

                                        // Log the ID of the new row for debugging purposes
                                        Log.d("MainActivity", "New note inserted with ID " + newRowId);
                                    }
                                });

                                // Add a "Cancel" button to the dialog box to allow the user to cancel adding the note
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                // Show the AlertDialog
                                builder.show();
                                return true;
                            }

                        });*/
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                        builder.setTitle("Add note");
                        builder.setMessage("Enter your note:");

// Add an EditText to the dialog box to allow the user to enter the note
                        final EditText input = new EditText(MapsActivity.this);
                        builder.setView(input);

// Add a "Save" button to the dialog box to allow the user to save the note
                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Create a new Note object with the note text and the marker's location
                                Note note = new Note(input.getText().toString(), markerOptions.getPosition().latitude, markerOptions.getPosition().longitude);

                                // Add the note to the database
                                NoteDatabaseHelper dbHelper = new NoteDatabaseHelper(MapsActivity.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(NoteContract.NoteEntry.COLUMN_NAME_NOTE_TEXT, note.gettext());
                                values.put(NoteContract.NoteEntry.COLUMN_NAME_LATITUDE, note.getLatitude());
                                values.put(NoteContract.NoteEntry.COLUMN_NAME_LONGITUDE, note.getLongitude());
                                long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);

                                // Log the ID of the new row for debugging purposes
                                Log.d("MainActivity", "New note inserted with ID " + newRowId);
                            }
                        });

// Add a "Cancel" button to the dialog box to allow the user to cancel adding the note
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

// Show the AlertDialog
                        builder.show();

                            }
                        }, 4000);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Add your custom logic here
        // For example, you can show a dialog box to confirm the user's action
        // or navigate to a different activity
        Intent intent= new Intent(MapsActivity.this,Splash.class);
        startActivity(intent);
        finish();
        // To navigate back to the previous activity, you can call the super method
        super.onBackPressed();
    }

}
