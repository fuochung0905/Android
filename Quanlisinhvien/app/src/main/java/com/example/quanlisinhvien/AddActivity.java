package com.example.quanlisinhvien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlisinhvien.databinding.ActivityAddBinding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import database.Database;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    Database database;
    ActivityResultLauncher<Intent>launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=new Database(this);
        launcher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode()==RESULT_OK && o.getData()!=null){
                    Bitmap bitmap=(Bitmap)o.getData().getExtras().get("data");
                    binding.imgAvatar.setImageBitmap(bitmap);
                }
            }
        }
        );
        addEvent();
    }
    private void addEvent(){
        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String studentName=binding.edittenhocsinh.getText().toString().trim();
                    String className=binding.editlop.getText().toString().trim();
                    Double totalScore=Double.parseDouble(binding.editdiem.getText().toString());
                    if(studentName.isEmpty()||totalScore<0||className.isEmpty()){
                        Toast.makeText(AddActivity.this,"Please fill in all information",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        boolean result=database.insertData(studentName,className,totalScore,convertPhoto());
                        if(result){
                            Toast.makeText(AddActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddActivity.this,MainActivity.class));
                        }else {
                            Toast.makeText(AddActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(AddActivity.this,"Please enter a valid  for the total Score",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private byte[] convertPhoto(){
        BitmapDrawable bitmapDrawable=(BitmapDrawable)binding.imgAvatar.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
       ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        return outputStream.toByteArray();
    }
}
