package com.roombooking.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.roombooking.R
import com.roombooking.model.Item
import com.roombooking.model.ItemParcelable
import com.roombooking.repository.RetrofitClient
import com.roombooking.ui.roomdetail.RoomDetailInfoActivity
import com.squareup.picasso.Picasso

/**
 * Created by raghavendra.malgi on 28-11-2017.
 */
class RoomsListAdapter(var context: Context, var itemList: List<Item>?) :
        RecyclerView.Adapter<RoomsListAdapter.RoomListViewHolder>() {


    private var dateStr: String? = null

    fun notifyData(itemList: List<Item>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsListAdapter.RoomListViewHolder {

        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.room_view_row, null)
        return RoomListViewHolder(layoutView)
    }


    override fun onBindViewHolder(holder: RoomListViewHolder, position: Int) {
        val roomInfo = getItem(position)
        holder.capacity.text = context.getText(R.string.capacity).toString() + " " + roomInfo.capacity
        val floor = roomInfo.location

        holder.floor.text = floor
        holder.roomName.text = "Room " + roomInfo.name
        if (roomInfo.images != null) {
            Picasso.with(context).load(RetrofitClient.API_BASE_URL + roomInfo.images[0]).into(holder.thumbnailIv)
        }

        holder.itemView.setOnClickListener({
            val items = getItem(position)
            val itemParcel = ItemParcelable()
            itemParcel.name = items.name
            itemParcel.location = items.location
            itemParcel.equipment = items.equipment
            itemParcel.images = items.images
            itemParcel.avail = items.avail
            itemParcel.capacity = items.capacity
            itemParcel.size = items.size


            val intent = Intent(context, RoomDetailInfoActivity::class.java)
            intent.putExtra(RoomDetailInfoActivity.PARCEL, itemParcel)
            intent.putExtra(RoomDetailInfoActivity.DATE_STRING, dateStr)
            context.startActivity(intent)
        })

    }


    override fun getItemCount(): Int {
        return this.itemList!!.size
    }

    private fun getItem(position: Int): Item {
        return itemList!![position]
    }

    fun setDateStr(dateStr: String) {
        this.dateStr = dateStr
    }

    inner class RoomListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var thumbnailIv: ImageView = itemView.findViewById(R.id.room_photo) as ImageView
        var capacity: TextView = itemView.findViewById(R.id.capacity) as TextView
        var roomName: TextView = itemView.findViewById(R.id.roomname) as TextView
        var floor: TextView = itemView.findViewById(R.id.floor) as TextView


    }

}