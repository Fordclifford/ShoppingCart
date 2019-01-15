package com.example.techsavanna.melvinscart.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;
import com.example.techsavanna.melvinscart.helper.MySharedPreference;
import com.example.techsavanna.melvinscart.helper.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class CheckRecyclerViewAdapter extends RecyclerView.Adapter<CheckRecyclerViewAdapter.CheckRecyclerViewHolder> {

    private Context context;
    private MySharedPreference sharedPreference;
    private Gson gson;
    private List<Product> productList;
DatabaseHelper db;
    SharedPreferences msharedPreference;

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }
    public CheckRecyclerViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList= productList;
    }

    @Override
    public CheckRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_layout, parent, false);
        CheckRecyclerViewHolder productHolder = new CheckRecyclerViewHolder(layoutView);
        db=new DatabaseHelper(parent.getContext());
        sharedPreference = new MySharedPreference(parent.getContext());
        return productHolder;
    }

    @Override
    public void onBindViewHolder(final CheckRecyclerViewHolder holder, final int position) {
        //get product quantity
        final  Product product=productList.get(position);

        System.out.println("product desc"+product);
        //holder.txtQty.setText("1");
        holder.productName.setText(productList.get(position).getProductname());
        holder.productPrice.setText(String.valueOf(productList.get(position).getUnit_price()) + "KES");
        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        System.out.println("the following:"+gson);
        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=productList.get(position).getProductname();

                db.deleteOrderQty(name);
                Toast.makeText(context, "Do you want to remove product from cart", Toast.LENGTH_LONG).show();
                // int cartValue = Integer.parseInt(gson.toJson(product.getProduct_id()));
                //  int cartValue=position;
                // System.out.println("detailesss:"+cartValue);
                //sharedPreference.removeProductToTheCart(cartValue);
                productList.remove(position);
                notifyItemRemoved(position);



               // msharedPreference.edit().remove(String.valueOf(position));
            }
        });

        holder.editcarton.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        holder.editcarton.setBackgroundResource(R.drawable.txtinputfocus);
        holder.editcarton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                holder.editcarton.setBackgroundResource(R.drawable.txtinputfocus);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.editcarton.getText().toString().matches("")) {
                    //ViewCompat.setBackground(holder.editcarton,Adr);

                    int carton = Integer.parseInt(holder.editcarton.getText().toString());

                    int t = productList.get(position).getUnit_price().intValue() * carton;
                    holder.productPrice.setText(String.valueOf(t) );

                    String name=productList.get(position).getProductname();
                    db.updateOrderCartonQty(name,carton);

                    db.updateOrderUnitprice(name,t);

                    if(mOnDataChangeListener != null){
                        mOnDataChangeListener.onDataChanged(productList.size());
                    }
                }
            }
        });

    }
});




        holder.editpeice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.editpeice.getText().toString().matches("")) {
                    int piece = Integer.parseInt(holder.editpeice.getText().toString());


                    int amounteach = productList.get(position).getUnit_price().intValue();

                    int value=amounteach/piece;

                    int amount = Integer.parseInt(holder.productPrice.getText().toString());
                    int t = amount + value;
                    holder.productPrice.setText(String.valueOf(t));

                    String name=productList.get(position).getProductname();
                    db.updateOrderPieceQty(name,piece);

                    db.updateUnitprice(name,t);

                    if(mOnDataChangeListener != null){
                        mOnDataChangeListener.onDataChanged(productList.size());
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });



//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.txtQty.setText(String.valueOf((Integer.parseInt(holder.txtQty.getText().toString()))+1));
//                if (Integer.parseInt(holder.txtQty.getText().toString()) > 0) {
//                    //Calculating Total Amount
//                    int t = productList.get(position).getUnit_price().intValue() * Integer.parseInt(holder.txtQty.getText().toString());
//                   holder.productPrice.setText(String.valueOf(t)+" KES");
//
//                    String name=productList.get(position).getProductname();
//                    int qty=Integer.parseInt(String.valueOf(holder.txtQty.getText()));
//                    db.updateQty(name,qty);
//
//                    db.updateUnitprice(name,t);
//
//                    if(mOnDataChangeListener != null){
//                        mOnDataChangeListener.onDataChanged(productList.size());
//                    }
//
//                }
//            }
//        });
//        //Decreasing Quantity
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // TextView qty = (TextView) row.findViewById(R.id.txtQty);
//                if (Integer.parseInt(holder.txtQty.getText().toString()) > 0) {
//                    holder.txtQty.setText(String.valueOf((Integer.parseInt(holder.txtQty.getText().toString()))-1));
//                    //Calculating Total Amount
//                    int t = productList.get(position).getUnit_price().intValue() * Integer.parseInt(holder.txtQty.getText().toString());
//                  holder.productPrice.setText(String.valueOf(t)+" KES");
//
//                    String name=productList.get(position).getProductname();
//                    int qty=Integer.parseInt(String.valueOf(holder.txtQty.getText()));
//                    db.updateQty(name,qty);
//
//                    db.updateUnitprice(name,t);
//                    if(mOnDataChangeListener != null){
//                        mOnDataChangeListener.onDataChanged(productList.size());
//                    }
//
//                }
//            }
//        });

//        if (position == productList.size()) {
//            holder.subTotal.setText((int) getTotalPrice(productList));
//        }else {
//           // holder.price.setText(productList.get(position).getPrice());
//        }

    }
    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }
    public double getTotalPrice(List<Product> productList) {
        double total = 0;
        for (int i = 0; i < productList.size(); i++) {
            Product pObject = productList.get(i);
          //  total += productList.get(i);
            total = total + pObject.getUnit_price();
        }
        return total;
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }


   public class CheckRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView  productName, productPrice, removeProduct,txtQty,subTotal;
        public EditText editcarton,editpeice;

       ImageView plus,minus;
        public CheckRecyclerViewHolder(View itemView) {
            super(itemView);

            productName =(TextView)itemView.findViewById(R.id.product_name);
            productPrice = (TextView)itemView.findViewById(R.id.product_price);
            removeProduct = (TextView)itemView.findViewById(R.id.remove_from_cart);
            editcarton=itemView.findViewById(R.id.cartons);
            editpeice=itemView.findViewById(R.id.pieces);
            subTotal = (TextView )itemView.findViewById(R.id.sub_total);

        }


    }

}
