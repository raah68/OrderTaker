package com.raah.fragraments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.raah.customadpater.MenuListAdapter;
import com.raah.customobjects.Product;
import com.raah.databasehandler.SqliteHandler;
import com.raah.orderpos.OrderPOSMainActivity;
import com.raah.orderpos.R;

import java.util.ArrayList;

/**
 * Created by Rajon on 03/03/2017.
 * TabListViewContents
 */

class TabListViewContents extends OrderPOSMainActivity{
    private SqliteHandler myDb;
    private Context mContext;

    TabListViewContents(Context mContext) {
        this.mContext = mContext;
        myDb = new SqliteHandler(mContext);
    }

    void createListView(ListView menuListView, String header, String query){
        if(header !=null){
            TextView menuListViewOneHeader = getHeader(header, mContext);
            menuListView.addHeaderView(menuListViewOneHeader);
        }
        //menuListView.setDivider(null);
        ArrayList<Product> menuListData = myDb.getProductForListView(query);
        if(menuListData.size() > 0){
            MenuListAdapter adapter = new MenuListAdapter(mContext, menuListData, mainContext);
            menuListView.setAdapter(adapter);
        }
    }

    private TextView getHeader(String headerName, Context mContext){
        TextView view = new TextView(mContext);
        view.setText(headerName);
        view.setTextSize(20);
        view.setAllCaps(true);
        view.setGravity(Gravity.CENTER);
        view.setPadding(1,1,1,1);
        view.setTypeface(view.getTypeface(), Typeface.BOLD);
        return view;
    }

} // End of class