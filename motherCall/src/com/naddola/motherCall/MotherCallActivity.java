package com.naddola.motherCall;

import com.naddola.motherCall.QuickCallActivity;
import com.naddola.motherCall.MotherCallActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class MotherCallActivity extends Activity {
	
	ImageButton buttonQuick;
	ImageButton buttonOriginal;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findView();
        addListener();
    }
    
    public void findView() {
    	buttonQuick = (ImageButton) findViewById(R.id.buttonQuick);
    	buttonOriginal = (ImageButton) findViewById(R.id.buttonOriginal);
    }
    
    public void addListener() {
    	buttonQuick.setOnClickListener(new MyListener());
    	buttonOriginal.setOnClickListener(new MyListener());
    }
    
    class MyListener implements View.OnClickListener{
    	ImageButton select;
		public void onClick(View v) {
			Intent intent;
			select = (ImageButton)v;
			switch(select.getId()){
			case R.id.buttonQuick:
				intent = new Intent(MotherCallActivity.this,
						QuickCallActivity.class);
				startActivity(intent);
				break;
			case R.id.buttonOriginal:
				intent = new Intent(MotherCallActivity.this,
						OriginalCallActivity.class);
				startActivity(intent);
				break;
			}
		}
    }
}