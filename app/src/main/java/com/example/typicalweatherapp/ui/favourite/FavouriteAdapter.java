package com.example.typicalweatherapp.ui.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.databinding.ItemFavouriteBinding;
import com.example.typicalweatherapp.utils.UiUtils;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final List<FavouriteCity> favouriteCities;
    private final OnFavouriteClickListener onFavouriteClickListener;
    private final OnRemoveFavouriteClickListener onRemoveFavouriteClickListener;

    public FavouriteAdapter(
        List<FavouriteCity> favouriteCities,
        OnFavouriteClickListener onFavouriteClickListener, OnRemoveFavouriteClickListener onRemoveFavouriteClickListener
    ) {
        this.favouriteCities = favouriteCities;
        this.onFavouriteClickListener = onFavouriteClickListener;
        this.onRemoveFavouriteClickListener = onRemoveFavouriteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FavouriteAdapter.ViewHolder(
            inflater.inflate(R.layout.item_favourite, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavouriteCity favouriteCity = favouriteCities.get(position);
        ItemFavouriteBinding binding = holder.binding;

        binding.textViewCity.setText(UiUtils.formatCityText(favouriteCity));

        binding.getRoot().setOnClickListener(
            v -> onFavouriteClickListener.onFavouriteClickListener(position)
        );

        binding.cardRemoveFavourite.setOnClickListener(
            v -> onRemoveFavouriteClickListener.onRemoveFavouriteClick(favouriteCity, this)
        );
    }

    @Override
    public int getItemCount() {
        return favouriteCities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemFavouriteBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemFavouriteBinding.bind(view);
        }
    }

    interface OnFavouriteClickListener {
        void onFavouriteClickListener(int position);
    }

    interface OnRemoveFavouriteClickListener {
        void onRemoveFavouriteClick(FavouriteCity favourite, FavouriteAdapter adapter);
    }
}
