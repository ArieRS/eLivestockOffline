package com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ui.elivestock.R;
import com.ui.model.database.masterBangsaSapi;

public class MasterBangsaSapiAdapter extends ArrayAdapter<masterBangsaSapi> {

	int resource;
	String response;
	Context mContext;

	public MasterBangsaSapiAdapter(Context context, int resource,
			ArrayList<masterBangsaSapi> listOfBangsa) {
		super(context, resource, listOfBangsa);
		this.resource = resource;
		this.mContext = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.textHeader = (TextView) convertView
					.findViewById(R.id.tvHeader);
			holder.textDetail = (TextView) convertView
					.findViewById(R.id.tvDetail);
			holder.imageView =(ImageView) convertView.findViewById(R.id.imageIcon);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		masterBangsaSapi itemDataSapi = (masterBangsaSapi) getItem(position);
		holder.textHeader.setText("Id	: " +String.valueOf(itemDataSapi.getId()));
		holder.textDetail.setText("Bangsa	: "+itemDataSapi.getValue());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.master_ic));
		
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
		ImageView imageView;
	}

}
