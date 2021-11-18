package com.example.typicalweatherapp.ui.addcity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.geo.search.CitiesSearchResult;
import com.example.typicalweatherapp.data.repository.GeonamesRepository;
import com.example.typicalweatherapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddCityViewModel extends ViewModel {
    private static final String TAG = "AddCityViewModel";

    @Inject
    public GeonamesRepository geonamesRepository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<CitiesSearchResult> mCities = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();

    public AddCityViewModel() {
        App.getAppComponent().inject(this);
    }

    public void fetchCities(String query) {
        compositeDisposable.add(geonamesRepository
            .getCities(
                query,
                20,
                "en", // TODO HARDCODED
                Constants.GN_USERNAME
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                new DisposableSingleObserver<CitiesSearchResult>() {
                    @Override
                    public void onSuccess(CitiesSearchResult cities) {
                        loadError.setValue(false);
                        mCities.setValue(cities);
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
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public LiveData<CitiesSearchResult> getCities() {
        return mCities;
    }

    public LiveData<Boolean> getLoadError() {
        return loadError;
    }
}
