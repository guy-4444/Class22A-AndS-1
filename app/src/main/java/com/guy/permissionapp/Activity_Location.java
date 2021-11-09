package com.guy.permissionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class Activity_Location extends AppCompatActivity {

    /*
    1. Location Services
    2. Location Permissions (s!)
    3. Background Location
    4. location Settings
     */

    private enum STATE {
        NA,
        NO_REGULAR_PERMISSION,
        NO_BACKGROUND_PERMISSION,
        LOCATION_DISABLE,
        LOCATION_SETTINGS_PROCCESS,
        LOCATION_SETTINGS_OK,
        LOCATION_ENABLE
    }

    private MaterialTextView location_LBL_title;
    private MaterialTextView location_LBL_content;
    private MaterialTextView location_LBL_progress;
    private MaterialButton location_BTN_next;
    private MaterialButton location_BTN_back;

    private STATE state = STATE.NA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        findViews();
        initViews();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d("pttt", "hasFocus= " + hasFocus);
        if (hasFocus) {
            start();
        }
    }

    private void start() {
        if (!isLocationEnabled(this)) {
            state = STATE.LOCATION_DISABLE;
        } else if
        updateUI();
    }

    private void updateUI() {
        if (state == STATE.NA) {
            location_LBL_title.setText("0");
            location_LBL_content.setText("NA");
            location_LBL_progress.setText("-/-");
            location_BTN_back.setVisibility(View.INVISIBLE);
            location_BTN_next.setVisibility(View.INVISIBLE);
        } else if (state == STATE.LOCATION_DISABLE) {
            location_LBL_title.setText("Enable location services");
            location_LBL_content.setText("The app samples your location.\nPlease enable location services (GPS).");
            location_LBL_progress.setText("1/4");
            location_BTN_next.setOnClickListener(v -> {
                enableLocationServicesProgrammatically();
            });
            location_BTN_next.setText("Turn On");
            location_BTN_back.setVisibility(View.VISIBLE);
            location_BTN_next.setVisibility(View.VISIBLE);
        } else if (state == STATE.NO_REGULAR_PERMISSION) {
            location_LBL_title.setText("Location permission");
            location_LBL_content.setText("Location permission is needed for core functionality.\nPlease Enable the app permission to access your location data");
            location_LBL_progress.setText("2/4");
            location_BTN_next.setOnClickListener(v -> {
                //askForPermissions(checkForMissingPermission(this));
            });
            location_BTN_next.setText("Allow");
            location_BTN_back.setVisibility(View.VISIBLE);
            location_BTN_next.setVisibility(View.VISIBLE);
        } else if (state == STATE.NO_BACKGROUND_PERMISSION) {
            location_LBL_title.setText("Background location permission");
            location_LBL_content.setText("This app collects location data even when the app is closed or not in use.\nTo protect your privacy, the app stores only calculated indicators, like distance from home and never exact location.\nA notification is always displayed in the notifications bar when service is running.");
            location_LBL_progress.setText("3/4");
            location_BTN_next.setOnClickListener(v -> {
                //askForPermissions(checkForMissingPermission(this));
            });
            location_BTN_next.setText("Allow");
            location_BTN_back.setVisibility(View.VISIBLE);
            location_BTN_next.setVisibility(View.VISIBLE);
        } else if (state == STATE.LOCATION_SETTINGS_PROCCESS) {
            location_LBL_title.setText("4");
            location_LBL_content.setText("LOCATION_SETTINGS_PROCCESS");
            location_BTN_back.setVisibility(View.INVISIBLE);
            location_BTN_next.setVisibility(View.INVISIBLE);
        } else if (state == STATE.LOCATION_SETTINGS_OK) {
            location_LBL_title.setText("");
            location_LBL_content.setText("Location services are running and all permissions have been granted.\n" +
                    "You can now start recording.");
            location_LBL_progress.setText("4/4");
            location_BTN_next.setOnClickListener(v -> {
                finish();
            });
            location_BTN_next.setText("Close");
            location_BTN_back.setVisibility(View.INVISIBLE);
            location_BTN_next.setVisibility(View.VISIBLE);
        }
    }

    private void enableLocationServicesProgrammatically() {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    @SuppressWarnings("deprecation")
    public static Boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // This is a new method provided in API 28
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isLocationEnabled();
        } else {
            // This was deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);
        }
    }

    private void initViews() {
        location_BTN_back.setText("Close");
        location_BTN_back.setOnClickListener(v -> onBackPressed());
    }

    private void findViews() {
        location_LBL_title = findViewById(R.id.location_LBL_title);
        location_LBL_content = findViewById(R.id.location_LBL_content);
        location_LBL_progress = findViewById(R.id.location_LBL_progress);
        location_BTN_next = findViewById(R.id.location_BTN_next);
        location_BTN_back = findViewById(R.id.location_BTN_back);
    }
}

