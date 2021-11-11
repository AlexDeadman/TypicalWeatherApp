package com.example.typicalweatherapp.ui.weekforecast;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.animation.DecelerateInterpolator;
import androidx.recyclerview.widget.DefaultItemAnimator;
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

public class WeekForecastActivity extends AppCompatActivity implements CardStackListener {

    private ActivityWeekForecastBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWeekForecastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UiUtils.initActionBar(getSupportActionBar(), getString(R.string.week_forecast));

        ArrayList<Daily> dailies = getIntent().getParcelableArrayListExtra("dailies");

        if (dailies != null) {
            CardStackAdapter adapter = new CardStackAdapter(dailies);
            CardStackView cardStackView = binding.cardStackView;

            CardStackLayoutManager manager = new CardStackLayoutManager(this, this);
            manager.setDirections(Direction.HORIZONTAL);
            manager.setCanScrollHorizontal(true);
            manager.setCanScrollVertical(false);
            manager.setMaxDegree(30.0f);

            cardStackView.setLayoutManager(manager);
            cardStackView.setAdapter(adapter);

            RecyclerView.ItemAnimator itemAnimator = cardStackView.getItemAnimator();
            if (itemAnimator instanceof DefaultItemAnimator) {
                ((DefaultItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
            }

            binding.buttonPrevious.setOnClickListener(v -> {
                RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                    .setDirection(Direction.Top)
                    .setDuration(Duration.Normal.duration)
                    .build();
                manager.setRewindAnimationSetting(setting);
                cardStackView.rewind();
            });

            // TODO pagination (loop cards)
        }
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {
    }

    @Override
    public void onCardSwiped(Direction direction) {
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
}
