package com.roombooking.ui.addparticipant;

import android.content.Context;

import com.roombooking.RoomBookingApplication;
import com.roombooking.model.Booking;
import com.roombooking.model.Participant;
import com.roombooking.model.Pass;
import com.roombooking.model.SendPassModel;
import com.roombooking.model.SendPasses;
import com.roombooking.repository.AddParticipantRepoListener;
import com.roombooking.repository.RoomsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;


public class AddParticipantPresenter implements AddParticipantContract.UserActionsListener,AddParticipantRepoListener {

     @Inject
     RoomsRepository addParticipantRepo;
     @Inject
     SendPasses passes;
     @Inject
     Booking booking;

     private  AddParticipantContract.View  view;



    public AddParticipantPresenter(Context context) {
        ((RoomBookingApplication)context).getAppComponent().inject(this);
    }

    @Override
    public void setView(AddParticipantContract.View view){
        this.view = view;
    }


    @Override
    public void onSuccess(String message) {
        view.setProgressIndicator(false);
        view.SendPassSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        view.setProgressIndicator(false);
        view.failedAttemptToSend(message);
    }



    @Override
    public void sendPasses(SendPassModel model, List<Participant> participantList) {
        view.setProgressIndicator(true);
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm Z",Locale.ENGLISH);
        Date startTime = null;
        try {
            startTime = df.parse(model.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endTime = null;
        try {
            endTime = df.parse(model.getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat bookedSlotSdf = new SimpleDateFormat("HH:mm Z",Locale.ENGLISH);


        long startTimeUnixTimestamp = startTime.getTime()/1000;
        long endTimeUnixTimestamp = endTime.getTime()/1000;
        SimpleDateFormat selectedDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

        Date selectedDate = null;
        try {
            selectedDate = selectedDateFormat.parse(model.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        booking.setRoom(model.getName());
        booking.setDate(""+selectedDate.getTime()/1000);
        booking.setDescription("Project 1aim is going to be starting tommorrow");
        booking.setTimeStart(""+startTimeUnixTimestamp);
        booking.setTimeEnd(""+endTimeUnixTimestamp);
        booking.setTitle("Project Kick of");
        List<Pass> passList = new ArrayList<>();
        for (Participant participant:participantList){
            Pass pass = new Pass();
            pass.setEmail(participant.getEmail());
            pass.setName(participant.getName());
            pass.setNumber(participant.getPhone());
            passList.add(pass);
        }

        passes.setBooking(booking);
        passes.setPasses(passList);
        addParticipantRepo.sendPasses(this,passes);
    }
}
