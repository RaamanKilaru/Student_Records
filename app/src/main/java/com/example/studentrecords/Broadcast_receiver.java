package com.example.studentrecords;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import java.util.Random;


public class Broadcast_receiver extends BroadcastReceiver {

    private TabLayout tabLayout;

    public Broadcast_receiver(TabLayout tabLayout){
        if (tabLayout != null){
            this.tabLayout = tabLayout;
            Log.i("SCREEN","Reciever Initialized.");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
           // Log.i("SCREEN_ON","Got screen ON Intent.");
            //Toast.makeText(context, "Screen ON",Toast.LENGTH_LONG).show();
            Random random = new Random();
            tabLayout.setBackgroundColor(Color.argb(
                   100 ,
                    random.nextInt(256) ,
                    random.nextInt(256) ,
                    random.nextInt(256)  ) );
        }
    }
}
