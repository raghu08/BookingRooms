package com.roombooking.repository;

import android.util.Log;

import com.roombooking.api.RoomsApiService;
import com.roombooking.model.Item;
import com.roombooking.model.Param;
import com.roombooking.model.SendPasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RoomsRepository {
    private final static String ERROR = "error";
    private final static String TEXT  = "text";


    public void getRooms(final RoomsRepoListener listener,String date){
        RoomsApiService apiService = RetrofitClient.getInstance().getRoomsApiService();
        Param p = new Param();
        p.setDate(date);
        Call<List<Item>> call = apiService.getRooms(p);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                listener.onSuccess(response.body());
            }
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.e("retrofit","failure"+t);
                listener.onFailure();
            }

        });

    }

    public void sendPasses(final AddParticpantRepoListener listener,SendPasses sendPass){
        RoomsApiService apiService = RetrofitClient.getInstance().getRoomsApiService();

        Call<String> call = apiService.sendPasses(sendPass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject json = new JSONObject(response.body());
                    JSONObject error = json.getJSONObject(ERROR);
                    if(error!=null){
                        listener.onSuccess(error.getString(TEXT));
                    }
                    else{
                        listener.onSuccess(null);
                    }

                } catch (JSONException e) {
                    listener.onSuccess(null);
                    e.printStackTrace();
                }



            }
            public void onFailure(Call<String> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }

        });

    }




}
