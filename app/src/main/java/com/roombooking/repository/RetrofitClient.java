package com.roombooking.repository;

import com.roombooking.api.RoomsApiService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

  private static RetrofitClient instance = null;
  private Retrofit retrofit;
  private OkHttpClient client;

  private RoomsApiService roomsApiService;
  public  static final String API_BASE_URL = "https://challenges.1aim.com/roombooking_app/";



  public RetrofitClient() {


    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    Interceptor apiKeyInterceptor = new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
      }
    };
    OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
    okHttpBuilder.addInterceptor(loggingInterceptor);
    okHttpBuilder.addInterceptor(apiKeyInterceptor);
    client = okHttpBuilder.build();
    retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    roomsApiService = retrofit.create(RoomsApiService.class);
  }

  public static RetrofitClient getInstance() {
    if (instance == null) {
      instance = new RetrofitClient();
    }

    return instance;
  }

  public RoomsApiService getRoomsApiService() {
    return roomsApiService;
  }
}