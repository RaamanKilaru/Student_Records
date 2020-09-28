package com.example.studentrecords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"Inside oncreate() of Activity.");

        TabLayout tabslayout = (TabLayout) findViewById(R.id.tabslayout);
        //Initialized the placeholder for fragments i.e. ViewPager.
        final ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        //Created a new PagerAdapter instance so that it can create fragments and arrange them in order.
        final PagerAdapter pageradapter = new PagerAdapter(getSupportFragmentManager(), tabslayout.getTabCount());
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
}