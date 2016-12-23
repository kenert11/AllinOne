package studio.tsooj.kenert.allinoneapp;


import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tsooj.kenert.allinoneapp.R;

public class ScreenFlashlight extends AppCompatActivity {
    private static String tag;
    private static final String TAG = tag;
    private boolean brightnessOnOff;
    private ImageButton screenFlashOnOffButton;
    private AdView mAdView;


    public ScreenFlashlight() throws Settings.SettingNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_flashlight);
        mAdView = (AdView) findViewById(R.id.adscreenflash);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        screenFlashOnOffButton = (ImageButton) findViewById(R.id.flashOnOffButton);
        brightnessOnOff = false;


    }

    public void screenFlashButtonClicked(View view) {

        try {
            if (brightnessOnOff) {
                brightnessOnOff = false;
                turnBrightnessOff();
                Log.d(TAG, "screenFlashButtonClicked: ");
            } else {
                brightnessOnOff = true;
                turnBrightnessOn();
                Log.d(TAG, "screenFlashButtonClicked: ");
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
            lp.screenBrightness = currentBrightness / 100.0f;
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
        finish();
    }

    public void screenButtonClickedTwo(View view) {
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnBrightnessOff();
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
        turnBrightnessOff();
    }

    @Override
    protected void onDestroy() {
        if(mAdView != null){
            mAdView.destroy();
        }
        super.onDestroy();
        turnBrightnessOff();
    }

    @Override
    protected void onPause() {
        if(mAdView != null){
            mAdView.pause();
        }
        super.onPause();
        turnBrightnessOff();
    }
}
