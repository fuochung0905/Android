package com.nguyenphuochung.multiple_example;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nguyenphuochung.multiple_example.databinding.ActivityMainBinding;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ExecutorService executorService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();

        addEvents();

    };
    private void addEvents(){
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.containerLayout.removeAllViews();
                executeLongRunningTask();
            }
        });
    }

    private void executeLongRunningTask() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int numbOfViews = Integer.parseInt(binding.edtNumbOfViews.getText().toString());
                Random random = new Random();
                for (int i=1; i<= numbOfViews;i++){
                    int percent = i * 100 / numbOfViews;
                    int randNumb = random.nextInt(100);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.txtPercent.setText(percent + "%");
                            binding.pbPercent.setProgress(percent);

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT);

                            params.setMargins(10,10,10,10);

                            Button button = new Button(MainActivity.this);
                            button.setLayoutParams(params);

                            button.setText(String.valueOf(randNumb));
                            button.setTextSize(22);
                            button.setTextColor(Color.WHITE);
                            if(randNumb % 2 == 0) {
                                button.setBackgroundColor(Color.rgb(0, 150, 136));
                                params.gravity = Gravity.START;
                            }else{
                                button.setBackgroundColor((Color.rgb(244,57,123)));
                                params.gravity = Gravity.END;
                            }
                            binding.containerLayout.addView(button);
                            button.setLayoutParams(params);

                            if (percent == 100){
                                binding.txtPercent.setText("DONE!!");
                            }
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(executorService != null){
            executorService.shutdown();
        }
    }
}