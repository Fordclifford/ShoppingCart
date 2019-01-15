package com.example.techsavanna.melvinscart;

import android.app.SearchManager;
import android.content.Context;
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

import com.example.techsavanna.melvinscart.adapters.CustomerAdapter;
import com.example.techsavanna.melvinscart.helper.Product;
import com.example.techsavanna.melvinscart.helper.ApiInterfaceProduct;
import com.example.techsavanna.melvinscart.helper.ApiProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Product> productList;
    private CustomerAdapter customerAdapter;
    private ApiInterfaceProduct apiInterfaceProduct;
    ProgressBar progressBar;
    TextView search;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_order);
        progressBar = findViewById(R.id.prograss);
        recyclerView = findViewById(R.id.recyclerViewqo);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchProduct("prod", "m");
    }
    public void fetchProduct(String type, String key) {

        apiInterfaceProduct = ApiProduct.getApiProduct().create(ApiInterfaceProduct.class);

        Call<List<Product>> call = apiInterfaceProduct.getProduct(type, key);


        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {


                progressBar.setVisibility(View.GONE);
                productList = response.body();
                customerAdapter = new CustomerAdapter(productList, QuickOrder.this);
                recyclerView.setAdapter(customerAdapter);
                customerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Toast.makeText(QuickOrder.this, "Error\n" + t.toString(), Toast.LENGTH_LONG).show();
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
                fetchProduct("prod", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchProduct("prod", newText);
                return false;
            }
        });
        return true;
    }
}
