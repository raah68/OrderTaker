package com.raah.fragraments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.itextpdf.text.List;
import com.raah.customobjects.GlobalMethods;
import com.raah.orderpos.R;

/**
 * Created by Rajon on 01/03/2017.
 * Fragment for tab1
 */

public class MenuFragment extends Fragment {
    ListView menuListView, menuListViewExtra;
    String headerParam, menuQueryParam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.menu_list_view_fragment, container, false);
        GlobalMethods globalMethods = new GlobalMethods(this.getContext());
        TabListViewContents tabListViewContents = new TabListViewContents(getActivity());
        headerParam = getArguments().getString(globalMethods.FRAGMENT_HEADER);
        menuQueryParam = getArguments().getString(globalMethods.FRAGMENT_QUERY);

        menuListView = (ListView) v.findViewById(R.id.lstv_menuListView);
        tabListViewContents.createListView(menuListView, headerParam, menuQueryParam);

        return v;
    }//end of onCreateView()

}//end of class
