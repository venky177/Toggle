package android.venky.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.venky.com.fragments.OnboardingPageFragment;
import android.venky.com.manager.PageViewManager;

/**
 * Created by snambiar on 9/07/15.
 */
public class SlidePageAdapter extends FragmentStatePagerAdapter {


    PageViewManager mPageMgr;

    public SlidePageAdapter(FragmentManager pFm, PageViewManager pPagerMgr) {
        super(pFm);
        this.mPageMgr = pPagerMgr;
    }




    @Override
    public Fragment getItem(int position) {
        OnboardingPageFragment lOp;
        lOp = OnboardingPageFragment.newInstance(this.mPageMgr.getPage(position));
        return (Fragment)lOp;
    }

    @Override
    public int getCount() {
        return this.mPageMgr.numPages();
    }
}
