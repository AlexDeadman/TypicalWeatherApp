package com.example.typicalweatherapp.ui.main;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import com.example.typicalweatherapp.data.model.daily.Daily;
import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.example.typicalweatherapp.databinding.BottomSheetMainBinding;
import com.example.typicalweatherapp.databinding.CardWeatherBinding;
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

    MainViewModel viewModel;

    private boolean loadError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initBackground();
        initBottomSheet();

        updateWeather();

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        binding.content.buttonMenu.setOnClickListener(v -> binding.drawerLayout.open());
        binding.content.buttonAddCity.setOnClickListener(v -> { /* TODO */ });

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

        bottomSheetLayout.setVisibility(View.INVISIBLE);

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

    void updateWeather() {
        viewModel.fetchWeather();

        viewModel.getLoadError().observe(this, v -> loadError = v.booleanValue());

        if (!loadError) {
            viewModel.getWeather().observe(
                this,
                v -> {
                    binding.content.textViewTemperature.setText(
                        Math.round(v.getCurrent().getTemp()) + "˚C"
                    );

                    // TODO date

                    BottomSheetMainBinding bottomSheet = binding.content.bottomSheet;

                    //  generating and filling weather cards

                    for (int i = 0; i < 4; i++) {
                        LinearLayoutCompat weatherCards = bottomSheet.layoutWeatherCards;
                        View cardView = getLayoutInflater().inflate(
                            R.layout.card_weather,
                            weatherCards,
                            false
                        );

                        CardWeatherBinding cardBinding = CardWeatherBinding.bind(cardView);
                        cardBinding.textViewTime.setText(i * 6 + ":00");
                        cardBinding.textViewDegrees.setText(
                            Math.round(v.getHourly().get(i * 6).getTemp()) + "˚C"
                        );

                        weatherCards.addView(cardView);
                    }

                    //  filling info cards

                    Daily today = v.getDaily().get(0);

                    // 1
                    bottomSheet.cardInfo1.imageViewCardInfo.setImageDrawable(
                        AppCompatResources.getDrawable(this, R.drawable.ic_thermometer)
                    );
                    bottomSheet.cardInfo1.textViewCardInfo.setText(
                        Math.round(today.getTemp().getDay()) + "˚C"
                    );

                    // 2
                    bottomSheet.cardInfo2.imageViewCardInfo.setImageDrawable(
                        AppCompatResources.getDrawable(this, R.drawable.ic_humidity)
                    );
                    bottomSheet.cardInfo2.textViewCardInfo.setText(
                        today.getHumidity() + "%"
                    );

                    // 3
                    bottomSheet.cardInfo3.imageViewCardInfo.setImageDrawable(
                        AppCompatResources.getDrawable(this, R.drawable.ic_breeze)
                    );
                    bottomSheet.cardInfo3.textViewCardInfo.setText(
                        today.getWindSpeed() + " m/s"
                    );

                    // 4
                    bottomSheet.cardInfo4.imageViewCardInfo.setImageDrawable(
                        AppCompatResources.getDrawable(this, R.drawable.ic_barometer)
                    );
                    bottomSheet.cardInfo4.textViewCardInfo.setText(
                        today.getPressure() + " hPa"
                    );
                }
            );
        }
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
}