package com.spsdmv.homeauto;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class CustomList extends ArrayAdapter<String>{
	
	 
	private final Activity context;
	private final String[] web;
	private final Integer[] imageId;
	private final String[] device_pair;
	public CustomList(Activity context,String[] web, Integer[] imageId,String[] device_pair) {
			super(context, R.layout.list_single, web);
			this.context = context;
			this.web = web;
			this.imageId = imageId;
			this.device_pair=device_pair;
 
	}
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single1, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		TextView txtparied=(TextView)rowView.findViewById(R.id.txt2);
 
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(web[position]);
		txtparied.setText(device_pair[position]);
 
		imageView.setImageResource(imageId[position]);
		return rowView;
}
}
