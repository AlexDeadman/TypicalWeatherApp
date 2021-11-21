package com.example.typicalweatherapp.ui.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.repository.FavouritesRepository;
import com.example.typicalweatherapp.databinding.ActivityFavouriteBinding;
import com.example.typicalweatherapp.ui.BaseActivity;

import java.util.List;

import javax.inject.Inject;

public class FavouriteActivity extends BaseActivity {

    private ActivityFavouriteBinding binding;

    @Inject // TODO fix MVVM violation
    public FavouritesRepository favouritesRepository;
    private List<FavouriteCity> favouriteCities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar(getSupportActionBar(), getString(R.string.favourite));

        App.getAppComponent().inject(this);
        favouriteCities = favouritesRepository.getFavouritesCities();

        checkFavouritesCount();

        FavouriteAdapter.OnFavouriteClickListener onFavouriteClickListener =
            position -> favouritesRepository.setCurrentCity(position);

        @SuppressLint("NotifyDataSetChanged")
        FavouriteAdapter.OnRemoveFavouriteClickListener onRemoveFavouriteClickListener =
            (favourite, adapter) -> {
                favouriteCities.remove(favourite);
                adapter.notifyDataSetChanged();
                checkFavouritesCount();
            };

        FavouriteAdapter adapter = new FavouriteAdapter(favouriteCities, onFavouriteClickListener, onRemoveFavouriteClickListener);
        binding.recyclerViewFavourites.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        favouritesRepository.saveFavourites();
        super.onStop();
    }

    private void checkFavouritesCount() {
        if (favouriteCities.isEmpty()) {
            binding.textViewEmpty.setVisibility(View.VISIBLE);
        }
    }
}
