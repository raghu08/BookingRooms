package com.roombooking.api;



import com.roombooking.model.Item;
import com.roombooking.model.Param;
import com.roombooking.model.SendPasses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RoomsApiService {
    @POST("getrooms")
    Call<List<Item>> getRooms(@Body Param date);
    @POST("sendpass")
    Call<String> sendPasses(@Body SendPasses passes);


}
