package com.example.wifilisttest;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.wifilisttest.*;

public class ImageAdapter extends BaseAdapter{

	public LayoutInflater inflater;
	public ArrayList<Image> arraylist;
	public HashMap<Integer, View> lmap = new HashMap<Integer, View>();
	public HashMap<Integer, View> lmapbuf = new HashMap<Integer, View>();
	public boolean a=false;
	
	public ImageAdapter(LayoutInflater inflater,ArrayList<Image> arraylist,boolean a){
        this.inflater=inflater;
        this.arraylist=arraylist;
        this.a=a;
    }
	
	public ArrayList<Image> getarray() {
		// TODO Auto-generated method stub
		return arraylist;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arraylist.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ViewHolder item = null;
		 
		 if(a==true){
	
             convertView = inflater.inflate(R.layout.activity_listimage, null);
             item = new ViewHolder(convertView);
             convertView.setTag(item);//绑定ViewHolder对象  
             lmap.put(lmap.size(), convertView);

			
			 Image image = arraylist.get(position);
			 item.imageview.setImageBitmap(image.getImage());
			 
			 a=false;
		 }else{
			 
			 if(lmap.get(position) ==null)
	         {
	             convertView = inflater.inflate(R.layout.activity_listimage, null);
	             item = new ViewHolder(convertView);
	             convertView.setTag(item);//绑定ViewHolder对象  
	             lmap.put(position, convertView);
	         }
	         else
	         {
	        	 convertView=lmap.get(position);
	             item = (ViewHolder) convertView.getTag();//取出ViewHolder对象
	         }
			
			 Image image = arraylist.get(position);
			 item.imageview.setImageBitmap(image.getImage());
			 
		 }
		 
		return convertView;
		
	}
	
	
	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_listimage, null);
		Image image = arraylist.get(position);
		ImageView imageview=(ImageView) view.findViewById(R.id.imageView2);
		imageview.setImageBitmap(image.getImage());
		
	

		int a = listView.getChildCount();
		Image image = arraylist.get(a);
        RelativeLayout layout = (RelativeLayout) listView.getChildAt(a);
        ImageView imageview = (ImageView) layout.findViewById(R.id.imageView2);
        imageview.setImageBitmap(image.getImage());
		
		
		return view;
	}*/

}


