package com.matej.opomnik;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver {
	
	@Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Alarm went off", Toast.LENGTH_SHORT).show();
        
        Intent scheduledIntent = new Intent(context, Opomnik.class);
       
        scheduledIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(scheduledIntent);
    }
	
}
