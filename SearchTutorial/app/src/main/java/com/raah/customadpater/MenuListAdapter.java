package com.raah.customadpater;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.raah.customobjects.GlobalMethods;
import com.raah.customobjects.Product;
import com.raah.databasehandler.SqliteHandler;
import com.raah.orderpos.OrderPOSMainActivity;
import com.raah.orderpos.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Rajon on 28/02/2017.
 * Adapter for the list
 */

public class MenuListAdapter extends ArrayAdapter<Product> {
    private Context mContext;
    private ArrayList<Product> productList;
    private Button itemNames, itemAdd;
    private LayoutInflater inflater;
    public ArrayList<Product> myData = new ArrayList<>();
    GlobalMethods globalMethods;

    // Constructor
    public MenuListAdapter(Context mContext, ArrayList<Product> productList, Context mainActivityContext) {
        super(mContext, R.layout.listview_menu_structure, productList);
        this.mContext = mContext;
        this.productList = productList;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.globalMethods = new GlobalMethods(mContext);
    }

    @Nullable
    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = convertView;
        Product prod = getItem(position);

        if (v == null) {
            v = inflater.inflate(R.layout.listview_menu_structure, parent, false);
            itemNames = (Button) v.findViewById(R.id.btn_item);
            //itemAdd = (Button) v.findViewById(R.id.btn_add);
            String productNameAndPrice = (prod != null) ? prod.getProduct_name()+"\t"+"\t(£"+prod.getProduct_price().setScale(2, RoundingMode.HALF_DOWN).toString()+")"  : null;
            itemNames.setText(productNameAndPrice);
            itemNames.setId(position);
            v.setTag(position);
        } else {
            convertView.getTag();
            v.setTag(productList.get(position));
        }

        // Add items to receipt
        if(itemNames != null) {
            itemNames.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product selectedItem = productList.get(v.getId());
                    ArrayList<Product> myData = globalMethods.getDataContainer();
                    Product matchedProduct = productMatched(selectedItem, myData);
                    if (myData.size() == 0 || matchedProduct == null) {
                        Product prodToAdd = new Product(selectedItem.getProduct_type(), selectedItem.getProduct_name(),
                                            globalMethods.getPriceForProduct(selectedItem.getProduct_name()),
                                            selectedItem.product_id);
                        myData.add(prodToAdd);
                        OrderPOSMainActivity.myAdapter.sort(new Comparator<Product>() {
                            @Override
                            public int compare(Product o1, Product o2) {
                                return o1.product_id > o2.product_id ? 1 : (o1.product_id < o2.product_id ? -1 : 0);
                            }
                        });
                        OrderPOSMainActivity.myAdapter.notifyDataSetChanged();
                    } else {
                        matchedProduct.setProduct_quantity(matchedProduct.getProduct_quantity() + 1);
                        OrderPOSMainActivity.myAdapter.notifyDataSetChanged();
                    }
                }
            });
        }//end of add items action
        return v;
    }

    //Method to check if the item already exists in the item array
    private Product productMatched(Product product, ArrayList<Product> productList){
        Product retVal = null;
        for (Product prod : productList){
            if(prod.getProduct_name().contentEquals(product.getProduct_name())){
                retVal = prod;
            }
        }
        return retVal;
    }

}//end of Class
