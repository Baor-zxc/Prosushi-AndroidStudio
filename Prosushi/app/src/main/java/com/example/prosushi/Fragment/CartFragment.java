package com.example.prosushi.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.prosushi.Adapter.GiohangAdapter;
import com.example.prosushi.Model.ModelGiohang;
import com.example.prosushi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    TextView tvtongtien;
    Button btnbuy;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    GiohangAdapter giohangAdapter;
    int tonggia=1;
    ArrayList<ModelGiohang> listgh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView=view.findViewById(R.id.rcyv1);
        tvtongtien=view.findViewById(R.id.tvtongtien);
        btnbuy=view.findViewById(R.id.btnbuy);


        loaddatagiohang();


        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
            }
        });

//gettotal tư adapter
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(mBroadcastReceiver,new IntentFilter("Tonggiagiohang"));


        loaddatarecycleview();
        return view;
    }

    public void loaddatagiohang(){

        database= FirebaseDatabase.getInstance();
        databaseReference = database.getReference("giohang");

        listgh= new ArrayList<>();

        //add arraylist voi firebase

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listgh.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelGiohang giohang = dataSnapshot.getValue(ModelGiohang.class);
                    listgh.add(giohang);
                }
                giohangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void loaddatarecycleview(){
        giohangAdapter=new GiohangAdapter(getContext(),listgh);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(giohangAdapter);
    }
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tonggia = intent.getIntExtra("tonggia",0);
            tvtongtien.setText(tonggia+" VND");
        }
    };

}