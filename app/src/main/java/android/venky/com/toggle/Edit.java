package android.venky.com.toggle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;

import me.drakeet.materialdialog.MaterialDialog;


public class Edit extends AppCompatActivity {
    public static final String PREFS_NAME = "PRODUCT_APP";
    ListView lv;
    Context context;
    ImageButton ib;
    ArrayList<Device> arrayList;
    CustomAdapterEdit adap;
    SharedPreference s;
    EditText dev,pow;
    Spinner mySpinner,roomspin,pin;
    MaterialEditText et;
    String arr_img[]={"tubelight","fan","ac","comp","bulb2","tv"};
    Integer arr_images[] = { R.drawable.tubelight,
            R.drawable.fan, R.drawable.ac,
            R.drawable.comp,R.drawable.bulb2,R.drawable.tv};
    String rooms[] = {"Living room","Kitchen","Bedroom1","Bedroom2","Study room","Store room"};
    String pins[] = {"16","18","22","24","26"};

    //public static int[] prgmImages = {R.drawable.images, R.drawable.images1, R.drawable.images2, R.drawable.images3};
    //public static String[] prgmNameList = {"Let Us C", "c++", "JAVA", "Jsp"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        String ip = preferences.getString("ip","");
        et = (MaterialEditText) findViewById(R.id.viewip);
        et.setText(ip);

        s = new SharedPreference();
        arrayList = FireBase.devicesOnCloud;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setTitle("Edit Settings");
        context = this;

        lv = (ListView) findViewById(R.id.listView2);
        adap = new CustomAdapterEdit(this, arrayList);
        lv.setAdapter(adap);

        ib = (ImageButton) findViewById(R.id.imageButton2);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Device d = new Device("Refri",R.drawable.images3,50,-1);
                //add(d);
                showInputDialog();


            }
        });


    }

    public void add(Device d) {
        arrayList.add(d);
        s.saveFavorites(Edit.this, arrayList);
        FireBase.sendData(d,"Devices");
        adap.notifyDataSetChanged();
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Edit.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Edit.this);
        alertDialogBuilder.setView(promptView);

        dev = (EditText) promptView.findViewById(R.id.devname);
        pow = (EditText) promptView.findViewById(R.id.power);
        pin = (Spinner) promptView.findViewById(R.id.pinnum);
        //pin.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
        mySpinner = (Spinner)promptView.findViewById(R.id.spinner);
        //mySpinner.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
        mySpinner.setAdapter(new MyAdapter(Edit.this, R.layout.dropdown,arr_img));
        roomspin = (Spinner)promptView.findViewById(R.id.roomspinner);
        //roomspin.getBackground().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
        /*ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item1, rooms); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomspin.setAdapter(spinnerArrayAdapter);

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, pins); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pin.setAdapter(spinnerArrayAdapter1);*/
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.rooms, R.layout.spinner_item);
        roomspin.setAdapter(adapter);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,R.array.pins, R.layout.spinner_item1);
        pin.setAdapter(adapter1);


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        if(dev.getText().toString().trim().isEmpty() || pow.getText().toString().trim().isEmpty())
                        {
                            Toast.makeText(Edit.this,"Enter proper values!",Toast.LENGTH_LONG).show();
                        }
                        else {
                            String name = dev.getText().toString().trim();
                            int power = Integer.parseInt(pow.getText().toString().trim());
                            String yo = (String) mySpinner.getSelectedItem();
                            int pinn = Integer.parseInt(pin.getSelectedItem().toString());
                            String r = roomspin.getSelectedItem().toString();
                            Device d = new Device(name,yo,power,0,pinn,r);
                            add(d);
                            String title = "A new device has been added!";
                            String text = name+" has been created in the room "+r;
                            FireBase.noti(context,title,text,Edit.class);

                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
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
        Button buttonbackground2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground2.setTextColor(context.getResources().getColor(R.color.primary));
        buttonbackground2.setTextSize(16);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                String ip = et.getText().toString().trim();
                if(ip.isEmpty()) {
                    Toast.makeText(Edit.this,"Enter valid IP",Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("ip", ip);
                    editor.commit();
                    Intent i = new Intent(this, Display.class);
                    startActivity(i);
                    finish();
                }
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        new com.afollestad.materialdialogs.MaterialDialog.Builder(this)
                .titleColorRes(R.color.primary)
                .title("Are you sure?!")
                .content("Any changes made will not be reflected!")
                .positiveText("OK")
                .negativeText("CANCEL")
                .positiveColorRes(R.color.primary)
                .negativeColorRes(R.color.grey)
                .backgroundColor(context.getResources().getColor(R.color.white))
                .contentColor(context.getResources().getColor(R.color.grey))
                .cancelable(false)
                .callback(new com.afollestad.materialdialogs.MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(com.afollestad.materialdialogs.MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Intent i = new Intent(Edit.this, Display.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onNegative(com.afollestad.materialdialogs.MaterialDialog dialog) {
                        super.onNegative(dialog);

                    }

                }).show();



    }


    public class MyAdapter extends ArrayAdapter<String> {
        String[] arr;
        Context context;
        LayoutInflater inflater;
        public MyAdapter(Context context, int textView,String[] arr) {
            super(context,textView,arr);
            this.arr = arr;
            this.context = context;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            //LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.dropdown, parent, false);
            ImageView icon=(ImageView)row.findViewById(R.id.imageview123);
            icon.setImageResource(getImageId(context,arr[position]));

            return row;
        }
    }
    public static int getImageId(Context context,String Imname){
        return context.getResources().getIdentifier("drawable/"+Imname,null,context.getPackageName());
    }

}
