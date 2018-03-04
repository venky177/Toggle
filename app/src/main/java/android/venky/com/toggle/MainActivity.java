package android.venky.com.toggle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.gc.materialdesign.views.ButtonRectangle;

import com.github.mrengineer13.snackbar.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends Activity implements SnackBar.OnMessageClickListener {


    MaterialEditText etip;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!haveNetworkConnection()) {
            new SnackBar.Builder(this)
                    .withOnClickListener(this)
                    .withMessage("Can't connect right now.") // OR
                    .withActionMessage("Close") // OR
                    .withTextColorId(R.color.primary)
                    .withActionIconId(R.color.primary)
                            //.withBackGroundColorId(this.getResources().getColor(R.color.black))
                            // .withVisibilityChangeListener(this)
                    .withStyle(SnackBar.Style.DEFAULT)
                    .withDuration(SnackBar.PERMANENT_SNACK)
                    .show();
        }
        else {
            setContentView(R.layout.activity_main);
            SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
            String ip = preferences.getString("ip", "");
            Firebase.setAndroidContext(this);
            FireBase.getDevices(this, "Devices");
            FireBase.getData(this, "Train");
            FireBase.getAlarms(this, "Alarms");




            //myloader();
            //FireBase.getData(MainActivity.this, "Live");
            //FireBase.getData(MainActivity.this,"Train");
            etip = (MaterialEditText) findViewById(R.id.etIP);
            b = (Button) findViewById(R.id.button);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ip = etip.getText().toString().trim();
                    if (ip.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Enter valid IP", Toast.LENGTH_LONG).show();
                    } else {
                        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("ip", ip);
                        editor.commit();
                        Intent i = new Intent(MainActivity.this, CurrentDevices.class);
                        i.putExtra("ip", ip);
                        startActivity(i);
                        finish();
                        //scheduleAlarm();
                    }
                }
            });


    }
    }

    public void scheduleAlarm()
    {
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day
        Long time = new GregorianCalendar().getTimeInMillis()+2*60*1000;

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();
        Date d= new Date(time);
        Log.e("time",d.toString());

    }

    void myloader()
    {
        try {

            final ProgressDialog progDailog = ProgressDialog.show(MainActivity.this,
                    "Sending Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        Date d= new Date();
                        d.setHours(4);
                        Post p= new Post("1",d,"1",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"1",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"1",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"1",4);
                        FireBase.sendData(p,"Train");


                        d.setHours(5);
                        p= new Post("1",d,"0",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"0",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"0",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"0",4);
                        FireBase.sendData(p, "Train");

                        d.setHours(6);
                        p= new Post("1",d,"0",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"0",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"0",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"0",4);
                        FireBase.sendData(p, "Train");

                        d.setHours(7);
                        p= new Post("1",d,"1",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"1",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"1",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"1",4);
                        FireBase.sendData(p, "Train");

                        d.setHours(8);
                        p= new Post("1",d,"1",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"1",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"1",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"1",4);
                        FireBase.sendData(p,"Train");

                        d.setHours(9);
                        p= new Post("1",d,"1",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"1",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"1",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"1",4);
                        FireBase.sendData(p,"Train");

                        d.setHours(1);
                        p= new Post("1",d,"0",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"0",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"0",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"0",4);
                        FireBase.sendData(p, "Train");

                        d.setHours(2);
                        p= new Post("1",d,"0",1);
                        FireBase.sendData(p, "Train");
                        p= new Post("2",d,"0",2);
                        FireBase.sendData(p,"Train");
                        p= new Post("3",d,"0",3);
                        FireBase.sendData(p,"Train");
                        p= new Post("4",d,"0",4);
                        FireBase.sendData(p, "Train");

                        sleep(50000);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }



    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public void onMessageClick(Parcelable token) {
        this.finish();
    }
}
