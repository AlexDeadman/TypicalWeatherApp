package com.example.typicalweatherapp.ui.weekforecast;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.daily.Daily;
import com.example.typicalweatherapp.databinding.ItemWeekForecastBinding;
import com.example.typicalweatherapp.utils.UiUtils;
import com.example.typicalweatherapp.utils.UnitsFormatter;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private ArrayList<Daily> dailies;
    private final UnitsFormatter formatter;

    public CardStackAdapter(ArrayList<Daily> dailies) {
        this.dailies = dailies;
        this.formatter = new UnitsFormatter();
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
            UiUtils.formatDate(daily.getDt())
        );
        binding.imageViewDailyWeather.setImageDrawable(
            UiUtils.getWeatherDrawable(
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
