package com.abouqassilm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;

public class activity_user_data extends AppCompatActivity {
    void buttonEffect(LinearLayout linear, String color, String rippleColor,  boolean light) {
        {
            android.graphics.drawable.GradientDrawable ui = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            ui.setColor(Color.parseColor(color));
            if (light)
                linear.setElevation(d * 5);
            ui.setCornerRadius(d * 15);
            android.graphics.drawable.RippleDrawable ripped;
            ripped = new RippleDrawable(new android.content.res.ColorStateList(new int[][]{new  int[]{}}, new int[]{Color.parseColor(rippleColor)}), ui, null);
            linear.setBackground(ripped);
            linear.setClickable(true);
        }
    }
    void viewEffect(LinearLayout linear , String color, boolean light) {
        {
            android.graphics.drawable.GradientDrawable ui = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            ui.setColor(Color.parseColor(color));
            if (light)
                linear.setElevation(d * 5);
            ui.setCornerRadius(d * 15);
            linear.setBackground(ui);
        }
    }
    void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    void setFont(TextView text) {
        Typeface customFont = ResourcesCompat.getFont(this, R.font.visby_round);
        text.setTypeface(customFont);
    }
    void setColorToSystem(String color, LinearLayout l, boolean dark){
        Window win = getWindow();
        win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        win.setStatusBarColor(Color.parseColor(color));
        if (dark) {
            l.setSystemUiVisibility(0);
        } else {l.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);}
        l.setBackgroundColor(Color.parseColor(color));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(Color.parseColor(color));
        }
    }
    void color(TextView text, String color){
        text.setTextColor(Color.parseColor(color));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        LinearLayout cnt = findViewById(R.id.cnt);
        TextView cntText = findViewById(R.id.cntText);
        TextView ques = findViewById(R.id.ques);
        LinearLayout background = findViewById(R.id.background);
        LinearLayout dataBackground = findViewById(R.id.dataBackground);
        EditText dd = findViewById(R.id.dd_mm);
        EditText mm = findViewById(R.id.mm);
        EditText yy = findViewById(R.id.yy);
        String black = "#FF242426";
        String white = "#FFFFFFFF";
        String amoled = "#FF121212";
        String grey = "#9E9E9E";
        setFont(cntText);
        setFont(mm);
        setFont(dd);
        setFont(yy);
        setFont(ques);
        yy.setHintTextColor(Color.parseColor("#9E9E9E"));
        dd.setHintTextColor(Color.parseColor("#9E9E9E"));
        mm.setHintTextColor(Color.parseColor("#9E9E9E"));
        if (sharedPreferences.contains("dark")){
            if (sharedPreferences.getString("dark", "").equals("on")){
                setColorToSystem(black, background, true);
                background.setBackgroundColor(Color.parseColor(black));
                color(dd, white);
                color(mm, white);
                color(yy, white);
                color(ques, white);
                color(cntText, white);
                buttonEffect(dataBackground, "#121313", "#121313", false);
                buttonEffect(cnt, amoled, grey, false);
            } else {
                setColorToSystem(white, background, false);
                background.setBackgroundColor(Color.parseColor(white));
                color(dd, black);
                color(mm, black);
                color(yy, black);
                color(ques, black);
                color(cntText, black);
                buttonEffect(cnt, white, grey, true);
            }
        }
        cnt.setOnClickListener(new View.OnClickListener() {
            final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            @Override
            public void onClick(View view) {
                if (dd.getText().length() > 0 && mm.getText().length() > 0 && yy.getText().length() > 0) {
                    if (Integer.parseInt(dd.getText().toString()) <= 31 && Integer.parseInt(mm.getText().toString()) <= 12 && Integer.parseInt(yy.getText().toString()) <= Calendar.getInstance().get(Calendar.YEAR)) {
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("day", dd.getText().toString());
                        edit.putString("month", mm.getText().toString());
                        edit.putString("year", yy.getText().toString());
                        edit.apply();
                        v.vibrate(20);
                        Intent i = new Intent(getApplicationContext(), Home.class);
                        startActivity(i);
                    } else{
                        showMessage("Birthdate Error..!");
                    }
                } else {
                    v.vibrate(100);
                    showMessage("All fields are required");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}