package com.doviettue.cau1_tulam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText edit_text_number;
    private LinearLayout linear_layout_view;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần UI
        edit_text_number = findViewById(R.id.edit_text_number);
        linear_layout_view = findViewById(R.id.linear_layout_view);
        random = new Random();

        edit_text_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // khong lam gi khi text chua thay doi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new DrawViewsTask().execute();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // khong lam gi khi text da thay doi
            }
        });
    }
    private class DrawViewsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            runOnUiThread(() -> linear_layout_view.removeAllViews());
            String numberText = edit_text_number.getText().toString();
            if (numberText.isEmpty()) {
                return null; // Không làm gì nếu EditText rỗng
            }

            int numberOfViews = Integer.parseInt(numberText);
            for (int i = 0; i < numberOfViews; i++) {
                int randomValue = random.nextInt(101);
                if (i % 2 == 0) {
                    Button button = new Button(MainActivity.this);
                    button.setText(String.valueOf(randomValue));
                    runOnUiThread(() -> linear_layout_view.addView(button));
                } else {
                    EditText editText = new EditText(MainActivity.this);
                    editText.setText(String.valueOf(randomValue));
                    runOnUiThread(() -> linear_layout_view.addView(editText));
                }
                try {
                    Thread.sleep(100);  // Giả lập độ trễ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}