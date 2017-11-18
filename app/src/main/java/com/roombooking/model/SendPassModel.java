package com.roombooking.model;

import android.os.Parcel;
import android.os.Parcelable;



public class SendPassModel implements Parcelable {
    public SendPassModel() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Creator<SendPassModel> getCREATOR() {
        return CREATOR;
    }

    private String name;
    private String startTime;
    private String endTime;
    private String date;
    private SendPassModel(Parcel in) {
        name = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        date = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(date);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SendPassModel> CREATOR = new Creator<SendPassModel>() {
        @Override
        public SendPassModel createFromParcel(Parcel in) {
            return new SendPassModel(in);
        }

        @Override
        public SendPassModel[] newArray(int size) {
            return new SendPassModel[size];
        }
    };
}
