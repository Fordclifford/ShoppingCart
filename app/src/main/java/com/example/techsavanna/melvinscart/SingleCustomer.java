package com.example.techsavanna.melvinscart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.techsavanna.melvinscart.helper.Current;
import com.example.techsavanna.melvinscart.helper.CustomerHelper;
import com.example.techsavanna.melvinscart.helper.DatabaseHelper;

import java.util.List;

public class SingleCustomer extends AppCompatActivity {
TextView firstnametxt,lastnametxt,citytxt,streettxt,phonetxt;

    public static final String  CUSTOMER_FIRSTNAME = "firstname";
    public static final String  CUSTOMER_lASTNAME= "lastname";
    public static final String  CUSTOMER_CITY = "city";
    public static final String  CUSTOMER_STREET= "street";
    public static final String  CUSTOMER_PHONE= "phone";
    Button btnconfirm;

    //String FirstName,LastName,City,Street,Phone;
    DatabaseHelper db;

    String firstname,lastname,city,street,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer);

        firstname=getIntent().getExtras().getString(CUSTOMER_FIRSTNAME);

       // System.out.print("First:name: "+firstname);
        lastname=getIntent().getExtras().getString(CUSTOMER_lASTNAME);
        city=getIntent().getExtras().getString(CUSTOMER_CITY);
        street=getIntent().getExtras().getString(CUSTOMER_STREET);
        phone=getIntent().getExtras().getString(CUSTOMER_PHONE);
        db = new DatabaseHelper(this);

        firstnametxt=findViewById(R.id.firstname);
        citytxt=findViewById(R.id.city);
        streettxt=findViewById(R.id.location);
        phonetxt=findViewById(R.id.phone);

        btnconfirm=findViewById(R.id.confirmbtn);

        firstnametxt.setText(firstname+" "+lastname);
        citytxt.setText(city);
        streettxt.setText(street);
        phonetxt.setText(phone);

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SingleCustomer.this);

                builder.setTitle("Confirmation");
                builder.setIcon(R.drawable.logo);
                builder.setMessage("Confirm location with either of the following");
                builder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>GPS</font>"),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                SingleCustomer.this.startActivity(new Intent(SingleCustomer.this, MapsActivityCurrentPlace.class));
finish();
                            }
                        });

                builder.setNeutralButton(Html.fromHtml("<font color='#FF7F27'>MANUAL</font>"),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                SingleCustomer.this.startActivity(new Intent(SingleCustomer.this, CustomerDashboard.class));
                                //dialog.cancel();
                                finish();
                            }
                        });

                builder.setNegativeButton(Html.fromHtml("<font color='#FF7F27'>Exit</font>"),
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.cancel();
                            }
                        });
                builder.create().show();
//                List<Current> names=db.getNames();
//                for(final Current item:names){
//                    FirstName=item.getFirstName();
//                    LastName=item.getLastName();
//                    City=item.getCity();
//                    Street=item.getStreet();
//                    Phone=item.getPhone();
//                }
                boolean nn=db.addCustomer(firstname,lastname, city, street, phone);
                System.out.println("Data:"+nn);
//                Intent intent=new Intent(SingleCustomer.this,CustomerDashboard.class);
//
//                startActivity(intent);
            }
        });

    }
}
