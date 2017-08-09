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

import com.raah.databasehandler.SqliteHandler;

import java.util.ArrayList;

/**
 * Created by Rajon on 27/01/2017.
 * Menu Provider
 */

public class menuProvider extends ContentProvider {
    SqliteHandler myDB;
    public ArrayList<String> menu;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        myDB = new SqliteHandler(getContext());
        Backgroundtask loadMenu = new Backgroundtask();
        loadMenu.execute();

        MatrixCursor matrixCursor = new MatrixCursor(new String[] {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        });

        if(menu != null){
            String query = uri.getLastPathSegment();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));
            int length = menu.size();

            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++){
                String menuItem = menu.get(i);
                if(menuItem.toLowerCase().contains(query)){
                    matrixCursor.addRow(new Object[] {i,menuItem,i});
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

    private class Backgroundtask extends AsyncTask<String, String, String >{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            menu = myDB.getAllMenu();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }


} // End of Class
