package com.nguyenphuochung.multiple_example2;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenphuochung.multiple_example2.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    int percent;
    int randomNumber;
    int count;
    Random random = new Random();
    Handler handler = new Handler();
    Runnable foreGroundThread = new Runnable() {
        @Override
        public void run() {
            //Update UI
            binding.percent.setText(percent + "%");
            binding.prgressBar.setProgress(percent);
//            ViewGroup.LayoutParams params= new ViewGroup.LayoutParams(200,ViewGroup.LayoutParams.WRAP_CONTENT);
            TableLayout tableLayout = new TableLayout(MainActivity.this);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TableRow tableRow = new TableRow(MainActivity.this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            if (count % 2 == 0) {
                Button button1 = new Button(MainActivity.this);
                TableRow.LayoutParams params1 = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        3 // layout_weight cho button dài
                );
                button1.setLayoutParams(params1);
                button1.setPadding(5, 5, 5, 5);
                // Tạo Button nhỏ hình vuông
                Button button2 = new Button(MainActivity.this);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // layout_weight cho button nhỏ
                );
                button2.setLayoutParams(params2);
                button2.setPadding(5, 5, 5, 5); // Đặt padding nếu cần

                // Thêm Button vào TableRow
                tableRow.addView(button1);
                tableRow.addView(button2);

                // Thêm TableRow vào TableLayout
                tableLayout.addView(tableRow);
            } else {

                Button button2 = new Button(MainActivity.this);
                TableRow.LayoutParams params2 = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // layout_weight cho button nhỏ
                );
                button2.setLayoutParams(params2);
                button2.setPadding(5, 5, 5, 5); // Đặt padding nếu cần

                Button button1 = new Button(MainActivity.this);
                TableRow.LayoutParams params1 = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        3 // layout_weight cho button dài
                );
                button1.setLayoutParams(params1);
                button1.setPadding(5, 5, 5, 5); // Đặt padding nếu cần
                // Tạo Button nhỏ hình vuông
                // Thêm Button vào TableRow
                tableRow.addView(button2);
                tableRow.addView(button1);
                // Thêm TableRow vào TableLayout
                tableLayout.addView(tableRow);
            }
            binding.linerlayout.addView(tableLayout);
            if (percent == 100) {
                binding.percent.setText("DONE");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvent();
    }

    private void addEvent() {
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.linerlayout.removeAllViews();
                execBakGroundThread();
            }


        });
    }

    private void execBakGroundThread() {

        //Worker
        int numbofviews = Integer.parseInt(binding.editNumOfViews.getText().toString());
        Thread backGroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < numbofviews; i++) {
                    percent = i * 100 / numbofviews;
                    count = i;
                    randomNumber = random.nextInt(100);
                    handler.post(foreGroundThread);
                    SystemClock.sleep(1000);
                }
            }
        });
        backGroundThread.start();
    }
}