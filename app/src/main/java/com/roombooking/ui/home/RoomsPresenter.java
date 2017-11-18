package com.roombooking.ui.home;

import com.roombooking.model.Item;
import com.roombooking.repository.RoomsRepoListener;
import com.roombooking.repository.RoomsRepository;

import java.util.List;


public class RoomsPresenter implements RoomsContract.UserActionsListener,RoomsRepoListener {

    private RoomsRepository roomsRepo;
    private final RoomsContract.View  view;

    public RoomsPresenter(RoomsContract.View view) {
        roomsRepo = new RoomsRepository();
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
