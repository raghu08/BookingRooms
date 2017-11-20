package com.roombooking.di;

import com.roombooking.api.RoomsApiService;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://challenges.1aim.com/roombooking_app/";
    private static final String NAME_BASE_URL = "NAME_BASE_URL";

    @Provides
    @Named(NAME_BASE_URL)
    String provideBaseUrlString() {
        return BASE_URL;
    }
    @Provides
    @Singleton
    Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter, @Named(NAME_BASE_URL) String baseUrl) {
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
        OkHttpClient client = okHttpBuilder.build();
        return new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(converter)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    RoomsApiService provideRoomsApi(Retrofit retrofit) {
        return retrofit.create(RoomsApiService.class);
    }
}