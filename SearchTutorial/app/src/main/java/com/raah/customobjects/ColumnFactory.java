package com.raah.customobjects;

/**
 * Created by Rajon on 12/04/2017.
 * Custom object to define global table column names
 */

public class ColumnFactory {
    private static final String DBNAME = "restaurant.sqlite";

    //Menu table and its column
    private static final String TABLE_MENU = "menu";
    private static final String ITEMTYPE = "itemtype";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PRODUCTID = "id";
    //addresses table columns
    private static final String TABLE_ADDRESS = "address";
    private static final String DOOR_NUMBER = "door_number";
    private static final String BUILDING_NAME = "building_name";
    private static final String STREET_NAME = "street_name";
    private static final String TOWN_NAME = "town_name";
    private static final String POST_CODE = "post_code";
    //customer table name
    private static final String TABLE_CUSTOMER = "customer";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String PHONE_NUMBER = "phone_number";
    // we will be reusing the DOOR_NUMBER variable for our customer table
    private static final String LAST_ORDERDATE = "last_orderDate";
    private static final String ORDER_COUNT = "order_count";

    public static String getDBNAME() {
        return DBNAME;
    }

    public static String getTableMenu() {
        return TABLE_MENU;
    }

    public static String getTYPE() {
        return ITEMTYPE;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getPRICE() {
        return PRICE;
    }

    public static String getTableAddress() {
        return TABLE_ADDRESS;
    }

    public static String getDoorNumber() {
        return DOOR_NUMBER;
    }

    public static String getBuildingName() {
        return BUILDING_NAME;
    }

    public static String getStreetName() {
        return STREET_NAME;
    }

    public static String getTownName() {
        return TOWN_NAME;
    }

    public static String getPostCode() {
        return POST_CODE;
    }

    public static String getTableCustomer() {
        return TABLE_CUSTOMER;
    }

    public static String getCustomerName() {
        return CUSTOMER_NAME;
    }

    public static String getPhoneNumber() {
        return PHONE_NUMBER;
    }

    public static String getLastOrderdate() {
        return LAST_ORDERDATE;
    }

    public static String getOrderCount() {
        return ORDER_COUNT;
    }

    public static String getPRODUCTID() {
        return PRODUCTID;
    }

    public static String getAddressForView(CustomerAddress customerAddress){
        StringBuilder stringBuilder = new StringBuilder();
        String tab = " ";
        stringBuilder.append(customerAddress.getDoor_number());
        stringBuilder.append(tab);
        stringBuilder.append(customerAddress.getBuilding_name());
        stringBuilder.append(tab);
        stringBuilder.append(customerAddress.getStreet_name());
        stringBuilder.append("\n");
        stringBuilder.append(customerAddress.getTown_name());
        stringBuilder.append(tab);
        stringBuilder.append(customerAddress.getPost_code());

        return stringBuilder.toString();

    }//end of method getAddressForView()

}// End of Class
