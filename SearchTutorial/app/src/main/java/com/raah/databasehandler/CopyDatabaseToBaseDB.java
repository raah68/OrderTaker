
package com.raah.databasehandler;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDatabaseToBaseDB {
    private final String DBNAME = "restaurant.sqlite";
    private final String DBLOCATION = "/data/data/com.raah.searchtutorial/databases/";

    public boolean copyDatabase(Context mContext){
        try {
            InputStream inputStream = mContext.getAssets().open(DBNAME);
            String outFileName = DBLOCATION + DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int lenght = 0;
            while ((lenght = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0 , lenght);

            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity", "DB Copied");
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
