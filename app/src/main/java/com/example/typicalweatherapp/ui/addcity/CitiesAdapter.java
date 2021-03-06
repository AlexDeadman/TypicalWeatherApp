package com.example.typicalweatherapp.ui.addcity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.search.Geoname;
import com.example.typicalweatherapp.databinding.ItemCityBinding;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private ArrayList<Geoname> geonames;
    private final OnCityClickListener onClickListener;

    public CitiesAdapter(
        ArrayList<Geoname> geonames,
        OnCityClickListener onClickListener
    ) {
        this.geonames = geonames;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CitiesAdapter.ViewHolder(
            inflater.inflate(R.layout.item_city, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Geoname geoname = geonames.get(position);
        ItemCityBinding binding = holder.binding;

        binding.textViewCity.setText(geoname.getName());

        String cityDetails = "";
        if (geoname.getCountryName() != null) { // may be not received from API
            cityDetails += geoname.getCountryName();
            if (!geoname.getAdminName1().isEmpty()) { // receives always but can be empty
                cityDetails += ", " + geoname.getAdminName1();
            }
        }
        binding.textViewCityDetails.setText(cityDetails);

        // TODO mark cities that already favourite
        // TODO fix view reuse (mb replace icons with styled checkboxes)

        LinearLayoutCompat root = binding.getRoot();
        root.setOnClickListener(v -> {
            onClickListener.onCityClick(geoname);
            binding.imageViewStar.setImageDrawable(
                AppCompatResources.getDrawable(
                    holder.itemView.getContext(),
                    R.drawable.ic_baseline_star
                )
            );
            root.setClickable(false);
        });
    }

    @Override
    public int getItemCount() {
        return geonames.size();
    }

    public void setGeonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemCityBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemCityBinding.bind(view);
        }
    }

    interface OnCityClickListener {
        void onCityClick(Geoname geoname);
    }
}
