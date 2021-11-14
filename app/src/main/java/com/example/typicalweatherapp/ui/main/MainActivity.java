package com.example.typicalweatherapp.ui.main;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.ui.about.AboutActivity;
import com.example.typicalweatherapp.ui.settings.SettingsActivity;
import com.example.typicalweatherapp.ui.weekforecast.WeekForecastActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity
    implements
    NavigationView.OnNavigationItemSelectedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private MainUiUpdater uiUpdater;

    private SharedPreferences preferences;
    private boolean loadError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context appContext = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        preferences.registerOnSharedPreferenceChangeListener(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        updateWeather();

        initBottomSheet();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        binding.content.buttonMenu.setOnClickListener(v -> binding.drawerLayout.open());
        binding.content.buttonAddCity.setOnClickListener(v -> { /* TODO */ });

        // Temp
        binding.content.toggleButton.setToggled(R.id.toggle_c, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.drawerLayout.close();
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
            // TODO bottom sheet closing
            super.onBackPressed();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (uiUpdater != null) {
            switch (key) {
                case ("units_temp"):
                    uiUpdater.updateMainInfo();
                    uiUpdater.updateWeatherCards();
                    uiUpdater.updateDayTempCard();
                case ("units_speed"):
                    uiUpdater.updateDayWindSpeed();
                case ("units_pressure"):
                    uiUpdater.updateDayPressure();
            }
        }
    }

    void updateWeather() {
        viewModel.getLoadError().observe(this, value -> {
            loadError = value;
            if (value) {
                binding.content.bottomSheet.progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(
                    this,
                    R.string.network_error,
                    Toast.LENGTH_SHORT
                ).show();
            }
        });

        if (!loadError) {
            viewModel.getWeather().observe(
                this,
                weather -> {
                    binding.content.bottomSheet.buttonWeekForecast.setVisibility(View.VISIBLE);

                    uiUpdater = new MainUiUpdater(
                        this,
                        binding,
                        preferences,
                        getLayoutInflater(),
                        weather
                    );

                    uiUpdater.updateAll();
                }
            );
        }
    }

    private void initBottomSheet() {
        LinearLayoutCompat bottomSheetLayout = binding.content.bottomSheet.getRoot();

        LayoutTransition transition = new LayoutTransition();
        transition.setAnimateParentHierarchy(false);
        bottomSheetLayout.setLayoutTransition(transition);

        BottomSheetBehavior<LinearLayoutCompat> bottomSheetBehavior = BottomSheetBehavior.from(
            bottomSheetLayout
        );

        MaterialButton button = binding.content.bottomSheet.buttonWeekForecast;
        LinearLayoutCompat infoLayout = binding.content.bottomSheet.layoutInfoCards;

        bottomSheetBehavior.addBottomSheetCallback(
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (!loadError) {
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
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            }
        );

        binding.content.bottomSheet.buttonWeekForecast.setOnClickListener(v -> {
            if (!loadError) {
                viewModel.getWeather().observe(
                    this,
                    w -> {
                        Intent intent = new Intent(this, WeekForecastActivity.class);
                        intent.putExtra("dailies", w.getDaily());
                        startActivity(intent);
                    }
                );
            }
        });
    }
}