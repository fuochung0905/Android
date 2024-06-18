package com.example.quanlisinhvien;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlisinhvien.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.StudentAdapter;
import database.Database;
import model.Student;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Database database;
    StudentAdapter adapter;
    ArrayList<Student>students;

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
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }
    private void prePareData(){
        database=new Database(this);
        database.CreateDatabase();

    }
    private void loadData(){
        adapter=new StudentAdapter(R.layout.item,getDataFromDb(),MainActivity.this);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = (Student) parent.getItemAtPosition(position);
                openDialogEdit(student, position);
                return true;
            }
        });
    }
    private List<Student>getDataFromDb(){
        students=new ArrayList<>();
        Cursor cursor=database.queryData("SELECT * FROM "+Database.TBL_NAME);
        if(cursor!=null){
            while (cursor.moveToNext()){
                Student student= new Student();
                student.setId(cursor.getInt(0));
                student.setStudentName(cursor.getString(1));
                student.setClassName(cursor.getString(2));
                student.setTotalScore(cursor.getDouble(3));
                student.setImage(cursor.getBlob(4));
                students.add(student);
            }
            cursor.close();
        }
        return students;
    }
    public void openDialogEdit(Student student,int position){
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit);
        EditText editTen=dialog.findViewById(R.id.edtcapnhatten);
        editTen.setText(student.getStudentName());
        EditText editLop=dialog.findViewById(R.id.edtcapnhatlop);
        editLop.setText(student.getClassName());
        EditText editDiem=dialog.findViewById(R.id.edtcapnhatdiemtong);
        editDiem.setText(String.valueOf(student.getTotalScore()));
        Button capnhat=dialog.findViewById(R.id.btnCapnhat);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentName=editTen.getText().toString();
                String className=editLop.getText().toString();
                double total=Double.parseDouble(editDiem.getText().toString());
                database.execSql("UPDATE " + Database.TBL_NAME + " SET "
                        + Database.COL_NAME + " = '" + studentName + "', "
                        + Database.COL_TOTALSCORE + " = " + total + ", "
                        + Database.COL_CLASSNAME + " = '" + className
                        + "' WHERE " + Database.COL_Id + " = " + student.getId());
                loadData();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }
}