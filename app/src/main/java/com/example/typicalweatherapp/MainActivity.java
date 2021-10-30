package com.example.typicalweatherapp;

import android.animation.LayoutTransition;
import android.content.Intent;
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

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureBackground();
        configureBottomSheet();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        exitToast = Toast.makeText(
                getApplicationContext(),
                R.string.press_again_to_exit,
                Toast.LENGTH_SHORT
        );
    }

    private void configureBackground() {
        int themeQualifier = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        ImageView background = binding.content.imageViewBackground;

        if (themeQualifier == Configuration.UI_MODE_NIGHT_YES) {
            background.setImageResource(R.drawable.bg_dark);
        } else {
            background.setImageResource(R.drawable.bg_light);
        }
    }

    private void configureBottomSheet() {
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimateParentHierarchy(false);

        LinearLayoutCompat bottomSheetLayout = binding.content.bottomSheet.getRoot();

        bottomSheetLayout.setLayoutTransition(transition);

        BottomSheetBehavior<LinearLayoutCompat> bottomSheetBehavior = BottomSheetBehavior.from(
                bottomSheetLayout
        );

        MaterialButton button = binding.content.bottomSheet.buttonWeatherForecast;
        LinearLayoutCompat infoLayout = binding.content.bottomSheet.layoutInfoCards;

        bottomSheetBehavior.addBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            if (button.getVisibility() != View.GONE) {
                                button.setVisibility(View.INVISIBLE);
                            }
                            button.setVisibility(View.GONE);
                            infoLayout.setVisibility(View.VISIBLE);
                        }

                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            infoLayout.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) { }
                }
        );
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
        if (item.getItemId() == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isOpen()) {
            binding.drawerLayout.close();
        } else {
            // можно добавить закрытие bottom sheet
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        exitToast.cancel();
    }
}