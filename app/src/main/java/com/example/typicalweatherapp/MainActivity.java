package com.example.typicalweatherapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.typicalweatherapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private boolean backPressedOnce = false;
    private Toast exitToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        binding.appBarMain.toolbarTitle.setText("Saint-Petersburg"); // TEMPO

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        exitToast = Toast.makeText(
                getApplicationContext(),
                R.string.press_again_to_exit,
                Toast.LENGTH_SHORT
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