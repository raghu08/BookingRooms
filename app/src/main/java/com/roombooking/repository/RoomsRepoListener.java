package com.roombooking.repository;


import com.roombooking.model.Item;

import java.util.List;


public interface RoomsRepoListener {
     void onSuccess(List<Item> response);
    void onFailure();

}
