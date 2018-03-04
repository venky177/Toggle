package android.venky.com.toggle;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.materialdrawer.Drawer;
import com.rengwuxian.materialedittext.MaterialEditText;

import static java.lang.Thread.sleep;

public class Display extends AppCompatActivity {

    //public static final String PREFS_NAME = "PRODUCT_APP";
    ListView lv;
    MaterialEditText et;
    Context context;
    Device d1,d2,d3;
    private Drawer result = null;
     //public static int[] prgmImages = {R.drawable.images, R.drawable.images1, R.drawable.images2};
    //public static String[] prgmNameList = {"Light", "Fan", "AC"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        //FireBase.getDevices(this,"Devices");




        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        String ip = preferences.getString("ip","");
        et = (MaterialEditText) findViewById(R.id.viewip);
        et.setText(ip);

    //    d1 = new Device("TubeLight",R.drawable.tubelight,20,0);
    //    d2 = new Device("Fan",R.drawable.fan,20,0);
    //    d3 = new Device("AC ",R.drawable.ac,20,0);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setTitle("Settings");
        context = this;

        lv = (ListView) findViewById(R.id.listView);


        SharedPreference s = new SharedPreference();

        /*ArrayList<Device> devs = new ArrayList<>();
        devs.add(d1);
        devs.add(d2);
        devs.add(d3);
        s.saveFavorites(this,devs);
*/
        /*SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(devs);
        editor.putString("devices", jsonFavorites);
        editor.commit();*/

        //get
        /*SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String jsonFavorites1 = prefs.getString("devices", null);
        Gson gson1 = new Gson();
        Device[] d = gson1.fromJson(jsonFavorites1,Device[].class);
        ArrayList<Device> arrayList = new ArrayList<Device>(Arrays.asList(d));*/

        //favorites = Arrays.asList(favoriteItems);
            //favorites = new ArrayList<Product>(favorites);


         ArrayList<Device> arrayList = FireBase.devicesOnCloud;//s.getFavorites(Display.this);

        /*final ProgressDialog progDailog = ProgressDialog.show(context,
                "Loading Data...",
                "Please wait....", true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sleep(1000);
                }
                 catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progDailog.dismiss();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {*/
                        lv.setAdapter(new MyCustomAdapter(context, arrayList));
                   /*     //title.clearComposingText();//not useful

                    }
                });

            }
        });
        t.start();*/





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                Intent i = new Intent(this,Edit.class);
                startActivity(i);
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();

        }

    }


}