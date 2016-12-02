package com.example.kenert.allinoneapp;


import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Nightmode extends AppCompatActivity {
    private boolean nightmodeOnOff;
    public ImageButton modeOnOffButton;
    private int brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightmode);

        modeOnOffButton = (ImageButton) findViewById(R.id.nightmodeOnOffButton);
        nightmodeOnOff = false;
        stopServiceIfActive();



        int prog;
        //Seekbar
        SeekBar skbar = (SeekBar) findViewById(R.id.nightModeBar);
        skbar.setMax(255);
        skbar.setKeyProgressIncrement(127);

        try {
            brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        skbar.setProgress(brightness);

        skbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress <= 25) {
                    brightness = 25;
                } else {
                    brightness = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                android.provider.Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                WindowManager.LayoutParams lpp = getWindow().getAttributes();
                lpp.screenBrightness = brightness/(float)255;
                getWindow().setAttributes(lpp);
            }
        });

    }


    public void nightmodeButtonClicked(View view) {
        try {
            if (nightmodeOnOff) {
                nightmodeOnOff = false;


                turnNightOff();
            } else {
                nightmodeOnOff = true;
                turnNightOn();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnNightOn() {

        try {
            modeOnOffButton.setImageResource(R.drawable.nightmodeonbutton);
            Intent i = new Intent(Nightmode.this,NightmodeService.class);
            startService(i);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnNightOff() {

        try {
            modeOnOffButton.setImageResource(R.drawable.nightmodeonoffbutton);
            stopServiceIfActive();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void stopServiceIfActive() {
        if (NightmodeService.STATE == NightmodeService.ACTIVE) {
            Intent i = new Intent(Nightmode.this, NightmodeService.class);
            stopService(i);
        }
    }





    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
