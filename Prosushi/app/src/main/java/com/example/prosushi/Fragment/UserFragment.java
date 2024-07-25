package com.example.prosushi.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prosushi.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    TextView tvTaikhoan,tvTen,tvRole,tvDiachi,tvSdt;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        tvTaikhoan = view.findViewById(R.id.tvTk);
        tvRole = view.findViewById(R.id.tvRole);
        tvTen = view.findViewById(R.id.tvTenNd);
        tvDiachi = view.findViewById(R.id.tvDiaChi);
        tvSdt = view.findViewById(R.id.tvSdt);

        SharedPreferences getshare = getActivity().getSharedPreferences("role",MODE_PRIVATE);
        int mand = getshare.getInt("mand",0);

        databaseReference = FirebaseDatabase.getInstance().getReference("nguoidung");

        databaseReference.child(String.valueOf(mand)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tenTk = snapshot.child("taikhoan").getValue(String.class);
                int role = snapshot.child("role").getValue(int.class);
                String ten = snapshot.child("tennguoidung").getValue(String.class);
                String sdt = snapshot.child("sdt").getValue(String.class);
                String diachi = snapshot.child("diachi").getValue(String.class);

                //
                tvTaikhoan.setText(tenTk);
                if(role==1){
                    tvRole.setText("Admin");
                }else if(role ==2){
                    tvRole.setText("NguoiDung");
                }
                tvTen.setText("Tên : "+ten);
                tvDiachi.setText("Địa chỉ : "+diachi);
                tvSdt.setText("Số điện thoại : "+sdt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }
}