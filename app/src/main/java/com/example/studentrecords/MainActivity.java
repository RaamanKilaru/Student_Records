package com.example.studentrecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabslayout;
    ViewPager viewpager;
    PagerAdapter pageradapter;
    Broadcast_receiver tab_color_receiver;
    private static final String TAG = "myActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i(TAG,"Inside oncreate() of Activity.");

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

        //To change TabLayout background for every Intent.ACTION_SCREEN_ON.
        tab_color_receiver = new Broadcast_receiver(tabslayout);
        registerReceiver(tab_color_receiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(tab_color_receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}