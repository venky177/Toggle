package android.venky.com.toggle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.google.gson.JsonElement;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.ui.AIButton;


public class Toggle extends AppCompatActivity {

    public static RecyclerView rv;
    public static DeviceAdapter adapter;
    ToggleButton toggleButton;
    TextView text;
    AIButton aiButton;
    String dev = "",state="";
    int date11, date12, date21, date22, date13, date23;
    static int sum = 0, avg;
    long[] hours;
    long hr;
    Date Date1, Date2;
    double Power;
    int ghanta;
    int index = 0;
    TextView t;
    Intent Stats = null;
    String ip,room;
    Context context;
Button stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        context=this;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv=(RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(layoutManager);
        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        ip = preferences.getString("ip", "");
        room=preferences.getString("room","");
        //FireBase.getDevicesPerRoom(this, "Devices",room);
         adapter = new DeviceAdapter(this,ip);
        rv.setAdapter(adapter);


        final String ip = getIntent().getExtras().getString("ip");
        //if (ip.equals("")) ip = "192.168.0.110";
        //int port = 27017;//7700;
        //Mongo mon = new Mongo(ip, port);
        //String op = mon.getdata();



        toggleButton = (ToggleButton) findViewById(R.id.toggleButton2);

        //toggleButton.setChecked(Boolean.parseBoolean(op));


        //Normal code
        //if (op.contains("true"))
          //  toggleButton.toggle();

       /* toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

                String state;
                if (isChecked == true) {
                    state = "1";
                } else {
                    state = "0";
                }
                send_data_to_pi(state);
                Post p= new Post("1",new Date(),state);
                senData(p);


                *//*String ip = getIntent().getExtras().getString("ip");
                if (ip.equals("")) ip = "192.168.0.110";
                String sampleURL = "http://" + ip.trim() + ":7000/venky/" + state;
                final HttpClient Client = new DefaultHttpClient();
                final HttpPost httppost = new HttpPost(sampleURL);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Your code goes here
                            HttpResponse response = Client.execute(httppost);
                            Toast.makeText(Toggle.this, "hello", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();*//*
                Stats = new Intent(Toggle.this, Statistics.class);
                if (isChecked) {
                    Date1 = new Date();

                } else if (!isChecked) {
                    Date2 = new Date();
                    long diff = Date2.getTime() - Date1.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                    System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                    System.out.println("Time in hours: " + diffHours + " hours.");
                    if (Date2.getDate() == Date1.getDate()) {
                        hr = hr + diffHours +1;
                        System.out.println(hr);
                    } else if (Date2.getDate() == Date1.getDate() + 1) {
                        long s = 24 - Date1.getHours();
                        System.out.println(Date1.getHours() + "     " + s);
                        hr = hr + s;


                        index++;
                    }
                    double d = (double) hr;
                    Power = 30 * d / 1000;
                    System.out.println(Power + " KWH");
                    Power=Power*100;
                    int i =(int) Power;
                    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");

                    String swton,swtoff;
                    swton=sdf.format(Date1);
                    swtoff=sdf.format(Date2);
                    System.out.println(i+"        "+i);
                    Stats.putExtra("seconds", diffSeconds);
                    Stats.putExtra("minutes",diffMinutes);
                    Stats.putExtra("hours",diffHours);
                    Stats.putExtra("date1",swton);
                    Stats.putExtra("date2",swtoff);
                    Stats.putExtra("hr",i);
                }

            }
        });
*/

        //Mic Shizz code
        final AIConfiguration config = new AIConfiguration("8a7be1accd474d37b2e1963aa82ed822", "54cb832d-cb92-4a29-995d-193517c9844b",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiButton = (AIButton) findViewById(R.id.micButton);

        aiButton.initialize(config);
        aiButton.setResultsListener(new AIButton.AIButtonListener() {
            @Override
            public void onResult(final AIResponse response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ApiAi", "onResult");
                        // TODO process response here
                        final Result result = response.getResult();
                        final HashMap<String, JsonElement> params = result.getParameters();
                        if (params != null && !params.isEmpty()) {
                            for (final Map.Entry<String, JsonElement> entry : params.entrySet()) {
                                // String.format("%s: %s", entry.getKey(), entry.getValue().toString());
                                if (entry.getKey().equals("type") ) {

                                    dev = entry.getValue().toString();
                                    dev = dev.replaceAll("\"", "");

                                }
                                if(entry.getKey().equals("st")) {
                                    state = entry.getValue().toString();
                                    state = state.replaceAll("\"", "");
                                }

                            }
                            Log.d("Res", dev+" "+state);
                            List<Device> devices;
                            devices=FireBase.devicesInRoom;
                            int i=0;
                            for(Device dd : devices)
                            {
                                if(dd.getName().toLowerCase().contains(dev.toLowerCase()))break;
                                i++;
                            }
                            Log.d("i", "" + i);
                            FireBase.updateDevInRoom(i, state);
                            devices.get(i).setState(Integer.parseInt(state));
                            DeviceAdapter adap=new DeviceAdapter(devices,context,ip);
                            rv.setAdapter(adap);
                            DeviceAdapter.send_data_to_pi(state,devices.get(i).getPin(),ip);
                            //final DeviceAdapter adapter = new DeviceAdapter(context,ip);
                            //rv.setAdapter(adapter);


                        }
                    }
                });

            }

            @Override
            public void onError(final AIError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("ApiAi", "onError");
                        Toast.makeText(Toggle.this, "Error in API AI!!!", Toast.LENGTH_LONG).show();
                        // TODO process error here
                    }
                });
            }

            @Override
            public void onCancelled() {

            }
        });

    }

    public void send_data_to_pi(String state) {
        String ip = getIntent().getExtras().getString("ip");
        if (ip.equals("")) ip = "192.168.0.110";
        String sampleURL = "http://" + ip.trim() + ":7000/venky/" + state;
        final HttpClient Client = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(sampleURL);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    HttpResponse response = Client.execute(httppost);
                    Toast.makeText(Toggle.this, "hello", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void senData(final Post p)
    {
        try {

            final ProgressDialog progDailog = ProgressDialog.show(Toggle.this,
                    "Sending Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        FireBase.sendData(p,"Live");
                        sleep(10000);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

    }

    public void Stats() {
        startActivity(Stats);

    }

}