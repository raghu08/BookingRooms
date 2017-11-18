
package com.roombooking.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SendPasses {

    @SerializedName("booking")
    private Booking booking;
    @SerializedName("passes")
    private List<Pass> mPasses;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<Pass> getPasses() {
        return mPasses;
    }

    public void setPasses(List<Pass> passes) {
        mPasses = passes;
    }

}
