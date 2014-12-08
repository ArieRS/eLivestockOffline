package com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ui.adapter.Search_DataSapiAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_KejadianKematianModel;

public class Search_KematianAdapter extends ArrayAdapter<Search_KejadianKematianModel>{
	int mResource;
	Context mContext;

	public Search_KematianAdapter(Context context, int resource,
			ArrayList<Search_KejadianKematianModel> listKematianModels) {
		super(context, resource, listKematianModels);
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
			holder.textTangalKematian = (TextView) convertView
					.findViewById(R.id.tvTanggalKematianSearch);
			holder.textSebabKematian = (TextView) convertView
					.findViewById(R.id.tvSebabKematianSearch);
			holder.textLokasiKematian = (TextView) convertView
					.findViewById(R.id.tvLokasiKematianSearch);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_KejadianKematianModel mSearch_KejadianKematianModel = (Search_KejadianKematianModel) getItem(position);
		holder.textTangalKematian.setText("Tanggal Kematian	= "+mSearch_KejadianKematianModel.getTanggalKematian());
		holder.textSebabKematian.setText("Sebab Kematian	= "+mSearch_KejadianKematianModel.getSebabKematian());
		holder.textLokasiKematian.setText("Lokasi Kematian	= "+mSearch_KejadianKematianModel.getLokasiKematian());
		return convertView;
	}

	static class ViewHolder {
		TextView textTangalKematian;
		TextView textSebabKematian;
		TextView textLokasiKematian;
	}
}
