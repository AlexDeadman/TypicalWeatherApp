package com.example.typicalweatherapp.ui.weekforecast;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.weather.daily.Daily;
import com.example.typicalweatherapp.databinding.ActivityWeekForecastBinding;
import com.example.typicalweatherapp.ui.BaseActivity;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;

import java.util.ArrayList;

public class WeekForecastActivity extends BaseActivity implements CardStackListener {

    private CardStackAdapter adapter;
    private CardStackLayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityWeekForecastBinding binding = ActivityWeekForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActionBar(getSupportActionBar(), getString(R.string.week_forecast));

        ArrayList<Daily> dailies = fetchDailies();

        if (dailies != null) {
            adapter = new CardStackAdapter(dailies);
            CardStackView cardStackView = binding.cardStackView;

            manager = new CardStackLayoutManager(this, this);
            manager.setDirections(Direction.HORIZONTAL);
            manager.setSwipeThreshold(0.1f);
            manager.setCanScrollHorizontal(true);
            manager.setCanScrollVertical(false);
            manager.setMaxDegree(30.0f);

            cardStackView.setLayoutManager(manager);
            cardStackView.setAdapter(adapter);
            cardStackView.setItemAnimator(null);

            binding.buttonPrevious.setOnClickListener(v -> {
                RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Top)
                    .setDuration(Duration.Normal.duration)
                    .build();
                manager.setRewindAnimationSetting(setting);
                cardStackView.rewind();
            });
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (manager.getTopPosition() == adapter.getItemCount() - 1) {
            paginate();
        }
    }

    @Override
    public void onCardRewound() {
    }

    @Override
    public void onCardCanceled() {
    }

    @Override
    public void onCardAppeared(View view, int position) {
    }

    @Override
    public void onCardDisappeared(View view, int position) {
    }

    private ArrayList<Daily> fetchDailies() {
        return getIntent().getParcelableArrayListExtra("dailies");
    }

    private void paginate() {
        ArrayList<Daily> oldDailies = adapter.getDailies();

        ArrayList<Daily> newDailies = new ArrayList<>(oldDailies);
        newDailies.addAll(fetchDailies());

        DailiesDiffCallback callback = new DailiesDiffCallback(oldDailies, newDailies);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        adapter.setDailies(newDailies);
        result.dispatchUpdatesTo(adapter);
    }
}
