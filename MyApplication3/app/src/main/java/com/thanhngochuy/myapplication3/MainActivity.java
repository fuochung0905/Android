package com.thanhngochuy.myapplication3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btn;
    EditText edt;
    GridLayout gly;
    private class Draw extends AsyncTask<String, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            gly.removeAllViews();
        }

        @Override
        protected Void doInBackground(String... strings) {
            final int cnt = Integer.parseInt(strings[0]);
            for(int i=0; i<cnt; i++){
                final int ind = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tv = new TextView(MainActivity.this);
                        tv.setText(String.valueOf(ind));
                        tv.setPadding(10, 10, 10, 10);
                        tv.setGravity(Gravity.CENTER);
                        tv.setBackgroundColor(ind % 2 == 0 ? 0xFFCCCCCC : 0xFF00FF00);
                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.width=200;
                        params.height=200;
                        tv.setLayoutParams(params);
                        gly.addView(tv);
                    }

                });
                try {
                    Thread.sleep(100); // Mô phỏng thời gian xử lý lâu
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btnDraw);
        edt = findViewById(R.id.edtNum);
        gly = findViewById(R.id.glyDraw);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Draw().execute(edt.getText().toString());
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;
        });
    }

}