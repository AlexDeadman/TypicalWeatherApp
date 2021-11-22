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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

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

        CitiesAdapter.OnCityClickListener cityClickListener =
            geoname -> viewModel.fetchCity(geoname.getGeonameId());

        CitiesAdapter adapter = new CitiesAdapter(new ArrayList<>(), cityClickListener);
        RecyclerView recyclerView = binding.recyclerViewCities;
        recyclerView.addItemDecoration(
            new DividerItemDecoration(this, RecyclerView.VERTICAL)
        );
        recyclerView.setAdapter(adapter);

        viewModel.getCities().observe(
            this,
            cities -> {
                adapter.setGeonames(cities.getGeonames());
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                if (cities.getGeonames().size() == 0) {
                    binding.textViewNotFound.setVisibility(View.VISIBLE);
                }
            }
        );
    }

    @Override
    protected void onStop() {
        viewModel.saveFavourites();
        super.onStop();
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
                viewModel.fetchCities(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                onBackPressed();
            }
        });

        menu.performIdentifierAction(R.id.action_search, 0);

        return true;
    }
}
