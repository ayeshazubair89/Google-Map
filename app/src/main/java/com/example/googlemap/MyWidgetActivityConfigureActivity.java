package com.example.googlemap;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;

import com.example.googlemap.databinding.MyWidgetActivityConfigureBinding;

/*
 * The configuration screen for the {@link MyWidgetActivity MyWidgetActivity} AppWidget.
 */
public class MyWidgetActivityConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.googlemap.MyWidgetActivity";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    Button btn;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = MyWidgetActivityConfigureActivity.this;

            // When the button is clicked, store the string locally
        /*    String widgetText = mAppWidgetText.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetText);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);*/

            // It is the responsibility of the configuration activity to update the app widget
            /*AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            MyWidgetActivity.onUpdate(context, appWidgetManager, new int[]{mAppWidgetId});




            MyWidgetActivity myWidget = new MyWidgetActivity();
            myWidget.onUpdate(context, appWidgetManager, new int[]{mAppWidgetId});


            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();*/

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetText);

            // Create an intent to launch your app
            Intent intent = new Intent(context, Splash.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Create the widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget = new ComponentName(context, MyWidgetActivity.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            for (int appWidgetId : appWidgetIds) {
                // Construct the RemoteViews object
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_activity);
                views.setTextViewText(R.id.btn, widgetText);

                // Set the click listener for the button to launch your app
                views.setOnClickPendingIntent(R.id.btn, pendingIntent);

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();


        }
    };
    private MyWidgetActivityConfigureBinding binding;

    public MyWidgetActivityConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        binding = MyWidgetActivityConfigureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btn=findViewById(R.id.search);
        mAppWidgetText = binding.appwidgetText;
        binding.addButton.setOnClickListener(mOnClickListener);


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(MyWidgetActivityConfigureActivity.this, mAppWidgetId));
    }
}