package com.roombooking.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.roombooking.R;
import com.roombooking.model.Item;
import com.roombooking.model.ItemParcelable;
import com.roombooking.model.SendPassModel;
import com.roombooking.repository.RetrofitClient;
import com.roombooking.ui.roomdetail.RoomDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RoomsListAdapter extends RecyclerView.Adapter<RoomsListAdapter.RoomListViewHolder> {

    private List<Item> itemList;
    private Context context;
    private String dateStr;

    public RoomsListAdapter(Context context, List<Item> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    public void notifyData(List<Item> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public RoomListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_view_row, null);
        RoomListViewHolder roomListViewHolder = new RoomListViewHolder(layoutView);
        return roomListViewHolder;
    }

    @Override
    public void onBindViewHolder(RoomListViewHolder holder, final int position) {
        Item roomInfo = getItem(position);
        holder.capacity.setText(context.getText(R.string.capacity)+" " +roomInfo.getCapacity());
        String floor = roomInfo.getLocation();

        holder.floor.setText(floor);
        holder.roomName.setText("Room "+roomInfo.getName());
        if(roomInfo.getImages()!=null) {
            Picasso.with(context).load(RetrofitClient.API_BASE_URL+roomInfo.getImages().get(0)).into(holder.thumbnailIv);
        }

      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Item items = getItem(position);
              ItemParcelable itemParcel= new ItemParcelable();
              itemParcel.setName(items.getName());
              itemParcel.setLocation(items.getLocation());
              itemParcel.setEquipment(items.getEquipment());
              itemParcel.setImages(items.getImages());
              itemParcel.setAvail(items.getAvail());
              itemParcel.setCapacity(items.getCapacity());
              itemParcel.setSize(items.getSize());


              Intent intent = new Intent(context, RoomDetailActivity.class);
              intent.putExtra(RoomDetailActivity.PARCEL,itemParcel);
              intent.putExtra(RoomDetailActivity.DATE_STRING,dateStr);
              context.startActivity(intent);
          }
      });

    }



    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    private Item getItem(int position) {
        return itemList.get(position);
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }


    class RoomListViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnailIv;
        public TextView capacity;
        public TextView roomName;
        public TextView floor;
        public View itemView;



        public RoomListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            thumbnailIv = (ImageView) itemView.findViewById(R.id.room_photo);
            capacity = (TextView) itemView.findViewById(R.id.capacity);
            roomName = (TextView) itemView.findViewById(R.id.roomname);
            floor = (TextView) itemView.findViewById(R.id.floor);


        }



    }
}