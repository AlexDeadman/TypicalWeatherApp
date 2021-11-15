package com.example.typicalweatherapp.ui.weekforecast;

import androidx.recyclerview.widget.DiffUtil;

import com.example.typicalweatherapp.data.model.weather.daily.Daily;

import java.util.ArrayList;

public class DailiesDiffCallback extends DiffUtil.Callback {

    private final ArrayList<Daily> oldDailies;
    private final ArrayList<Daily> newDailies;

    public DailiesDiffCallback(
        ArrayList<Daily> oldDailies,
        ArrayList<Daily> newDailies
    ) {
        this.oldDailies = oldDailies;
        this.newDailies = newDailies;
    }

    @Override
    public int getOldListSize() {
        return oldDailies.size();
    }

    @Override
    public int getNewListSize() {
        return newDailies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        int oldDt = oldDailies.get(oldItemPosition).getDt();
        int newDt = newDailies.get(newItemPosition).getDt();
        return oldDt == newDt;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
