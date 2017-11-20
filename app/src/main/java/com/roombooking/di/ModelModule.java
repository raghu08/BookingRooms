package com.roombooking.di;

import android.content.Context;

import com.roombooking.model.Booking;
import com.roombooking.model.SendPasses;
import com.roombooking.repository.RoomsRepository;
import com.roombooking.ui.addparticipant.AddParticipantContract;
import com.roombooking.ui.addparticipant.AddParticipantPresenter;
import com.roombooking.ui.home.RoomsContract;
import com.roombooking.ui.home.RoomsPresenter;

import javax.inject.Singleton;

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