package com.raah.customobjects;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.raah.databasehandler.SqliteHandler;
import com.raah.orderpos.OrderPOSMainActivity;
import com.raah.orderpos.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Rajon on 17/04/2017.
 * Class contains global static methods
 */

public class GlobalMethods {
    private Context mContext;
    private Product newProduct;
    public String FRAGMENT_HEADER = "header";
    public String FRAGMENT_QUERY = "query";
    public GlobalMethods(Context context) {
        this.mContext = context;
    }//end of constructor

    //Method to set buttons in the alert dialogue
    public void setAlertDialogueButtons(AlertDialog alertDialog) {
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Context context = mContext;

                //Designing the OK and Cancel button of the dialog box
                Button negButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                negButton.setBackgroundColor(context.getResources().getColor(R.color.colorMenuButtonThree));
                negButton.setTextColor(context.getResources().getColor(R.color.colorWhite));

                Button posButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                posButton.setBackgroundColor(context.getResources().getColor(R.color.colorMenuButtonThree));
                posButton.setTextColor(context.getResources().getColor(R.color.colorWhite));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                //Setting margins to space out the buttons
                params.setMargins(100, 0, 200, 0);
                negButton.setLayoutParams(params);
                posButton.setLayoutParams(params);
            }
        });
    }//end of method setAlertDialogueButtons()

    //Method to get single item price from the data base
    public BigDecimal getPriceForProduct(String productName) {
        BigDecimal retVal = new BigDecimal(0);
        SqliteHandler db = new SqliteHandler(mContext);
        Product prod = db.getProduct(productName.toLowerCase().trim());
        if (prod != null) {
            retVal = prod.getProduct_price();
        }
        return retVal;
    }//end of getPriceForProduct() method

    // Method to access the data array list for the shopping list view
    public ArrayList<Product> getDataContainer() {
        return OrderPOSMainActivity.myData;
    }//end of method getDataContainer()

    public void setDialogueOnMenuItems(final Product oldProduct) {
        ArrayList<String> itemList = initialiseSpinnerList(oldProduct.getProduct_name().toLowerCase());
        newProduct = new Product(null, null, null, 1);
        newProduct.setProduct_name(oldProduct.getProduct_name());
        newProduct.setProduct_price(oldProduct.getProduct_price());

        if (itemList.size() > 0) {
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Choose Samosa");
            dialog.show();
            dialog.getWindow().setLayout(500, 400);
            dialog.setCanceledOnTouchOutside(true);
            Spinner dropDownList = (Spinner) dialog.findViewById(R.id.spinner);

            List<String> uniqueItemTypes = new ArrayList<>(new LinkedHashSet<>(itemList));
            dropDownList.setAdapter(new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_dropdown_item, uniqueItemTypes));


            dropDownList.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                    newProduct.setProduct_name(oldProduct.getProduct_name()+" ("+parent.getSelectedItem().toString()+")");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button okButton = (Button) dialog.findViewById(R.id.btn_okDialog);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItemsToDataArray(newProduct, oldProduct);
                    dialog.dismiss();
                }
            });
        }
    }//end of setDialogOnMenuItems() method

    private ArrayList<String> initialiseSpinnerList(String typeName) {
        ArrayList<String> itemType = new ArrayList<>();
        if (typeName.equalsIgnoreCase("samosa")) {
            itemType.add("Meat");
            itemType.add("Chicken");
            itemType.add("Vegetable");
            return itemType;
        } else if (typeName.equalsIgnoreCase("tikka") || typeName.equalsIgnoreCase("shashlik")) {
            itemType.add("Chicken");
            itemType.add("Lamb");
            return itemType;
        }
        else {
            return itemType;
        }
    }//end of initialiseSinnerList() method

    private void addItemsToDataArray(Product newProduct, Product oldProduct) {
        ArrayList<Product> myData = getDataContainer();
        Product existingProduct = compare(newProduct);
        if (existingProduct == null){
            newProduct.setProduct_quantity(1);
            newProduct.setProduct_price(getPriceForProduct(oldProduct.getProduct_name()));
            getDataContainer().add(newProduct);
            OrderPOSMainActivity.myAdapter.notifyDataSetChanged();
        } else {
            existingProduct.setProduct_quantity(Math.max(0, existingProduct.getProduct_quantity() + 1));
            OrderPOSMainActivity.myAdapter.notifyDataSetChanged();
        }
    }//end of method addItemsToDataArray()

    public Product compare(Product product){
        Product retVal = null;
        ArrayList<Product> myData = getDataContainer();
        if(myData.size() > 0){
            for(Product prod : myData){
                if(prod.getProduct_name().equalsIgnoreCase(product.getProduct_name())){
                    retVal = prod;
                    break;
                } else {
                    retVal = null;
                }
            }
        } else {
            retVal = null;
        }
        return retVal;
    }//end of method compare()

}//end of class


