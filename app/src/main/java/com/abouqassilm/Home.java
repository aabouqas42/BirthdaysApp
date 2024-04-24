package com.abouqassilm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.*;

import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Home extends AppCompatActivity {

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
    void setFont(TextView text) {
        Typeface customFont = ResourcesCompat.getFont(this, R.font.visby_round);
        text.setTypeface(customFont);
    }
    void filter(ImageView image, String color){
        Drawable Icon = image.getDrawable();
        Icon.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
    }
    void buttonEffect(LinearLayout linear, String color, String rippleColor,  boolean light) {
        {
            android.graphics.drawable.GradientDrawable ui = new android.graphics.drawable.GradientDrawable();
            int d = (int) getApplicationContext().getResources().getDisplayMetrics().density;
            ui.setColor(Color.parseColor(color));
            if (light)
                linear.setElevation(d * 5);
            ui.setCornerRadius(d * 15);
            android.graphics.drawable.RippleDrawable ripped;
            ripped = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new  int[]{}}, new int[]{Color.parseColor(rippleColor)}), ui, null);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        LinearLayout background = findViewById(R.id.background);
        LinearLayout line1 = findViewById(R.id.line1);
        LinearLayout line2 = findViewById(R.id.line2);
        LinearLayout line3 = findViewById(R.id.line3);
        LinearLayout menu = findViewById(R.id.menu);
        TextView userDate = findViewById(R.id.uerdata);
		TextView yy = findViewById(R.id.yy);
        TextView time_state = findViewById(R.id.time_state);
        TextView nextBirthDate = findViewById(R.id.nextBirthDate);
        TextView countDown = findViewById(R.id.countDown);
        TextView nextTitle = findViewById(R.id.nextTitle);
        TextView dd_mm = findViewById(R.id.dd_mm);
        TextView hello = findViewById(R.id.hello);
        TextView dds = findViewById(R.id.dayofwake);
        ImageView menuIcon = findViewById(R.id.menuIcon);

        String black = "#FF242426";
        String amoled = "#FF121212";
        String white = "#FFFFFFFF";
        String grey = "#9E9E9E";
        String soft_dark = "#FF1B1B1B";
        setFont(userDate);
        setFont(hello);
        setFont(yy);
        setFont(dd_mm);
        setFont(dds);
        setFont(time_state);
        setFont(nextBirthDate);
        setFont(countDown);
        setFont(nextTitle);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor sh = sharedPreferences.edit();
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
            }
        });

        if (sharedPreferences.contains("day") && sharedPreferences.contains("month") && sharedPreferences.contains("year")) {
            int day = Integer.parseInt(sharedPreferences.getString("day", ""));
            int month = Integer.parseInt(sharedPreferences.getString("month", ""));
            int year = Integer.parseInt(sharedPreferences.getString("year", ""));
            userDate.setText(new StringBuilder().append((day < 10) ? "0" + day : day).append(" • ").append((month < 10) ? "0" + month : month).append(" • ").append(year));
            hello.setText("Welcome");
        }
        if (sharedPreferences.contains("dark")) {
            if (sharedPreferences.getString("dark", "").equals("on")) {
                setColorToSystem(black, background, true);
                color(userDate, white);
                color(dd_mm, white);
                color(yy, white);
                color(dds, white);
                color(hello, white);
                color(time_state, white);
                color(nextBirthDate, white);
                color(nextTitle, white);
                color(countDown, white);
                filter(menuIcon, white);
                viewEffect(line1, soft_dark, false);
                viewEffect(line2, soft_dark, false);
                viewEffect(line3, soft_dark, false);
                buttonEffect(menu, soft_dark, grey, false);
                background.setBackgroundColor(Color.parseColor(black));
            } else {
                setColorToSystem(white, background, false);
                color(userDate, black);
                color(dd_mm, black);
                color(yy, black);
                color(time_state, black);
                color(dds, black);
                color(hello, black);
                color(nextBirthDate, black);
                color(countDown, black);
                color(nextTitle, black);
                filter(menuIcon, black);
                viewEffect(line1, white, true);
                viewEffect(line2, white, true);
                viewEffect(line3, white, true);
                buttonEffect(menu, white, grey, true);
                background.setBackgroundColor(Color.parseColor(white));
            }
        }

        int day = Integer.parseInt(sharedPreferences.getString("day", "-1"));
        int month = Integer.parseInt(sharedPreferences.getString("month", "-1")) - 1;
        int year = Integer.parseInt(sharedPreferences.getString("year", "-1"));
        Calendar birthdate = Calendar.getInstance();
        birthdate.set(year, month, day);
        int DayOfWeekNumber = birthdate.get(Calendar.DAY_OF_WEEK);
        String[] DaysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String dayOfWakeSTR = DaysOfWeek[DayOfWeekNumber - 1];
        Calendar currentDate = Calendar.getInstance();
        long millisecond = currentDate.getTimeInMillis() - birthdate.getTimeInMillis();
        long days = millisecond / (1000 * 60 * 60 * 24);
        long years = (long) (days / 365.25);
        long months = (long) ((days - (years * 365.25)) / 30.44);
        long remaningDays = (long) ((days - (years * 365.25)) % 30.44);
        yy.setText(String.valueOf(years));
        dds.setText(dayOfWakeSTR);
        dd_mm.setText(months + (months > 1 ? " months" : " month") + " • " + remaningDays + (remaningDays > 1 ? " days" : " day"));
        Calendar nextBirth = Calendar.getInstance();
        nextBirth.set(currentDate.get(Calendar.YEAR), month, day, 0,0,0);
        if (nextBirth.before(currentDate)) {
            nextBirth.add(Calendar.YEAR, 1);
        }
        long nextBirthMills = nextBirth.getTimeInMillis() - currentDate.getTimeInMillis();
        CountDownTimer timer = new CountDownTimer(nextBirthMills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                long remainingDays = 0;
                long weeks = 0;
                long months = 0;
                countDown.setTextSize(25);
                if (days >= 30.44) {
                    months = (long) (days / 30.44);
                    remainingDays = (long) (days - (months * 30.44)) % 7;
                    weeks = (long) ((days % 30.44) / 7);
                    nextBirthDate.setText(String.valueOf( months));
                    countDown.setText(weeks + (weeks > 1 ? " weeks" : " week") + " • " + remainingDays + (remainingDays > 1 ? " days" : " day"));
                    nextBirthDate.setVisibility(View.VISIBLE);
                    time_state.setText((months > 1) ? " Months" : " Month");
                } else if (days < 30.44 && days > 3) {
                    weeks = days / 7;
                    remainingDays = days % 7;
                    nextBirthDate.setText(String.valueOf(weeks));
                    time_state.setText((weeks > 1) ? " Weeks" : " Week");
                    countDown.setText(" " + remainingDays);
                    nextBirthDate.setVisibility(View.VISIBLE);
                } else {
                    StringBuilder time = new StringBuilder();
                    millisUntilFinished -= TimeUnit.DAYS.toMillis(days);
                    long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);
                    long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                    millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);
                    nextBirthDate.setText(String.valueOf(days));
                    time_state.setText((days > 1) ? " Days" : " Day");
                    if (hours < 10)
                        time.append("0" + hours + " : ");
                    else
                        time.append(hours + " : ");
                    if (minutes < 10)
                        time.append("0" + minutes + " : ");
                    else
                        time.append(minutes + " : ");
                    if (seconds < 10)
                        time.append("0" + seconds);
                    else
                        time.append(seconds);
                    countDown.setText(time);
                    if (days == 0) {
                        nextBirthDate.setVisibility(View.GONE);
                        time_state.setVisibility(View.GONE);
                        countDown.setTextSize(40);
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    @Override
    public void onBackPressed() {

    }
}