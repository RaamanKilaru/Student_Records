package com.example.studentrecords;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    TabLayout tabslayout;
    ViewPager viewpager;
    PagerAdapter pageradapter;
    private static final String TAG = "SAI";/*"myActivity";*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Inside onCreate() of Main Activity.");
        tabslayout = (TabLayout) findViewById(R.id.tabslayout);

        //Initialized the placeholder for fragments i.e. ViewPager.
        viewpager = (ViewPager) findViewById(R.id.pager);

        //Created a new PagerAdapter instance so that it can create fragments and arrange them in order.
        pageradapter = new PagerAdapter(getSupportFragmentManager(), tabslayout.getTabCount());

        //The fragments in the pageradapter now take the place holded by viewpager.
        viewpager.setAdapter(pageradapter);

        //To automatically select the appropriate tab when fragment is slided.
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabslayout));

        //To automatically show the appropriate fragment when a tab is clicked.
        tabslayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageradapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageradapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Inside onStart() of Main Activity.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Inside onStop() of Main Activity.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Inside onDestroy() of Main Activity.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Inside onPause() of Main Activity.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Inside onResume() of Main Activity.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Inside onRestart() of Main Activity.");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"Inside onConfigurationChanged() of Main Activity.");
    }
}