package com.juma.stsensor;

import java.util.HashMap;
import java.util.UUID;

import com.juma.stsensor.R;
import com.juma.sdk.JumaDevice;
import com.juma.sdk.JumaDeviceCallback;
import com.juma.sdk.ScanHelper;
import com.juma.sdk.ScanHelper.ScanCallback;
import com.juma.stsensor.CustomDialog.Callback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class STSensor extends Activity {
	private Button btStart;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;
	private ProgressBar pb1,pb2,pb3,pb4,pb5,pb6,pb7,pb8,pb9,pb10,pb11,pb12;  
	private ScanHelper scanner;
	private JumaDevice myDevice;
	private HashMap<UUID, JumaDevice> deviceList =  new HashMap<UUID, JumaDevice>();
	public static final String ACTION_DEVICE_DISCOVERED = "com.example.temperaturegatheringdemo.ACTION_DEVICE_DISCOVERED";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info);
		initView();
		scanDevice();
		clicked();
		
	}

	private JumaDeviceCallback callback = new JumaDeviceCallback() {
		@Override
		public void onConnectionStateChange(int status, int newState) {
			// TODO Auto-generated method stub
			super.onConnectionStateChange(status, newState);
			if(newState == JumaDevice.STATE_CONNECTED && status == JumaDevice.SUCCESS){
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						btStart.setText("Receiving");
						btStart.setBackgroundResource(R.drawable.bt_click);
						btStart.setEnabled(true);
					}
					
				});
			}else if(newState == JumaDevice.STATE_DISCONNECTED){
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						btStart.setText("Select Device");
						btStart.setBackgroundResource(R.drawable.bt_click);
						btStart.setEnabled(true);
						tv1.setText("");
						pb1.setProgress(0);
						tv2.setText("");
						pb2.setProgress(0);
						tv3.setText("");
						pb3.setProgress(0);
						tv4.setText("");
						pb4.setProgress(0);
						tv5.setText("");
						pb5.setProgress(0);
						tv6.setText("");
						pb6.setProgress(0);
						tv7.setText("");
						pb7.setProgress(0);
						tv8.setText("");
						pb8.setProgress(0);
						tv9.setText("");
						pb9.setProgress(0);
						tv10.setText("");
						pb10.setProgress(0);
						tv11.setText("");
						pb11.setProgress(0);
						tv12.setText("");
						pb12.setProgress(0);
					}
					
				});
			}
		}
	
		private int w,s,y,cx,cy,cz,jx,jy,jz,tx,ty,tz;
		@Override
		public void onReceive(byte type, byte[] message) {
			// TODO Auto-generated method stub
			super.onReceive(type, message);
			if(cge && (type == (byte)0x01||type == (byte)0x02)){
				cge = false;
				if(type == (byte)0x01){
				w = 0;
				s = 0;
				y = 0;
				cx = 0;
				cy = 0;
				cz = 0;
				w |= message[0];
				w <<= 8;
				w |= (message[1]&0x00FF);
				s |= message[2];
				s <<= 8;
				s |= (message[3]&0x00FF);
				y |= message[4];
				y <<= 8;
				y |= (message[5]&0x00FF);
				y <<= 8;
				y |= (message[6]&0x0000FF);
				cx |= message[7];
				cx <<= 8;
				cx |= (message[8]&0x00FF);
				cy |= message[9];
				cy <<= 8;
				cy |= (message[10]&0x00FF);
				cz |= message[11];
				cz <<= 8;
				cz |= (message[12]&0x00FF);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						
							tv1.setText((double)w/100+"℃");
							pb1.setProgress(w/100);
							tv2.setText((double)s/100+"RH%");
							pb2.setProgress(s/100);
							tv3.setText((double)y/100+"hPa");
							pb3.setProgress((y/100)-260);
							tv5.setText(""+(double)cy/100+"Gs");
							tv6.setText(""+(double)cz/100+"Gs");
							tv4.setText(""+(double)cx/100+"Gs");
							pb4.setProgress(cx/100+500);
							pb5.setProgress(cy/100+500);
							pb6.setProgress(cz/100+500);
							
						cge = true;
					}
				});
				}else{
					jx = 0;
					jy = 0;
					jz = 0;
					tx = 0;
					ty = 0;
					tz = 0;
					jx |= message[0];
					jx <<= 8;
					jx |= (message[1]&0x00FF);
					jy |= message[2];
					jy <<= 8;
					jy |= (message[3]&0x00FF);
					jz |= message[4];
					jz <<= 8;
					jz |= (message[5]&0x00FF);
					tx |= message[6];
					tx <<= 8;
					tx |= (message[7]&0x00FF);
					ty |= message[8];
					ty <<= 8;
					ty |= (message[9]&0x00FF);
					tz |= message[10];
					tz <<= 8;
					tz |= (message[11]&0x00FF);
					
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							
							tv7.setText((double)jx/100+"m/s²");
							tv8.setText((double)jy/100+"m/s²");
							tv9.setText((double)jz/100+"m/s²");
							pb7.setProgress(jx/100+20);
							pb8.setProgress(jy/100+20);
							pb9.setProgress(jz/100+20);
							tv10.setText(""+(double)tx/100+"rad/s");
							tv11.setText(""+(double)ty/100+"rad/s");
							tv12.setText(""+(double)tz/100+"rad/s");
							pb10.setProgress(tx/100+500);
							pb11.setProgress(ty/100+500);
							pb12.setProgress(tz/100+500);
								
							cge = true;
						}
					});
				}
			
			}
					
			
		}
	};
	private boolean cge = true;
	private void clicked(){
		btStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			TextView[]	tv = {tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12};
			ProgressBar[] pb = {pb1,pb2,pb3,pb4,pb5,pb6,pb7,pb8,pb9,pb10,pb11,pb12};
				for(int i=0;i<tv.length;i++){
					tv[i].setText("");
					pb[i].setProgress(0);
				}
				if(btStart.getText().equals("Receiving")){
					myDevice.disconnect();
				}else{
					deviceList.clear();
					scanner.startScan(null);
					final CustomDialog scanDialog = new CustomDialog(STSensor.this, R.style.NobackDialog);
					scanDialog.setScanCallback(new Callback() {
						
						@Override
						public void onDevice(final UUID uuid, final String name) {
							scanner.stopScan();
							myDevice = deviceList.get(uuid);
							btStart.setEnabled(false);
							btStart.setBackgroundColor(Color.argb(0x20, 0x00, 0x00, 0x00));
							btStart.setText("Connecting");
							myDevice.connect(callback);
						}

						@Override
						public void onDismiss() {
							scanner.stopScan();
						}
					});
					
					scanDialog.setNegativeButton(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							scanDialog.dismiss();
							
						}
					});
					scanDialog.show();
				}
			}
		});
			
		
	}
	private void initView(){
		tv1 = (TextView)findViewById(R.id.tv1);
		tv2 = (TextView)findViewById(R.id.tv2);
		tv3 = (TextView)findViewById(R.id.tv3);
		tv4 = (TextView)findViewById(R.id.tv4);
		tv5 = (TextView)findViewById(R.id.tv5);
		tv6 = (TextView)findViewById(R.id.tv6);
		tv7 = (TextView)findViewById(R.id.tv7);
		tv8 = (TextView)findViewById(R.id.tv8);
		tv9 = (TextView)findViewById(R.id.tv9);
		tv10 = (TextView)findViewById(R.id.tv10);
		tv11 = (TextView)findViewById(R.id.tv11);
		tv12 = (TextView)findViewById(R.id.tv12);
		pb1 = (ProgressBar)findViewById(R.id.pb1);
		pb2 = (ProgressBar)findViewById(R.id.pb2);
		pb3 = (ProgressBar)findViewById(R.id.pb3);
		pb4 = (ProgressBar)findViewById(R.id.pb4);
		pb5 = (ProgressBar)findViewById(R.id.pb5);
		pb6 = (ProgressBar)findViewById(R.id.pb6);
		pb7 = (ProgressBar)findViewById(R.id.pb7);
		pb8 = (ProgressBar)findViewById(R.id.pb8);
		pb9 = (ProgressBar)findViewById(R.id.pb9);
		pb10 = (ProgressBar)findViewById(R.id.pb10);
		pb11 = (ProgressBar)findViewById(R.id.pb11);
		pb12 = (ProgressBar)findViewById(R.id.pb12);
		btStart = (Button)findViewById(R.id.bt_start);
	}
	
	private void scanDevice(){
		scanner = new ScanHelper(getApplicationContext(), new ScanCallback(){

			@Override
			public void onDiscover(JumaDevice device, int rssi) {
				// TODO Auto-generated method stub
				if(!deviceList.containsKey(device.getUuid())){
					deviceList.put(device.getUuid(), device);
					Intent intent = new Intent(STSensor.ACTION_DEVICE_DISCOVERED);
					intent.putExtra("name", device.getName());
					intent.putExtra("uuid", device.getUuid().toString());
					intent.putExtra("rssi", rssi);
					LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
				}
			}

			@Override
			public void onScanStateChange(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
