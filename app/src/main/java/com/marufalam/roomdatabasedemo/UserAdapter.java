package com.marufalam.roomdatabasedemo;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> datalist = new ArrayList<>();

    public UserAdapter(List<User> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User d = datalist.get(holder.getAdapterPosition());

        holder.name.setText(d.getName());
        holder.number.setText(d.getNumber());

        holder.delete.setOnClickListener(view -> {
            //initialize main data

            AppDatabase db = Room.databaseBuilder(view.getContext(), AppDatabase.class, "room_db").allowMainThreadQueries().build();

            //delete text from database
            db.userDao().delete(d);
            //notify when data i deleted
            datalist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeRemoved(position, datalist.size());

        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                User d = datalist.get(holder.getAdapterPosition());
                //Get Id
                int uID = d.getUid();
                //Get text
                String nameText = d.getName();
                String numberText = d.getNumber();
                //Create dialog
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(R.layout.insert_data_layout);
                //initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout
                dialog.getWindow().setLayout(width,height);
                //show dialog
                dialog.show();
                //initialize and assign variable
                EditText updateName = dialog.findViewById(R.id.nameEt);
                EditText updateNumber = dialog.findViewById(R.id.numberET);
                Button btupdate = dialog.findViewById(R.id.addBtn);
                btupdate.setText("Update");
                //set text on edit test
                updateName.setText(nameText);
                updateNumber.setText(numberText);
                btupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dismiss dialog
                        dialog.dismiss();
                        //get update text from edit text
                        String uname = updateName.getText().toString().trim();
                        String unumber = updateNumber.getText().toString().trim();
                        //initalize database
                        AppDatabase dbupdate = Room.databaseBuilder(v.getContext(), AppDatabase.class, "room_db").allowMainThreadQueries().build();

                        //update text in database
                        dbupdate.userDao().update(uID,uname,unumber);
                        //Notify when data is update
                        datalist.clear();
                        datalist.addAll(dbupdate.userDao().getAll());
                        notifyDataSetChanged();

                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, number;
        ImageView edit, delete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            number = itemView.findViewById(R.id.numberTV);
            edit = itemView.findViewById(R.id.bt_edit);
            delete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
