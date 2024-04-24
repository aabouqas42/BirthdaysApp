package com.abouqassilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.apache.commons.lang3.*;

public class settings extends AppCompatActivity {
    public boolean swtched = false;
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
        setContentView(R.layout.activity_settings);
        LinearLayout background =  findViewById(R.id.background);
        LinearLayout dark_light = findViewById(R.id.dark_light);
        LinearLayout line = findViewById(R.id.line);
        LinearLayout back = findViewById(R.id.back);
        LinearLayout set_edit = findViewById(R.id.set_edit);
        TextView tedit = findViewById(R.id.tedit);
        TextView tdark = findViewById(R.id.tdark);
        TextView set = findViewById(R.id.set);
        ImageView back_icon = findViewById(R.id.back_icon);
        String black = "#FF242426";
        String amoled = "#FF121212";
        String white = "#FFFFFFFF";
        String grey = "#9E9E9E";
        String soft_dark = "#FF1B1B1B";
        /*--------------DIALOG-------------*/
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_ui);
        d.getWindow().setBackgroundDrawableResource(R.color.traspaernt);
        d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout dbg = d.findViewById(R.id.dbg);
        LinearLayout dline = d.findViewById(R.id.dline);
        LinearLayout edit = d.findViewById(R.id.edit);
        LinearLayout cancel = d.findViewById(R.id.cancel);
        EditText dd_edit = d.findViewById(R.id.dd_edit);
        EditText mm_edit = d.findViewById(R.id.mm_edit);
        EditText yy_edit = d.findViewById(R.id.yy_edit);
        TextView donetx = d.findViewById(R.id.donetx);
        TextView canceltx = d.findViewById(R.id.canceltx);
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor sh = sharedPreferences.edit();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dd_edit.getText().length() > 0 && mm_edit.getText().length() > 0 && yy_edit.getText().length() > 0) {
                    if (StringUtils.isNumeric(dd_edit.getText()) && StringUtils.isNumeric(mm_edit.getText()) && StringUtils.isNumeric(yy_edit.getText()))
                    {
                        sh.putString("day", dd_edit.getText().toString());
                        sh.putString("month", mm_edit.getText().toString());
                        sh.putString("year", yy_edit.getText().toString());
                        sh.apply();
                        d.dismiss();
                    } else {
                        showMessage("Just numbers are allowed :)");
                        d.dismiss();
                    }
                } else {
                    showMessage("All fields are required");
                }
            }
        });
        dark_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swtched) {
                    setColorToSystem(black, background, true);
                    background.setBackgroundColor(Color.parseColor(black));
                    viewEffect(line, soft_dark, false);
                    buttonEffect(dark_light, soft_dark, grey, false);
                    buttonEffect(back, soft_dark, grey, false);
                    buttonEffect(set_edit, soft_dark, grey, false);
                    filter(back_icon, white);
                    color(tdark, white);
                    color(tedit, white);
                    color(set, white);
                    // for dialog
                    viewEffect(dbg, black, false);
                    viewEffect(dline, white, false);
                    color(dd_edit, white);
                    color(mm_edit, white);
                    color(yy_edit, white);
                    color(donetx, white);
                    color(canceltx, white);
                    color(set, white);
                    sh.putString("dark", "on").apply();
                    buttonEffect(edit, soft_dark, grey, false);
                    buttonEffect(cancel, soft_dark, grey, false);
                    swtched = false;
                } else {
                    setColorToSystem(white, background, false);
                    background.setBackgroundColor(Color.parseColor(white));
                    viewEffect(line, white, true);
                    buttonEffect(dark_light, white, grey, true);
                    buttonEffect(back, white, grey, true);
                    buttonEffect(set_edit, white, grey, true);
                    filter(back_icon, black);
                    color(tdark, black);
                    color(tedit, black);
                    color(set, black);
                    // for dialog
                    viewEffect(dbg, white, false);
                    viewEffect(dline, soft_dark, false);
                    color(dd_edit, black);
                    color(mm_edit, black);
                    color(yy_edit, black);
                    color(donetx, black);
                    color(canceltx, black);
                    sh.putString("dark", "off").apply();
                    buttonEffect(edit, white, soft_dark, true);
                    buttonEffect(cancel, white, soft_dark, true);
                    swtched = true;
                }
            }
        });
        //
        if (sharedPreferences.getString("dark", null).equals("on")) {
            setColorToSystem(black, background, true);
            background.setBackgroundColor(Color.parseColor(black));
            viewEffect(dline, white, false);
            viewEffect(line, soft_dark, false);
            viewEffect(dbg, black, false);
            buttonEffect(dark_light, soft_dark, grey, false);
            buttonEffect(set_edit, soft_dark, grey, false);
            buttonEffect(back, soft_dark, grey, false);
            filter(back_icon, white);
            color(tdark, white);
            // for dialog
            buttonEffect(edit, soft_dark, grey, false);
            buttonEffect(cancel, soft_dark, grey, false);
            color(tedit, white);
            color(set, white);
            color(dd_edit, white);
            color(mm_edit, white);
            color(yy_edit, white);
            color(donetx, white);
            color(canceltx, white);
            swtched = false;
        } else {
            setColorToSystem(white, background, false);
            background.setBackgroundColor(Color.parseColor(white));
            viewEffect(line, white, true);
            viewEffect(dline, white, true);
            viewEffect(dbg, white, false);
            buttonEffect(dark_light, white, grey, true);
            buttonEffect(back, white, grey, true);
            buttonEffect(set_edit, white, grey, true);
            filter(back_icon, black);
            // for dialog
            buttonEffect(edit, grey, soft_dark, true);
            buttonEffect(cancel, grey, soft_dark, true);
            color(tdark, black);
            color(tedit, black);
            color(set, black);
            color(dd_edit, black);
            color(mm_edit, black);
            color(yy_edit, black);
            color(donetx, black);
            color(canceltx, black);
            swtched = true;
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        set_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = sharedPreferences.getString("day", "");
                String month = sharedPreferences.getString("month", "");
                String year = sharedPreferences.getString("year", "");
                dd_edit.setText(Integer.parseInt(day) < 10 ? "0" + day : day);
                mm_edit.setText(month);
                yy_edit.setText(year);
                setFont(dd_edit);
                setFont(mm_edit);
                setFont(yy_edit);
                setFont(canceltx);
                setFont(donetx);
                yy_edit.setHintTextColor(Color.parseColor("#9E9E9E"));
                dd_edit.setHintTextColor(Color.parseColor("#9E9E9E"));
                mm_edit.setHintTextColor(Color.parseColor("#9E9E9E"));
                d.show();
            }
        });
        setFont(set);
        setFont(tdark);
        setFont(tedit);
    }
}