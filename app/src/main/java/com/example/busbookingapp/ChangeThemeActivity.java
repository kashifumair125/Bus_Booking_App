package com.example.busbookingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ChangeThemeActivity extends BaseActivity {

    private RadioGroup themeRadioGroup;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_theme);

        sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        Button applyThemeButton = findViewById(R.id.applyThemeButton);

        // Set the current theme
        int currentTheme = sharedPreferences.getInt("theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        setRadioButtonForTheme(currentTheme);

        applyThemeButton.setOnClickListener(v -> {
            applySelectedTheme();
            restartApp();
        });
    }

    private void setRadioButtonForTheme(int theme) {
        int radioButtonId;
        switch (theme) {
            case AppCompatDelegate.MODE_NIGHT_NO:
                radioButtonId = R.id.lightTheme;
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                radioButtonId = R.id.darkTheme;
                break;
            default:
                radioButtonId = R.id.systemTheme;
        }
        themeRadioGroup.check(radioButtonId);
    }

    private void applySelectedTheme() {
        int selectedTheme = getSelectedTheme();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme", selectedTheme);
        editor.apply();

        applyTheme();
        Toast.makeText(this, "Theme applied. The app will now restart.", Toast.LENGTH_LONG).show();
    }

    private int getSelectedTheme() {
        int checkedId = themeRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.lightTheme) {
            return AppCompatDelegate.MODE_NIGHT_NO;
        } else if (checkedId == R.id.darkTheme) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }
    }

    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }
}
