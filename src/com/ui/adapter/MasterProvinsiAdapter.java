package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ui.elivestock.R;
import com.ui.model.database.elsProvinsi;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class MasterProvinsiAdapter extends ArrayAdapter<elsProvinsi> {

	int resource;
	String response;
	Context mContext;

	public MasterProvinsiAdapter(Context context, int resource,
			ArrayList<elsProvinsi> listOfProvinsi) {
		super(context, resource, listOfProvinsi);
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
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		elsProvinsi itemDataSapi = (elsProvinsi) getItem(position);
		holder.textHeader.setText("Id	: "+ String.valueOf(itemDataSapi.getId_provinsi()));
		holder.textDetail.setText("Provinsi	: " + itemDataSapi.getNama_provinsi());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.master_ic));
		
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
		ImageView imageView;
	}

}
