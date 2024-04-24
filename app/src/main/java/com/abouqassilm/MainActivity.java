package com.abouqassilm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
        void setColorToSystem(String color, LinearLayout l, boolean dark){
        Window win = getWindow();
        win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.setStatusBarColor(Color.parseColor(color));
            }
            if (dark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    l.setSystemUiVisibility(0);
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    l.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            }
        l.setBackgroundColor(Color.parseColor(color));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(Color.parseColor(color));
        }
    }
    void setFont(TextView text) {
        Typeface customFont = ResourcesCompat.getFont(this, R.font.baloo);
        text.setTypeface(customFont);
    }
    void filter(ImageView image, String color){
        Drawable Icon = image.getDrawable();
        Icon.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        TextView t1 = findViewById(R.id.t1);
        LinearLayout background = findViewById(R.id.background);
        ImageView logo = findViewById(R.id.logo);
        String black = "#FF242426";
        String white = "#FFFFFFFF";
        setFont(t1);
        int isOnDark = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (sharedPreferences.getString("oneTime", "").equals("")) {
            if (isOnDark == Configuration.UI_MODE_NIGHT_YES){
                edit.putString("dark", "on");
                edit.apply();
            } else {
                edit.putString("dark", "off");
                edit.apply();
            }
            edit.putString("oneTime", "done");
            edit.apply();
        }
        if (sharedPreferences.contains("dark")) {
            if (sharedPreferences.getString("dark", "").equals("on")) {
                setColorToSystem(black, background, true);
                filter(logo, white);
                t1.setTextColor(Color.parseColor(white));
            } else {
                setColorToSystem(white, background, false);
                filter(logo, black);
                t1.setTextColor(Color.parseColor(black));
            }
        }
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                if (sharedPreferences.contains("day") && sharedPreferences.contains("month") && sharedPreferences.contains("year"))
                {
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), activity_user_data.class);
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
    @Override
    public void onBackPressed() {

    }
}