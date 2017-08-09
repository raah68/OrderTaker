package com.raah.databasehandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.raah.customobjects.Address;
import com.raah.customobjects.ColumnFactory;
import com.raah.customobjects.Customer;
import com.raah.customobjects.CustomerAddress;
import com.raah.customobjects.Product;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Rajon on 26/01/2017.
 * Database Handler Class
 */

public class SqliteHandler extends SQLiteOpenHelper {
    private static final String DBNAME = "restaurant.sqlite";

    //other variables
    private Context context;
    private static final String TAG = SqliteHandler.class.getSimpleName();
    private ArrayList<String> menuItem = new ArrayList<>();
    private ArrayList<Address> address = new ArrayList<>();
    private ArrayList<CustomerAddress> customerAddresses = new ArrayList<>();

    //constructor
    public SqliteHandler(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    //Get writable database
    private SQLiteDatabase getDb(){
        return this.getWritableDatabase();
    }

    // Method to create the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        // No Database to create
    }

    // Method to upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing to drop
    }

    // Method to add data into the database
    public String addCustomer(Customer customer) {
        String retMessage = "";
        String querySearch = "SELECT * FROM " + ColumnFactory.getTableAddress() + " WHERE upper(post_code) = ?";
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        SQLiteDatabase db = getDb();
        Cursor cursor = db.rawQuery(querySearch, new String[]{customer.getPOST_CODE()});

        //Updating existing customer details
        String orderCount = "1";
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            String returnCustomerPostCode = cursor.getString(cursor.getColumnIndex(ColumnFactory.getPostCode()));
            ContentValues updateContent = new ContentValues();
            int newOrderCount = Integer.parseInt(orderCount) +
                    cursor.getInt(cursor.getColumnIndex(ColumnFactory.getOrderCount()));
            //Prepare data to update the returned result
            updateContent.put(ColumnFactory.getOrderCount(), newOrderCount);
            updateContent.put(ColumnFactory.getLastOrderdate(), currentDateTimeString);
            //Close the cursor as we don't need access to it anymore
            cursor.close();

            if (returnCustomerPostCode.equalsIgnoreCase(customer.getPOST_CODE())) {
                long result = db.update(ColumnFactory.getTableCustomer(), updateContent, "post_code = ?", new String[]{customer.getPOST_CODE()});
                db.close();
                if (result == 1) {
                    retMessage = "Customer details updated successfully!";
                    Log.d(TAG, "Customer record updated successfully!");
                }
            }
        } //Creating new Customer record
        else {
            if (cursor.getCount() <= 0) {
                ContentValues newContentValues = new ContentValues();
                newContentValues.put(ColumnFactory.getCustomerName(), customer.getCUSTOMER_NAME());
                newContentValues.put(ColumnFactory.getPostCode(), customer.getPOST_CODE());
                newContentValues.put(ColumnFactory.getDoorNumber(), customer.getDOOR_NUMBER());
                newContentValues.put(ColumnFactory.getPhoneNumber(), customer.getPHONE_NUMBER());
                newContentValues.put(ColumnFactory.getLastOrderdate(), currentDateTimeString);
                newContentValues.put(ColumnFactory.getOrderCount(), orderCount);
                long result = db.insert(ColumnFactory.getTableCustomer(), null, newContentValues);
                db.close();
                if (result == 1) {
                    retMessage = "New Customer record created successfully!";
                    Log.d(TAG, "New customer record inserted successfully!");
                }
            }
        }
        return retMessage;
    }//end of method btnAddCustomer()

    // Method to obtain all menu data from the database and put them in an array
    public ArrayList<String> getAllMenu(){
        // Do this on a different thread
        BackGroundTask backGroundTask = new BackGroundTask();
        backGroundTask.execute();
        if(menuItem != null){
            return menuItem;
        } else {
            return null;
        }
    }

    // Method to obtain all menu data from the database and put them in an array
    public ArrayList<CustomerAddress> getCustomer(){
        // Do this on a different thread
        BackGroundTaskThree backGroundTask = new BackGroundTaskThree();
        backGroundTask.execute();
        if(customerAddresses != null){
            return customerAddresses;
        } else {
            return null;
        }
    }

    // Method to obtain all Address from the database and put them in an array
    public ArrayList<Address> getAddress(){
        // Do this on a background thread
        BackGroundTaskTwo backGroundTaskTwo = new BackGroundTaskTwo();
        backGroundTaskTwo.execute();
        if(address != null){
            return address;
        } else {
            return null;
        }
    } // End of getAddress()

    // Method to Search
    public Product getProduct(String inName){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ColumnFactory.getTableMenu() + " WHERE lower(name) = ?";
        Product product = null;
        Cursor cursor = db.rawQuery( query, new String[] {inName} );

        if(cursor != null && cursor.moveToFirst()){
            String type = cursor.getString(cursor.getColumnIndex(ColumnFactory.getTYPE()));
            String name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getNAME()));
            BigDecimal price = new BigDecimal(cursor.getString(cursor.getColumnIndex(ColumnFactory.getPRICE())));
            int productID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ColumnFactory.getPRODUCTID())));
            product = new Product(type, name, price, productID);
            cursor.close();
            db.close();
        }
        if(product != null){
            return product;
        } else {
            return null;
        }
    }

    /**
     * Method to searh the menu table and return all the products by item type
     * @param itemType - type of items to be returned
     * @return - returns a list of menu items
     */
    public ArrayList<Product> getProductForListView(String itemType){
        ArrayList<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ColumnFactory.getTableMenu() + " WHERE lower(itemtype) = ?";
        Cursor cursor = db.rawQuery( query, new String[] {itemType} );

        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                String type = cursor.getString(cursor.getColumnIndex(ColumnFactory.getTYPE()));
                String name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getNAME()));
                BigDecimal price = new BigDecimal(cursor.getString(cursor.getColumnIndex(ColumnFactory.getPRICE()))).setScale(2, BigDecimal.ROUND_UP);
                int productID = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ColumnFactory.getPRODUCTID())));
                Product aProduct = new Product(type, name, price, productID);
                productList.add(aProduct);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return productList;
    }
//***********************************All the back ground tasks************************
    /**
     * Async task to load all menu in the background
     * This is needed to load the menu into the list view adapter
     */
    private class BackGroundTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        /**
         * Handling the loading menu in a different thread
         */
        @Override
        protected void onPreExecute() {
            SQLiteDatabase db = getDb();
            Cursor cursor = db.query(ColumnFactory.getTableMenu(), null, null, null, null, null, null);

            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    String name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getNAME()));
                    menuItem.add(name);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            } else {
                db.close();
            }
        }
    } //End of AsyncTask class

    /**
     * Async task to load all addresses in the background
     */
    private class BackGroundTaskTwo extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        /**
         * Handling the loading address in a different thread
         * This is needed for the post code search
         */
        @Override
        protected void onPreExecute() {
            SQLiteDatabase db = getDb();
            Cursor cursor = db.query(ColumnFactory.getTableAddress(), null, null, null, null, null, null);

            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    String building_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getBuildingName()));
                    String street_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getStreetName()));
                    String town_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getTownName()));
                    String post_code = cursor.getString(cursor.getColumnIndex(ColumnFactory.getPostCode()));
                    Address addressRow = new Address(null, building_name, street_name, town_name, post_code);
                    address.add(addressRow);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            } else {
                db.close();
            }
        }
    } //End of AsyncTask class

    /**
     * Async task to load customer and address together in the background
     * This is needed for the phone number search
     */
    private class BackGroundTaskThree extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        /**
         * Handling the loading address in a different thread
         */
        @Override
        protected void onPreExecute() {
            SQLiteDatabase db = getDb();
            String query = "select customer.customer_name, customer.phone_number, customer.door_number, address.building_name, address.street_name, address.town_name, address.post_code\n" +
                    "from customer join address on address.post_code = customer.post_code";
            Cursor cursor = db.rawQuery(query, null);
            //Cursor cursor = db.query(TABLE_CUSTOMER, null, null, null, null, null, null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    String customer_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getCustomerName()));
                    String phone_number = cursor.getString(cursor.getColumnIndex(ColumnFactory.getPhoneNumber()));
                    String door_number = cursor.getString(cursor.getColumnIndex(ColumnFactory.getDoorNumber()));
                    String building_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getBuildingName()));
                    String street_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getStreetName()));
                    String town_name = cursor.getString(cursor.getColumnIndex(ColumnFactory.getTownName()));
                    String post_code = cursor.getString(cursor.getColumnIndex(ColumnFactory.getPostCode()));
                    CustomerAddress customerAddress = new CustomerAddress(customer_name, phone_number, door_number,
                                                                          building_name, street_name, town_name, post_code, null);
                    customerAddresses.add(customerAddress);
                } while (cursor.moveToNext());
                cursor.close();
                db.close();
            } else {
                db.close();
            }
        }
    } //End of AsyncTask class

} // End of class
