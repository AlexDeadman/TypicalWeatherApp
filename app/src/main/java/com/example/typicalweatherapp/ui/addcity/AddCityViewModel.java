package com.example.typicalweatherapp.ui.addcity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.city.search.Geonames;
import com.example.typicalweatherapp.data.repository.CitySearchRepository;
import com.example.typicalweatherapp.utils.Constants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddCityViewModel extends ViewModel {
    private static final String TAG = "AddCityViewModel";

    @Inject
    public CitySearchRepository citySearchRepository;

    private CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Geonames> mGeonames = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();

    private String query;

    public AddCityViewModel() {
        App.getAppComponent().inject(this);
    }

    public void fetchGeonames() {
        disposable.add(citySearchRepository
            .getCities(
                query,
                10,
                "ru",
                Constants.GN_USERNAME
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                new DisposableSingleObserver<Geonames>() {
                    @Override
                    public void onSuccess(Geonames geonames) {
                        loadError.setValue(false);
                        mGeonames.setValue(geonames);
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

    public void setQuery(String query) {
        this.query = query;
    }

    public LiveData<Geonames> getGeonames() {
        return mGeonames;
    }

    public LiveData<Boolean> getLoadError() {
        return loadError;
    }
}
