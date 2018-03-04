package android.venky.com.manager;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import android.venky.com.model.Page;

/**
 * Created by snambiar on 7/07/15.
 * Manages pages for slides, these pages are fed to the @SlidePageAdapter
 */
public class PageViewManager {

    List<Page> mPageList;
    int mCurrent = 0;
    int mLastPage = 0;
    Class mTakeoffActivity;

    public PageViewManager() {

        mPageList = new LinkedList<Page>();
    }

    public void init() {

    }

    public Page getPage(int index){

        return mPageList.get(index);
    }

    public int numPages(){

        return mPageList.size();
    }

    public int getCurrPage(){

        Log.w("getCurrPage","The curent page is set to :"+mCurrent);
        return this.mCurrent;
    }

    public void addPage(Page pPage){

        mPageList.add(pPage);
    }

    public void onNext(){

        this.mCurrent++;
    }

    public void onPrev(){

       this.mCurrent--;
    }

    public void setCurrPage(int pPageNum){

        mCurrent = pPageNum;
    }

    public void setLastPage(int pLastScrolledPage){

        this.mLastPage = pLastScrolledPage;
    }


    public int getLastPage(){

        return this.mLastPage;
    }

    public Class getTakeoffActivity() {
        return mTakeoffActivity;
    }

    public void setTakeoffActivity(Class pTakeoffActivity){

        this.mTakeoffActivity = pTakeoffActivity;

    }
}