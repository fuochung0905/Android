package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ontapthi.MainActivity;
import com.example.ontapthi.R;

import java.util.ArrayList;

import database.Database;
import model.Phone;

public class PhoneAdapter extends BaseAdapter {
   int layout_item;
    ArrayList<Phone>phones;
    MainActivity context;

    public PhoneAdapter(int layout_item, ArrayList<Phone> phones, MainActivity context) {
        this.layout_item = layout_item;
        this.phones = phones;
        this.context = context;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Object getItem(int position) {
        return phones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout_item, null);
            holder.txtInformation =  convertView.findViewById(R.id.txtPhoneInformation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Phone phone = phones.get(position);
        if(phone !=null){
            holder.txtInformation.setText("Tên sản phẩm"+phone.getTenDienThoai()+": "+phone.getGia());
        }
        return convertView;
    }

    public static class ViewHolder{
        TextView txtInformation;
    }
}
