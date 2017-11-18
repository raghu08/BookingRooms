package com.roombooking.ui.addparticipant;

import com.roombooking.model.Item;
import com.roombooking.model.Participant;
import com.roombooking.model.SendPassModel;

import java.util.List;


public interface AddParticipantContract {

    interface View {

        void setProgressIndicator(boolean active);
        void SendPassSuccess(String message);
        void failedAttemptToSend(String message);


    }

    interface UserActionsListener {
        void  sendPasses(SendPassModel model, List<Participant> participantList);

    }
}
