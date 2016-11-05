package com.example.kenert.allinoneapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;


import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import static android.content.DialogInterface.*;

public class Flashlight extends AppCompatActivity {

    CameraManager cm;
    private ImageButton flashlightButton;
    private boolean flashlightOnOrOff;
    private String mCameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        flashlightButton = (ImageButton) findViewById(R.id.flashOnOffButton);
        flashlightOnOrOff = false;


        //Error if device does not have flashlight
        boolean hasFlash = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasFlash == false)

        {
            final AlertDialog  dialo = new AlertDialog.Builder(Flashlight.this).create();
            dialo.setTitle("Error");
            dialo.setMessage("Sorry your device does not have flashlight!");
            dialo.setButton(BUTTON_POSITIVE, "OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                     dialo.cancel();

                }
            });
            dialo.show();


        }
        cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {

            mCameraId = cm.getCameraIdList()[0];
        } catch (CameraAccessException ec) {
            ec.printStackTrace();
        }}

    public void flashButtonClicked(View view) {
        try {
            if (flashlightOnOrOff) {
                turnOffFlashlight();
                flashlightOnOrOff = false;

            } else{
                turnOnFlashlight();
            flashlightOnOrOff = true;

            }
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }




    public void turnOffFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.setTorchMode(mCameraId, false);
                flashlightButton.setImageResource(R.drawable.offbutton2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOnFlashlight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm.setTorchMode(mCameraId, true);
                flashlightButton.setImageResource(R.drawable.onbutton2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //What flashlight does on Stop
    @Override
    protected void onStop() {
        super.onStop();
        flashlightOnOrOff = false;
    }

    //What flashlight does on Pause
    @Override
    protected void onPause() {
        super.onPause();
        flashlightOnOrOff = false;
    }

    //What flashlight does on Resume
    @Override
    protected void onPostResume() {
        super.onPostResume();
        flashlightOnOrOff = true;
    }
}
