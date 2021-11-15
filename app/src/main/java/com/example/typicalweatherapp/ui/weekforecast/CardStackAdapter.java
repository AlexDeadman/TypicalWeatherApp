package com.example.typicalweatherapp.ui.weekforecast;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.daily.Daily;
import com.example.typicalweatherapp.databinding.ItemWeekForecastBinding;
import com.example.typicalweatherapp.utils.UiFormatter;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private ArrayList<Daily> dailies;
    private final UiFormatter formatter;

    public CardStackAdapter(
        ArrayList<Daily> dailies,
        SharedPreferences preferences
    ) {
        this.dailies = dailies;
        this.formatter = new UiFormatter(preferences);

        formatter.updateAllUnits();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(
            inflater.inflate(R.layout.item_week_forecast, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Daily daily = dailies.get(position);
        ItemWeekForecastBinding binding = holder.binding;

        binding.textViewDailyDate.setText(
            formatter.formatDate(daily.getDt())
        );
        binding.imageViewDailyWeather.setImageDrawable(
            formatter.getWeatherDrawable(
                holder.itemView.getContext(),
                daily.getWeather().get(0)
            )
        );

        Resources res = holder.itemView.getResources();

        binding.textViewWfTemperature.setText(
            res.getString(
                formatter.getTempPlaceholderId(),
                formatter.formatTemp(daily.getTemp().getDay()))
        );
        binding.textViewWfWindSpeed.setText(
            res.getString(
                formatter.getSpeedPlaceholderId(),
                formatter.formatSpeed(daily.getWindSpeed())
            )
        );
        binding.textViewWfInfoHumidity.setText(
            res.getString(
                R.string.humidity_p,
                daily.getHumidity()
            )
        );
        binding.textViewWfPressure.setText(
            res.getString(
                formatter.getPressurePlaceholderId(),
                formatter.formatPressure(daily.getPressure())
            )
        );
    }

    @Override
    public int getItemCount() {
        return dailies.size();
    }

    public ArrayList<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(ArrayList<Daily> dailies) {
        this.dailies = dailies;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemWeekForecastBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemWeekForecastBinding.bind(view);
        }
    }
}
