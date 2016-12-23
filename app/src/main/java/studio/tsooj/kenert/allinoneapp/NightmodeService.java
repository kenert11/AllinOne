package studio.tsooj.kenert.allinoneapp;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.graphics.PixelFormat;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class NightmodeService extends Service {
    LinearLayout linearL;


    public static int STATE;

    public static final int INACTIVE=0;
    public static final int ACTIVE=0;

    static{
        STATE=INACTIVE;
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        linearL = new LinearLayout(this);
        linearL.setBackgroundColor(Color.parseColor("#B3000000"));
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(linearL, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(linearL!=null){
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(linearL);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        linearL.setBackgroundColor(Color.parseColor("#B3000000"));
        return super.onStartCommand(intent, flags, startId);
    }
}