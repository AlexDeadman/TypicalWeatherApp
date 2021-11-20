package com.example.typicalweatherapp.ui.favourite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.byid.AlternateName;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.byid.Favourites;
import com.example.typicalweatherapp.databinding.ItemFavouriteBinding;
import com.example.typicalweatherapp.ui.addcity.CitiesAdapter;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private final Favourites favourites;
    private final OnFavouriteClickListener onClickListener;

    public FavouriteAdapter(
        Favourites favourites,
        OnFavouriteClickListener onClickListener
    ) {
        this.favourites = favourites;
        this.onClickListener = onClickListener;
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
        FavouriteCity favouriteCity = favourites.getFavouriteCities().get(position);
        ItemFavouriteBinding binding = holder.binding;

        String textCity = "";
        for (AlternateName altName : favouriteCity.getAlternateNames()) {
            String lang = altName.getLang();
            if (lang != null && lang.equals("en")) { // TODO Language hardcoded
                textCity = altName.getName();
            }
        }
        if (textCity.isEmpty()) {
            textCity = favouriteCity.getToponymName();
        }
        binding.textViewCity.setText(textCity);

        binding.cardRemoveFavourite.setOnClickListener(
            v -> onClickListener.onFavouriteClick(favouriteCity, position, this)
        );
    }

    @Override
    public int getItemCount() {
        return favourites.getFavouriteCities().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemFavouriteBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemFavouriteBinding.bind(view);
        }
    }

    interface OnFavouriteClickListener {
        void onFavouriteClick(FavouriteCity favourite, int position, FavouriteAdapter adapter);
    }
}
