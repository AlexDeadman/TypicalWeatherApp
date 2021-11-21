package com.example.typicalweatherapp.ui.addcity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.repository.FavouritesRepository;
import com.example.typicalweatherapp.databinding.ActivityAddCityBinding;
import com.example.typicalweatherapp.ui.BaseActivity;
import com.example.typicalweatherapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AddCityActivity extends BaseActivity {

    private static final String TAG = "AddCityActivity";

    private ActivityAddCityBinding binding;
    private AddCityViewModel viewModel;

    @Inject // TODO fix MVVM violation
    public FavouritesRepository favouritesRepository;
    private List<FavouriteCity> favouriteCities;

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

        App.getAppComponent().inject(this);
        favouriteCities = favouritesRepository.getFavouritesCities();

        viewModel.getFavouriteCity().observe(
            this,
            favouriteCity -> {
                boolean exist = false;
                for (FavouriteCity favCity : favouriteCities) {
                    if (favCity.equals(favouriteCity)) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    favouriteCities.add(favouriteCity);
                    if (favouriteCities.size() == 1) {
                        SharedPreferences.Editor editor = App.getPreferences().edit();
                        editor.putInt(Constants.CURRENT_CITY_INDEX_PREF_KEY, 0);
                        editor.apply();
                    }
                }
            }
        );

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
        favouritesRepository.saveFavourites();
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
