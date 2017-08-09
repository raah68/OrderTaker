package com.raah.customobjects;

/**
 * Created by Rajon on 04/02/2017.
 * Customer Object Creator
 */

public class Customer {

    private String CUSTOMER_NAME = "customer_name";
    private String PHONE_NUMBER = "phone_number";
    private String POST_CODE = "post_code";
    private String DOOR_NUMBER = "door_number";

    public Customer(String CUSTOMER_NAME, String DOOR_NUMBER, String POST_CODE, String PHONE_NUMBER) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.POST_CODE = POST_CODE;
        this.DOOR_NUMBER = DOOR_NUMBER;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }
    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }
    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getPOST_CODE() {
        return POST_CODE;
    }
    public void setPOST_CODE(String POST_CODE) {
        this.POST_CODE = POST_CODE;
    }

    public String getDOOR_NUMBER() {
        return DOOR_NUMBER;
    }
    public void setDOOR_NUMBER(String DOOR_NUMBER) {
        this.DOOR_NUMBER = DOOR_NUMBER;
    }
} // End of class
