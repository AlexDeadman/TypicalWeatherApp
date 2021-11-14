package com.example.typicalweatherapp.ui.about;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.ui.BaseActivity;

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initActionBar(getSupportActionBar(), getString(R.string.about));
    }
}
