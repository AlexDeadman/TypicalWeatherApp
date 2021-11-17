package com.example.typicalweatherapp.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.ui.BaseActivity;
import com.example.typicalweatherapp.utils.UiUtils;

public class SettingsActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.units_settings, SettingsFragment.newInstance(R.xml.units_preferences))
                .replace(R.id.general_settings, SettingsFragment.newInstance(R.xml.general_preferences))
                .commit();
        }

        initActionBar(getSupportActionBar(), getString(R.string.settings));

        App.getPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
        if (key.equals("theme")) {
            UiUtils.updateTheme();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        public static SettingsFragment newInstance(int preferenceScreen) {
            SettingsFragment settingsFragment = new SettingsFragment();

            Bundle args = new Bundle();
            args.putInt("preferenceScreen", preferenceScreen);
            settingsFragment.setArguments(args);

            return settingsFragment;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            if (getArguments() != null) {
                setPreferencesFromResource(getArguments().getInt("preferenceScreen"), rootKey);
            }
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            getListView().setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }
}
