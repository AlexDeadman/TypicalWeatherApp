package com.example.typicalweatherapp.ui.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.Favourites;
import com.example.typicalweatherapp.databinding.ActivityFavouriteBinding;
import com.example.typicalweatherapp.ui.BaseActivity;

import javax.inject.Inject;

public class FavouriteActivity extends BaseActivity {

    private ActivityFavouriteBinding binding;

    @Inject
    public Favourites favourites;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar(getSupportActionBar(), getString(R.string.favourite));

        App.getAppComponent().inject(this);

        checkFavouritesCount();

        @SuppressLint("NotifyDataSetChanged")
        FavouriteAdapter.OnFavouriteClickListener favouriteClickListener =
            (favourite, position, adapter) -> {
                favourites.getFavouriteCities().remove(favourite);
                adapter.notifyDataSetChanged();
                checkFavouritesCount();
            };

        FavouriteAdapter adapter = new FavouriteAdapter(favourites, favouriteClickListener);
        binding.recyclerViewFavourites.setAdapter(adapter);
    }

    private void checkFavouritesCount() {
        if (favourites.getFavouriteCities().isEmpty()) {
            binding.textViewEmpty.setVisibility(View.VISIBLE);
        }
    }
}
