package com.raah.customadpater;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.raah.customobjects.GlobalMethods;
import com.raah.customobjects.Product;
import com.raah.orderpos.OrderPOSMainActivity;
import com.raah.orderpos.R;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by Rajon on 07/02/2017.
 * Custom Adapter class
 */

public class CustomAdapter extends ArrayAdapter<Product> {
    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private Context mContext;
    private GlobalMethods globalMethods;

    public CustomAdapter(Context context, ArrayList<Product> product) {
        super(context, R.layout.custom_row, product);
        this.products = product;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.globalMethods = new GlobalMethods(context);

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        final ListViewHolder holder;
        final View view;
        if (convertView == null) {
            holder = new ListViewHolder();
            view = inflater.inflate(R.layout.custom_row, parent, false);
            holder.product_quantity = (TextView) view.findViewById(R.id.txv_quantity);
            holder.product_name = (TextView) view.findViewById(R.id.edtx_itemname);
            holder.pound_symbol = (TextView) view.findViewById(R.id.txv_poundSymbol);
            holder.product_price = (TextView) view.findViewById(R.id.txv_itemprice);
            holder.product_delete = (Button) view.findViewById(R.id.btn_itemDelete);
            holder.product_add = (Button) view.findViewById(R.id.btn_itemAdd);

            // Setting the ID so that the item can be modified
            holder.product_delete.setId(position);
            holder.product_add.setId(position);
            holder.product_quantity.setId(position);
            holder.product_name.setId(position);

            view.setTag(holder);
            holder.position = position;
        }
        else {
            view = convertView;
            holder = (ListViewHolder) view.getTag();
            holder.product_quantity.setId(position);
            holder.product_delete.setId(position);
            holder.product_add.setId(position);
            holder.product_name.setId(position);
        }

        Product pr = getItem(position);
        holder.setDataIntoViewHolder(pr, position);
        calculation();

        //Add items using the plus button
        holder.product_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int posClicked = v.getId();
                Product product = products.get(posClicked);
                int quantityCalc = Math.max(0, product.getProduct_quantity()+1);
                product.setProduct_quantity(quantityCalc);
                updateQuantity(product);
            }
        });//end of add items click action

        //Delete items using the minus button
        holder.product_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int posClicked = v.getId();
                Product product = products.get(posClicked);
                removeItemFromDataArray(product);
            }
        });//end of delete items click action

        //quantity change action
        holder.product_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0 && s.toString().equalsIgnoreCase("0")){
                    s.clear();
                    s.append("1");
                }
                final int posClicked = holder.product_quantity.getId();
                if(s.length() > 0 && Integer.parseInt(s.toString()) > 1){
                    Product productFromProductList = products.get(posClicked);
                    productFromProductList.setProduct_quantity(Integer.parseInt(s.toString()));
                    BigDecimal calculation =  globalMethods.getPriceForProduct(productFromProductList.getProduct_name())
                                              .multiply(new BigDecimal(productFromProductList.getProduct_quantity()));
                    productFromProductList.setProduct_price(calculation);
                    updateQuantity(productFromProductList);
                }
            }
        });//end of quantity change action

        holder.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v != null){
                    final int posClicked = holder.product_name.getId();
                    final Product productFromProductList = products.get(posClicked);
                    AlertDialog.Builder alertDialogue = new AlertDialog.Builder(mContext, R.style.DialogeTheme);
                    alertDialogue.setTitle("Add item description...");
                    final EditText addDescription = new EditText(mContext);
                    addDescription.setGravity(Gravity.CENTER);
                    addDescription.setTextColor(Color.WHITE);
                    LinearLayout linearLayout = new LinearLayout(mContext);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.addView(addDescription);
                    alertDialogue.setView(linearLayout);

                    alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(addDescription.getText().length() > 0){
                                        productFromProductList.setProduct_name(productFromProductList.getProduct_name()+" ("
                                                + addDescription.getText().toString()+")");
                                        updateQuantity(productFromProductList);
                                    }
                                }
                            }
                    );

                    alertDialogue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog ad = alertDialogue.create();
                    globalMethods.setAlertDialogueButtons(ad);
                    ad.show();
                }
            }//end of override method
        });//end of product_name click action

        return view;
    }//end of method getView()

    @Override
    public Product getItem(int position)
    {
        return products.get(position);
    }

    // Main method to remove items from the myData array in the main activity
    private void removeItemFromDataArray(Product product){
        ArrayList<Product> productList = globalMethods.getDataContainer();
        if(product != null){
            if(product.getProduct_quantity()== 1){
                productList.remove(product);
            } else {
                int quantityCalc = Math.max(0, product.getProduct_quantity()-1);
                product.setProduct_quantity(quantityCalc);
                BigDecimal calculation =  globalMethods.getPriceForProduct(product.getProduct_name())
                        .multiply(new BigDecimal(product.getProduct_quantity()));
                product.setProduct_price(calculation);
            }
            updateQuantity(product);
            //notifyAdapter();
            notifyDataSetChanged();
            //((OrderPOSMainActivity) mContext).refreshAdapter();
        }
    }//end of method removeItemFromDataArray()

    // Method to update the quantity field of the product in the myData array in the main activity
    private void updateQuantity(Product product){
        ArrayList<Product> productList = globalMethods.getDataContainer();
            for(Product prod : productList){
                if(product != null && prod == product){
                    prod.setProduct_quantity(product.getProduct_quantity());
                    prod.setProduct_price(product.getProduct_price());
                    //((OrderPOSMainActivity) mContext).sortReceipt();
                    notifyAdapter();
                    ((OrderPOSMainActivity) mContext).refreshAdapter();
                    break;
                }
            }
            if(productList.size() == 0){
                notifyAdapter();
                ((OrderPOSMainActivity) mContext).refreshAdapter();
            }
    }//end of method updateQuantity()

    // Method to notify the adapter in the main activity
    private void notifyAdapter(){
            OrderPOSMainActivity.myAdapter.notifyDataSetChanged();
            calculation();
    }//end of method notifyAdapter()

    private void calculation(){
        if((mContext instanceof OrderPOSMainActivity)){
            ((OrderPOSMainActivity)mContext).calculateSubTotal();
            ((OrderPOSMainActivity)mContext).calculateDiscount();
            ((OrderPOSMainActivity)mContext).calculateTotal();
        }
    }//end of method calculation()

    //Method to hide the add button if item description has been added
    public void hidePlusButton(Product item, View view){
        if(item.getProduct_name().contains("(")){
            view.setEnabled(false);
            view.setVisibility(View.INVISIBLE);
        }
    }//end of method hidePlusButton()

} //End of Custom Class
