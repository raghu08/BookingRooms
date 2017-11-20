package com.roombooking.ui.home;

import com.roombooking.model.Item;

import java.util.List;


public interface RoomsContract {

    interface View {

        void setProgressIndicator(boolean active);
        void loadRoomsUi(List<Item> response);
        void failedToFetchRooms();


    }

    interface UserActionsListener {
        void  getRooms(String date);
        void  setView(RoomsContract.View view);

    }
}
