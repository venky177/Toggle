package android.venky.com.toggle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.venky.com.manager.PageViewManager;
import android.venky.com.adapter.SlidePageAdapter;
import android.venky.com.model.Page;
import android.venky.com.fragments.OnBoardingPagerIndicator;
import android.venky.com.fragments.OnboardingPageFragment;
import android.venky.com.util.OnboardingSharedPref;

import static android.content.Context.MODE_PRIVATE;


public class Onboard extends ActionBarActivity implements
        OnboardingPageFragment.OnFragmentInteractionListener,
        OnBoardingPagerIndicator.OnboardingPagerIndicatorInteractionListener{

    private PageViewManager mPagerMgr; //manages the slides/pages
    private ViewPager mViewPager; //slides
    private OnBoardingPagerIndicator onp; //the control at the bottom

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagerMgr = new PageViewManager();
        mPagerMgr.init();
        mPagerMgr.setTakeoffActivity(MainActivity.class);

        Page p = new Page(0, "#354353", R.mipmap.one);
        mPagerMgr.addPage(p);
        p = new Page(1, "#354ABC", R.mipmap.two);
        mPagerMgr.addPage(p);
        p = new Page(2, "#23f353", R.mipmap.three);
        mPagerMgr.addPage(p);

        Log.e("onCreate", "ViewpagerActivity onCreate called ---------------------------------->");
        /*We are starting up or coming from an Activity destroyed state, add the fragment*/
        if (savedInstanceState == null) {
            Log.e("viewpager", "adding Fragment now to Activity:" );
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            /*add the fragment or recreate if it was destroyed*/
            onp = OnBoardingPagerIndicator.newInstance(mPagerMgr.numPages());
            ft.add(R.id.homeLayout, onp, "footer" );
            ft.commit();

        }



        setContentView(R.layout.activity_onboard);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mViewPager= (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SlidePageAdapter(getSupportFragmentManager(), mPagerMgr));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*set the last page we were on*/
                mPagerMgr.setLastPage(position);

            }

            public void onPageSelected(int position) {
                //Log.i("viewpager", "onPageSelected:" + position + " Last page:" + MainActivity.this.mPagerMgr.getLastPage());
                OnBoardingPagerIndicator lFooterFrag = (OnBoardingPagerIndicator) getSupportFragmentManager().findFragmentByTag("footer");
                View v = lFooterFrag.getView().findViewById(R.id.pageviewindicatorcircles);
                View linearLayout = lFooterFrag.getView();
                if(position == 0) {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.one));
                    linearLayout.findViewById(R.id.skipTxt).setBackgroundColor(getResources().getColor(R.color.one));
                    linearLayout.findViewById(R.id.doneTxt).setBackgroundColor(getResources().getColor(R.color.one));
                    linearLayout.findViewById(R.id.pageviewindicatorcircles).setBackgroundColor(getResources().getColor(R.color.one));
                }
                else if(position == 1)
                {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.two));
                    linearLayout.findViewById(R.id.skipTxt).setBackgroundColor(getResources().getColor(R.color.two));
                    linearLayout.findViewById(R.id.doneTxt).setBackgroundColor(getResources().getColor(R.color.two));
                    linearLayout.findViewById(R.id.pageviewindicatorcircles).setBackgroundColor(getResources().getColor(R.color.two));
                }
                else {
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.three));
                    linearLayout.findViewById(R.id.skipTxt).setBackgroundColor(getResources().getColor(R.color.three));
                    linearLayout.findViewById(R.id.doneTxt).setBackgroundColor(getResources().getColor(R.color.three));
                    linearLayout.findViewById(R.id.pageviewindicatorcircles).setBackgroundColor(getResources().getColor(R.color.three));
                }
                //Log.d("asdfgh",""+position);
                mPagerMgr.setCurrPage(position);
                saveSharedPrefState();

                int lLastPage = Onboard.this.mPagerMgr.getLastPage();
                if (lLastPage == position) {
                    //this is an adjustment during scrolling back, hopefully the idea works - Simith
                    // do not really want to add the gesture listener
                    lLastPage = lLastPage + 1;
                }

                lFooterFrag.updateIndicatorCircles(lFooterFrag.getView(), position, lLastPage);
                lFooterFrag.updateIndicatorControlViews(lFooterFrag.getView(), position, lLastPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        saveSharedPrefState();
    }

    private void saveSharedPrefState() {

        OnboardingSharedPref.saveState(getPreferences(Context.MODE_PRIVATE),mPagerMgr.getCurrPage());
    }

    @Override
    protected void onStop(){

        super.onStop();
        Log.w("STOPPED", "A C T I V I T Y Stopped.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSkip() {

        takeOff();

    }

    @Override
    public void onDone() {

        takeOff();


    }

    @Override
    public void onNext() {

        this.mViewPager.setCurrentItem(this.mPagerMgr.getCurrPage() + 1);
        saveSharedPrefState();

    }

    @Override
    public void onPrev() {

    }

    public void takeOff(){

        //start the Takeoff activity
        Class lTakeoffActivity = null;
        lTakeoffActivity = mPagerMgr.getTakeoffActivity();

        if(lTakeoffActivity == null)
            lTakeoffActivity= MainActivity.class;

        Intent i = new Intent(this,lTakeoffActivity);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(i);
        finish();
    }
}