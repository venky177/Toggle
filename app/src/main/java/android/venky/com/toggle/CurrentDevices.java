package android.venky.com.toggle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class CurrentDevices extends AppCompatActivity {

    private Drawer result = null;
    ArrayList<Device> arrayList;
    ListView lv;

    CustomAdapterCurrentDevices adap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_devices);
        //FireBase.getData(this, "Train");
        //FireBase.getDevices(this, "Devices");
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar1);
        context = this;
        Firebase.setAndroidContext(this);
        arrayList = FireBase.devicesOnCloud;
        lv = (ListView) findViewById(R.id.listView123);
        lv.setAdapter(new CustomAdapterCurrentDevices(context, arrayList));

        result = new DrawerBuilder()
                .withActivity(CurrentDevices.this)
                .withRootView(R.id.frame)
                .withToolbar(toolbar1)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerWidthDp(200)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Set Alarms"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Graph"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Statistics"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Settings")).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            switch (position) {
                                case 0:
                                    intent = new Intent(CurrentDevices.this, Home.class);
                                    CurrentDevices.this.startActivity(intent);
                                    break;

                                case 2:
                                    intent = new Intent(CurrentDevices.this, TimeSetter.class);
                                    CurrentDevices.this.startActivity(intent);
                                    break;
                                case 4:
                                    intent = new Intent(CurrentDevices.this, Graph.class);
                                    CurrentDevices.this.startActivity(intent);
                                    break;
                                case 6:
                                    intent = new Intent(CurrentDevices.this, Statistics.class);
                                    CurrentDevices.this.startActivity(intent);

                                    break;
                                case 8:
                                    intent = new Intent(CurrentDevices.this, Display.class);
                                    CurrentDevices.this.startActivity(intent);
                                    break;

                            }

                        }

                        return false;
                    }
                }).build();
       /* if (savedInstanceState == null) {
            // set the selection to the item with the identifier 10
            result.setSelectionByIdentifier(10, false);


        }*/




    }
}
