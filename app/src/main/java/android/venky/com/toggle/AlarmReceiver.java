package android.venky.com.toggle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        TimeSetter inst = TimeSetter.instance();
        try {
            FireBase.noti(context, "Alarm", "Triggered", AlarmReceiver.class);
            Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
            Toast.makeText(inst.getApplicationContext(), " Teri maaa bajra me", Toast.LENGTH_LONG).show();
            //this will sound the alarm tone
            //this will sound the alarm once, if you wish to
            //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            String st=intent.getStringExtra("state");
            int pin= intent.getIntExtra("pin",18);
            String ip=intent.getStringExtra("ip");
            System.out.print(st+","+pin);
            //this will send a notification message
            /*ComponentName comp = new ComponentName(context.getPackageName(),
                    AlarmService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);*/
            DeviceAdapter.send_data_to_pi(st,pin,ip);
            Log.e("im", "oooo huzooor");
        } catch (Exception e) {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            System.out.print(e+"");

        }
    }
        public void send_data_to_pi(String state) {

            String ip = "192.168.2.100";
            String sampleURL = "http://" + ip.trim() + ":7000/venky/" + state;
            final HttpClient Client = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost(sampleURL);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Your code goes here
                        HttpResponse response = Client.execute(httppost);
                        //Toast.makeText(Toggle.this, "hello", Toast.LENGTH_SHORT).show();
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


    }
