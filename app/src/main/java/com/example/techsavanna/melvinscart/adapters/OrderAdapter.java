package com.example.techsavanna.melvinscart.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;

import java.util.List;
import java.util.StringTokenizer;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderA> orderList;
    private Context contexts;
    DatabaseHelper db;
    String ProductName,ProductAmount,ProductCategory,ProductQty;

    public OrderAdapter(List<OrderA> orderList, Context contexts) {
        this.orderList = orderList;
        this.contexts = contexts;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(contexts).inflate(R.layout.order_row,viewGroup, false);
        db=new DatabaseHelper(contexts);
        orderList=db.getAllOrder();
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {
        OrderA orderItem = orderList.get(i);

        System.out.println("Get Values"+orderItem);
       // OrderA item = getItem(i);
     //   List<OrderItem> names=db.getAllProductInCart();
    //    System.out.println("get items"+names);

     //   List<OrderA> namess=db.getAllOrder();
//
       // OrderA orderA=new OrderA();
       // System.out.println("Items json:"+orderA.jsonObject());
//        for(final OrderA item:namess){
//
//            System.out.println("Items Names:"+item);
//        }
//        String s=namess.toString();
//
//
//        StringTokenizer st = new StringTokenizer(s, "[");
//
//        String community = st.nextToken();
//        StringTokenizer stt = new StringTokenizer(community, "]");
//
//        String communityy = stt.nextToken();
//
//        System.out.println("names cc :"+s);


//        StringTokenizer values = new StringTokenizer(communityy, ",");
//        String ordername = values.nextToken();
//        String orderunitprice = values.nextToken();
//        String orderqtycarton = values.nextToken();
//        String orderqtypiece = values.nextToken();
//
//        System.out.println("values :"+ordername+" "+orderunitprice+" "+orderqtycarton+" "+orderqtypiece);

//            for(final OrderItem item:names){
//            ProductAmount=item.getAmount();
//            ProductName=item.getName();
//            ProductCategory=item.getCategory();
//            ProductQty=item.getQuatity();
//
//            orderViewHolder.name.setText(ProductName);
//            orderViewHolder.category.setText(ProductCategory);
//            orderViewHolder.amount.setText(ProductAmount);
//            orderViewHolder.quatity.setText(ProductQty);
//        }
//
            orderViewHolder.name.setText(orderItem.getOrder_name());
            orderViewHolder.category.setText(orderItem.getQtycarton());
            orderViewHolder.amount.setText(orderItem.getOrder_unit_price());
            orderViewHolder.quatity.setText(orderItem.getQtypiece());

//            orderViewHolder.name.setText(ordername);
//            orderViewHolder.category.setText(orderqtypiece);
//            orderViewHolder.amount.setText(orderunitprice);
//            orderViewHolder.quatity.setText(orderqtycarton);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView name,category,amount,quatity;
        Button save;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.name);
            category= (TextView) itemView.findViewById(R.id.category);
            amount= (TextView) itemView.findViewById(R.id.amout);
            quatity= (TextView) itemView.findViewById(R.id.quatity);

        }
    }
}
