package com.ui.adapter;

import com.ui.elivestock.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayStringAdapter extends ArrayAdapter<String>{
	int resource;
	Context mContext;
	
	public ArrayStringAdapter(Context context, int resource,
			String[] text) {
		super(context, resource, text);
		this.resource = resource;
		mContext = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView ==null){
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.textString = (TextView) convertView.findViewById(R.id.tvHeader);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		String content = (String) getItem(position);
		holder.textString.setText(content);
		if (position%2==1){
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.itemSplitter));
		}
		return convertView;
	}
	static class ViewHolder{
		TextView textString;
	}
}
