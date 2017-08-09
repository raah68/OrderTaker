package com.raah.customcontentprovider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.raah.customobjects.Address;
import com.raah.customobjects.ColumnFactory;
import com.raah.customobjects.Customer;
import com.raah.customobjects.CustomerAddress;
import com.raah.databasehandler.SqliteHandler;

import java.util.ArrayList;

/**
 * Created by Rajon on 31/03/2017.
 * Phone Number Search Provider
 */

public class PhoneNumberSearchProvider extends ContentProvider {
    SqliteHandler myDB;
    public ArrayList<CustomerAddress> customerAddresses;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        myDB = new SqliteHandler(getContext());
        BackGroundTask loadMenu = new BackGroundTask();
        loadMenu.execute();

        String[] columnNames = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2,
                ColumnFactory.getCustomerName(), ColumnFactory.getPhoneNumber(), ColumnFactory.getDoorNumber(),
                ColumnFactory.getBuildingName(),ColumnFactory.getStreetName(), ColumnFactory.getTownName(),
                ColumnFactory.getPostCode()};
        MatrixCursor matrixCursor = new MatrixCursor(columnNames);

        if(customerAddresses != null) {
            String query = uri.getLastPathSegment();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));
            int length = customerAddresses.size();

            if(query.length() < 5){
                return null;
            }

            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++) {
                CustomerAddress customerRow = customerAddresses.get(i);
                if (customerRow.getPhone_number().contains(query)) {
                    String tab = "\t";
                    StringBuilder stringBuilderSuggColOne = new StringBuilder();
                    stringBuilderSuggColOne.append(customerRow.getCustomer_name());
                    stringBuilderSuggColOne.append(tab);
                    stringBuilderSuggColOne.append(customerRow.getPhone_number());

                    StringBuilder stringBuilderSuggColTwo = new StringBuilder();
                    //stringBuilderSuggColTwo.append(tab);
                    stringBuilderSuggColTwo.append(customerRow.getDoor_number());
                    stringBuilderSuggColTwo.append(tab);
                    stringBuilderSuggColTwo.append(customerRow.getBuilding_name());
                    stringBuilderSuggColTwo.append(tab);
                    stringBuilderSuggColTwo.append(customerRow.getStreet_name());
                    stringBuilderSuggColTwo.append(tab);
                    stringBuilderSuggColTwo.append(customerRow.getTown_name());
                    stringBuilderSuggColTwo.append(tab);
                    stringBuilderSuggColTwo.append(customerRow.getPost_code());
                    //matrixCursor.addRow(new Object[]{i, stringBuilder, i});
                    matrixCursor.addRow(new Object[]{i, stringBuilderSuggColOne, stringBuilderSuggColTwo, customerRow.getCustomer_name(),
                            customerRow.getPhone_number(), customerRow.getDoor_number(), customerRow.getBuilding_name(),
                            customerRow.getStreet_name(), customerRow.getTown_name(), customerRow.getPost_code()});
                }
            }
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private class BackGroundTask extends AsyncTask<String, String, String >{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customerAddresses = myDB.getCustomer();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }


} // End of Class
