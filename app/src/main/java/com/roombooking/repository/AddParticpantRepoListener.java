package com.roombooking.repository;


import com.roombooking.model.Item;

import java.util.List;


public interface AddParticpantRepoListener {
     void onSuccess(String message);
     void onFailure(String message);

}
