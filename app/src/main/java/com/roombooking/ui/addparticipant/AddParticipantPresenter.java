package com.roombooking.ui.addparticipant;

import android.util.Log;

import com.roombooking.model.Booking;
import com.roombooking.model.Participant;
import com.roombooking.model.Pass;
import com.roombooking.model.SendPassModel;
import com.roombooking.model.SendPasses;
import com.roombooking.repository.AddParticpantRepoListener;
import com.roombooking.repository.RoomsRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class AddParticipantPresenter implements AddParticipantContract.UserActionsListener,AddParticpantRepoListener {

    private RoomsRepository addParticipantRepo;
    private final AddParticipantContract.View  view;

    public AddParticipantPresenter(AddParticipantContract.View view) {
        addParticipantRepo = new RoomsRepository();
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

        SendPasses p = new SendPasses();
        Booking booking  =new Booking();
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

        p.setBooking(booking);
        p.setPasses(passList);
        addParticipantRepo.sendPasses(this,p);
    }
}
