package com.example.typicalweatherapp.data.model.weather;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {

    private String main;
    private String description;

    public WeatherInfo(
        String main,
        String description
    ) {
        this.main = main;
        this.description = description;
    }

    public WeatherInfo() {
    }

    protected WeatherInfo(Parcel in) {
        main = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(main);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
