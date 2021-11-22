package com.example.typicalweatherapp.ui.favourite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.databinding.ActivityFavouriteBinding;
import com.example.typicalweatherapp.ui.BaseActivity;

import java.util.List;

public class FavouriteActivity extends BaseActivity {

    private ActivityFavouriteBinding binding;

    private FavouriteViewModel viewModel;
    private List<FavouriteCity> favouriteCities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar(getSupportActionBar(), getString(R.string.favourite));

        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);

        FavouriteAdapter.OnFavouriteClickListener onFavouriteClickListener =
            position -> {
                viewModel.setCurrentCity(position);
                finish();
            };

        favouriteCities = viewModel.getFavouriteCities();
        checkFavouritesCount();

        @SuppressLint("NotifyDataSetChanged")
        FavouriteAdapter.OnRemoveFavouriteClickListener onRemoveFavouriteClickListener =
            (favourite, adapter) -> {
                favouriteCities.remove(favourite);
                adapter.notifyDataSetChanged();
                checkFavouritesCount();
            };

        FavouriteAdapter adapter = new FavouriteAdapter(
            favouriteCities,
            onFavouriteClickListener,
            onRemoveFavouriteClickListener
        );
        binding.recyclerViewFavourites.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        viewModel.saveFavourites();
        super.onStop();
    }

    private void checkFavouritesCount() {
        if (favouriteCities.isEmpty()) {
            binding.textViewEmpty.setVisibility(View.VISIBLE);
            viewModel.setCurrentCity(-1);
        }
    }
}
