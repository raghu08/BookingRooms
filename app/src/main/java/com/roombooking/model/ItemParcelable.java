package com.roombooking.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;



public class ItemParcelable implements Parcelable {

    private String name;
    private String location;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeStringList(equipment);
        dest.writeString(size);
        dest.writeString(capacity);
        dest.writeStringList(avail);
        dest.writeStringList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemParcelable> CREATOR = new Creator<ItemParcelable>() {
        @Override
        public ItemParcelable createFromParcel(Parcel in) {
            return new ItemParcelable(in);
        }

        @Override
        public ItemParcelable[] newArray(int size) {
            return new ItemParcelable[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public List<String> getAvail() {
        return avail;
    }

    public void setAvail(List<String> avail) {
        this.avail = avail;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ItemParcelable(){
    }


    private List<String> equipment;
    private String size;
    private String capacity;
    private List<String> avail;
    private List<String> images;

    private ItemParcelable(Parcel in) {
        name = in.readString();
        location = in.readString();
        equipment = in.createStringArrayList();
        size = in.readString();
        capacity = in.readString();
        avail = in.createStringArrayList();
        images = in.createStringArrayList();
    }


}
