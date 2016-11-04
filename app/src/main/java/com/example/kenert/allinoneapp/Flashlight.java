package com.example.kenert.allinoneapp;

import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class Flashlight extends AppCompatActivity {
    private CameraManager cm;
    private ImageButton flashlightButton;
    private boolean flashlightOnOrOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        flashlightButton =(ImageButton) findViewById(R.id.flashOnOffButton);
        flashlightOnOrOff = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
