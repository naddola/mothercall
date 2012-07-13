package com.naddola.motherCall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

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
	
	Button buttonThirty;
	Button buttonFiveMinute;

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
	}
	
	public void findView(){
		buttonThirty = (Button) findViewById(R.id.buttonThirty);
		buttonFiveMinute = (Button) findViewById(R.id.buttonFiveMinute);
	}
	
	public void addListener(){
		buttonThirty.setOnClickListener(new myListener());
	}
	
	class myListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			
			case R.id.buttonThirty:
				break;
			case R.id.buttonFiveMinute:
				break;
				
			case R.id.buttonMother:
				break;
			case R.id.buttonMyLove:
				break;
				
			case R.id.buttonMrs:
				break;
			case R.id.buttonGirl:
				break;
			case R.id.buttonBoy:
				break;
			}
		}
	}
	
}
