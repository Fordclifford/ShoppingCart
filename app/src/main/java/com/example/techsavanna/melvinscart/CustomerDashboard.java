package com.example.techsavanna.melvinscart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.techsavanna.melvinscart.adapters.CustomerDash;
import com.example.techsavanna.melvinscart.adapters.CustomerDashAdapter;
import com.example.techsavanna.melvinscart.adapters.DashAapter;
import com.example.techsavanna.melvinscart.adapters.Dashboard;

import java.util.ArrayList;
import java.util.List;


public class CustomerDashboard extends AppCompatActivity {
    private List<CustomerDash> customerDashboardsList;
    private Context context;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        rv=(RecyclerView)findViewById(R.id.rv);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

    }
    private void initializeData(){
      customerDashboardsList= new ArrayList<>();
        customerDashboardsList.add(new CustomerDash("Stock Take", R.drawable.map));
        customerDashboardsList.add(new CustomerDash("Quick Order",  R.drawable.report));
        customerDashboardsList.add(new CustomerDash("Sales History",  R.drawable.sync));
        customerDashboardsList.add(new CustomerDash("Delivery",  R.drawable.logout));
        customerDashboardsList.add(new CustomerDash("Save GPS Location", R.drawable.map));
        customerDashboardsList.add(new CustomerDash("Customer Notes",  R.drawable.report));
        customerDashboardsList.add(new CustomerDash("Customer Care",  R.drawable.sync));
    }
    private void initializeAdapter(){
       CustomerDashAdapter adapter = new CustomerDashAdapter(customerDashboardsList);

        rv.setAdapter(adapter);

    }
}
