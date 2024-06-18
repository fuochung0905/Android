package com.example.quanlisach;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlisach.databinding.ActivityMainBinding;

import java.util.ArrayList;

import adapter.SachAdapter;
import database.Database;
import model.Sach;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
Database db;
ArrayList<Sach>sachArrayList;
SachAdapter sachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prePareData();
        loadData();

    }
    private void prePareData(){
        db=new Database(this);
        db.CreateSampleData();
    }
    private void loadData(){
        sachAdapter=new SachAdapter(MainActivity.this,R.layout.item_list,getDataFromDB());
        binding.livSach.setAdapter(sachAdapter);
        binding.livSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Sach sach=(Sach) parent.getItemAtPosition(position);
                openDialogAdd();
                return false;
            }
        });
    }
    private void openDialogAdd(){
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.dialog_add,null);
        AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).create();
        Button btnAdd=dialogView.findViewById(R.id.btnAdd);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnCancel=dialogView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editMa=dialogView.findViewById(R.id.editMaSach);
                EditText editTen=dialogView.findViewById(R.id.editTenSach);
                EditText editText=dialogView.findViewById(R.id.editGiaSach);
                String maSach=editMa.getText().toString().trim();
                String tenSach=editTen.getText().toString().trim();
                Double giaSach=Double.parseDouble(editText.getText().toString());

                if(maSach.isEmpty()||tenSach.isEmpty()||giaSach<0){
                    Toast.makeText(MainActivity.this,"Please fill information",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean result=db.insertData(maSach,tenSach,giaSach);
                    if(result){
                        Toast.makeText(MainActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        loadData();
                        dialog.dismiss();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        dialog.show();
    }
    private void openDialogEdit(Sach sach){
        LayoutInflater inflater=getLayoutInflater();

        View dialogView=inflater.inflate(R.layout.dialog_add,null);
        EditText editMa=dialogView.findViewById(R.id.editMaSach);
        EditText editTen=dialogView.findViewById(R.id.editTenSach);
        EditText editText=dialogView.findViewById(R.id.editGiaSach);
        editText.setText(sach.getMaSach());
        editMa.setText(sach.getTenSach());
        editMa.setText(String.valueOf(sach.getGiaSach()));
        AlertDialog dialog=new AlertDialog.Builder(this).setView(dialogView).create();
        Button btnAdd=dialogView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSach=editMa.getText().toString().trim();
                String tenSach=editTen.getText().toString().trim();
                Double giaSach=Double.parseDouble(editText.getText().toString());

                db.execSql("UPDATE TABLE "+Database.TBL_NAME+" SET "
                        +Database.COL_TEN+" ='"+tenSach+"', "
                        +Database.COL_GIA+ " ="+giaSach
                        +" where "+Database.COL_MA+" ='"+maSach+"'");
                loadData();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private ArrayList<Sach>getDataFromDB(){
        sachArrayList=new ArrayList<>();
        String sql= "SELECT * FROM " + Database.TBL_NAME;
        Cursor cursor=db.querySql(sql);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Sach sach= new Sach();
                sach.setMaSach(cursor.getString(0));
                sach.setTenSach(cursor.getString(1));
                sach.setGiaSach(cursor.getDouble(2));
                sachArrayList.add(sach);
            }
            cursor.close();
        }
        return sachArrayList;
    }
}