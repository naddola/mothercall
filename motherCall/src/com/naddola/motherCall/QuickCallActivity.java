package com.naddola.motherCall;


import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class QuickCallActivity extends Activity {
	
	int callTime;
	int callWho;
	int callVoice;
	
	int timeThirty = 0;
	int timeFiveMinute = 1;
	
	int whoMother = 0;
	int whoLove = 1;
	
	int voiceMrs = 0;
	int voiceGirl = 1;
	int voiceBoy = 2;
	
	private Button buttonThirty;
	private Button buttonFiveMinute;
	private Button buttonConfirm;
	private Intent intent;
	private PendingIntent ServicePending;
	private AlarmManager AM;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.quickcall);
	    
	    callTime = 0;
	    callWho = 0;
	    callVoice = 0;
	    
	    findView();
	    addListener();
	    
		intent = new Intent(QuickCallActivity.this, QuickCallService.class);

		ServicePending = PendingIntent.getService(QuickCallActivity.this, 0, intent, 0);
		AM = (AlarmManager) getSystemService(ALARM_SERVICE);
	}
	
	public void findView(){
		buttonThirty = (Button) findViewById(R.id.QuickButtonThirty);
		buttonFiveMinute = (Button) findViewById(R.id.QuickButtonFiveMinute);
		buttonConfirm = (Button) findViewById(R.id.QuickButtonConfirm);
	}
	
	public void addListener(){
		buttonThirty.setOnClickListener(new myListener());
		
		buttonConfirm.setOnClickListener(new myListener());
	}
	
	class myListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			
			case R.id.QuickButtonThirty:
				break;
			case R.id.QuickButtonFiveMinute:
				break;
				
			case R.id.QuickButtonMother:
				break;
			case R.id.QuickButtonMyLove:
				break;
				
			case R.id.QuickButtonMrs:
				break;
			case R.id.QuickButtonGirl:
				break;
			case R.id.QuickButtonBoy:
				break;
			case R.id.QuickButtonConfirm:
				Date t = new Date();
				t.setTime(java.lang.System.currentTimeMillis() + 5 * 1000);
				AM.set(AlarmManager.RTC_WAKEUP, t.getTime(), ServicePending);
				finish();
				break;
			}
		}
	}
	
}
