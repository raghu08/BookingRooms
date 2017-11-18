package com.roombooking.ui.addparticipant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roombooking.R;
import com.roombooking.model.Participant;

import java.util.List;


public class AddParticipantAdapter extends RecyclerView.Adapter<AddParticipantAdapter.AddParticipantViewHolder> {

    private List<Participant> participantList;
    private Context context;
    private NotifyDataToUi uiListener;

    public AddParticipantAdapter(Context context, List<Participant> itemList,NotifyDataToUi uiListener) {
        this.participantList = itemList;
        this.context = context;
        this.uiListener = uiListener;

    }

    public interface NotifyDataToUi{
        void updateStatus();
    }

    public void notifyData(List<Participant> participantList){
        this.participantList = participantList;
        notifyDataSetChanged();
    }

    @Override
    public AddParticipantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_participants_row, null);
        AddParticipantViewHolder addParticipantViewHolder = new AddParticipantViewHolder(layoutView);
        return addParticipantViewHolder;
    }

    @Override
    public void onBindViewHolder(AddParticipantViewHolder holder, int position) {
        final Participant participant = getItem(position);
        holder.name.setText(participant.getName());

       holder.closeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               participantList.remove(participant);
               notifyData(participantList);
               uiListener.updateStatus();
           }
       });

    }



    @Override
    public int getItemCount() {
        return this.participantList.size();
    }

    private Participant getItem(int position) {
        return participantList.get(position);
    }


    class AddParticipantViewHolder extends RecyclerView.ViewHolder {
        public ImageView closeButton;
        public TextView name;
        public AddParticipantViewHolder(View itemView) {
            super(itemView);
            closeButton = (ImageView) itemView.findViewById(R.id.closeButton);
            name = (TextView) itemView.findViewById(R.id.roomname);
        }
    }
}