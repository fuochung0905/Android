package com.doviettue.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doviettue.cau2_de4.R;
import com.doviettue.models.Product;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    Activity activity;
    int itemLayout;
    ArrayList<Product> products;

    public ProductAdapter(Activity activity, int itemLayout, ArrayList<Product> products) {
        this.activity = activity;
        this.itemLayout = itemLayout;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(itemLayout, null);
            holder.tvMaSP = convertView.findViewById(R.id.maSanPham);
            holder.tvGiaSP = convertView.findViewById(R.id.giaSanPham);
            holder.tvTenSP = convertView.findViewById(R.id.tenSanPham);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();

        }

        Product p = products.get(position);
        holder.tvTenSP.setText(p.getTenSP());
        holder.tvGiaSP.setText(p.getGiaSp() + "");
        holder.tvMaSP.setText(p.getMaSp());

        return convertView;

    }

    public class ViewHolder{
        TextView tvMaSP, tvTenSP, tvGiaSP ;




    }
}
