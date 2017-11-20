package com.roombooking;

import android.app.Application;

import com.roombooking.di.AppComponent;
import com.roombooking.di.AppModule;
import com.roombooking.di.DaggerAppComponent;

/**
 * Created by raghavendra.malgi on 20-11-2017.
 */

public class RoomBookingApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(RoomBookingApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}
