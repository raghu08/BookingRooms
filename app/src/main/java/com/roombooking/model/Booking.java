
package com.roombooking.model;

import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("date")
    private String mDate;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("room")
    private String mRoom;
    @SerializedName("time_end")
    private String mTimeEnd;
    @SerializedName("time_start")
    private String mTimeStart;
    @SerializedName("title")
    private String mTitle;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getRoom() {
        return mRoom;
    }

    public void setRoom(String room) {
        mRoom = room;
    }

    public String getTimeEnd() {
        return mTimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        mTimeEnd = timeEnd;
    }

    public String getTimeStart() {
        return mTimeStart;
    }

    public void setTimeStart(String timeStart) {
        mTimeStart = timeStart;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
