package com.naddola.motherCall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class CallScreen01Activity extends Activity {
	Button stop;
	Intent intent;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		  // Window feature for no title - must be set prior to calling setContentView.
	    requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.calling_default);		
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		stop = (Button) findViewById(R.id.CallingButtonTemp);
		
		intent = new Intent(CallScreen01Activity.this, QuickCallService.class);
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopService(intent);
				// 서비스 취소
				//AM.cancel(ServicePending);
				// 알람 취소

				
			}
		});
	}
}
