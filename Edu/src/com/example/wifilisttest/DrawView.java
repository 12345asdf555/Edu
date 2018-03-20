package com.example.wifilisttest;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DrawView extends View {
	private Bitmap cacheBitmap;// ��ֽ
	public Canvas cacheCanvas;// ��������������
	public Path path = new Path();// ��ͼ��·��
	public Paint paint;// ����
	public float preX, preY;// ֮ǰ��XY��λ�ã���������������ƶ�
	public float x;
	public float y;
	public int view_width, view_height;

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		path = new Path();
		paint = new Paint();
		cacheCanvas = new Canvas();
		// ��ȡ��Ļ�ĸ߶�����
		view_width = context.getResources().getDisplayMetrics().widthPixels;
		view_height = context.getResources().getDisplayMetrics().heightPixels;	
		cacheBitmap = Bitmap.createBitmap( view_width, view_height,
				Config.ARGB_8888);// ����ͼ�񻺳�����������ͼ��		
		cacheCanvas.setBitmap(cacheBitmap);
		
		paint.setColor(Color.BLACK);// ���û��ʵ�Ĭ����ɫ
		paint.setStyle(Paint.Style.STROKE);// ���û��ʵ���䷽ʽΪ�����
		paint.setStrokeWidth(1);

	}

	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(cacheBitmap, 0, 0, paint);
		//canvas.drawPath(path, paint);// ����·��
	}

	public void clear()
    {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        cacheCanvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
        
        invalidate();
    }
	
	/*public void draw(float x, float y, int type){
		
		switch (type) {// ��ȡ�����ĸ���˲��
		
		case 1:// ���ư���
			path.moveTo(x, y);// ��ͼ����ʼ��
			preX = x;
			preY = y;
			break;
			
		case 2:
			float dx = Math.abs(x - preX);
			float dy = Math.abs(y - preY);
			if (dx > 5 || dy > 5) {// �û�Ҫ�ƶ�����5���ز����ǻ�ͼ������ֻ����ֶ�����
				path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
				preX = x;
				preY = y;
				cacheCanvas.drawPath(path, paint);// ����·��
			}
			break;
			
		case 3:
			path.reset();
			break;
			
		}
		invalidate();
	}*/
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean bo = false;
		return bo;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		// ��ȡ����λ��
		x = ev.getX();
		y = ev.getY();
		switch (ev.getAction()) {// ��ȡ�����ĸ���˲��
		case MotionEvent.ACTION_DOWN:// ���ư���
			path.moveTo(x, y);// ��ͼ����ʼ��
			preX = x;
			preY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x - preX);
			float dy = Math.abs(y - preY);
			if (dx > 10 || dy > 10) {// �û�Ҫ�ƶ�����5���ز����ǻ�ͼ������ֻ����ֶ�����
				path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
				preX = x;
				preY = y;
				cacheCanvas.drawPath(path, paint);// ����·��
			}
			break;
		case MotionEvent.ACTION_UP:
			
			path.reset();
			break;
		}
		invalidate();
		return true;
	}

	public void saveBitmap() throws Exception {

		String sdpath = Environment.getExternalStorageDirectory()
				.getAbsolutePath();// ��ȡsdcard�ĸ�·��
		String filename = new SimpleDateFormat("yyyyMMddhhmmss",
				Locale.getDefault())
				.format(new Date(System.currentTimeMillis()));// ����ʱ�������Ϊ�ļ���
		File file = new File(sdpath + File.separator + filename + ".png");
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);// ��100%��Ʒ�ʴ���png
		// ���ߴ���
		fileOutputStream.flush();
		fileOutputStream.close();
		Toast.makeText(getContext(),
				"ͼ���ѱ��浽" + sdpath + File.separator + filename + ".png",
				Toast.LENGTH_SHORT).show();

	}
	


}
