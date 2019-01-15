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

import com.example.techsavanna.melvinscart.CustomersActivity;
import com.example.techsavanna.melvinscart.LoginActivity;
import com.example.techsavanna.melvinscart.R;
import com.example.techsavanna.melvinscart.ReportsActivity;
import com.example.techsavanna.melvinscart.helper.SQLiteHandler;
import com.example.techsavanna.melvinscart.helper.SessionManager;

import java.util.List;

public class DashAapter extends RecyclerView.Adapter<DashAapter.DashViewHolder> {
    List<Dashboard> dashboards;
    private SQLiteHandler db;
    private SessionManager session;
    public DashAapter(List<Dashboard> dashboards) {

        this.dashboards = dashboards;
    }
//    public MyArrayAdapter(Context con, String[] countries) {
//        // TODO Auto-generated constructor stub
//        mcon = con;
//        COUNTRIES_ = countries;
//        mInflater = LayoutInflater.from(con);
//    }
    @NonNull
    @Override
    public DashViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dash_row, viewGroup, false);
        // SqLite database handler
        db = new SQLiteHandler(viewGroup.getContext());

        // session manager
        session = new SessionManager(viewGroup.getContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        DashViewHolder pvh = new DashViewHolder(v);
       // List<DashAapter> contactModelList = new ArrayList<>()

        return pvh;
    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();


    }
    @Override
    public void onBindViewHolder(@NonNull DashViewHolder dashViewHolder, int i) {

      // dashViewHolder.currentItem = dashboards.get(i);
        dashViewHolder.dashName.setText(dashboards.get(i).name);
      dashViewHolder.dashPhoto.setImageResource(dashboards.get(i).photoId);

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
        return dashboards.size();
    }



    public class DashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        private Context context;
        TextView dashName;
        ImageView dashPhoto;
        public DashViewHolder(@NonNull View itemView) {
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
                    Intent intent = new Intent(v.getContext(), CustomersActivity.class);
                    v.getContext().startActivity(intent);
                    break;

                case 1:
                    Intent intentt = new Intent(v.getContext(), ReportsActivity.class);
                    v.getContext().startActivity(intentt);

                case 3:
                    session.setLogin(false);

                    db.deleteUsers();
                    Intent intent3 = new Intent(v.getContext(), LoginActivity.class);
                    v.getContext().startActivity(intent3);

                    break;
            }
        }
    }
}
