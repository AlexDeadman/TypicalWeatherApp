package com.example.typicalweatherapp.ui.main;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.Weather;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.ui.about.AboutActivity;
import com.example.typicalweatherapp.ui.settings.SettingsActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBackground();
        initBottomSheet();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        binding.content.buttonMenu.setOnClickListener(v -> binding.drawerLayout.open());
        binding.content.buttonAddCity.setOnClickListener(v -> { /* TODO */ });

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getLoading().observe(this, v -> Log.d(TAG, "Loading: " + v));
        viewModel.getLoadError().observe(this, v -> Log.d(TAG, "Error: " + v));
        viewModel.getWeather().observe(this, v -> Log.d(TAG, "Weather: " + v));

        // Temp
        binding.content.toggleButton.setToggled(R.id.toggle_c, true);
    }

    private void initBackground() {
        int themeQualifier = getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;

        CoordinatorLayout content = binding.content.getRoot();

        if (themeQualifier == Configuration.UI_MODE_NIGHT_YES) {
            content.setBackground(AppCompatResources.getDrawable(this, R.drawable.bg_dark));
        } else {
            content.setBackground(AppCompatResources.getDrawable(this, R.drawable.bg_light));
        }
    }

    private void initBottomSheet() {
        LayoutTransition transition = new LayoutTransition();
        transition.setAnimateParentHierarchy(false);

        LinearLayoutCompat bottomSheetLayout = binding.content.bottomSheet.getRoot();

        bottomSheetLayout.setLayoutTransition(transition);

        BottomSheetBehavior<LinearLayoutCompat> bottomSheetBehavior =
                BottomSheetBehavior.from(bottomSheetLayout);

        MaterialButton button = binding.content.bottomSheet.buttonWeekForecast;
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
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                }
        );

        binding.content.bottomSheet.buttonWeekForecast.setOnClickListener(v -> { /* TODO */ });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        if (itemId == R.id.nav_favourite) {
//            TODO
        }
        if (itemId == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isOpen()) {
            drawer.close();
        } else {
            // TODO закрытие bottom sheet
            super.onBackPressed();
        }
    }
}