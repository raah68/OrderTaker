package com.raah.customobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajon on 12/04/2017.
 * Custom object to create a customer with address
 */

public class CustomerAddress implements Parcelable{
    private String customer_name;
    private String phone_number;
    private String door_number;
    private String building_name;
    private String street_name;
    private String town_name;
    private String post_code;
    private String instruction;

    public CustomerAddress(String customer_name, String phone_number, String door_number,
                           String building_name, String street_name, String town_name,
                           String post_code, String instruction) {
        this.customer_name = customer_name;
        this.phone_number = phone_number;
        this.door_number = door_number;
        this.building_name = building_name;
        this.street_name = street_name;
        this.town_name = town_name;
        this.post_code = post_code;
        this.instruction = instruction;
    }//end CustomerAddress constructor

    protected CustomerAddress(Parcel in) {
        customer_name = in.readString();
        phone_number = in.readString();
        door_number = in.readString();
        building_name = in.readString();
        street_name = in.readString();
        town_name = in.readString();
        post_code = in.readString();
        instruction = in.readString();
    }//end parcelable constructor

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customer_name);
        dest.writeString(phone_number);
        dest.writeString(door_number);
        dest.writeString(building_name);
        dest.writeString(street_name);
        dest.writeString(town_name);
        dest.writeString(post_code);
        dest.writeString(instruction);
    }//end of method writeToParcel()

    public static final Parcelable.Creator<CustomerAddress> CREATOR
            = new Parcelable.Creator<CustomerAddress>() {
        @Override
        public CustomerAddress createFromParcel(Parcel in) {
            return new CustomerAddress(in);
        }

        public CustomerAddress[] newArray(int size) {
            return new CustomerAddress[size];
        }
    };
}//end of class
