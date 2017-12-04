package com.roombooking.model

import com.google.gson.annotations.SerializedName

class SendPasses {

    @SerializedName("booking")
    var booking: Booking? = null
    @SerializedName("passes")
    var passes: List<Pass>? = null

}
