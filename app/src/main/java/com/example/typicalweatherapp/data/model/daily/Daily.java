package com.example.typicalweatherapp.data.model.daily;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.typicalweatherapp.data.model.WeatherInfo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily implements Parcelable {

    private Integer dt;

    private Temp temp;

    private Integer pressure;

    private Integer humidity;

    @SerializedName("wind_speed")
    private Double windSpeed;

    private ArrayList<WeatherInfo> weather = null;

    public Daily(
        Integer dt,
        Temp temp,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        ArrayList<WeatherInfo> weather,
        Double pop
    ) {
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weather = weather;
    }

    public Daily() {
    }

    protected Daily(Parcel in) {
        if (in.readByte() == 0) {
            dt = null;
        } else {
            dt = in.readInt();
        }
        temp = in.readParcelable(Temp.class.getClassLoader());
        if (in.readByte() == 0) {
            pressure = null;
        } else {
            pressure = in.readInt();
        }
        if (in.readByte() == 0) {
            humidity = null;
        } else {
            humidity = in.readInt();
        }
        if (in.readByte() == 0) {
            windSpeed = null;
        } else {
            windSpeed = in.readDouble();
        }
        weather = in.createTypedArrayList(WeatherInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(dt);
        }
        dest.writeParcelable(temp, flags);
        if (pressure == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pressure);
        }
        if (humidity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(humidity);
        }
        if (windSpeed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(windSpeed);
        }
        dest.writeTypedList(weather);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Daily> CREATOR = new Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel in) {
            return new Daily(in);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public ArrayList<WeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<WeatherInfo> weather) {
        this.weather = weather;
    }
}
