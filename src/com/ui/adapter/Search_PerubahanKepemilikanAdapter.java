package com.ui.adapter;

import java.util.ArrayList;

import com.ui.adapter.Search_RiwayatKesehatanAdapter.ViewHolder;
import com.ui.common.Validation;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Search_PerubahanKepemilikanAdapter extends ArrayAdapter<Search_PerubahanKepemilikanModel>{
	int mResource;
	Context mContext;

	public Search_PerubahanKepemilikanAdapter(Context context, int resource,
			ArrayList<Search_PerubahanKepemilikanModel> listPerubahanKepemilikanModels) {
		super(context, resource, listPerubahanKepemilikanModels);
		mResource = resource;
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
			holder = new ViewHolder();
			holder.textKotaSebelum = (TextView) convertView
					.findViewById(R.id.tvKotaSebelumPerubahanKepemilikan);
			holder.textKotaSesudah = (TextView) convertView
					.findViewById(R.id.tvKotaSesudahPerubahanKepemilikan);
			holder.textTanggalKejadian = (TextView) convertView
					.findViewById(R.id.tvTanggalKejadianPerubahanKepemilikan);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_PerubahanKepemilikanModel mSearch_PerubahanKepemilikanModel = (Search_PerubahanKepemilikanModel) getItem(position);
		holder.textKotaSebelum.setText("Kota Sebelumnya	= "+ Validation.ifStringNull(mSearch_PerubahanKepemilikanModel.getKotaSebelumnya()));
		holder.textKotaSesudah.setText("Kota Sesudahnya	= "+ Validation.ifStringNull(mSearch_PerubahanKepemilikanModel.getKotaSesudahnya()));
		holder.textTanggalKejadian.setText("Tanggal Kejadian	= "+ Validation.ifStringNull(mSearch_PerubahanKepemilikanModel.getTanggalKejadian()));
		return convertView;
	}

	static class ViewHolder {
		TextView textKotaSebelum;
		TextView textKotaSesudah;
		TextView textTanggalKejadian;
	}
}
