package com.raah.databasehandler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.raah.customobjects.ColumnFactory;
import com.raah.customobjects.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM menu", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<Product> getProductForListView(String itemType){
        ArrayList<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM " + ColumnFactory.getTableMenu() + " WHERE lower(itemtype) = ?";
        Cursor cursor = database.rawQuery( query, new String[] {itemType} );

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
            database.close();
        }
        return productList;
    }
}