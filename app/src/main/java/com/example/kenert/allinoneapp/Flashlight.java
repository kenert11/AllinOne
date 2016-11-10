package com.example.kenert.allinoneapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import static android.content.DialogInterface.*;

public class Flashlight extends AppCompatActivity {

    CameraManager cm;
    private ImageButton flashlightButton;
    private boolean flashlightOnOrOff;
    private String mCameraId;
    int freg;
    Thread th;
    Strobo sr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        flashlightButton = (ImageButton) findViewById(R.id.screenFlashOnOffButton);
        flashlightOnOrOff = false;


        //Error if device does not have flashlight
        boolean hasFlash = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasFlash == false)

        {
            final AlertDialog dialo = new AlertDialog.Builder(Flashlight.this).create();
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
        }
        //Seekbar
        SeekBar skbar = (SeekBar) findViewById(R.id.seekBar);
        skbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                freg = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void flashButtonClicked(View view) {
        try {
            if (flashlightOnOrOff) {
                flashlightOnOrOff = false;
                if (th != null) {
                    sr.stopRunning = true;
                    th = null;
                    return;
                } else {
                    turnOffFlashlight();
                }


            } else {
                flashlightOnOrOff = true;
                if (freg != 0) {
                    sr = new Strobo();
                    sr.freg = freg;
                    th = new Thread(sr);
                    th.start();
                    return;
                } else {
                    turnOnFlashlight();
                }
            }
        } catch (Exception ec) {
            ec.printStackTrace();
        }
    }

    public class Strobo implements Runnable {
        int freg;
        boolean stopRunning = false;

        @Override
        public void run() {

            try {
                while (!stopRunning) {
                    turnOnFlashlight();
                    Thread.sleep(100 - freg);
                    turnOffFlashlight();
                    Thread.sleep(freg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

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
    public void ledButtonClicked(View view){
        return;
    }
    public void screenButtonClicked(View view){
        Intent screen = new Intent(this,ScreenFlashlight.class);
        startActivity(screen);
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
