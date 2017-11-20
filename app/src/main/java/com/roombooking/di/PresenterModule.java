package com.roombooking.di;

import android.content.Context;

import com.roombooking.repository.RoomsRepository;
import com.roombooking.ui.addparticipant.AddParticipantContract;
import com.roombooking.ui.addparticipant.AddParticipantPresenter;
import com.roombooking.ui.home.RoomsContract;
import com.roombooking.ui.home.RoomsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

  @Provides
  @Singleton
  RoomsContract.UserActionsListener provideRoomsListPresenter(Context context) {
    return new RoomsPresenter(context);
  }

  @Provides
  @Singleton
  AddParticipantContract.UserActionsListener provideAddParticipantPresenter(Context context) {
    return new AddParticipantPresenter(context);
  }

  @Provides
  @Singleton
  RoomsRepository provideRoomsRepo(Context context) {
     return new RoomsRepository(context);
  }
}