package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlisinhvien.MainActivity;
import com.example.quanlisinhvien.R;

import java.util.ArrayList;
import java.util.List;

import model.Student;

public class StudentAdapter extends BaseAdapter {
    MainActivity context;
    int layout_item;
    List<Student>students;

    public StudentAdapter(int layout_item, List<Student> students, MainActivity activity) {
        this.layout_item = layout_item;
        this.students = students;
        this.context = activity;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null){
            holder=new Holder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout_item,null);
            holder.txtInformation=convertView.findViewById(R.id.txtInformation);
            holder.image=convertView.findViewById(R.id.btnImage);
            holder.btnedit=convertView.findViewById(R.id.btnedit);
            holder.btndelete=convertView.findViewById(R.id.btndelete);
            convertView.setTag(holder);
        }else {
           holder=(Holder) convertView.getTag();
        }
        //binding data
        Student student=students.get(position);
        if(student!=null){

            byte []photo = student.getImage();
            if(photo!= null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                holder.image.setImageBitmap(bitmap);
            }
            else {
                System.out.println("Mảng bị null");
            }
            holder.txtInformation.setText("Tên học sinh:"+student.getStudentName()
                    +"\n Tên lớp:"+student.getClassName()
                    +"\n Tổng điểm:"+student.getTotalScore());
            holder.btnedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return convertView;
    }
    public void updateList(ArrayList<Student>studentList){
        students=new ArrayList<>();
        students.addAll(studentList);
        notifyDataSetChanged();
    }
    public static class Holder{
        ImageButton btnedit;
        ImageButton btndelete;
        ImageView image;
        TextView txtInformation;
    }
}
