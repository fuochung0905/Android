package com.example.ontapthi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ontapthi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.PhoneAdapter;
import database.Database;
import model.Phone;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Database database;
    PhoneAdapter phoneAdapter;
    ArrayList<Phone>phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prePareData();
        loadData();
        addEvent();
    }
    private void addEvent(){
        binding.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.dialog_add,null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();

       @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button=dialogView.findViewById(R.id.btnthem);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    EditText edtId=dialogView.findViewById(R.id.edtaddmadienthoai);
                    EditText edtTen=dialogView.findViewById(R.id.edtaddten);
                    EditText editPrice=dialogView.findViewById(R.id.edtadddiemtong);
                    String Id=edtId.getText().toString().trim();
                    String phoneName=edtTen.getText().toString().trim();
                    Double price=Double.parseDouble(editPrice.getText().toString());
                    if(Id.isEmpty()||phoneName.isEmpty()){
                        Toast.makeText(MainActivity.this,"Please a fill information",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        boolean resultInsert=database.insertData(Id,phoneName,price);

                        if(resultInsert){
                            loadData();
                            dialog.dismiss();
                        }
                        else {

                            Toast.makeText(MainActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){

                }

            }
        });



        dialog.show();
    }

    private ArrayList<Phone>getDataFromdb(){
        phones=new ArrayList<>();
        Cursor cursor=database.querySql("SELECT * FROM "+Database.TBL_NAME);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Phone phone= new Phone();
                phone.setMaDienThoai(cursor.getString(0));
                phone.setTenDienThoai(cursor.getString(1));
                phone.setGia(cursor.getDouble(2));
                phones.add(phone);
            }
            cursor.close();
        }
        return phones;
    }
    private void loadData(){
        phoneAdapter=new PhoneAdapter(R.layout.item_list,getDataFromdb(),MainActivity.this);
        binding.listviewPhone.setAdapter(phoneAdapter);
        binding.listviewPhone.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Phone phone=(Phone) parent.getItemAtPosition(position);
                    openDialogEdit(phone);
                return false;
            }


        });
    }
    private void openDialogEdit(Phone phone) {
        LayoutInflater inflater=getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.dialog_update,null);
        //tham chiếu đến các thành phần trong UI
        EditText editTextma=dialogView.findViewById(R.id.edtcapnhatmadienthoai);
        EditText editTextname=dialogView.findViewById(R.id.edtcapnhatten);
        EditText editTextTien=dialogView.findViewById(R.id.edtcapnhatdiemtong);
        editTextma.setText(phone.getMaDienThoai());
        editTextname.setText(phone.getTenDienThoai());
        editTextTien.setText(String.valueOf(phone.getGia()));
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        Button capnhat=dialogView.findViewById(R.id.btnCapnhat);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setMaDienThoai(editTextma.getText().toString().trim());
                phone.setTenDienThoai(editTextname.getText().toString().trim());
                phone.setGia(Double.parseDouble(editTextTien.getText().toString()));

                //Cập nhật danh sách
                String ma=editTextma.getText().toString().trim();
                String name=editTextname.getText().toString().trim();
                editTextma.setFocusable(false);
                editTextname.setEnabled(false);
                double gia=Double.parseDouble(editTextTien.getText().toString());
                database.execSql("UPDATE "+Database.TBL_NAME+" SET "
                        +Database.COL_NAME+" ='"+name+"',"
                        +Database.COL_PRICE+" ="+gia
                        +" where "+Database.COL_CODE+" ='"+ma+"'");
                loadData();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    private void prePareData(){
        database=new Database(this);
        database.CreateSampleData();
    }
}