package com.roombooking.di;

import com.roombooking.repository.AddParticipantRepoListener;
import com.roombooking.repository.RoomsRepository;
import com.roombooking.ui.addparticipant.AddParticipantActivity;
import com.roombooking.ui.addparticipant.AddParticipantPresenter;
import com.roombooking.ui.home.RoomListActivity;
import com.roombooking.ui.home.RoomsPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by raghavendra.malgi on 20-11-2017.
 */

    @Singleton
    @Component(modules = {AppModule.class, PresenterModule.class,NetworkModule.class,ModelModule.class})
    public interface AppComponent {

        void inject(RoomListActivity roomListActivity);
        void inject(RoomsRepository roomsRepository);
        void inject(RoomsPresenter roomsPresenter);
        void inject(AddParticipantRepoListener addParticpantRepoListener);
        void inject(AddParticipantActivity addParticipantActivity);
        void inject(AddParticipantPresenter addParticipantPresenter);
    }


