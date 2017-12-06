package com.example.wifilisttest;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
		 WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
    	int ip = wifiInfo.getIpAddress();
    	String ipAddress = intToIp(ip);   

		RecorderManager.getInstance(MainActivity.this.getApplicationContext(),ipAddress).startRecorder(MainActivity.this.getApplicationContext(), 0.5f);  
	
		/*Button btnstudy_borrow=(Button)findViewById(R.id.buttonnew);
        btnstudy_borrow.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Intent i2 = new Intent(MainActivity.this,Loading.class);
            	startActivity(i2); 
            }
        });*/
		
	}

	private String intToIp(int i) {
		 return (i & 0xFF ) + "." +       
			        ((i >> 8 ) & 0xFF) + "." +       
			        ((i >> 16 ) & 0xFF) + "." +       
			        ( i >> 24 & 0xFF) ;  

	}

	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	    // TODO Auto-generated method stub  
	    if(keyCode == KeyEvent.KEYCODE_BACK)  
	       {    
	           exitBy2Click();      //����˫���˳�����  
	       }  
	    return false;  
	}  
	/** 
	 * ˫���˳����� 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // ׼���˳�    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // ȡ���˳�  
	            }  
	        }, 20); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����  
	  
	    } else {  
	        finish();  
	        System.exit(0);  
	    }  
	}  
	
	

	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// ����˵��¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		DrawView drawView = (DrawView) findViewById(R.id.drawView1);
		switch (item.getItemId()) {
		// ����idΪmenu_exit�Ĳ˵�������Ҫִ�еķ�����
		case R.id.menu1_sub1:
			drawView.paint.setStrokeWidth(1);
			break;
		case R.id.menu1_sub2:
			drawView.paint.setStrokeWidth(5);
			break;
		case R.id.menu1_sub3:
			drawView.paint.setStrokeWidth(10);
			break;
		case R.id.menu1_sub4:
			drawView.paint.setStrokeWidth(50);
			break;
		case R.id.menu2:
			drawView.paint.setColor(Color.BLACK);
			break;
		case R.id.menu3:
			drawView.clear();
			break;
		case R.id.menu4:
			try {
				drawView.saveBitmap();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.menu5:
			System.exit(0);// ��������
			break;
		}
		return true;
	}
	
	
}
