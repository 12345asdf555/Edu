package com.example.wifilisttest;

import android.view.View;
import android.widget.ImageView;

public class ViewHolder {

	 public ImageView imageview;
     public DrawView drawview;
     
     public ViewHolder(View convertView) {
    	 imageview= (ImageView) convertView.findViewById(R.id.imageView2);
    	 drawview= (DrawView) convertView.findViewById(R.id.drawView1);
     }
	
}
