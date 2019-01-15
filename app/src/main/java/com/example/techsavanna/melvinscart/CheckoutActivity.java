package com.example.techsavanna.melvinscart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techsavanna.melvinscart.adapters.CheckRecyclerViewAdapter;
import com.example.techsavanna.melvinscart.entities.ProductObject;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;
import com.example.techsavanna.melvinscart.helper.MySharedPreference;
import com.example.techsavanna.melvinscart.helper.Product;
import com.example.techsavanna.melvinscart.helper.SimpleDividerItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private static final String TAG = CheckoutActivity.class.getSimpleName();

    private RecyclerView checkRecyclerView;

    private TextView subTotal,txtQty;

    private double mSubTotal = 0;

    DatabaseHelper db;

    private MySharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("Check Out");

        subTotal = (TextView )findViewById(R.id.sub_total);

        checkRecyclerView = (RecyclerView)findViewById(R.id.checkout_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckoutActivity.this);
        checkRecyclerView.setLayoutManager(linearLayoutManager);
        checkRecyclerView.setHasFixedSize(true);
        checkRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CheckoutActivity.this));
        sharedPreference = new MySharedPreference(this);
        // get content of cart
        MySharedPreference mShared = new MySharedPreference(CheckoutActivity.this);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Product[] addCartProducts = gson.fromJson(mShared.retrieveProductFromCart(), Product[].class);
        List<Product> productList = convertObjectArrayToListObject(addCartProducts);
       // System.out.println("Error 202:"+productList);
        CheckRecyclerViewAdapter mAdapter = new CheckRecyclerViewAdapter(CheckoutActivity.this, productList);
        checkRecyclerView.setAdapter(mAdapter);
        db=new DatabaseHelper(this);

     mSubTotal = getTotalPrice(productList);
     subTotal.setText("Subtotal excluding tax and shipping: " + String.valueOf(mSubTotal) + " KES");
        mAdapter.setOnDataChangeListener(new CheckRecyclerViewAdapter.OnDataChangeListener(){
            public void onDataChanged(int size){
                //do whatever here
                int total=db.totalamount();
                System.out.println("my total is:"+total);
                subTotal.setText("Subtotal excluding tax and shipping: " + String.valueOf(total) + " KES");
            }
        });


        Button shoppingButton = (Button)findViewById(R.id.shopping);
        assert shoppingButton != null;
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingIntent = new Intent(CheckoutActivity.this, QuickOrder.class);
                startActivity(shoppingIntent);
                finish();
            }
        });

        Button checkButton = (Button)findViewById(R.id.checkout);
        assert checkButton != null;
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(CheckoutActivity.this, MpesaCheckoutActivity.class);
                paymentIntent.putExtra("TOTAL_PRICE", mSubTotal);
                startActivity(paymentIntent);
                finish();
            }
        });
    }

    private List<Product> convertObjectArrayToListObject(Product[] allProducts){
        List<Product> mProduct = new ArrayList<Product>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    private int returnQuantityByProductName(String productName, List<Product> mProducts){
        int quantityCount = 0;
        for(int i = 0; i < mProducts.size(); i++){
            Product pObject = mProducts.get(i);
            if(pObject.getProductname().trim().equals(productName.trim())){
                quantityCount++;
            }
        }
        return quantityCount;
    }

    private double getTotalPrice(List<Product> mProducts){
        double totalCost = 0;

        for(int i = 0; i < mProducts.size(); i++){
            Product pObject = mProducts.get(i);
           // System.out.println("Error 202:"+pObject);
            totalCost = totalCost + pObject.getUnit_price();
        }
        return totalCost;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.tab_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.remove) {

            sharedPreference.removeProductToTheCart();

//        } else if () {
//            startActivity(new Intent(this, PreferencesUnlockScreen.class));
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
