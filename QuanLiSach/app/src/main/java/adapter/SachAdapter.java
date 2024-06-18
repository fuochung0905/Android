package adapter;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlisach.MainActivity;
import com.example.quanlisach.R;

import java.util.ArrayList;

import database.Database;
import model.Sach;

public class SachAdapter extends BaseAdapter {
    MainActivity context;
    int layout_item;
    ArrayList<Sach>sachArrayList;

    public SachAdapter(MainActivity context, int layout_item, ArrayList<Sach> sachArrayList) {
        this.context = context;
        this.layout_item = layout_item;
        this.sachArrayList = sachArrayList;
    }

    @Override
    public int getCount() {
        return sachArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return sachArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout_item,null);
            holder.txtMa=convertView.findViewById(R.id.txtMaSach);
            holder.tenSach=convertView.findViewById(R.id.txtTenSach);
            holder.giaSach=convertView.findViewById(R.id.txtGiaSach);
            convertView.setTag(holder);
        }
        else {
           holder=(ViewHolder) convertView.getTag();
        }
        Sach sach= sachArrayList.get(position);
        if(sach!=null){
            holder.txtMa.setText(sach.getMaSach());
            holder.tenSach.setText(sach.getTenSach());
            holder.giaSach.setText(String.valueOf(sach.getGiaSach()));
        }
        return convertView;
    }
    static class ViewHolder{
        TextView txtMa;
        TextView tenSach;
        TextView giaSach;
    }
}
