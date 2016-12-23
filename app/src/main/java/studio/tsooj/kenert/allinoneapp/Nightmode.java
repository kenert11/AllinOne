package studio.tsooj.kenert.allinoneapp;


import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tsooj.kenert.allinoneapp.R;

public class Nightmode extends AppCompatActivity {
    private boolean nightmodeOnOff;
    public ImageButton modeOnOffButton;
    private int brightness;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightmode);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        if(mAdView != null){
            mAdView.resume();
        }

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
        if(mAdView != null){
            mAdView.destroy();
        }
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        if(mAdView != null){
            mAdView.pause();
        }
        super.onPause();

    }
}
