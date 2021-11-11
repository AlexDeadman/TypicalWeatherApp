package com.example.typicalweatherapp.ui.weekforecast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.daily.Daily;
import com.example.typicalweatherapp.databinding.ItemWeekForecastBinding;
import com.example.typicalweatherapp.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private ArrayList<Daily> dailies;

    public CardStackAdapter(ArrayList<Daily> dailies) {
        this.dailies = dailies;
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

        // TODO use text placeholders or handle by utils

        binding.textViewWfTemperature.setText(
            Math.round(daily.getTemp().getDay()) + "˚C"
        );
        binding.textViewWfWindSpeed.setText(
            daily.getWindSpeed() + " m/s"
        );
        binding.textViewWfInfoHumidity.setText(
            daily.getHumidity() + "%"
        );
        binding.textViewWfPressure.setText(
            daily.getPressure() + " hPa"
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

        ItemWeekForecastBinding binding;

        public ViewHolder(@NonNull View view) {
            super(view);
            binding = ItemWeekForecastBinding.bind(view);
        }
    }
}
