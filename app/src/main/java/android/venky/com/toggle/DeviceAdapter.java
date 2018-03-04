package android.venky.com.toggle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.daimajia.swipe.SwipeLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.PersonViewHolder> {
    List<Device> devices;
    Context context;
    static String ip;
    double Power;
    Map<Integer, Long> map = new HashMap<Integer, Long>();
    Map<Integer, Double> powe = new HashMap<Integer, Double>();
    static long ID;
    Date Date1=null,Date2=null;
    long hr;
    static int pow;
    DeviceAdapter(Context context,String ip){

        this.devices = FireBase.devicesInRoom;
        this.context=context;
        this.ip=ip;
    }
    DeviceAdapter(List<Device> dev,Context context,String ip){

        this.devices = dev;
        this.context=context;
        this.ip=ip;
    }

    public static int getImageId(Context context,String Imname){
        return context.getResources().getIdentifier("drawable/"+Imname,null,context.getPackageName());
    }



    @Override
    public DeviceAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device, viewGroup, false);

        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, final int i) {

        personViewHolder.Power.setText(((double)(devices.get(i).getHoursOn()) *devices.get(i).getVolt()) / 1000 + " kWh");
        personViewHolder.Duration.setText(devices.get(i).getHoursOn() + " hrs");
        SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, HH:mm");
        personViewHolder.Laston.setText(dateformat.format(devices.get(i).getStartTime()));
        personViewHolder.Im.setImageResource(getImageId(context, devices.get(i).getDrawable()));
        ID=devices.get(i).getId();
        int mystate=devices.get(i).getState();
        if(mystate==1)personViewHolder.toggleButton.setChecked(true);
        pow=devices.get(i).getVolt();
        final int showhrs=devices.get(i).getHoursOn();
        final Date old= new Date(devices.get(i).getStartTime());
        personViewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state;
                if (isChecked) {
                    state = "1";
                    Date1 = new Date();
                    FireBase.updateDevStart(i, Date1.getTime());

                } else {
                    state = "0";
                    Date2 = new Date();
                    long diff = Date2.getTime() - old.getTime();
                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000);
                    System.out.println("Time in seconds: " + diffSeconds + " seconds.");
                    System.out.println("Time in minutes: " + diffMinutes + " minutes.");
                    System.out.println("Time in hours: " + diffHours + " hours.");

                    Power = ((double)(showhrs + diffHours) * pow) / 1000;
                    System.out.println(Power + " KWH");
                    personViewHolder.Power.setText(Power + " kWh");
                    personViewHolder.Duration.setText(devices.get(i).getHoursOn() + " hrs");
                    SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd, HH:mm");
                    personViewHolder.Laston.setText(dateformat.format(devices.get(i).getStartTime()));
                    FireBase.updateDevHrs(i, (int) (showhrs + diffHours));
                }


                FireBase.updateDev(i, state);
                send_data_to_pi(state, devices.get(i).getPin(),ip);
                Post p = new Post("1", new Date(), state, devices.get(i).getId());
                senData(p);
            }
        });



        personViewHolder.Name.setText(devices.get(i).getName());


    }
    @Override
    public int getItemCount() {
        return devices.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView Laston,Power,Name,Duration;
        ImageView Im,Im2;
        ToggleButton toggleButton;
        public SwipeLayout swipeLayout;
        CardView cv,cv1;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.view);

            Im=(ImageView)itemView.findViewById(R.id.imageView);
            Name = (TextView)itemView.findViewById(R.id.name);
            Laston = (TextView)itemView.findViewById(R.id.laston);
            Power = (TextView) itemView.findViewById(R.id.power);
            toggleButton=(ToggleButton)itemView.findViewById(R.id.toggleButton2);
            Duration = (TextView) itemView.findViewById(R.id.duration);

        }
    }
    /*public void send_data_to_pi(String state) {
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

    }*/



    public static void send_data_to_pi(String state,int pin,String ip ) {

        if (ip.equals("")) ip = "192.168.0.110";
        String sampleURL = "http://" + ip.trim() + ":7000/venky/" + state+","+pin;
        final HttpClient Client = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(sampleURL);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    HttpResponse response = Client.execute(httppost);
                    //Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
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

            final ProgressDialog progDailog = ProgressDialog.show(context,
                    "Sending Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        FireBase.sendData(p,"Live");
                        sleep(100);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

    }
}