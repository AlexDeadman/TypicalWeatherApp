package com.example.typicalweatherapp.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.weather.Weather;
import com.example.typicalweatherapp.data.repository.WeatherRepository;
import com.example.typicalweatherapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    @Inject
    public WeatherRepository weatherRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Weather> mWeather = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();

    public MainViewModel() {
        App.getAppComponent().inject(this);
        fetchWeather();
    }

    public void fetchWeather() {
        disposable.add(weatherRepository
            .getWeather(
                59.939098, // TODO Hardcoded
                30.315868, // TODO Hardcoded
                "minutely",
                "metric",
                Constants.OWM_API_KEY
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                new DisposableSingleObserver<Weather>() {
                    @Override
                    public void onSuccess(Weather weather) {
                        loadError.setValue(false);
                        mWeather.setValue(weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadError.setValue(true);
                        Log.e(TAG, "onError: ", e);
                    }
                }
            )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }

    public LiveData<Weather> getWeather() {
        return mWeather;
    }

    public LiveData<Boolean> getLoadError() {
        return loadError;
    }
}
