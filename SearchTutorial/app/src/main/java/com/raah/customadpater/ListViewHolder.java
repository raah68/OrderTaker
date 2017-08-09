package com.raah.customadpater;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.raah.customobjects.Product;

import java.math.BigDecimal;




public class ListViewHolder {
    public TextView product_quantity;
    public TextView product_name;
    public TextView pound_symbol;
    public TextView product_price;
    public Button product_delete;
    public Button product_add;
    public TextView product_type;
    public int position;

    public void setDataIntoViewHolder(Product product, int position){
        position = position;
        product_quantity.setText(String.valueOf(product.getProduct_quantity()));
        product_name.setText(product.getProduct_name());
        String price = product.getProduct_price().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        product_price.setText(price);
        //product_type.setText(product.getProduct_type());
    }


}
