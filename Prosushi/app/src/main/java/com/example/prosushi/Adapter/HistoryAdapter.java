package com.example.prosushi.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prosushi.Model.ModelHoadon;
import com.example.prosushi.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Viewholder>{

    Context c;
    ArrayList<ModelHoadon> listhd;

    public HistoryAdapter(Context c, ArrayList<ModelHoadon> listhd) {
        this.c = c;
        this.listhd = listhd;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        View view = inflater.inflate(R.layout.oneitem_hoadon,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        ModelHoadon modelHoadon = listhd.get(position);

        holder.tvmahd.setText("Mã hóa đơn : "+modelHoadon.getMahd()+"");
        holder.tvmakh.setText("Mã khách hàng : "+modelHoadon.getMand()+"");
        holder.tvngaytao.setText("Ngày tạo : "+modelHoadon.getNgaytao());
        holder.tvtongtien.setText(modelHoadon.getTongtien()+" VND");
        holder.tvTrangThai.setText("Trạng thái : "+modelHoadon.getTrangthai());

        int mahd = modelHoadon.getMahd();
        int makh = modelHoadon.getMand();
        String ngaytao = modelHoadon.getNgaytao();
        String tongtien = modelHoadon.getTongtien();

        holder.btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String trangthai = "Đơn hàng đã hủy";
                ModelHoadon hd = new ModelHoadon(mahd,makh,ngaytao,tongtien,trangthai);
                holder.TrangThai(hd);
            }
        });
        holder.btXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trangthai = "Đơn hàng đã được xác nhận";
                ModelHoadon hd = new ModelHoadon(mahd,makh,ngaytao,tongtien,trangthai);
                holder.TrangThai(hd);

            }
        });
        holder.btGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trangthai = "Đơn hàng đang vận chuyển ";
                ModelHoadon hd = new ModelHoadon(mahd,makh,ngaytao,tongtien,trangthai);
                holder.TrangThai(hd);
            }
        });

        holder.tvpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
            }
        });

    }

    @Override
    public int getItemCount() {
        return listhd.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvmahd,tvmakh,tvngaytao,tvtongtien,tvpress;

        Button btXacNhan,btGiao,btHuy;
        TextView tvTrangThai;
        DatabaseReference databaseReference ;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvmahd=(TextView) itemView.findViewById(R.id.tvmahd);
            tvmakh=(TextView) itemView.findViewById(R.id.tvmakh);
            tvngaytao=(TextView) itemView.findViewById(R.id.tvngaytao);
            tvtongtien=(TextView) itemView.findViewById(R.id.tvtongtien);
            tvpress=(TextView) itemView.findViewById(R.id.tvpress);

            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btXacNhan = itemView.findViewById(R.id.btXacNhan);
            btGiao = itemView.findViewById(R.id.btGiaoHang);
            btHuy = itemView.findViewById(R.id.btHuy);

            databaseReference = FirebaseDatabase.getInstance().getReference("hoadon");


        }
        public void TrangThai (ModelHoadon hoadon){
            String path = String.valueOf(hoadon.getMahd());
            databaseReference.child(path).setValue(hoadon, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                }
            });
        }

    }
}
