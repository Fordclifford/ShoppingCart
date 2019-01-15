package com.example.techsavanna.melvinscart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.techsavanna.melvinscart.adapters.DashAapter;
import com.example.techsavanna.melvinscart.adapters.Dashboard;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private List<Dashboard> dashboardList;
    private Context context;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        rv=(RecyclerView)findViewById(R.id.rv);

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();

    }
    private void initializeData(){
        dashboardList= new ArrayList<>();
        dashboardList.add(new Dashboard("My Route", R.drawable.map));
        dashboardList.add(new Dashboard("My report",  R.drawable.report));
        dashboardList.add(new Dashboard("Synchronize",  R.drawable.sync));
        dashboardList.add(new Dashboard("Log out",  R.drawable.logout));
    }
    private void initializeAdapter(){
      DashAapter adapter = new DashAapter(dashboardList);

        rv.setAdapter(adapter);

    }

}
