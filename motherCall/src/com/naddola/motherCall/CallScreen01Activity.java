package com.naddola.motherCall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CallScreen01Activity extends Activity {
	Button stop;
	Intent intent;

	ImageView photo;
	TextView who;
	TextView phone;

	Thread vibrateThread;
	Vibrator vibrator;
	boolean isRunning = true;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Window feature for no title - must be set prior to calling
		// setContentView.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calling_default);

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
						| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		// ////////////////////

		vibrate();

		findView();

		intent = getIntent();
		setScreen();
		stop = (Button) findViewById(R.id.CallingButtonTemp);

		intent = new Intent(CallScreen01Activity.this, QuickCallService.class);
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopService(intent);
				// 서비스 취소
				// AM.cancel(ServicePending);
				// 알람 취소

			}
		});
	}

	private void vibrate() {
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrateThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try {
						Log.e("Androday", "Ring ~ ");
						vibrator.vibrate(1000);
						Thread.sleep(2000);
					} catch (Exception e) {
						Log.i("MyServiceIntent", e.getMessage());
					}
				}
			}
		});
		vibrateThread.start();
	}

	private void setScreen() {
		photo.setImageBitmap(OriginalCallActivity.bitmap);
		who.setText(intent.getStringExtra("name"));
		phone.setText(intent.getStringExtra("phone"));
		isRunning = intent.getBooleanExtra("vib", true);
	}

	private void findView() {
		photo = (ImageView) findViewById(R.id.CallingPhoto);
		who = (TextView) findViewById(R.id.callingWho);
		phone = (TextView) findViewById(R.id.callingWhosPhone);
	}
	
	@Override
	protected void onDestroy() {
		isRunning = false;
		super.onDestroy();
	}

}
