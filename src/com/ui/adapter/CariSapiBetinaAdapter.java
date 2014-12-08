package com.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ui.elivestock.R;
import com.ui.model.sync.CariSapiBetinaModelSync;

public class CariSapiBetinaAdapter extends ArrayAdapter<CariSapiBetinaModelSync> {

	int resource;
	Context mContext;

	public CariSapiBetinaAdapter(Context context, int resource,
			int textViewResourceId) {
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
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
	}
}