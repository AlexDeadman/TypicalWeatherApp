package com.example.typicalweatherapp.ui.addcity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.databinding.ActivityAddCityBinding;
import com.example.typicalweatherapp.ui.BaseActivity;

import java.util.ArrayList;

public class AddCityActivity extends BaseActivity {

    private ActivityAddCityBinding binding;
    private AddCityViewModel viewModel;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar(getSupportActionBar(), "");

        viewModel = new ViewModelProvider(this).get(AddCityViewModel.class);

        viewModel.getLoadError().observe(this, value -> {
            if (value) {
                Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

        CitiesAdapter adapter = new CitiesAdapter(new ArrayList<>());
        binding.recyclerViewCities.setAdapter(adapter);

        viewModel.getGeonames().observe(
            this,
            geonames -> {
                adapter.setGeonames(geonames.getGeonames());
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                if (geonames.getGeonames().size() == 0) {
                    binding.textViewNotFound.setVisibility(View.VISIBLE);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.textViewNotFound.setVisibility(View.GONE);
                viewModel.fetchGeonames(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        menu.performIdentifierAction(R.id.action_search, 0);

        return true;
    }
}
