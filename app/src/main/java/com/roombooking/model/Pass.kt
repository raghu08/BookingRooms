package com.roombooking.model

import com.google.gson.annotations.SerializedName

class Pass {

    @SerializedName("email")
    var email: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("number")
    var number: String? = null

}
