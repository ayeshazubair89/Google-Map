package com.example.googlemap;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.Manifest;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MyWidgetActivityConfigureActivity MyWidgetActivityConfigureActivity}
 */
public class MyWidgetActivity extends AppWidgetProvider {
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 1;

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int myAppWidgetId = appWidgetIds[0];
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);

            // Check if the user has granted location permissions
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                // Get the user's current location
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Uri uri = Uri.parse("geo:" + latitude + "," + longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    views.setOnClickPendingIntent(R.id.btn, PendingIntent.getActivity(context, 0, intent, 0));
                } else {
                    // Unable to get location, show a toast
                    Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Request location permissions
                Intent intent = new Intent(context, MyWidgetActivity.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                views.setOnClickPendingIntent(R.id.btn, PendingIntent.getBroadcast(context, 0, intent, 0));
               // views.setTextViewText(R.id., "Click to allow location access");
            /// Get the user's current location
             *//*   Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.example.myapp", "com.example.myapp.MainActivity");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*//*
            }

                // Update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
*/
   static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
       // Create an intent to launch your app
       Intent intent = new Intent(context, Splash.class);
       PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

       CharSequence widgetText = MyWidgetActivityConfigureActivity.loadTitlePref(context, appWidgetId);
       // Construct the RemoteViews object
       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
       views.setTextViewText(R.id.btn, widgetText);

       // Set the click listener for the button to launch your app
       views.setOnClickPendingIntent(R.id.btn, pendingIntent);

       // Instruct the widget manager to update the widget
       appWidgetManager.updateAppWidget(appWidgetId, views);
   }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            MyWidgetActivityConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
        // Add code here to be executed when the first widget is created
        // For example, you can display a toast message
        Toast.makeText(context, "MyWidgetActivity widget enabled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}