package com.roombooking.repository;


import com.roombooking.model.Item;

import java.util.List;


public interface AddParticipantRepoListener {
     void onSuccess(String message);
     void onFailure(String message);

}
