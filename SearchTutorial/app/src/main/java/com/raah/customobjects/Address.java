package com.raah.customobjects;

/**
 * Created by Rajon on 02/02/2017.
 * Address Object Class
 */

public class Address {
    private String DOOR_NUMBER = "door_number";
    private String BUILDING_NAME = "building_number";
    private String STREET_NAME = "street_name";
    private String TOWN_NAME = "town_name";
    private String POST_CODE = "post_code";

    public Address(String DOOR_NUMBER, String BUILDING_NAME, String STREET_NAME, String TOWN_NAME, String POST_CODE) {
        this.DOOR_NUMBER = DOOR_NUMBER;
        this.BUILDING_NAME = BUILDING_NAME;
        this.STREET_NAME = STREET_NAME;
        this.TOWN_NAME = TOWN_NAME;
        this.POST_CODE = POST_CODE;
    }

    public String getDOOR_NUMBER() {
        return DOOR_NUMBER;
    }

    public void setDOOR_NUMBER(String DOOR_NUMBER) {
        this.DOOR_NUMBER = DOOR_NUMBER;
    }

    public String getBUILDING_NAME() {
        return BUILDING_NAME;
    }

    public void setBUILDING_NAME(String BUILDING_NUMBER) {
        this.BUILDING_NAME = BUILDING_NUMBER;
    }

    public String getSTREET_NAME() {
        return STREET_NAME;
    }

    public void setSTREET_NAME(String STREET_NAME) {
        this.STREET_NAME = STREET_NAME;
    }

    public String getTOWN_NAME() {
        return TOWN_NAME;
    }

    public void setTOWN_NAME(String TOWN_NAME) {
        this.TOWN_NAME = TOWN_NAME;
    }

    public String getPOST_CODE() {
        return POST_CODE;
    }

    public void setPOST_CODE(String POST_CODE) {
        this.POST_CODE = POST_CODE;
    }
}
