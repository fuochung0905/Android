package com.nguyenphuochung.multiple_example3;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenphuochung.multiple_example3.databinding.ActivityMainBinding;

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

                View emptyView = new View(MainActivity.this);
                TableRow.LayoutParams emptyParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // Trọng số của cột thứ nhất, nên để trống để Button ở cột thứ hai
                );
                emptyView.setLayoutParams(emptyParams);
                tableRow.addView(emptyView);
                Button button = new Button(MainActivity.this);
                TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // Trọng số của cột thứ hai
                );
                button.setLayoutParams(buttonParams);
                tableRow.addView(button);
                tableLayout.addView(tableRow);
            } else {
                Button button = new Button(MainActivity.this);
                TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // Trọng số của cột thứ hai
                );
                button.setLayoutParams(buttonParams);
                tableRow.addView(button);
                tableLayout.addView(tableRow);
                View emptyView = new View(MainActivity.this);
                TableRow.LayoutParams emptyParams = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1 // Trọng số của cột thứ nhất, nên để trống để Button ở cột thứ hai
                );
                emptyView.setLayoutParams(emptyParams);
                tableRow.addView(emptyView);



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
