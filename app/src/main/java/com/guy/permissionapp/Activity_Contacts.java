package com.guy.permissionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Contacts extends AppCompatActivity {

    private final int REQUEST_CODE_CONTACTS = 23;

    private Button btn;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btn);
        info = findViewById(R.id.info);

        btn.setOnClickListener(v -> start());

        updateUI();
    }

    private void updateUI() {
        String str = "";
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (result == PackageManager.PERMISSION_GRANTED) {
            str += "CONTACTS GRANTED";
        } else {
            str += "CONTACTS DENIED";
        }

        str += "\n\nShould Show Request Permission Rationale:\n" + ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS);

        info.setText(str);
    }

    private void start() {
        ActivityCompat.requestPermissions(Activity_Contacts.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_CODE_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_CONTACTS: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    permissionDenied();
               }

                updateUI();
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void permissionDenied() {
        Toast.makeText(Activity_Contacts.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    start();
                }
            });
            alertBuilder.show();
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(true);
            alertBuilder.setMessage("Don't ask again state. please grant permission manually");
            alertBuilder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    openPermissionSettings();
                }
            });
            alertBuilder.show();
        }

        // permission denied, boo! Disable the
        // functionality that depends on this permission.
    }

    private void openPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}