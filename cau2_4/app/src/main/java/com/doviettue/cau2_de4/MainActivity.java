package com.doviettue.cau2_de4;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.doviettue.adapters.ProductAdapter;
import com.doviettue.cau2_de4.databinding.ActivityMainBinding;
import com.doviettue.databases.SqliteDb;
import com.doviettue.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    SqliteDb sqliteDb;
    ProductAdapter adapter;
    ArrayList<Product> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDb();
        loadListView();
        registerForContextMenu(binding.lvItem);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void initDb(){
        sqliteDb = new SqliteDb(this);
//        sqliteDb.sampleData();
    }

    private void loadListView() {
        arrayList = new ArrayList<>();
        Cursor cs = sqliteDb.select("SELECT * FROM " + SqliteDb.TBL_NAME);
        if (cs != null){
            while (cs.moveToNext()){
                arrayList.add(new Product(cs.getString(0), cs.getString(1), cs.getDouble(2)));
            }
            adapter = new ProductAdapter(this, R.layout.item, arrayList);
            binding.lvItem.setAdapter(adapter);
        }
    }

}