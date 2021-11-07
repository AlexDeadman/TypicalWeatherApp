package com.example.typicalweatherapp.ui.about;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.utils.UiUtils;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        UiUtils.initActionBar(getSupportActionBar(), getString(R.string.about));
    }
}
