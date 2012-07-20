package com.naddola.motherCall;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class OriginalCallActivity extends Activity implements
		OnTimeChangedListener {

	MyListener myListener;
	ResultDisplayer mPendingResult;

	String name;
	String phone;
	boolean vibrate;
	String resource;

	private GregorianCalendar mCalendar;
	private TimePicker mTime;
	private EditText mDate;
	private ImageView photo;
	private EditText EditName;
	private EditText EditPhone;
	private CheckBox CheckVibrate;
	Button buttonAddress;
	Button buttonConfirm;

	private Intent intent;
	private PendingIntent ServicePending;
	private AlarmManager AM;
	private Toast mToast;
	
	public static Bitmap bitmap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.originalcall);

		mCalendar = new GregorianCalendar();

		myListener = new MyListener();

		findView();
		addListener();

		mDate.setText(mCalendar.get(Calendar.YEAR) + "년 "
				+ mCalendar.get(Calendar.MONTH) + "월 "
				+ mCalendar.get(Calendar.DATE) + "일");

		AM = (AlarmManager) getSystemService(ALARM_SERVICE);

	}

	private void addListener() {
		buttonAddress
				.setOnClickListener(new ResultDisplayer("Selected contact",
						ContactsContract.Contacts.CONTENT_ITEM_TYPE));
		buttonConfirm.setOnClickListener(myListener);
		mTime.setOnTimeChangedListener(this);
	}

	private void findView() {
		mDate = (EditText) findViewById(R.id.OriginalDate);
		mTime = (TimePicker) findViewById(R.id.OriginalTimePicker);
		photo = (ImageView) findViewById(R.id.OriginalPhoto);
		EditName = (EditText) findViewById(R.id.OriginalEditName);
		EditPhone = (EditText) findViewById(R.id.OriginalEditPhoneNum);
		CheckVibrate = (CheckBox) findViewById(R.id.OriginalCheckVibrate);
		buttonAddress = (Button) findViewById(R.id.OriginalButtonAddress);
		buttonConfirm = (Button) findViewById(R.id.OriginalConfirm);
	}

	class MyListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.OriginalConfirm:

				intent = new Intent(getApplicationContext(), callReceiver.class);
				setParam();
				ServicePending = PendingIntent.getBroadcast(
						OriginalCallActivity.this, 0, intent, 0);
				Log.i("call", "Original보내는 name : " + name);
				Log.i("call", "Original보내는 phone : " + phone);
				Date t = new Date();
				t.setTime(java.lang.System.currentTimeMillis() + 5 * 1000);
				// t.setTime(mCalendar.getTimeInMillis());
				AM.set(AlarmManager.RTC_WAKEUP, t.getTime(), ServicePending);
				finish();
				break;
			}
		}
	}

	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		mCalendar.set(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DATE),
				hourOfDay, minute);
	}

	public void setParam() {
		name = EditName.getText().toString();
		intent.putExtra("name", name);
		phone = EditPhone.getText().toString();
		intent.putExtra("phone", phone);
		vibrate = CheckVibrate.isChecked();
		intent.putExtra("vib", vibrate);
	}

	class ResultDisplayer implements OnClickListener {
		String mMsg;
		String mMimeType;

		ResultDisplayer(String msg, String mimeType) {
			mMsg = msg;
			mMimeType = mimeType;
		}

		public void onClick(View v) {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType(mMimeType);
			mPendingResult = this;
			startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();
			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver().query(
							uri,
							new String[] { BaseColumns._ID,
									Contacts.DISPLAY_NAME,
									Contacts.PHOTO_ID }, null, null, null);
					if (c != null && c.moveToFirst()) {
						int id = c.getInt(0);
						if (mToast != null) {
							mToast.cancel();
						}
						String txt = mPendingResult.mMsg + ":\n" + uri
								+ "\nid: " + id;
						mToast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
						mToast.show();
						// 이름 가져오기
						name = c.getString(c
								.getColumnIndex(Contacts.DISPLAY_NAME));
						EditName.setText(name);

						// 사진 가져오기
						photo.setImageBitmap(bitmap=openPhoto(c.getLong(c
								.getColumnIndexOrThrow(BaseColumns._ID))));
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
			
			
			//전화번호 가져오기
			uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver()
							.query(uri,
									new String[] {
											BaseColumns._ID,
											ContactsContract.CommonDataKinds.Phone.NUMBER, },
									null, null, null);
					if (c != null && c.moveToFirst()) {
						// 번호 가져오기
						phone = c
								.getString(c
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						EditPhone.setText(phone);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}

	public Bitmap openPhoto(long contactId) {
		Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,
				contactId);

		InputStream input = ContactsContract.Contacts
				.openContactPhotoInputStream(
						OriginalCallActivity.this.getContentResolver(),
						contactUri);

		if (input != null) {
			return BitmapFactory.decodeStream(input);
		}
		return null;
	}

}
