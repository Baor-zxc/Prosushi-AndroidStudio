package com.example.prosushi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prosushi.Model.ModelLoaisp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTypeProductAc extends AppCompatActivity {
    EditText edtId;
    EditText edtTen;
    Button btThem;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference drKiemtra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type_product);

        edtId = findViewById(R.id.edtMa);
        edtTen = findViewById(R.id.edtTen);
        btThem = findViewById(R.id.btThemloai);

        //hiện toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // thêm loại lên firebase
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("loaisp");
        // kiểm tra xem có trùng loại kông

        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lấy mã và tên đưa vào loại
                String ma = edtId.getText().toString().trim();
                String ten = edtTen.getText().toString().trim();

                // thêm loại
                if (ma.isEmpty() || ten.isEmpty()){
                    kiemtrasua();
                }else {

                    // so sánh nếu mã trùng với mã đã có hiện thông báo
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean kiem = false;
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                int maloai = ds.child("maloai").getValue(int.class);
                                if (ma.equals(String.valueOf(maloai))) {
                                    kiem = true;
                                    break;
                                }
                            }
                            if (kiem) {
                                kiemtraloi();
                            } else {
                                ModelLoaisp loai = new ModelLoaisp(Integer.parseInt(ma),ten);
                                themLoai(loai);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    // toolbar quay về trang chủ
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // thêm loại sản phẩm
    public void themLoai(ModelLoaisp loai){
        String path = String.valueOf(loai.getMaloai());
            databaseReference.child(path).setValue(loai, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    edtId.setText("");
                    edtTen.setText("");
                    Toast.makeText(AddTypeProductAc.this, "Thêm loại thành công ", Toast.LENGTH_SHORT).show();

                }
            });
    }

    private void kiemtrasua(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo ");
        builder.setMessage("Vui lòng nhập đầy đủ thông tin  ");
        builder.setNegativeButton("Ok",null);
        builder.show();
    }

    private void kiemtraloi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo ");
        builder.setMessage("Mã loại đã tồn tại ");
        builder.setNegativeButton("Ok",null);
        builder.show();
    }

}