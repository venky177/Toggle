package android.venky.com.toggle;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import android.venky.com.toggle.Display;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapterCurrentDevices extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    ArrayList<Device> d;
    MaterialEditText starttime,room,volt,pin;
    TextView devname;
    private static LayoutInflater inflater=null;
    public CustomAdapterCurrentDevices(Context mainActivity, ArrayList<Device> d) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.my_custom_list_layout_current_devices, parent, false);
        //rowView = inflater.inflate(R.layout.my_custom_list_layout_edit, null);

        ImageButton info=(ImageButton) row.findViewById(R.id.imageButton12345);
        info.setTag(position);
        info.setColorFilter(context.getResources().getColor(R.color.primary));
        info.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v12) {
                int pos = (int) v12.getTag();
                showInputDialog(d.get(pos).getName(),d.get(pos).getVolt(),d.get(pos).getPin(),d.get(pos).getState(),d.get(pos).getRoom(),d.get(pos).getStartTime());
                //Toast.makeText(context, "yoyoyo", Toast.LENGTH_LONG).show();
            }
        });
        TextView tv=(TextView) row.findViewById(R.id.textView1);
        ImageView img=(ImageView) row.findViewById(R.id.imageView1);
        tv.setText(d.get(position).getName());
        img.setImageResource(getImageId(context, d.get(position).getDrawable()));
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
    protected void showInputDialog(String name,int v,int p,int s,String r,long st) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.display_info_device, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        Date dd= new Date(st);


        devname = (TextView) promptView.findViewById(R.id.yo);
        volt = (MaterialEditText) promptView.findViewById(R.id.volt1);
        starttime = (MaterialEditText) promptView.findViewById(R.id.starttime1);
        room = (MaterialEditText) promptView.findViewById(R.id.room1);
        pin = (MaterialEditText) promptView.findViewById(R.id.pin1);
        devname.setText(name);
        volt.setText(""+v);
        room.setText(""+r);
        starttime.setText(dd.getHours()+":"+dd.getMinutes());
        pin.setText(""+p);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        alert.show();
        Button buttonbackground1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(context.getResources().getColor(R.color.primary));
        buttonbackground1.setTextSize(16);
    }

} 