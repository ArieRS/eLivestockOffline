package com.ui.adapter;

import com.ui.elivestock.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MasterAdapter extends ArrayAdapter<String> {

	int resource;
	String response;
	Context mContext;

	public MasterAdapter(Context context, int resource, int textViewResourceId) {
		super(context, resource, textViewResourceId);
		this.resource = resource;
		this.mContext = context;
	}

	@Override
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

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// // ListDataSapi itemDataSapi = (ListDataSapi) getItem(position);
		//
		// // Name
		// holder.textHeader.setText(itemDataSapi.getLokasi());
		//
		// // Relation
		// holder.textDetail.setText(itemDataSapi.getNIT());
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
	}
}