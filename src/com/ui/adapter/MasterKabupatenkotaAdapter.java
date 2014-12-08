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
import com.ui.model.database.elsKabupatenKota;
import com.ui.model.laporan.MasterKabupatenKotaModel;
import com.ui.model.sync.MasterKabupatenKotaModelSync;

public class MasterKabupatenkotaAdapter extends
		ArrayAdapter<MasterKabupatenKotaModel> {

	int resource;
	String response;
	Context mContext;

	public MasterKabupatenkotaAdapter(Context context, int resource,
			ArrayList<MasterKabupatenKotaModel> objects) {
		super(context, resource, objects);
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
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon)
					;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MasterKabupatenKotaModel itemDataSapi = (MasterKabupatenKotaModel) getItem(position);
		holder.textHeader.setText(String.valueOf("Kabupaten/Kota	: " + itemDataSapi.getNama_Kabupaten_Kota()));
		holder.textDetail.setText("Provinsi		: "+itemDataSapi.getNama_Provinsi());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.master_ic));
		
		return convertView;
	}

	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
		ImageView imageView;
	}
}
