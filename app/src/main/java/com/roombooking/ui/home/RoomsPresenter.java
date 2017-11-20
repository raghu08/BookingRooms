package com.roombooking.ui.home;

import android.content.Context;

import com.roombooking.RoomBookingApplication;
import com.roombooking.model.Item;
import com.roombooking.repository.RoomsRepoListener;
import com.roombooking.repository.RoomsRepository;

import java.util.List;

import javax.inject.Inject;


public class RoomsPresenter implements RoomsContract.UserActionsListener,RoomsRepoListener {

    @Inject
     RoomsRepository roomsRepo;
    private  RoomsContract.View  view;



    public RoomsPresenter(Context context) {
        ((RoomBookingApplication)context).getAppComponent().inject(this);
    }

    @Override
    public void setView(RoomsContract.View view){
        this.view = view;
    }




    @Override
    public void onSuccess(List<Item> response) {
        view.setProgressIndicator(false);
        view.loadRoomsUi(response);
    }

    @Override
    public void onFailure() {
        view.setProgressIndicator(false);
        view.failedToFetchRooms();
    }

    @Override
    public void getRooms(String date) {
        view.setProgressIndicator(true);
        roomsRepo.getRooms(this,date);
    }
}
