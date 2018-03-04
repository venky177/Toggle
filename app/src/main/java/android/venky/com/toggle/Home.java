package android.venky.com.toggle; /**
 * Created by root on 2/2/16.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.venky.com.toggle.R;
import android.venky.com.toggle.Toggle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Home extends Activity implements View.OnTouchListener {
    String ip;
    String room="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView iv = (ImageView) findViewById (R.id.image);
        if (iv != null) {
            iv.setOnTouchListener (this);
        }
     /*   toast ("Touch the screen to discover where the regions are.");*/
        SharedPreferences preferences = getSharedPreferences("ipstore", Context.MODE_PRIVATE);
        ip = preferences.getString("ip","");

    }

    public boolean onTouch (View v, MotionEvent ev)
    {
        boolean handledHere = false;
        final int action = ev.getAction();
        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();
        int nextImage = -1;			// resource id of the next image to display

        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById (R.id.image);
        if (imageView == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.
        Integer tagNum = (Integer) imageView.getTag ();
        int currentResource = (tagNum == null) ? R.mipmap.home_default : tagNum.intValue ();

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (currentResource == R.mipmap.home_default) {
                    nextImage = R.mipmap.home_default;
                    handledHere = true;
                } else handledHere = true;
                break;

            case MotionEvent.ACTION_UP:
                // On the UP, we do the click action.
                // The hidden image (image_areas) has three different hotspots on it.
                // The colors are red, blue, and yellow.
                // Use image_areas to determine which region the user touched.
                int touchColor = getHotspotColor(R.id.image_areas, evX, evY);

                // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
                // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
                // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
                // varying pixel density.

                ColorTool ct = new ColorTool();

                int tolerance = 25;
                nextImage = R.mipmap.home_default;
                if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
                    nextImage = R.mipmap.store_room;
                    room="Store room";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);


                }
                else if (ct.closeMatch(Color.BLUE, touchColor, tolerance))    {
                    nextImage = R.mipmap.kitchen;
                    room="Kitchen";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);


                }
                else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance)) {
                    nextImage = R.mipmap.bedroom2;
                    room="Bedroom2";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);

                }
                else if (ct.closeMatch (Color.WHITE, touchColor, tolerance))  {
                    nextImage =R.mipmap.home_default;
                    room="";

                }
                else if(ct.closeMatch(Color.GREEN,touchColor,tolerance)) {
                    nextImage = R.mipmap.living_room;
                    room="Living room";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);

                }
                else if(ct.closeMatch(Color.BLACK,touchColor,tolerance)) {
                    nextImage = R.mipmap.study_room;
                    room="Study room";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);

                }
                else if(ct.closeMatch(Color.MAGENTA,touchColor,tolerance))  {
                    nextImage= R.mipmap.bedroom1;
                    room="Bedroom1";
                    FireBase.getDevicesPerRoom(Home.this,"Devices",room);

                }
                handledHere = true;
                break;

            default:
                handledHere = false;
        } // end switch



        if (handledHere) {

            if (nextImage > 0) {
                imageView.setImageResource (nextImage);
                imageView.setTag (nextImage);
            }
        }

        //FireBase.getDevicesPerRoom(Home.this,"Devices",room);

        return handledHere;
    }
/**
 */
// More methods

    /**
     * Get the color from the hotspot image at point x-y.
     *
     */

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    /**
     * Show a string on the screen via Toast.
     *
     * @param msg String
     * @return void
     */

    public void toast (String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}