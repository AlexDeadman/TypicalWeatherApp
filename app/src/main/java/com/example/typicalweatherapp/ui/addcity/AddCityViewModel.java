package com.example.typicalweatherapp.ui.addcity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.geo.search.Geonames;
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

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<Geonames> mGeonames = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();

    public AddCityViewModel() {
        App.getAppComponent().inject(this);
    }

    public void fetchGeonames(String query) {
        compositeDisposable.add(citySearchRepository
            .getCities(
                query,
                20,
                "en", // TODO HARDCODED
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
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public LiveData<Geonames> getGeonames() {
        return mGeonames;
    }

    public LiveData<Boolean> getLoadError() {
        return loadError;
    }
}
