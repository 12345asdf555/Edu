package com.example.wifilisttest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.wifilisttest.myListview;

public class MainActivity extends Activity {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Button takePicBtn = null;
    private Button takeVideoBtn = null;
    private Button takeChangeBtn = null;
    private ImageView imageView = null; 
    public myListview listView = null;
    private ArrayList<Image> arraylist;
    private LayoutInflater inflater;
    private Uri fileUri;
    private ImageAdapter imageadapter;
    private boolean first=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstpage);
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
    	int ip = wifiInfo.getIpAddress();
    	String ipAddress = intToIp(ip);   
    	
		RecorderManager.getInstance(MainActivity.this.getApplicationContext(),ipAddress).startRecorder(MainActivity.this.getApplicationContext(), 0.5f);  
		
		takePicBtn = (Button) findViewById(R.id.button1);
        takePicBtn.setOnClickListener(takePiClickListener);

        takeVideoBtn = (Button) findViewById(R.id.button2);
        takeVideoBtn.setOnClickListener(takeVideoClickListener);

        takeChangeBtn = (Button) findViewById(R.id.button3);
        takeChangeBtn.setOnClickListener(takeChangeListener);
        
        /*takeChangeBtn = (Button) findViewById(R.id.button00);
        takeChangeBtn.setOnClickListener(takeChangeClickListener);
        
        takeChangebackBtn = (Button) findViewById(R.id.button01);
        takeChangebackBtn.setOnClickListener(takeChangebackClickListener);*/
        
        listView = (myListview) findViewById(R.id.listView1);
        inflater=getLayoutInflater();
        arraylist =new ArrayList<Image>();

			
		}
		

	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}
	
	
	 private final OnClickListener takePiClickListener = new OnClickListener()
	    {

	        @Override
	        public void onClick(View v)
	        {
	            // 利用系统自带的相机应用:拍照
	            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

	            // create a file to save the image
	            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

	            // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
	            // set the image file name
	            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

	            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	        }

	    };

	    private final OnClickListener takeVideoClickListener = new View.OnClickListener()
	    {

	        @Override
	        public void onClick(View v)
	        {
	            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	        	
	        	screenshot();
	        	

	        }
	    };
	    
	    private final OnClickListener takeChangeListener = new View.OnClickListener()
	    {

	        @Override
	        public void onClick(View v)
	        {
				
	        	myListview ll = (myListview) findViewById(R.id.listView1);
	        	boolean bo = ll.getBo();
	        	if(bo == true){
	        		bo = false;
	        	}else{
	        		bo = true;
	        	}
	        	ll.setBo(bo);
	        	
	        }
	        
	    };
		
	    
	    /*private final OnClickListener takeChangeClickListener = new View.OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	// create a file to save the image
	        	
	        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	        	
	        	lp.addRule(RelativeLayout.BELOW, drawview.getId());
	        	
	        	listView.setLayoutParams(lp);

	        }
	    };
	    
	    private final OnClickListener takeChangebackClickListener = new View.OnClickListener()
	    {
	        @Override
	        public void onClick(View v)
	        {
	        	// create a file to save the image
	        	
	        	RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	        	
	        	lp.addRule(RelativeLayout.BELOW, listView.getId());
	        	
	        	drawview.setLayoutParams(lp);

	        }
	    };*/
	    
	    public static final int MEDIA_TYPE_IMAGE = 1;

	    /** Create a file Uri for saving an image or video */
	    private static Uri getOutputMediaFileUri(int type)
	    {
	        return Uri.fromFile(getOutputMediaFile(type));
	    }

	    /** Create a File for saving an image or video */
	    private static File getOutputMediaFile(int type)
	    {
	        // To be safe, you should check that the SDCard is mounted
	        // using Environment.getExternalStorageState() before doing this.

	        File mediaStorageDir = null;
	        try
	        {
	            // This location works best if you want the created images to be
	            // shared
	            // between applications and persist after your app has been
	            // uninstalled.
	            mediaStorageDir = new File(
	                    Environment
	                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	                    "MyCameraApp");


	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();

	        }

	        // Create the storage directory if it does not exist
	        if (!mediaStorageDir.exists())
	        {
	            if (!mediaStorageDir.mkdirs())
	            {
	                // 在SD卡上创建文件夹需要权限：
	                // <uses-permission
	                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	      
	                return null;
	            }
	        }

	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	                .format(new Date());
	        File mediaFile;
	        if (type == MEDIA_TYPE_IMAGE)
	        {
	            mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                    + "IMG_" + timeStamp + ".jpg");
	        }
	        else
	        {
	            return null;
	        }

	        return mediaFile;
	    }

	    @SuppressWarnings("deprecation")
		@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	    {
	        super.onActivityResult(requestCode, resultCode, data);

	        // 如果是拍照
	        if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode)
	        {

	            if (RESULT_OK == resultCode)
	            {

	                // Check if the result includes a thumbnail Bitmap
	                if (data != null)
	                {
	                    // 没有指定特定存储路径的时候

	                    // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
	                    // Image captured and saved to fileUri specified in the
	                    // Intent
	                    Toast.makeText(this, "Image saved to:\n" + data.getData(),
	                            Toast.LENGTH_LONG).show();

	                    if (data.hasExtra("data"))
	                    {
	                        Bitmap thumbnail = data.getParcelableExtra("data");
	                        imageView.setImageBitmap(thumbnail);
	                    }
	                }
	                else
	                {
	                    // If there is no thumbnail image data, the image
	                    // will have been stored in the target output URI.

	                    // Resize the full image to fit in out image view.
	                    int width = 1800;
	                    int height = 1100;

	                    BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

	                    factoryOptions.inJustDecodeBounds = true;
	                    BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);

	                    int imageWidth = factoryOptions.outWidth;
	                    int imageHeight = factoryOptions.outHeight;

	                    // Determine how much to scale down the image
	                    int scaleFactor = Math.min(imageWidth / width, imageHeight
	                            / height);

	                    // Decode the image file into a Bitmap sized to fill the
	                    // View
	                    factoryOptions.inJustDecodeBounds = false;
	                    factoryOptions.inSampleSize = scaleFactor;
	                    factoryOptions.inPurgeable = true;

	                    Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
	                            factoryOptions);
	                    
	                    if(first==true){
	        	        	Image image = new Image(bitmap);
	        	            arraylist.add(image);	
	        	            ImageAdapter imageadapter = new ImageAdapter(inflater, arraylist,true);
	        	            listView.setAdapter(imageadapter);
	        	            imageadapter.notifyDataSetChanged();
	        	            first=false;
	        	        }else{
	        	        	Image image = new Image(bitmap);
	        	        	imageadapter=((ImageAdapter) listView.getAdapter());
	        	        	imageadapter.arraylist.add(image);
	        	        	listView.setAdapter(imageadapter);
	        	        	imageadapter.notifyDataSetChanged();
	        	        }

	                    
	                    //删除图片
	                    String path = fileUri.getPath();
	                    String where = MediaStore.Images.Media.DATA + "='" + path + "'";
	                    File file = new File(path);
	                    file.delete();
	                    ContentResolver mContentResolver = MainActivity.this.getContentResolver();
	                    mContentResolver.delete(fileUri, where, null);

	                }
	            }
	            else if (resultCode == RESULT_CANCELED)
	            {
	                // User cancelled the image capture
	            }
	            else
	            {
	                // Image capture failed, advise user
	            }
	        }

	    }
	    
	    private void screenshot()
	    {
	        // 获取屏幕
	        View dView = getWindow().getDecorView();  
	        dView.setDrawingCacheEnabled(true);   
	        dView.buildDrawingCache();   
	        Bitmap bmp = Bitmap.createBitmap(dView.getDrawingCache());
	        
	        //保存截图
	        String path = fileUri.getPath();
	        File file = new File(path);
	        try {
				FileOutputStream out = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
		        try {
					out.flush();
			        out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(first==true){
	        	Image image = new Image(bmp);
	            arraylist.add(image);	
	            ImageAdapter imageadapter = new ImageAdapter(inflater, arraylist,true);
	            listView.setAdapter(imageadapter);
	            imageadapter.notifyDataSetChanged();
	            first=false;
	        }else{
	        	Image image = new Image(bmp);
	        	imageadapter=((ImageAdapter) listView.getAdapter());
	        	imageadapter.arraylist.add(image);
	        	imageadapter.a=true;
	        	listView.setAdapter(imageadapter);
	        	imageadapter.notifyDataSetChanged();
	        }


            /*int a = listView.getChildCount();
            RelativeLayout layout = (RelativeLayout) listView.getChildAt(a);
            ImageView imageview = (ImageView) layout.findViewById(R.id.imageView2);
            imageview.setImageBitmap(image.getImage());*/
	        
            //imageView.setImageBitmap(bmp);
            
            //删除截图
            String where = MediaStore.Images.Media.DATA + "='" + path + "'";
            file.delete();
            ContentResolver mContentResolver = MainActivity.this.getContentResolver();
            mContentResolver.delete(fileUri, where, null);
	        
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
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	/** 
	 * 双击退出函数 
	 */  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 20); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
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

	// 处理菜单事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
        //RelativeLayout layout = (RelativeLayout) listView.getChildAt();
        //DrawView drawView = (DrawView) layout.findViewById(R.id.drawView1);
		
		View view = getLayoutInflater().inflate(R.layout.activity_listimage, null);  
		DrawView drawView = (DrawView) view.findViewById(R.id.drawView1);
		switch (item.getItemId()) {
		// 设置id为menu_exit的菜单子项所要执行的方法。
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
			drawView.paint.setStrokeWidth(5);
			drawView.paint.setColor(Color.BLACK);
			break;
		case R.id.menu3:
			drawView.paint.setStrokeWidth(30);
			drawView.paint.setColor(Color.WHITE);
			break;
		case R.id.menu4:
			drawView.clear();
			break;
		case R.id.menu5:
			System.exit(0);// 结束程序
			break;
		}
		
	
		
		return true;
	}
	
	
}































/*package com.example.wifilisttest;

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
	           exitBy2Click();      //调用双击退出函数  
	       }  
	    return false;  
	}  
	*//** 
	 * 双击退出函数 
	 *//*  
	private static Boolean isExit = false;  
	  
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true; // 准备退出    
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false; // 取消退出  
	            }  
	        }, 20); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  
	  
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

	// 处理菜单事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		DrawView drawView = (DrawView) findViewById(R.id.drawView1);
		switch (item.getItemId()) {
		// 设置id为menu_exit的菜单子项所要执行的方法。
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
			System.exit(0);// 结束程序
			break;
		}
		return true;
	}
	
	
}
*/