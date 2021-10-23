package com.example.typicalweatherapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private boolean backPressedOnce = false;
    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.content.appbar.toolbar);

        configureActionBar();
        configureBackground();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        exitToast = Toast.makeText(
                getApplicationContext(),
                R.string.press_again_to_exit,
                Toast.LENGTH_SHORT
        );

        // TEMPO
        BottomSheetBehavior<LinearLayoutCompat> bottomSheet = BottomSheetBehavior.from(
                binding.content.bottomSheet.getRoot()
        );

        bottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    MaterialButton button = binding.content.bottomSheet.buttonWeatherForecast;
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        button.setVisibility(View.GONE);
                    }
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED){
                        button.setVisibility(View.VISIBLE);
                    }
//                    TODO Анимации (опционально)
//                    button.animate().scaleX(0).scaleY(0).setDuration(300).start();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) { }
            }
        );

    }

    private void configureActionBar() {
        setSupportActionBar(binding.content.appbar.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void configureBackground() {
        int themeQualifier =
                getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        ImageView background = binding.content.imageViewBackground;

        if (themeQualifier == Configuration.UI_MODE_NIGHT_YES) {
            background.setImageResource(R.drawable.bg_dark);
        } else {
            background.setImageResource(R.drawable.bg_light);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            binding.drawerLayout.open();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        TODO
        return false;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isOpen()) {
            binding.drawerLayout.close();
        } else {
            if (backPressedOnce) {
                super.onBackPressed();
            } else {
                backPressedOnce = true;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        backPressedOnce = false;
                    }
                }, 2000);
                exitToast.show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        exitToast.cancel();
    }
}