package com.example.typicalweatherapp.ui.addcity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.ui.BaseActivity;

public class AddCityActivity extends BaseActivity {

    private AddCityViewModel viewModel;
    private boolean loadError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        initActionBar(getSupportActionBar(), "");

        viewModel = new ViewModelProvider(this).get(AddCityViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setQuery(query);
                updateCities();
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

    void updateCities() {
        viewModel.getLoadError().observe(this, value -> loadError = value);

        if (!loadError) {
            viewModel.getGeonames().observe(
                this,
                geonames -> {
                    String geonameId = geonames.getGeonames().get(0).getGeonameId().toString();
                    Toast.makeText(this, geonameId, Toast.LENGTH_SHORT).show();
                }
            );
        } else {
            Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }
}
