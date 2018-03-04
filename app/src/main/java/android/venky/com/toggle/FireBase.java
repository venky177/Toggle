package android.venky.com.toggle;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by milind on 3/1/2016.
 */
public class FireBase {
    static Firebase myFirebaseRef = new Firebase("https://toggle.firebaseio.com/");
    public static List<Post> serverdata;
    public static ArrayList<Device> devicesOnCloud;
    public static ArrayList<Device> devicesInRoom;
    public static List<String> devkeysInRoom;
    public static ArrayList<Alarms> alarmsInRoom;
    public static List<String> devkeys;
    public static void sendData(final Post p)
    {
        myFirebaseRef.push().setValue(p);
    }
    public static String ip;
    public static void sendData(final Alarms p,String child)
    {
        myFirebaseRef.child(child).push().setValue(p);
    }
    public static void sendData(final Post p,String child)
    {
        myFirebaseRef.child(child).push().setValue(p);
    }

    public static void sendData(final Device p,String child)
    {
        myFirebaseRef.child(child).push().setValue(p);
    }

    public static List<Post> getData(Context context,final String child)
    {
        try {

            final ProgressDialog progDailog = ProgressDialog.show(context,
                    "Loading Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        myFirebaseRef.child(child).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                                serverdata=  new ArrayList<Post>();;
                                int i=0;
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Post post = postSnapshot.getValue(Post.class);
                                    serverdata.add(post);
                                    System.out.println(post.getTimestamp().toString() + " - " + post.getState());

                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                        sleep(5000);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

        return serverdata;
    }


    public static List<Alarms> getAlarms(Context context,final String child)
    {
        try {

            final ProgressDialog progDailog = ProgressDialog.show(context,
                    "Loading Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        myFirebaseRef.child(child).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                                alarmsInRoom=  new ArrayList<Alarms>();;
                                int i=0;
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Alarms post = postSnapshot.getValue(Alarms.class);
                                    Date d= new Date();
                                    if(post.getDate().getTime()>d.getTime());
                                    alarmsInRoom.add(0,post);


                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                        sleep(5000);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

        return alarmsInRoom;
    }

    public static List<Device> getDevices(final Context context,final String child)
    {
        try {

            final ProgressDialog progDailog = ProgressDialog.show(context,
                    "Loading Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        myFirebaseRef.child(child).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                String title = "Device Updated!";
                                String text = "All your devices have been updated";
                                noti(context,title,text,Edit.class);
                                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                                devicesOnCloud=  new ArrayList<Device>();
                                devkeys= new ArrayList<String>();

                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Device dev = postSnapshot.getValue(Device.class);
                                    devicesOnCloud.add(dev);
                                    System.out.println(dev.getName() + " - " + dev.getState());
                                    devkeys.add(postSnapshot.getKey());
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                        sleep(100);
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

        return devicesOnCloud;
    }

    public static List<Device> getDevicesPerRoom(final Context context,final String child,final String room)
    {
        SharedPreferences preferences = context.getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        ip = preferences.getString("ip","");
        try {

            final ProgressDialog progDailog = ProgressDialog.show(context,
                    "Loading Data...",
                    "Please wait....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        Query queryRef = myFirebaseRef.child(child).orderByChild("room").equalTo(room);
                        queryRef.addValueEventListener(new ValueEventListener() {
                            // myFirebaseRef.child(child).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                System.out.println("There are " + snapshot.getChildrenCount() + " child blog posts");
                                devicesInRoom = new ArrayList<Device>();
                                devkeysInRoom = new ArrayList<String>();

                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    Device dev = postSnapshot.getValue(Device.class);
                                    devicesInRoom.add(dev);
                                    System.out.println(dev.getName() + " - " + dev.getState());
                                    devkeysInRoom.add(postSnapshot.getKey());
                                    //devkeys.add(postSnapshot.getKey());

                                }
                                //Toggle.adapter=new DeviceAdapter(devicesInRoom,context,ip);
                                //Toggle.adapter.notifyDataSetChanged();

                                //Intent i = new Intent(context,Toggle.class);
                                //i.putExtra("ip", ip);
                                //i.putExtra("room",room);
                                //context.startActivity(i);
                                //((Activity)context).finish();
                            }



                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                System.out.println("The read failed: " + firebaseError.getMessage());
                            }
                        });
                        sleep(1000);
                        //Toggle.rv.setAdapter(new DeviceAdapter(context));
                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                    Intent i = new Intent(context,Toggle.class);
                    i.putExtra("ip",ip);
                    i.putExtra("room", room);
                    context.startActivity(i);




                    /*this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            //title.clearComposingText();//not useful

                        }
                    });*/



                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

        return devicesInRoom;
    }

public static void deleteDev(String Child,String Key)
{
    myFirebaseRef.child(Child).child(Key).removeValue();
}

public static void updateDev(int ind,String val)
{

    Map<String, Object> nickname = new HashMap<String, Object>();
    nickname.put("state", val);
    myFirebaseRef.child("Devices").child(FireBase.devkeys.get(ind)).updateChildren(nickname);
}

    public static void updateDevInRoom(int ind,String val)
    {

        Map<String, Object> nickname = new HashMap<String, Object>();
        nickname.put("state", val);
        myFirebaseRef.child("Devices").child(FireBase.devkeysInRoom.get(ind)).updateChildren(nickname);
    }


    public static void updateDevStart(final int ind,final long val)
    {

        try {

            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        Map<String, Object> nickname = new HashMap<String, Object>();
                        nickname.put("startTime", val);
                        myFirebaseRef.child("Devices").child(FireBase.devkeysInRoom.get(ind)).updateChildren(nickname);
                        sleep(100);
                    } catch (Exception e) {
                    }

                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

    }

    public static void updateDevHrs(final int ind,final int val)
    {

        try {

            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        Map<String, Object> nickname = new HashMap<String, Object>();
                        nickname.put("hoursOn", val);
                        myFirebaseRef.child("Devices").child(FireBase.devkeysInRoom.get(ind)).updateChildren(nickname);
                        sleep(100);
                    } catch (Exception e) {
                    }

                }
            }.start();

        } catch (Exception e) {
            Log.e("Error", "");

        }

    }


    public static void noti(Context context,String title,String msg,Class c) {



        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg);

        Intent notificationIntent = new Intent(context, c);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1010, builder.build());
    }



}
