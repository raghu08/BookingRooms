package com.roombooking.model

import com.google.gson.annotations.SerializedName

class Booking {

    @SerializedName("date")
    var date: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("room")
    var room: String? = null
    @SerializedName("time_end")
    var timeEnd: String? = null
    @SerializedName("time_start")
    var timeStart: String? = null
    @SerializedName("title")
    var title: String? = null

}
