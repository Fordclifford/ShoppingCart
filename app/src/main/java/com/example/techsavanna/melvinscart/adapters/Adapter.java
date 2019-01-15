package com.example.techsavanna.melvinscart.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.SingleCustomer;
import com.example.techsavanna.melvinscart.helper.Contact;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;

import java.util.List;

import static com.example.techsavanna.melvinscart.SingleCustomer.CUSTOMER_CITY;
import static com.example.techsavanna.melvinscart.SingleCustomer.CUSTOMER_FIRSTNAME;
import static com.example.techsavanna.melvinscart.SingleCustomer.CUSTOMER_PHONE;
import static com.example.techsavanna.melvinscart.SingleCustomer.CUSTOMER_STREET;
import static com.example.techsavanna.melvinscart.SingleCustomer.CUSTOMER_lASTNAME;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Contact> contactsList;
    private Context contexts;
    private DatabaseHelper db;

    public Adapter(List<Contact> contactsList, Context context) {
        this.contactsList = contactsList;
        this.contexts = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        db = new DatabaseHelper(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (AppStatus.getInstance(contexts.getApplicationContext()).isOnline()) {
            final Contact contact = contactsList.get(position);
            System.out.println("Data detiles" + contact.getFirstName());
            // holder.cardView.set;
            holder.firstname.setText(contact.getFirstName());
            holder.lastname.setText(contact.getLastName());
            holder.city.setText(contact.getCity());
            holder.street.setText(contact.getStreet());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(contexts, SingleCustomer.class);
                        intent.putExtra(CUSTOMER_FIRSTNAME, contact.getFirstName());
                        intent.putExtra(CUSTOMER_lASTNAME, contact.getLastName());
                        intent.putExtra(CUSTOMER_CITY, contact.getCity());
                        intent.putExtra(CUSTOMER_STREET, contact.getStreet());
                        intent.putExtra(CUSTOMER_PHONE, contact.getPhone());

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        contexts.startActivity(intent);
                    }
                }
            });
            db.addName(contact.getFirstName(), contact.getLastName(), contact.getCity(), contact.getStreet(), contact.getPhone());
            Toast.makeText(contexts.getApplicationContext(), "WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Internet is NOT available, Toast It!
             */
           List<Contact> names=db.getNames();

            for(final Contact item:names){
                System.out.println("Fetch Data:" + item);
                holder.firstname.setText(item.getFirstName());
                holder.lastname.setText(item.getLastName());
                holder.city.setText(item.getCity());
                holder.street.setText(item.getStreet());

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position != RecyclerView.NO_POSITION) {
                            Intent intent = new Intent(contexts, SingleCustomer.class);
                            intent.putExtra(CUSTOMER_FIRSTNAME, item.getFirstName());
                            intent.putExtra(CUSTOMER_lASTNAME, item.getLastName());
                            intent.putExtra(CUSTOMER_CITY, item.getCity());
                            intent.putExtra(CUSTOMER_STREET, item.getStreet());
                            intent.putExtra(CUSTOMER_PHONE, item.getPhone());

                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            contexts.startActivity(intent);
                        }
                    }
                });

            }



            Toast.makeText(contexts.getApplicationContext(), "Ooops! No WiFi/Mobile Networks Connected!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstname, lastname, city, street;

        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lasttname);
            city = itemView.findViewById(R.id.city);
            street = itemView.findViewById(R.id.street);
            cardView=itemView.findViewById(R.id.cardveiw);

        }
    }
}
