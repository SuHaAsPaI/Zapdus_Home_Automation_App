package com.spsdmv.homeauto;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Suhaas Pai on 7/25/2015.
 */
public class Splash extends Activity {
    int ik=0;
    TextView tload,tHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getActionBar();
		actionBar.hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                      | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                      | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                      | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                      | View.SYSTEM_UI_FLAG_FULLSCREEN);*/

        tload=(TextView)findViewById(R.id.tV2);
        Typeface font1 = Typeface.createFromAsset(getAssets(), "FFF_Tusj.ttf");  
        tload.setTypeface(font1); 
        
        tHome=(TextView)findViewById(R.id.textView1);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "GoodDog.otf");  
        tHome.setTypeface(font2); 
       
        final ProgressBar pLoad=(ProgressBar)findViewById(R.id.progressBar);
        pLoad.setMax(2000);
        pLoad.setProgress(ik);;

        Thread timer=new Thread(){

            public void run(){

                try{
                    for(int jk=0;jk<=9;jk++){
                        ik+=200;
                        pLoad.setProgress(ik);
                        sleep(100);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();

                }finally{
                    Intent sread=new Intent("com.spsdmv.homeauto.MAINACTIVITY");
                    startActivity(sread);
                }
            }
        };
        timer.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
