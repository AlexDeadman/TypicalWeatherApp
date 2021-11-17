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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.databinding.BottomSheetMainBinding;
import com.example.typicalweatherapp.ui.about.AboutActivity;
import com.example.typicalweatherapp.ui.addcity.AddCityActivity;
import com.example.typicalweatherapp.ui.settings.SettingsActivity;
import com.example.typicalweatherapp.ui.weekforecast.WeekForecastActivity;
import com.example.typicalweatherapp.utils.UiUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity
    implements
    NavigationView.OnNavigationItemSelectedListener,
    SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityMainBinding binding;

    private BottomSheetMainBinding bottomSheetBinding;
    private BottomSheetBehavior<LinearLayoutCompat> bottomSheetBehavior;
    private MaterialButton weekForecastButton;

    private MainViewModel viewModel;
    private MainUiUpdater uiUpdater;
    private boolean loadError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBottomSheet();

        App.getPreferences().registerOnSharedPreferenceChangeListener(this);

        UiUtils.updateTheme();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        updateWeather();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        binding.content.buttonMenu.setOnClickListener(v -> binding.drawerLayout.open());
        binding.content.buttonAddCity.setOnClickListener(v ->
            startActivity(new Intent(this, AddCityActivity.class))
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        if (itemId == R.id.nav_favourite) {
            // TODO
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
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.drawerLayout.close();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (uiUpdater != null) {
            if (key.equals("units_temp")) {
                uiUpdater.updateMainInfo();
                uiUpdater.updateWeatherCards();
                uiUpdater.updateDayTempCard();
            }

            if (key.equals("units_speed")) {
                uiUpdater.updateDayWindSpeed();
            }

            if (key.equals("units_pressure")) {
                uiUpdater.updateDayPressure();
            }
        }
    }

    private void initBottomSheet() {
        bottomSheetBinding = binding.content.bottomSheet;
        weekForecastButton = bottomSheetBinding.buttonWeekForecast;

        LinearLayoutCompat bottomSheetLayout = bottomSheetBinding.getRoot();

        LayoutTransition transition = new LayoutTransition();
        transition.setAnimateParentHierarchy(false);
        bottomSheetLayout.setLayoutTransition(transition);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        LinearLayoutCompat dayWeatherLayout = bottomSheetBinding.layoutDayWeatherCards;

        bottomSheetBehavior.addBottomSheetCallback(
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (!loadError) {
                        if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                            if (weekForecastButton.getVisibility() != View.GONE) {
                                weekForecastButton.setVisibility(View.INVISIBLE);
                            }
                            weekForecastButton.setVisibility(View.GONE);
                            dayWeatherLayout.setVisibility(View.VISIBLE);
                        }
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            dayWeatherLayout.setVisibility(View.INVISIBLE);
                            weekForecastButton.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            }
        );

        weekForecastButton.setOnClickListener(v -> {
            if (!loadError) {
                viewModel.getWeather().observe(
                    this,
                    weather -> {
                        Intent intent = new Intent(this, WeekForecastActivity.class);
                        intent.putExtra("dailies", weather.getDaily());
                        startActivity(intent);
                    }
                );
            }
        });
    }

    void updateWeather() {
        viewModel.getLoadError().observe(this, value -> loadError = value);

        if (!loadError) {
            viewModel.getWeather().observe(
                this,
                weather -> {
                    weekForecastButton.setVisibility(View.VISIBLE);

                    uiUpdater = new MainUiUpdater(
                        this,
                        binding,
                        getLayoutInflater(),
                        weather
                    );

                    uiUpdater.updateAll();
                }
            );
        } else {
            bottomSheetBinding.progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }
}