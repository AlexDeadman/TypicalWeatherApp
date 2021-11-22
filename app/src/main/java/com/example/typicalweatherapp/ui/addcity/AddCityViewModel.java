package com.example.typicalweatherapp.ui.addcity;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.typicalweatherapp.App;
import com.example.typicalweatherapp.data.model.geo.byid.FavouriteCity;
import com.example.typicalweatherapp.data.model.geo.search.CitySearchResult;
import com.example.typicalweatherapp.data.repository.FavouritesRepository;
import com.example.typicalweatherapp.data.repository.GeonamesRepository;
import com.example.typicalweatherapp.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddCityViewModel extends ViewModel {
    private static final String TAG = "AddCityViewModel";

    @Inject
    public GeonamesRepository geonamesRepository;
    @Inject
    public FavouritesRepository favouritesRepository;
    private final List<FavouriteCity> favouriteCities;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<CitySearchResult> mCities = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadError = new MutableLiveData<>();

    public AddCityViewModel() {
        App.getAppComponent().inject(this);
        favouriteCities = favouritesRepository.getFavouritesCities();
    }

    public void fetchCities(String query) {
        compositeDisposable.add(geonamesRepository
            // TODO Language hardcoded
            .getCities(query, 20, "en", Constants.GN_USERNAME)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                new DisposableSingleObserver<CitySearchResult>() {
                    @Override
                    public void onSuccess(CitySearchResult cities) {
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

    public void fetchCity(int geonameId){
        compositeDisposable.add(geonamesRepository
            .getCityById(
                geonameId,
                Constants.GN_USERNAME
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                new DisposableSingleObserver<FavouriteCity>() {
                    @Override
                    public void onSuccess(FavouriteCity favouriteCity) {
                        loadError.setValue(false);

                        boolean exist = false;
                        for (FavouriteCity favCity : favouriteCities) {
                            if (favCity.equals(favouriteCity)) {
                                exist = true;
                                break;
                            }
                        }
                        if (!exist) {
                            favouriteCities.add(favouriteCity);
                            if (favouriteCities.size() == 1) {
                                SharedPreferences.Editor editor = App.getPreferences().edit();
                                editor.putInt(Constants.CURRENT_CITY_INDEX_PREF_KEY, 0);
                                editor.apply();
                            }
                        }
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

    void saveFavourites() {
        favouritesRepository.saveFavourites();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public LiveData<CitySearchResult> getCities() {
        return mCities;
    }

    public LiveData<Boolean> getLoadError() {
        return loadError;
    }
}
