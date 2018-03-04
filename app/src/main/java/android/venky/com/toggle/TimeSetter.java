package android.venky.com.toggle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.Firebase;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by milind on 1/28/2016.
 */
public class TimeSetter extends Activity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{
    private CheckBox mode24Hours;

    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static TimeSetter inst;
    private TextView alarmTextView;

    String ip;
    Date d= new Date();
    String name[];
    int arr[];
    public static TimeSetter instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    RecyclerView rv;int i=0;
    ToggleButton toggleButton;
    List<Device> list=new ArrayList<Device>();
    AlarmManager alarmManager;
    Map pin=new HashMap<Long,Integer>();
    Map nam=new HashMap<String,Long>();
    private static List<Alarms> alarms=new ArrayList<Alarms>();
AlarmManager am;
    PendingIntent pi;
    BroadcastReceiver br;
    ButtonRectangle butt;
    String rooms[] = {"Living room","Kitchen","Bedroom1","Bedroom2","Study room","Store room"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.timer);
/*SP=new SharedPreference();*/

        Firebase.setAndroidContext(this);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mode24Hours = (CheckBox)findViewById(R.id.mode_24_hours);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv=(RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(layoutManager);
        list=FireBase.devicesOnCloud;
        arr=new int[list.size()];

        name=new String[list.size()];
        for(int i=0;i<list.size();i++)
        {

            arr[i]=list.get(i).getPin();
            name[i]=list.get(i).getName();

        }


        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        ip = preferences.getString("ip","");


        final CardAdapter adapter = new CardAdapter(this);
        rv.setAdapter(adapter);
        butt=(ButtonRectangle)findViewById(R.id.butt);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = new GregorianCalendar();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        TimeSetter.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        mode24Hours.isChecked()

                );


                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");


            }
            //scheduleAlarm();

        });

    }
        /*SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(rv,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped left", Toast.LENGTH_SHORT).show();
                                    alarmManager.cancel(alarms.get(position).PI);
                                    alarms.remove(position);
                                    Log.e("time", "deleted");
                                    adapter.notifyItemRemoved(position);


                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//
//  Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                                    alarmManager.cancel(alarms.get(position).PI);
                                    alarms.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Log.e("time","deleted");
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        rv.addOnItemTouchListener(swipeTouchListener);
*/

    public void scheduleAlarm(Date d,int pin,String device,String room,int state)
    {

        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra("state", state);
        intentAlarm.putExtra("pin", pin);

        /*PendingIntent PI= PendingIntent.getBroadcast(this, 1, intentAlarm, 0);
        PendingIntent sender = PendingIntent.getBroadcast(this, 192837, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        //i.putExtra("room",room);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);*/
        am.set(AlarmManager.RTC_WAKEUP, d.getTime(),pi);
        /*alarmManager.set(AlarmManager.RTC_WAKEUP, d.getTime(), PI);*/
        Toast.makeText(this, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();
        Log.e("time", d.toString());

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++" + state);


        alarms.add(new Alarms(d, pi, R.drawable.ic_alarm1, state, i, device, room));
        FireBase.sendData(new Alarms(d, R.drawable.ic_alarm1, state, i, device, room), "Alarms");
        reload();

    }
    private void onDataChange(){

    }
    public void schedule(Date d,int pin,String device,String room,int state)
    {
        Intent myIntent = new Intent(TimeSetter.this, AlarmReceiver.class);
        myIntent.putExtra("state", state+"");
        myIntent.putExtra("pin",pin);
        myIntent.putExtra("ip",ip);
        pendingIntent = PendingIntent.getBroadcast(TimeSetter.this, 0, myIntent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, d.getHours());
        calendar.set(Calendar.MINUTE, d.getMinutes());
        calendar.set(Calendar.SECOND,0);
        Log.e("time", d.toString());
        Log.e("time", calendar.toString());
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


        alarms.add(new Alarms(d, pi, R.drawable.ic_alarm1, state, i, device, room));
        FireBase.sendData(new Alarms(d, R.drawable.ic_alarm1, state, i, device, room), "Alarms");
        reload();

    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year1, int monthOfYear, int dayOfMonth) {
        String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year1;
        d.setDate(dayOfMonth);
        d.setMonth(monthOfYear);
        d.setYear(year1);
        System.out.println(d.toString());
        showdevicedialog();

        //scheduleAlarm(d);
    }


    private void showdevicedialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(TimeSetter.this);
        View promptView = layoutInflater.inflate(R.layout.devicedialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimeSetter.this);


        alertDialogBuilder.setView(promptView);

        final Spinner room=(Spinner)promptView.findViewById(R.id.room);
        final Spinner device=(Spinner)promptView.findViewById(R.id.device);
        final ToggleButton state=(ToggleButton)promptView.findViewById(R.id.toggleButton);

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        device.setAdapter(spinnerArrayAdapter2);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rooms); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room.setAdapter(spinnerArrayAdapter);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //resultText.setText("Hello, " + editText.getText());
                        int st=0;
                        if (state.isChecked()) {
                            st = 1;
                        } else if(!state.isChecked()) {
                            st=0;
                        }
                        Log.e("st",st+"");
                        System.out.print(st);
                        schedule(d, arr[device.getSelectedItemPosition()], device.getSelectedItem().toString(), room.getSelectedItem().toString(),st);

                    }

                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        Button buttonbackground1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(this.getResources().getColor(R.color.primary));
        buttonbackground1.setTextSize(16);
        Button buttonbackground2 = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground2.setTextColor(this.getResources().getColor(R.color.primary));
        buttonbackground2.setTextSize(16);


    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        d.setHours(hourOfDay);
        d.setMinutes(minute);
        d.setSeconds(0);
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                TimeSetter.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );



        dpd.show(getFragmentManager(), "Datepickerdialog");


    }


}
