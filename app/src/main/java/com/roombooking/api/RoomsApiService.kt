package com.roombooking.api


import com.roombooking.model.Item
import com.roombooking.model.Param
import com.roombooking.model.SendPasses

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface RoomsApiService {
    @POST("getrooms")
    fun getRooms(@Body date: Param): Call<List<Item>>

    @POST("sendpass")
    fun sendPasses(@Body passes: SendPasses): Call<String>


}
