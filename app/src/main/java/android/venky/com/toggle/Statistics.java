package android.venky.com.toggle;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDeterminate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class Statistics extends Activity {
    public static RecyclerView rv;
    public CustomGauge gauge2;
    public TextView text2,text3,text4,text5,text6,text7;
    int i;
    double Power;
    List<Device> device=FireBase.devicesOnCloud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);



        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv=(RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(layoutManager);
        final StatAdapter adapter = new StatAdapter(device);
        rv.setAdapter(adapter);
        //Button button = (Button) findViewById(R.id.button);
        gauge2 = (CustomGauge) findViewById(R.id.gauge1);
        text2 = (TextView) findViewById(R.id.textView2);
        text3 = (TextView) findViewById(R.id.textView3);
        text4 = (TextView) findViewById(R.id.textView4);

        //button.setOnClickListener(new OnClickListener() {
        Iterator iterator = device.iterator();
       /* while (iterator.hasNext()) {
            Power=Power+(device.get(i).getVolt()*device.get(i).getHoursOn())/1000;
        }*/

        //@Override
        //public void onClick(View v) {

        new Thread() {
            public void run() {
                for (i = 0; i<device.size(); i++) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int j=50 * i;

                                Power=Power+((device.get(i).getVolt()*device.get(i).getHoursOn())/1000);
                                System.out.println("+++++++++++++++++++++++++++++++++++++++++"+Power);
//										text2.setText(Integer.toString(gauge2.getValue()) + " KWH");
                                text3.setText(Power+" kWH");
                                gauge2.setValue((int)750);
                            }
                        });
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    //});
    //}


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
