package com.yarsi.skripsi.activity;

/**
 * Created by Ocky Aditia Saputra on 03/06/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, MyAlarmService.class);
        service.putExtra("Nama", intent.getStringExtra("Nama"));
        service.putExtra("Total", intent.getStringExtra("Total"));
        context.startService(service);
    }
}
