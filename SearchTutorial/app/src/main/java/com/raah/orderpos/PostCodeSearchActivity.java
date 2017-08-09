package com.raah.orderpos;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.print.PrintManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.raah.customobjects.ColumnFactory;
import com.raah.customobjects.CustomerAddress;
import com.raah.customobjects.GlobalMethods;

public class PostCodeSearchActivity extends AppCompatActivity implements SearchView.OnSuggestionListener {
    EditText customerName, phoneNumber, doorNumber, buildingName, streetName, townName, postCode, instruction;
    SearchView searchView;
    public static Activity postCodeSearchActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postCodeSearchActivity = this;
        stopPhoneNumberSearchActivity();

        setContentView(R.layout.activity_post_code_search);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.5), (int)(height));
    }//End of function onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        activateSearchView(menu);
        return true;
    }//End of onCreateOptionsMenu(menu)

    public void activateSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search_menu);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, PostCodeSearchActivity.class)));
        searchView.setOnSuggestionListener(this);
    } // End of activateSearchView(menu)

    public void startSearchPhoneNumberPopUpWindow(View view){
        startActivity(new Intent(PostCodeSearchActivity.this, PhoneNumberSearchActivity.class));
    }

    public void stopPhoneNumberSearchActivity(){
        if(PhoneNumberSearchActivity.phoneNumberActivity.getWindow().isActive()){
            PhoneNumberSearchActivity.phoneNumberActivity.finish();
        }
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }//end of method onSuggestionSelect()

    @Override
    public boolean onSuggestionClick(int position) {
        CursorAdapter cursorAdapter = searchView.getSuggestionsAdapter();
        Cursor cursor = cursorAdapter.getCursor();
        final CustomerAddress customerAddressForInDialogue = getCustomerFromCursor(cursor, position);

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this, R.style.DialogeTheme);
        alertDialogue.setTitle("Confirm Address");
        alertDialogue.setView(dialogLayout);

        setCustomerToView(dialogLayout, customerAddressForInDialogue);

        alertDialogue.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), OrderPOSMainActivity.class);
                        CustomerAddress customerAddress = new CustomerAddress(null, null, null, null, null, null, null, null);
                        customerAddress.setCustomer_name(customerName.getText().toString());
                        customerAddress.setPhone_number(phoneNumber.getText().toString());
                        customerAddress.setDoor_number(doorNumber.getText().toString());
                        customerAddress.setBuilding_name(buildingName.getText().toString());
                        customerAddress.setStreet_name(streetName.getText().toString());
                        customerAddress.setTown_name(townName.getText().toString());
                        customerAddress.setPost_code(postCode.getText().toString());
                        customerAddress.setInstruction(instruction.getText().toString());
                        intent.putExtra("customerAddress", customerAddress );
                        startActivity(intent);
                    }
                });
        alertDialogue.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        AlertDialog ad = alertDialogue.create();
        GlobalMethods posAndNegButtons = new GlobalMethods(getApplication());
        posAndNegButtons.setAlertDialogueButtons(ad);
        ad.show();

        return true;
    }//end of method onSuggestionClick()

    private CustomerAddress getCustomerFromCursor(Cursor cursor, int position){
        cursor.moveToPosition(position);
        CustomerAddress customerAddress = new CustomerAddress(null, null, null, null, null, null, null, null);
        customerAddress.setBuilding_name(cursor.getString(cursor.getColumnIndex(ColumnFactory.getBuildingName())));
        customerAddress.setStreet_name(cursor.getString(cursor.getColumnIndex(ColumnFactory.getStreetName())));
        customerAddress.setTown_name(cursor.getString(cursor.getColumnIndex(ColumnFactory.getTownName())));
        customerAddress.setPost_code(cursor.getString(cursor.getColumnIndex(ColumnFactory.getPostCode())));

        return customerAddress;
    }//end of method getCustomerFromCursor()

    private void setCustomerToView(View dialogLayout, CustomerAddress customerAddress){
        customerName = (EditText) dialogLayout.findViewById(R.id.edt_customerName);
        customerName.setText(customerAddress.getCustomer_name());
        phoneNumber = (EditText) dialogLayout.findViewById(R.id.edt_customerPhoneNumber);
        phoneNumber.setText(customerAddress.getPhone_number());
        doorNumber = (EditText) dialogLayout.findViewById(R.id.edt_doorNumber);
        doorNumber.setText(customerAddress.getDoor_number());
        buildingName = (EditText) dialogLayout.findViewById(R.id.edt_buildingName);
        buildingName.setText(customerAddress.getBuilding_name());
        streetName = (EditText) dialogLayout.findViewById(R.id.edt_streetName);
        streetName.setText(customerAddress.getStreet_name());
        townName = (EditText) dialogLayout.findViewById(R.id.edt_townName);
        townName.setText(customerAddress.getTown_name());
        postCode = (EditText) dialogLayout.findViewById(R.id.edt_postCode);
        postCode.setText(customerAddress.getPost_code());
        instruction = (EditText) dialogLayout.findViewById(R.id.edt_instruction);
    }//end of method setCustomerToView()
}//end of class
