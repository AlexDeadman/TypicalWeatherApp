package com.example.typicalweatherapp.ui.weekforecast;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.animation.DecelerateInterpolator;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.typicalweatherapp.R;
import com.example.typicalweatherapp.data.model.daily.Daily;
import com.example.typicalweatherapp.databinding.ActivityWeekForecastBinding;
import com.example.typicalweatherapp.utils.UiUtils;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WeekForecastActivity extends AppCompatActivity implements CardStackListener {

    private ActivityWeekForecastBinding binding;

    ArrayList<Daily> dailies;

    CardStackAdapter adapter;
    CardStackLayoutManager manager;
    CardStackView cardStackView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWeekForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UiUtils.initActionBar(getSupportActionBar(), getString(R.string.week_forecast));

        dailies = fetchDailies();

        if (dailies != null) {
            adapter = new CardStackAdapter(dailies);
            cardStackView = binding.cardStackView;

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
