package com.roombooking.di;

import com.roombooking.model.Booking;
import com.roombooking.model.SendPasses;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

  @Provides
  SendPasses provideSendPasses() {
    return new SendPasses();
  }

  @Provides
  Booking provideBooking() {
    return new Booking();
  }


}