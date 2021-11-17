package com.example.typicalweatherapp.ui.addcity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.geo.search.Geoname;
import com.example.typicalweatherapp.databinding.ItemCityBinding;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private ArrayList<Geoname> geonames;

    public CitiesAdapter(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
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

        String adminName = "";
        if (geoname.getAdminName1().length() != 0) {
            adminName = ", " + geoname.getAdminName1();
        }
        binding.textViewCityDetails.setText(
            geoname.getCountryName() + adminName
        );
    }

    public ArrayList<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<Geoname> geonames) {
        this.geonames = geonames;
    }

    @Override
    public int getItemCount() {
        return geonames.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemCityBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemCityBinding.bind(view);
        }
    }
}
