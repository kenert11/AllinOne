package com.example.kenert.allinoneapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class ScreenFlashlight extends AppCompatActivity {
    private boolean brightnessOnOff;
    private ImageButton screenFlashOnOffButton;

    public ScreenFlashlight() throws Settings.SettingNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_flashlight);
        screenFlashOnOffButton = (ImageButton) findViewById(R.id.screenFlashOnOffButton);
        brightnessOnOff = false;

    Intent grantintent= new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        grantintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(grantintent);
    }
    public void screenFlashButtonClicked(View view) {
        try {
            if (brightnessOnOff) {

                turnBrightnessOff();
            } else{
                    brightnessOnOff = true;
                turnBrightnessOn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void turnBrightnessOn() {
        try {
            screenFlashOnOffButton.setImageResource(R.drawable.screenonbutton);
            int currentBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            //Screen refresh to add brightness

            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.screenBrightness = currentBrightness/ 100.0f;
            getWindow().setAttributes(lp);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnBrightnessOff() {
        try {
            screenFlashOnOffButton.setImageResource(R.drawable.screenoffbutton);
            int currentBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            //Refresh screen

            WindowManager.LayoutParams lpp = getWindow().getAttributes();
            lpp.screenBrightness = currentBrightness;
            getWindow().setAttributes(lpp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ledButtonClickedTwo(View view) {
        Intent led = new Intent(this, Flashlight.class);
        startActivity(led);
    }

    public void screenButtonClickedTwo(View view) {
        return;
    }
}
