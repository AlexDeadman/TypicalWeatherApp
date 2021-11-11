package com.example.typicalweatherapp.data.model.daily;

import android.os.Parcel;
import android.os.Parcelable;

public class Temp implements Parcelable {

    private Double day;

    public Temp(
        Double day
    ) {
        this.day = day;
    }

    public Temp() {
    }

    protected Temp(Parcel in) {
        if (in.readByte() == 0) {
            day = null;
        } else {
            day = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (day == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(day);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Temp> CREATOR = new Creator<Temp>() {
        @Override
        public Temp createFromParcel(Parcel in) {
            return new Temp(in);
        }

        @Override
        public Temp[] newArray(int size) {
            return new Temp[size];
        }
    };

    public Double getDay() {
        return day;
    }

    public void setDay(Double day) {
        this.day = day;
    }

}
