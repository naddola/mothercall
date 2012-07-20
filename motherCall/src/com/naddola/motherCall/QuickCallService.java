package com.naddola.motherCall;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class QuickCallService extends Service {

	boolean isRunning = true;
	
	Vibrator vibrator;

	@Override
	public void onCreate() {

		// vibrate
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		try {
			Intent intent = new Intent(getBaseContext(),
					CallScreen01Activity.class);
			PendingIntent pi = PendingIntent.getActivity(getBaseContext(), 0,
					intent, PendingIntent.FLAG_ONE_SHOT);
			pi.send();
		}

		catch (CanceledException e) {
			e.printStackTrace();
		}

		Thread triggerService = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (isRunning) {
					try {

						Log.e("Androday", "Ring ~ ");

						vibrator.vibrate(1000);

						Thread.sleep(2000);

					} catch (Exception e) {
						// TODO: handle exception
						Log.i("MyServiceIntent", e.getMessage());
					}
				}

				// 한번만 울리고 서비스 종료 하려면
				// Done with our work... stop the service!
				// AlarmService_Service.this.stopSelf();
			}
		});

		triggerService.start();
	}

	@Override
	public void onDestroy() {

		isRunning = false;

		// Tell the user we stopped.
		Toast.makeText(this, R.string.StopCall, Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
