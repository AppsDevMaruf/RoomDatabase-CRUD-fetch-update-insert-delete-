package com.marufalam.roomdatabasedemo;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private FloatingActionButton fb;
    private String name = "";
    private String phoneNumber = "";
    private EditText phoneNumberEt;
    private EditText nameEt;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fb = view.findViewById(R.id.fb);

        fb.setOnClickListener(view1 -> {

            dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.insert_data_layout);
            //initialize width
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            //initialize height
            int height = WindowManager.LayoutParams.WRAP_CONTENT;
            //set layout
            dialog.getWindow().setLayout(width, height);
            Button addData = dialog.findViewById(R.id.addBtn);

            //show dialog
            dialog.show();
            addData.setOnClickListener(view2 -> {
                //new BackgroundThread().start();
                nameEt = dialog.findViewById(R.id.nameEt);
                phoneNumberEt = dialog.findViewById(R.id.numberET);

                name = nameEt.getText().toString().trim();
                phoneNumber = phoneNumberEt.getText().toString().trim();
                //allowMainThreadQueries()
                AppDatabase dbinsert = Room.databaseBuilder(requireActivity(), AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = dbinsert.userDao();
                userDao.insertRecord(new User(name, phoneNumber));

                nameEt.setText("");
                phoneNumberEt.setText("");
                dialog.dismiss();
                Toast.makeText(requireActivity(), "Successfully", Toast.LENGTH_SHORT).show();
            });

        });
        recyclerView = view.findViewById(R.id.RV);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        //initialize database
        AppDatabase db = Room.databaseBuilder(requireActivity(),
        AppDatabase.class, "room_db").allowMainThreadQueries().build();
        //store database value in data list
        userList.clear();
        userList.addAll(db.userDao().getAll());

        adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        return view;
    }
//use background thread
    /*class BackgroundThread extends Thread {
        @Override
        public void run() {
            super.run();
            nameEt = dialog.findViewById(R.id.nameEt);
            phoneNumberEt = dialog.findViewById(R.id.numberET);

            name = nameEt.getText().toString().trim();
            phoneNumber = phoneNumberEt.getText().toString().trim();
            //allowMainThreadQueries()
            AppDatabase db = Room.databaseBuilder(requireActivity(), AppDatabase.class, "room_db").build();
            UserDao userDao = db.userDao();
            userDao.insertRecord(new User(name,phoneNumber));
            nameEt.setText("");
            phoneNumberEt.setText("");
            dialog.dismiss();
            //Toast Can't use background thread (@!@)
          //Toast.makeText(requireActivity(), "Successfully", Toast.LENGTH_SHORT).show();

        }
    }*/




    /*datalist.clear();
                        datalist.addAll(dbupdate.userDao().getAll());
                        notifyDataSetChanged();*/
}