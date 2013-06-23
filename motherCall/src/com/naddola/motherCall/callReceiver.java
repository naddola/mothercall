package com.naddola.motherCall;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class callReceiver extends BroadcastReceiver {

	String name;
	String phone;
	boolean vibrate;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		getExtra(intent);
		Log.i("call", "Receiver받는 name : " + name);
		Log.i("call", "Receiver받는 phone : " + phone);
		

		try {
			intent = new Intent(context, CallScreen01Activity.class);
			setParam(intent);
			Log.i("call", "Receiver보내는 name : " + name);
			Log.i("call", "Receiver보내는 phone : " + phone);
			PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
					PendingIntent.FLAG_ONE_SHOT);

			pi.send();
			
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getExtra(Intent intent) {
		name = intent.getStringExtra("name");
		phone = intent.getStringExtra("phone");
		vibrate = intent.getBooleanExtra("vib", true);
		
	}

	private void setParam(Intent intent) {
		intent.putExtra("name", name);
		intent.putExtra("phone", phone);
		intent.putExtra("vib", vibrate);
	}
}
