package com.example.typicalweatherapp.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.Weather;
import com.example.typicalweatherapp.data.repository.WeatherRepository;
import com.example.typicalweatherapp.utils.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MainViewModel extends ViewModel {

    @Inject
    public WeatherRepository weatherRepository;

    private static final String TAG = "MainViewModel";

    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Weather> mWeather = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public MainViewModel() {
        App.getAppComponent().inject(this);
        fetchWeather();
    }

    private void fetchWeather() {
        loading.setValue(true);
        disposable.add(weatherRepository
                //TODO       HARDCODED   HARDCODED
                .getWeather(33.44, -94.04, "minutely", Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Weather>() {
                            @Override
                            public void onSuccess(Weather weather) {
                                loadError.setValue(false);
                                loading.setValue(false);
                                mWeather.setValue(weather);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loadError.setValue(true);
                                loading.setValue(false);
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

    public LiveData<Boolean> getLoading() {
        return loading;
    }
}