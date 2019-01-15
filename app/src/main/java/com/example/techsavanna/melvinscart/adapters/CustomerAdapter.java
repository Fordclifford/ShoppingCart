package com.example.techsavanna.melvinscart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.techsavanna.melvinscart.CartActivity;
import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.entities.ProductObject;
import com.example.techsavanna.melvinscart.helper.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.techsavanna.melvinscart.CartActivity.PRODUCT_CURRENCY;
import static com.example.techsavanna.melvinscart.CartActivity.PRODUCT_NAME;
import static com.example.techsavanna.melvinscart.CartActivity.PRODUCT_PRICE;
import static com.example.techsavanna.melvinscart.CartActivity.PRODUCT_QINS;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Product> productList;
    private Context contexts;

    public CustomerAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.contexts = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, final int position) {

        final  Product product=productList.get(position);
        // holder.cardView.set;
        holder.productname.setText(product.getProductname());
        holder.productprice.setText(product.getUnit_price().toString());
        holder.productcurrency.setText(product.getCurrency_code());
        holder.producttype.setText(product.getQtyinstock());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != RecyclerView.NO_POSITION){
                  Intent intent=new Intent(contexts,CartActivity.class);

//
//                    intent.putExtra(PRODUCT_NAME, product.getProductname());
//                    intent.putExtra(PRODUCT_PRICE, product.getUnit_price());
//                    intent.putExtra(PRODUCT_CURRENCY, product.getCurrency_code());
//                    intent.putExtra(PRODUCT_QINS, product.getQtyinstock());

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    String stringObjectRepresentation = gson.toJson(product);

                   intent.putExtra("PRODUCT", stringObjectRepresentation);
//                    intent.putExtra(CUSTOMER_CITY,contact.getCity());
//                    intent.putExtra(CUSTOMER_STREET, contact.getStreet());
//                    intent.putExtra(CUSTOMER_PHONE, contact.getPhone());

//              intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               contexts.startActivity(intent);
                    contexts.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView productname, productprice, producttype, productcurrency;

        CardView cardView;
        public CustomerViewHolder(View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.ProdName);
            productprice = itemView.findViewById(R.id.price);
            producttype = itemView.findViewById(R.id.type);
            productcurrency= itemView.findViewById(R.id.currency);
            cardView=itemView.findViewById(R.id.cardveiw);

        }
    }



}
