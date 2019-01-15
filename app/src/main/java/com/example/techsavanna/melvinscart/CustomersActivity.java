package com.example.techsavanna.melvinscart;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techsavanna.melvinscart.adapters.Adapter;
import com.example.techsavanna.melvinscart.adapters.AppStatus;
import com.example.techsavanna.melvinscart.helper.ApiClient;
import com.example.techsavanna.melvinscart.helper.ApiInterface;
import com.example.techsavanna.melvinscart.helper.Contact;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Contact> contacts;
    private Adapter adapter;
    private ApiInterface apiInterface;
    ProgressBar progressBar;
    DatabaseHelper db;
    TextView search;
    String[] item;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        db = new DatabaseHelper(this);
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            fetchContact("user", "");
        }
        else {
            offlineContact();
        }
 }
    public void offlineContact(){
        List<Contact> contacts=db.getNames();


        progressBar.setVisibility(View.GONE);
        progressBar.setBackgroundColor(Color.BLUE);
        adapter = new Adapter(contacts, CustomersActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void fetchContact(String type, String key) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Contact>> call = apiInterface.getContact(type, key);


        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {


                progressBar.setVisibility(View.GONE);
                progressBar.setBackgroundColor(Color.BLUE);
                contacts = response.body();
                adapter = new Adapter(contacts, CustomersActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Toast.makeText(CustomersActivity.this, "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //System.out.print("Errot new ::"+query);
                fetchContact("user", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchContact("user", newText);
                return false;
            }
        });
        return true;
    }
}
