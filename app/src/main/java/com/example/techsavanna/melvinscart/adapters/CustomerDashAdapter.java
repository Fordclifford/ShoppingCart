package com.example.techsavanna.melvinscart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techsavanna.melvinscart.QuickOrder;
import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.StockTake;

import java.util.List;

public class CustomerDashAdapter extends RecyclerView.Adapter<CustomerDashAdapter.CustomerDashViewHolder> {
    List<CustomerDash> customerDashList;
    public CustomerDashAdapter(List<CustomerDash> customerDashList) {

        this.customerDashList = customerDashList;
    }
    //    public MyArrayAdapter(Context con, String[] countries) {
//        // TODO Auto-generated constructor stub
//        mcon = con;
//        COUNTRIES_ = countries;
//        mInflater = LayoutInflater.from(con);
//    }
    @NonNull
    @Override
    public CustomerDashViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_dashboard, viewGroup, false);
        CustomerDashViewHolder pvh = new CustomerDashViewHolder(v);
        // List<DashAapter> contactModelList = new ArrayList<>()

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerDashViewHolder dashViewHolder, int i) {

        // dashViewHolder.currentItem = dashboards.get(i);
        dashViewHolder.dashName.setText(customerDashList.get(i).name);
        dashViewHolder.dashPhoto.setImageResource(customerDashList.get(i).photoId);

//        dashViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(view.getContext(), CustomersActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return customerDashList.size();
    }



    public class CustomerDashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        private Context context;
        TextView dashName;
        ImageView dashPhoto;
        public CustomerDashViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            dashName= (TextView)itemView.findViewById(R.id.person_name);
            dashPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i=getPosition();
            switch (i){
                case 0:
                    Intent intent = new Intent(v.getContext(), StockTake.class);
                    v.getContext().startActivity(intent);
                    break;

                case 1:
                    Intent intentt = new Intent(v.getContext(), QuickOrder.class);
                    v.getContext().startActivity(intentt);
                    break;
            }
        }
    }
}
