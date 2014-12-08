package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ui.elivestock.R;
import com.ui.model.sync.CariSapiBetinaModelSync;
import com.ui.model.sync.ListDataSapiModelSync;

public class DataSapiListAdapter extends ArrayAdapter<CariSapiBetinaModelSync> {

	int resource;
	Context mContext;

	public DataSapiListAdapter(Context context, int resource,
			ArrayList<CariSapiBetinaModelSync> listOfProStat) {
		super(context, resource, listOfProStat);
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

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CariSapiBetinaModelSync itemDataSapi = (CariSapiBetinaModelSync) getItem(position);

		// Name
		holder.textHeader.setText("NIT : " + itemDataSapi.getNit());

		// Relation
		holder.textDetail.setText("Bangsa : " + itemDataSapi.getBangsa());
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
	}

}
