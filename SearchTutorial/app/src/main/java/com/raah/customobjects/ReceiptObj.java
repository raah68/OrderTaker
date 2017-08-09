package com.raah.customobjects;

import android.widget.EditText;
import android.widget.TextView;

import com.raah.customadpater.CustomAdapter;
import com.raah.orderpos.R;

import java.util.ArrayList;

/**
 * Created by Rajon on 09/08/2017.
 * Object to store all the footer details
 */

public class ReceiptObj {
    private String _subtotal;
    private String _discountPrice;
    private String _discountPercent;
    private String _customerName;
    private String _customerNumber;
    private String _customerAddress;
    private String _instruction;
    private CustomAdapter _adapter;

    public ReceiptObj(String subtotal,
                      String discountPrice,
                      String discountPercent,
                      String customerName,
                      String customerNumber,
                      String customerAddress,
                      String instruction,
                      CustomAdapter adapter) {
        this._subtotal = subtotal;
        this._discountPrice = discountPrice;
        this._discountPercent = discountPercent;
        this._customerName = customerName;
        this._customerNumber = customerNumber;
        this._customerAddress = customerAddress;
        this._instruction = instruction;
        this._adapter = adapter;
    }//end of constructor()

    public String get_subtotal() {
        return _subtotal;
    }

    public void set_subtotal(String _subtotal) {
        this._subtotal = _subtotal;
    }

    public String get_discountPrice() {
        return _discountPrice;
    }

    public void set_discountPrice(String _discountPrice) {
        this._discountPrice = _discountPrice;
    }

    public String get_discountPercent() {
        return _discountPercent;
    }

    public void set_discountPercent(String _discountPercent) {
        this._discountPercent = _discountPercent;
    }

    public String get_customerName() {
        return _customerName;
    }

    public void set_customerName(String _customerName) {
        this._customerName = _customerName;
    }

    public String get_customerNumber() {
        return _customerNumber;
    }

    public void set_customerNumber(String _customerNumber) {
        this._customerNumber = _customerNumber;
    }

    public String get_customerAddress() {
        return _customerAddress;
    }

    public void set_customerAddress(String _customerAddress) {
        this._customerAddress = _customerAddress;
    }

    public String get_instruction() {
        return _instruction;
    }

    public void set_instruction(String _instruction) {
        this._instruction = _instruction;
    }

    public CustomAdapter get_adapter() {
        return _adapter;
    }

    public void set_adapter(CustomAdapter adapter) {
        this._adapter = adapter;
    }
}//end of class
