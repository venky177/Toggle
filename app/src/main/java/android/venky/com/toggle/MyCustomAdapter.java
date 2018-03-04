package android.venky.com.toggle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.venky.com.toggle.Display;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<Device> d;
    private static LayoutInflater inflater=null;
    public MyCustomAdapter(Context mainActivity, ArrayList<Device> d) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.d = d;
        /*result = new String[d.size()];
        imageId = new int[d.size()];
        int count = 0;
        for(Device i:d) {
            result[count] = i.getName();
            imageId[count] = i.getDrawable();
            ++count;
        }*/
        //result=prgmNameList;

        //imageId=prgmImages;

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return d.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return d.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.my_custom_list_layout, parent, false);
        //rowView = inflater.inflate(R.layout.my_custom_list_layout_edit, null);

        TextView tv=(TextView) row.findViewById(R.id.textView1);
        ImageView img=(ImageView) row.findViewById(R.id.imageView1);
        tv.setText(d.get(position).getName());
        img.setImageResource(getImageId(context,d.get(position).getDrawable()));

        /*rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
            }
        });*/
        return row;
    }
    public static int getImageId(Context context,String Imname){
        return context.getResources().getIdentifier("drawable/"+Imname,null,context.getPackageName());
    }
} 