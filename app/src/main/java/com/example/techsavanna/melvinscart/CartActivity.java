package com.example.techsavanna.melvinscart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techsavanna.melvinscart.entities.ProductObject;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;
import com.example.techsavanna.melvinscart.helper.MySharedPreference;
import com.example.techsavanna.melvinscart.helper.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = CartActivity.class.getSimpleName();
    public static final String  PRODUCT_NAME = "productname";
    public static final String  PRODUCT_PRICE= "productprice";
    public static final String  PRODUCT_CURRENCY= "currencycode";
    public static final String  PRODUCT_QINS="qtyinstock";
    TextView productnametxt,productpricetxt;

    String productname,productprice;
    Button btncart;
DatabaseHelper db;
    private Gson gson;
    private int cartProductNumber = 0;
    private MySharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPreference = new MySharedPreference(CartActivity.this);
db=new DatabaseHelper(this);
//       productname=getIntent().getExtras().getString(PRODUCT_NAME);
//
//        // System.out.print("First:name: "+firstname);
//        productprice=getIntent().getExtras().getString(PRODUCT_PRICE);

        productnametxt=findViewById(R.id.productname);
        productpricetxt=findViewById(R.id.price);
        btncart=findViewById(R.id.cart);

       // final Product quatity=gson.fromJson(quatityitem,Product.class);
        productpricetxt.setText(productprice);
        productnametxt.setText(productname);

        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();

        final String productInStringFormat = getIntent().getExtras().getString("PRODUCT");
        ArrayList<String> prod=new ArrayList<>();

          System.out.println("String Itemms:"+productInStringFormat);
        final Product singleProduct = gson.fromJson(productInStringFormat, Product.class);


       // System.out.println("test 2:"+singleProduct);
        if(singleProduct != null){
           // setTitle(singleProduct.getProductname());
            //
            productpricetxt.setText("Price: " + String.valueOf(new Double(singleProduct.getUnit_price()).intValue()) + " KES");
           productnametxt.setText(singleProduct.getProductname());

        }

        assert  btncart!= null;

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //increase product count

                List<Product> cartProduct = new ArrayList<Product>();

                System.out.println("the data:"+singleProduct);

                String ordername=singleProduct.getProductname();
                String orderunitprice= String.valueOf(singleProduct.getUnit_price());
                String ordercarton="1";
                String orderpiece="1";

                db.addOrder(ordername,orderunitprice,ordercarton,orderpiece);

                String productsFromCart = sharedPreference.retrieveProductFromCart();
                System.out.println("what is here:"+productsFromCart);

                if(productsFromCart.equals("")){
                    List<Product> cartProductList = new ArrayList<Product>();
                    // cartProductList.add(quatity);
                    //quatityitem.
                    cartProductList.add(singleProduct);
                    String cartValue = gson.toJson(cartProductList);
                    sharedPreference.addProductToTheCart(cartValue);
                   // sharedPreference.addProductToTheCart(quatityitem);
                    cartProductNumber = cartProductList.size();
                }else{
                    String productsInCart = sharedPreference.retrieveProductFromCart();
                    Product[] storedProducts = gson.fromJson(productsInCart, Product[].class);

                    List<Product> allNewProduct = convertObjectArrayToListObject(storedProducts);
                    allNewProduct.add(singleProduct);
                    String addAndStoreNewProduct = gson.toJson(allNewProduct);
                    sharedPreference.addProductToTheCart(addAndStoreNewProduct);
                    cartProductNumber = allNewProduct.size();
                }
                sharedPreference.addProductCount(cartProductNumber);
                invalidateCart();


            }
        });
    }
    private List<Product> convertObjectArrayToListObject(Product[] allProducts){
        List<Product> mProduct = new ArrayList<Product>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = sharedPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.cart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {

            String productsFromCart = sharedPreference.retrieveProductFromCart();

            if(productsFromCart.equals("")){
                Toast.makeText(CartActivity.this,"The card cannot be empty",Toast.LENGTH_SHORT);
            }else {

                Intent checkoutIntent = new Intent(CartActivity.this, CheckoutActivity.class);

                //checkoutIntent.putExtra("TOTAL_PRICE",quatityitem);
                startActivity(checkoutIntent);
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

}
