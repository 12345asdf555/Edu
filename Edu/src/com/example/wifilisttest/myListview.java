package com.example.wifilisttest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class myListview extends ListView{

	public boolean bo;
	
	public myListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//Log.d("d", "��ʡ��������<" + Util.actionToString(ev.getAction()) + "> : ��Ҫ����");
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		//Log.d("d", "��ʡ��������<" + Util.actionToString(ev.getAction()) + "> : ������" + bo);
		return bo;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		return super.onTouchEvent(ev);
		//return false;
	}
	
	public void setBo(boolean bo){
		this.bo = bo;
	}
	
	public boolean getBo(){
		return bo;
	}
	
}
