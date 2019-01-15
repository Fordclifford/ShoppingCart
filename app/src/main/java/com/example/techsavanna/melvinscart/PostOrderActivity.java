package com.example.techsavanna.melvinscart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.techsavanna.melvinscart.adapters.OrderA;
import com.example.techsavanna.melvinscart.adapters.OrderAdapter;
import com.example.techsavanna.melvinscart.adapters.OrderItem;
import com.example.techsavanna.melvinscart.app.AppController;
import com.example.techsavanna.melvinscart.helper.Contact;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;
import com.example.techsavanna.melvinscart.helper.MySharedPreference;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PostOrderActivity extends AppCompatActivity {

    TextView name,category,amount,quatity;
    FloatingActionButton save;
    JSONObject newjson;
    String currency_code,productname;
    String myUrl="http://192.168.1.233/melvinscart/retrofit/index.php";
    OrderAdapter orderAdapter;
    JSONObject jsonObject;
    List<OrderA> orderItemList;
    private MySharedPreference sharedPreference;
    private RecyclerView mList;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
     DatabaseHelper db;

    String FirstName,LastName,City,Street,Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_order);

        mList = findViewById(R.id.recyclerVieworder);
        orderItemList = new ArrayList<>();

        adapter = new OrderAdapter(orderItemList,getApplicationContext());

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());
        db = new DatabaseHelper(this);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
        getData();

        save = findViewById(R.id.fab);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference = new MySharedPreference(PostOrderActivity.this);
                String productsFromCart = sharedPreference.retrieveProductFromCart();

                System.out.println("Method hhhh:"+productsFromCart.toString());
                try {
                    JSONArray array = new JSONArray(productsFromCart);

//                    List<Contact> names=orderItemList.;
//                    for(final Contact item:array){
//                        FirstName=item.getFirstName();
//                        LastName=item.getLastName();
//                        City=item.getCity();
//                        Street=item.getStreet();
//                        Phone=item.getPhone();
//
//
//                    }

                    System.out.println("array new"+array);
                    for (int i = 0; i < array.length(); i++) {
                        newjson= array.getJSONObject(i);
                        OrderItem orderItem = new OrderItem();
                        //  orderItem.senewjson.get("currency_code").toString();
//                        orderItem.setFirstName(FirstName);
//                        orderItem.setLastName(LastName);
//                        orderItem.setCity(City);
//                        orderItem.setStreet(Street);
//                        orderItem.setPhone(Phone);
                        orderItem.setCategory(newjson.get("productcategory").toString());
                        orderItem.setName(newjson.get("productname").toString());
                        orderItem.setQuatity( newjson.get("qtyinstock").toString());
                        orderItem.setAmount( newjson.get("unit_price").toString());
                        System.out.println("data about:"+newjson.get("productcategory").toString());

                        Gson gson = new Gson();
                        String json = gson.toJson(orderItem);
                        jsonObject=new JSONObject(json);
                        System.out.println(jsonObject+",");
                       //jsonObject=array.toJSONObject(array);
                        System.out.println("New loop test"+jsonObject);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, myUrl, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(PostOrderActivity.this,response.toString(),Toast.LENGTH_LONG);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                    }

                        //System.out.println("New loop"+jsonObject);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(PostOrderActivity.this);
                builder1.setMessage("Order Posted Succesifully.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Ok</font>")
                        ,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                PostOrderActivity.this.startActivity(new Intent(PostOrderActivity.this, CheckoutActivity.class));


                               // dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                    Toast.makeText(PostOrderActivity.this,"O",Toast.LENGTH_LONG);

            }});

//        obj = jsonarray.toJSONObject(jsonarray);
//        System.out.println("Json request"+obj);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Gson gson = new GsonBuilder()
//                        .setLenient()
//                        .create();
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://192.168.1.251")
//                        .client(client)
//                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .build();
//                ApiService service = retrofit.create(ApiService.class);
//                Orders student = new Orders();
//                student.setTotal(name.getText().toString());
//                student.setCustomerno(amount.getText().toString());
//                student.setSubject(quatity.getText().toString());
//                Call<Orders> call = service.insertData(student.getTotal(),student.getCustomerno(),student.getSubject());
//
//                call.enqueue(new Callback<Orders>() {
//                    @Override
//                    public void onResponse(Call<Orders> call, Response<Orders> response) {
//
//                        Toast.makeText(PostOrderActivity.this, "response"+response, Toast.LENGTH_LONG).show();
//
//                        name.setText("");
//                       amount.setText("");
//                      quatity.setText("");
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Orders> call, Throwable t) {
//
//
//                        Log.i("Hello",""+t);
//                        Toast.makeText(PostOrderActivity.this, "Throwable"+t, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//            }
//        });

    }



    private void getData(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        sharedPreference = new MySharedPreference(PostOrderActivity.this);
        String productsFromCart = sharedPreference.retrieveProductFromCart();
        try {
            JSONArray array = new JSONArray(productsFromCart);
            System.out.println("Array length:"+array.length());
            System.out.println("Array :"+array);
            for (int i = 0; i<array.length();i++) {
                JSONObject newjson = array.getJSONObject(i);
               OrderA orderItem = new OrderA();
              //  orderItem.senewjson.get("currency_code").toString();
                orderItem.setQtypiece(newjson.get("productcategory").toString());
                orderItem.setOrder_name(newjson.get("productname").toString());
                orderItem.setQtycarton( newjson.get("qtyinstock").toString());
                orderItem.setOrder_unit_price( newjson.get("unit_price").toString());
                System.out.println("data about:"+newjson.get("productcategory").toString());
               orderItemList.add(orderItem);

//                amount.setText(unit_price+"  "+currency_code);
//                name.setText(productname);
//                category.setText(productcategory);
//                quatity.setText(qtyinstock);
//
 }
        System.out.println("My dataa" + currency_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent MainActivityIntent = new Intent(PostOrderActivity.this,CheckoutActivity.class);
        startActivity(MainActivityIntent);
        finish();
        super.onBackPressed();
    }
}
