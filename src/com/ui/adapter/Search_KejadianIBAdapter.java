package com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ui.adapter.Search_KematianAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_KejadianIBModel;

public class Search_KejadianIBAdapter extends
		ArrayAdapter<Search_KejadianIBModel> {
	int mResource;
	Context mContext;

	public Search_KejadianIBAdapter(Context context, int resource,
			ArrayList<Search_KejadianIBModel> listKejadianIBModels) {
		super(context, resource, listKejadianIBModels);
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
			holder.textKodeStraw = (TextView) convertView
					.findViewById(R.id.tvKodeStrawIB);
			holder.textTanggalIb = (TextView) convertView
					.findViewById(R.id.tvTanggalIB);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_KejadianIBModel mSearch_KejadianIBModel = (Search_KejadianIBModel) getItem(position);
		holder.textKodeStraw.setText("Kode Straw	= "+mSearch_KejadianIBModel.getKodeStraw());
		holder.textTanggalIb.setText("Tanggal IB	= "+mSearch_KejadianIBModel.getTanggalIB());
		return convertView;
	}

	static class ViewHolder {
		TextView textKodeStraw;
		TextView textTanggalIb;
	}
}
