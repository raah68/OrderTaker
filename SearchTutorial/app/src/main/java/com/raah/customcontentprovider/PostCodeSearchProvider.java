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
import com.raah.databasehandler.SqliteHandler;

import java.util.ArrayList;

/**
 * Created by Rajon on 31/03/2017.
 * Phone Number Search Provider
 */

public class PostCodeSearchProvider extends ContentProvider {
    SqliteHandler myDB;
    public ArrayList<Address> postCodeArray;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        myDB = new SqliteHandler(getContext());
        BackGroundTask loadPostCode = new BackGroundTask();
        loadPostCode.execute();

        String[] columnName = {BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_TEXT_2, ColumnFactory.getBuildingName(),
                ColumnFactory.getStreetName(), ColumnFactory.getTownName(),
                ColumnFactory.getPostCode()};
        MatrixCursor matrixCursor = new MatrixCursor(columnName);

        if(postCodeArray != null){
            String query = uri.getLastPathSegment().toString();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));
            int length = postCodeArray.size();

            if(query.length() < 3){
                return null;
            }
            String myQuery = query.substring(0, 3)+" "+query.substring(3, query.length());
            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++){
                Address addressRow = postCodeArray.get(i);
                if(addressRow.getPOST_CODE().toLowerCase().contains(myQuery)){
                    String tab = "\t";
                    StringBuilder stringBuilderSuggColumnTwo = new StringBuilder();
                    stringBuilderSuggColumnTwo.append(addressRow.getBUILDING_NAME());
                    stringBuilderSuggColumnTwo.append(tab);
                    stringBuilderSuggColumnTwo.append(addressRow.getSTREET_NAME());
                    stringBuilderSuggColumnTwo.append(tab);
                    stringBuilderSuggColumnTwo.append(addressRow.getTOWN_NAME());
                    matrixCursor.addRow(new Object[] {i, addressRow.getPOST_CODE(), stringBuilderSuggColumnTwo,
                            addressRow.getBUILDING_NAME(), addressRow.getSTREET_NAME(),addressRow.getTOWN_NAME(),
                            addressRow.getPOST_CODE()});
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
            postCodeArray = myDB.getAddress();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }


} // End of Class
