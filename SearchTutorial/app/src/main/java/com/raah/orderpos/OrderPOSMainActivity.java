package com.raah.orderpos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.raah.customadpater.CustomAdapter;
import com.raah.customobjects.ColumnFactory;
import com.raah.customobjects.CustomerAddress;
import com.raah.customobjects.GlobalMethods;
import com.raah.customobjects.Product;
import com.raah.customobjects.ReceiptObj;
import com.raah.fragraments.MenuFragment;

//import android.app.Fragment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrderPOSMainActivity extends AppCompatActivity {
    Button btnReceipt, btnMenu, btnSearchCustomer, btnTableNumber,
            st, tan, hosp, redGreen, moil, makh, pass, mas, kor, ach, bal,
            rez, jal, garl, cur, korm, kash, bhun, dup, pal, rog, dhan,
            pat, mad, vind, bir, veg, ric, bre, sun;
    ListView lvShoppingList;
    static public CustomAdapter myAdapter;
    static public ArrayList<Product> myData = new ArrayList<>();
    static public ReceiptObj receipt;
    View listViewFooter;
    LayoutInflater layoutInflater;
    TextView businessAddress, inOrOut, footerCustomerName, footerCustomerNumber, footerCustomerAddress, footerInstruction, display;
    EditText footerSubtotal,  footerTotal, footerDiscountPercent, footerDiscountPrice;
    public Context mainContext;
    LinearLayout linearLayoutReceipt, linearLayoutMenu, linearLayoutSearchCustomer;
    ArrayList<Button> arrowButtons = new ArrayList<>();
    GlobalMethods globalMethods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContext = getApplicationContext();
        setContentView(R.layout.activity_orderpos_main);

        inOrOut = (TextView) findViewById(R.id.txv_inOut);
        globalMethods = new GlobalMethods(getApplication());

        //Loading List view
        lvShoppingList = (ListView) findViewById(R.id.lstv_shoppingList);
        lvShoppingList.setDivider(null);
        //sortReceipt();
        myAdapter = new CustomAdapter(this, myData);
        lvShoppingList.setAdapter(myAdapter);

        //FooterView --- Customer Address display text
        loadFooterView();
        setBusinessAddress();
        inflateArrowButtons();

        // Methods
        menuFrameView();
        giveDiscount();

        //Setting customer to the receipt
        CustomerAddress customerAddress = getIntent().getParcelableExtra("customerAddress");
        if(customerAddress != null){
            setCustomerToFooterView(customerAddress);
        }//end of setting customer to the receipt
    }//end of method onCreate()

    //FrameView Containers
    public void menuFrameView(){
        linearLayoutMenu  = (LinearLayout) findViewById(R.id.linearLayout_Menu);
        MenuFragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString("header", "Starters / Appetizer");
        bundle.putString("query", "st");
        fragment = new MenuFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.linearLayout_Menu, fragment);
        ft.commit();
        setVisibilityOnFrame(linearLayoutMenu);
        setArrowsVisible(st);
    }//end of method menuFrameView()

    //On Click action to display menu items
    public void changeFragment(View view){
        MenuFragment fragment;
        if(view == findViewById(R.id.btn_starters)){
            setFragment("Starters / Appetizer", "st", st);

        } else if(view == findViewById(R.id.btn_TandooriDish)){
            setFragment("Tandoori Dish", "tan", tan);

        } else if(view == findViewById(R.id.btn_HouseSpecial)){
            setFragment("House Special", "hspecial", hosp);

        } else if(view == findViewById(R.id.btn_RedGreenCurry)){
            setFragment("Red/Green Curry", "redgreencurry", redGreen);

        } else if(view == findViewById(R.id.btn_Moilee)){
            setFragment("South Indian Moilee", "southindianmoilee", moil);

        } else if(view == findViewById(R.id.btn_Makhani)){
            setFragment("Makhani", "makhani", makh);

        } else if(view == findViewById(R.id.btn_Passanda)){
            setFragment("Passanda", "passanda", pass);

        } else if(view == findViewById(R.id.btn_Masala)){
            setFragment("Masala", "masala", mas);

        } else if(view == findViewById(R.id.btn_Korai)){
            setFragment("Korai", "korai", kor);

        } else if(view == findViewById(R.id.btn_Achari)){
            setFragment("Achari", "achari", ach);

        } else if(view == findViewById(R.id.btn_Balti)){
            setFragment("Balti", "balti", bal);

        } else if(view == findViewById(R.id.btn_Rezala)){
            setFragment("Rezala", "rezala", rez);

        } else if(view == findViewById(R.id.btn_Jalfrezi)){
            setFragment("Jalfrezi", "jalfrezi", jal);

        } else if(view == findViewById(R.id.btn_GarlicChilli)){
            setFragment("Garlic Chilli", "garlicchilli", garl);

        } else if(view == findViewById(R.id.btn_Curry)){
            setFragment("Traditional Curry", "curry", cur);

        } else if(view == findViewById(R.id.btn_Korma)){
            setFragment("Korma", "korma", korm);

        } else if(view == findViewById(R.id.btn_Kashmir)){
            setFragment("Kashmir", "kashmir", kash);

        } else if(view == findViewById(R.id.btn_Bhuna)){
            setFragment("Bhuna", "bhuna", bhun);

        } else if(view == findViewById(R.id.btn_Dupiaza)){
            setFragment("Duiaza", "dupiaza", dup);

        } else if(view == findViewById(R.id.btn_Palek)){
            setFragment("Palek", "palek", pal);

        } else if(view == findViewById(R.id.btn_Rogan)){
            setFragment("Rogan", "rogan", rog);

        } else if(view == findViewById(R.id.btn_Dhansak)){
            setFragment("Dhansak", "dhansak", dhan);

        } else if(view == findViewById(R.id.btn_Pathia)){
            setFragment("Pathia", "pathia", pat);

        } else if(view == findViewById(R.id.btn_Madras)){
            setFragment("Madras", "madras", mad);

        } else if(view == findViewById(R.id.btn_Vindaloo)){
            setFragment("Vindaloo", "vindaloo", vind);

        } else if(view == findViewById(R.id.btn_Biryani)){
            setFragment("Biryani", "biryani", bir);

        } else if(view == findViewById(R.id.btn_VegetableSideDish)){
            setFragment("Vegetable Side Dish", "vegsidedish", veg);

        } else if(view == findViewById(R.id.btn_Rice)){
            setFragment("Rice", "rice", ric);

        } else if(view == findViewById(R.id.btn_Breads)){
            setFragment("Breads", "breads", bre);

        } else if(view == findViewById(R.id.btn_Sundries)){
            setFragment("Sundries", "sundries", sun);
        } else {
            fragment = null;
        }
    }//end of method changeFragment()

    //method to open fragment
    private void setFragment(String header, String query, Button arrow){
        MenuFragment fragment;
        Bundle bundle = new Bundle();
        bundle.putString(globalMethods.FRAGMENT_HEADER, header);
        bundle.putString(globalMethods.FRAGMENT_QUERY, query);
        fragment = new MenuFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.linearLayout_Menu, fragment);
        ft.commit();
        setVisibilityOnFrame(linearLayoutMenu);
        setArrowsVisible(arrow);
    }

    public void startSearchPopUpWindow(View view){
        startActivity(new Intent(OrderPOSMainActivity.this, PhoneNumberSearchActivity.class));
    }//end of method startSearchPopUpWindow()

    //Open the Menu Fragment
    private void openMenu(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutMenu.setVisibility(View.VISIBLE);
            }
        });
    }//end of method openMenu()


    // Load Receipt footer
    private void loadFooterView(){
        LinearLayout receiptFooter = (LinearLayout) findViewById(R.id.layout_footer);
        // Get inflater service to inflate a view
        layoutInflater = getLayoutInflater();
        listViewFooter = layoutInflater.inflate(R.layout.listview_footer, receiptFooter, true);
        footerSubtotal = (EditText) listViewFooter.findViewById(R.id.edtv_subtotal_price);
        footerTotal = (EditText) listViewFooter.findViewById(R.id.edtx_totalPrice);
        footerDiscountPrice = (EditText) listViewFooter.findViewById(R.id.edtx_discountPrice);
        footerDiscountPercent = (EditText) listViewFooter.findViewById(R.id.edtx_discountPercent);
        footerCustomerName = (TextView) receiptFooter.findViewById(R.id.txv_customerName);
        footerCustomerNumber = (TextView) receiptFooter.findViewById(R.id.txv_customerNumber);
        footerCustomerAddress = (TextView) listViewFooter.findViewById(R.id.txv_customerAddress);
        footerInstruction = (TextView) listViewFooter.findViewById(R.id.txv_instruction);

    }//end of method loadFooterView

    //Set Business Address on the Receipt
    private void setBusinessAddress() {
        businessAddress = (TextView) findViewById(R.id.txv_displayBusinessAddress);
        businessAddress.setText("Beckenham Curry Club" + "\n" +
                                "94 Croydon Road, Beckenham" +
                                "\n" +
                                "020 8675 6678");
    }//end of method setBusinessAddress()

    /**
     * Enlarging the Toast message
     * @param message string message to resize
     */
    static public SpannableStringBuilder bigTextForToast(String message) {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(message);
        biggerText.setSpan(new RelativeSizeSpan(1.35f), 0, message.length(), 0);
        return biggerText;
    }//end of the method

    //Method to redraw the adapter if it is empty
    public void refreshAdapter(){
        if(myData.isEmpty() || myAdapter.isEmpty()){
            lvShoppingList.setAdapter(myAdapter);
        }
    }//end of method refreshAdapter()

    public void calculateSubTotal(){
        BigDecimal sum = new BigDecimal(0);
        if(myData.size() > 0){
            for(Product product : myData){
                sum = sum.add(product.getProduct_price());
            }
            String subTotalPrice = sum.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            footerSubtotal.setText(subTotalPrice);
        } else {
            footerSubtotal.setText(String.valueOf(new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP).toString()));
        }
    }//end of method calculateSubTotal()

    public void calculateDiscount(){
        BigDecimal subTotal = new BigDecimal(footerSubtotal.getText().toString());
        BigDecimal hundredPercent = new BigDecimal(100);
        BigDecimal discount = new BigDecimal(0);
        BigDecimal footerDiscountValue;

        if(footerDiscountPercent.getText().toString().length() == 0){
            footerDiscountValue = discount;
            footerDiscountPercent.setText(String.valueOf(discount));

        } else {
            footerDiscountValue = new BigDecimal(footerDiscountPercent.getText().toString());
        }

        BigDecimal calculateDiscount = subTotal.multiply(footerDiscountValue).divide(hundredPercent, RoundingMode.CEILING);
        BigDecimal roundedPrice = calculateDiscount.setScale(1, RoundingMode.HALF_DOWN).setScale(2, RoundingMode.CEILING);
        footerDiscountPrice.setText(String.valueOf(roundedPrice.toString()));
    }//end of the method calculateDiscount()

    public void calculateTotal(){
        BigDecimal subTotalForTotalCalc = new BigDecimal(footerSubtotal.getText().toString());
        BigDecimal discountedAmount = new BigDecimal(footerDiscountPrice.getText().toString());
        BigDecimal totalCalculation = subTotalForTotalCalc.subtract(discountedAmount, new MathContext(2, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_UP);
        footerTotal.setText(String.valueOf(totalCalculation.toString()));
    }//end of method calculateTotal()

    private void giveDiscount(){
        footerDiscountPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0 ){
                    calculateDiscount();
                    calculateTotal();
                }
            }
        });
    }//end of method giveDiscount()

    private void setVisibilityOnFrame(View view){
        if(view != null){
            if(view == linearLayoutMenu){
                linearLayoutMenu.setVisibility(View.VISIBLE);
            } else {
                linearLayoutMenu.setVisibility(View.GONE);
            }
        } else {
            linearLayoutMenu.setVisibility(View.GONE);
        }
    } // end of function setVisibilityOnFrame()

    //Set customer to footer view method
    private void setCustomerToFooterView(CustomerAddress customerAddress){
        footerCustomerNumber.setText(customerAddress.getPhone_number());
        footerCustomerName.setText(customerAddress.getCustomer_name());
        footerCustomerAddress.setText(ColumnFactory.getAddressForView(customerAddress));
        footerInstruction.setText(customerAddress.getInstruction());
    }//end of method setCustomerToFooterView()

    //Inflating the arrow buttons
    private void inflateArrowButtons(){

        st = (Button) findViewById(R.id.btn_St);
        arrowButtons.add(st);

        tan = (Button) findViewById(R.id.btn_Tan);
        arrowButtons.add(tan);

        hosp = (Button) findViewById(R.id.btn_Hosp);
        arrowButtons.add(hosp);

        redGreen = (Button) findViewById(R.id.btn_RedGreen);
        arrowButtons.add(redGreen);

        moil = (Button) findViewById(R.id.btn_Moil);
        arrowButtons.add(moil);

        makh = (Button) findViewById(R.id.btn_Makh);
        arrowButtons.add(makh);

        pass = (Button) findViewById(R.id.btn_Pass);
        arrowButtons.add(pass);

        mas = (Button) findViewById(R.id.btn_Mas);
        arrowButtons.add(mas);

        kor = (Button) findViewById(R.id.btn_Kor);
        arrowButtons.add(kor);

        ach = (Button) findViewById(R.id.btn_Ach);
        arrowButtons.add(ach);

        bal = (Button) findViewById(R.id.btn_Bal);
        arrowButtons.add(bal);

        rez = (Button) findViewById(R.id.btn_Rez);
        arrowButtons.add(rez);

        jal = (Button) findViewById(R.id.btn_Jal);
        arrowButtons.add(jal);

        garl = (Button) findViewById(R.id.btn_Garl);
        arrowButtons.add(garl);

        cur = (Button) findViewById(R.id.btn_Cur);
        arrowButtons.add(cur);

        korm = (Button) findViewById(R.id.btn_Korm);
        arrowButtons.add(korm);

        kash = (Button) findViewById(R.id.btn_Kash);
        arrowButtons.add(kash);

        bhun = (Button) findViewById(R.id.btn_Bhun);
        arrowButtons.add(bhun);

        dup = (Button) findViewById(R.id.btn_Dup);
        arrowButtons.add(dup);

        pal = (Button) findViewById(R.id.btn_Pal);
        arrowButtons.add(pal);

        rog = (Button) findViewById(R.id.btn_Rog);
        arrowButtons.add(rog);

        dhan = (Button) findViewById(R.id.btn_Dhan);
        arrowButtons.add(dhan);

        pat = (Button) findViewById(R.id.btn_Pat);
        arrowButtons.add(pat);

        mad = (Button) findViewById(R.id.btn_Mad);
        arrowButtons.add(mad);

        vind = (Button) findViewById(R.id.btn_Vind);
        arrowButtons.add(vind);

        bir = (Button) findViewById(R.id.btn_Bir);
        arrowButtons.add(bir);

        veg = (Button) findViewById(R.id.btn_Veg);
        arrowButtons.add(veg);

        ric = (Button) findViewById(R.id.btn_Ric);
        arrowButtons.add(ric);

        bre = (Button) findViewById(R.id.btn_Bre);
        arrowButtons.add(bre);

        sun = (Button) findViewById(R.id.btn_Sun);
        arrowButtons.add(sun);
    }//end of method inflateArrowButtons()

    //Method to set the arrow visible when the menu buttons are clicked on
    private void setArrowsVisible(View view){
        for(int i = 0; i < arrowButtons.size(); i++){
            if(arrowButtons.get(i)==view){
                view.setVisibility(View.VISIBLE);
                view.setSelected(true);
            } else {
                arrowButtons.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }//end of the method setArrowsVisible()

    //Method to set the table number on the receipt
    public void setTableNumber(View view){
        if(view == findViewById(R.id.btn_tableNumber)){
            AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this, R.style.DialogeTheme);
            alertDialogue.setTitle("Enter Table Number");
            final EditText enterTableNumber = new EditText(this);
            enterTableNumber.setHint("Table Number");
            enterTableNumber.setGravity(Gravity.CENTER);
            enterTableNumber.setTextColor(Color.WHITE);
            final EditText enterCustomerNumber = new EditText(this);
            enterCustomerNumber.setHint("Number of Customer");
            enterCustomerNumber.setGravity(Gravity.CENTER);
            enterCustomerNumber.setTextColor(Color.WHITE);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(enterTableNumber);
            linearLayout.addView(enterCustomerNumber);
            alertDialogue.setView(linearLayout);

            alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            inOrOut.setText("Table: "+
                                    enterTableNumber.getText()+
                                    " Customer: " +
                                    enterCustomerNumber.getText());
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
    }//end of method setTableNumber()

    //Method to set collection on the receipt
    public void setCollectionOnReceipt(View view){
        if(view == findViewById(R.id.btn_collection)){
            AlertDialog.Builder alertDialogue = new AlertDialog.Builder(this, R.style.DialogeTheme);
            alertDialogue.setTitle("Collection Details");
            final EditText customerName = new EditText(this);
            customerName.setHint("Customer Name");
            customerName.setGravity(Gravity.CENTER);
            customerName.setTextColor(Color.WHITE);
            final EditText telephoneNumber = new EditText(this);
            telephoneNumber.setHint("Telephone Number");
            telephoneNumber.setGravity(Gravity.CENTER);
            telephoneNumber.setTextColor(Color.WHITE);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(customerName);
            linearLayout.addView(telephoneNumber);
            alertDialogue.setView(linearLayout);

            alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            inOrOut.setText("Collection for: "+
                                    customerName.getText()+
                                    " - " +
                                    telephoneNumber.getText());
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
            //inOrOut.setText("Collection");
        }
    }//end of method setCollectionOnReceipt

    public void printDocument(View view) {

        //PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        //String jobName = this.getString(R.string.app_name) +" Document";
        //printManager.print(jobName, new CustomPrinting(this),null);
        receipt = new ReceiptObj(footerSubtotal.getText().toString(),
                footerDiscountPrice.getText().toString(), footerDiscountPercent.getText().toString(),
                footerCustomerName.getText().toString(),
                footerCustomerNumber.getText().toString(), footerCustomerAddress.getText().toString(),
                footerInstruction.getText().toString(), myAdapter);
    }






} // End of Class
