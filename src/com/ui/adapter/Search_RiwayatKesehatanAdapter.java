package com.ui.adapter;

import java.util.ArrayList;

import com.ui.adapter.Search_DataSapiAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Search_RiwayatKesehatanAdapter extends
		ArrayAdapter<Search_RiwayatKesehatanModel> {

	int mResource;
	Context mContext;

	public Search_RiwayatKesehatanAdapter(Context context, int resource,
			ArrayList<Search_RiwayatKesehatanModel> listPemeriksaanKesehatan) {
		super(context, resource, listPemeriksaanKesehatan);
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
			holder.textKodePemeriksaan = (TextView) convertView
					.findViewById(R.id.tvKodePemeriksaanKesehatan);
			holder.textDiagnosa = (TextView) convertView
					.findViewById(R.id.tvDiagnosaPemeriksaanKesehatan);
			holder.textPerlakuanPemeriksaan = (TextView) convertView
					.findViewById(R.id.tvPerlakuanPeriksaanKesehatan);
			holder.textIdPetugas = (TextView) convertView.findViewById(R.id.tvIdPetugasPeriksaanKesehatan);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_RiwayatKesehatanModel mSearch_RiwayatKesehatanModel = (Search_RiwayatKesehatanModel) getItem(position);
		holder.textKodePemeriksaan.setText("Kode Pemeriksaan Kesehatan	= "+mSearch_RiwayatKesehatanModel.getKodePemeriksaanKesehatan());
		holder.textDiagnosa.setText("Diagnosa	= "+mSearch_RiwayatKesehatanModel.getDiagnosa());
		holder.textPerlakuanPemeriksaan.setText("Perlakuan	= "+mSearch_RiwayatKesehatanModel.getPerlakuan());
		holder.textIdPetugas.setText("Id Petugas = "+ mSearch_RiwayatKesehatanModel.getId_petugas());
		
		return convertView;
	}

	static class ViewHolder {
		TextView textKodePemeriksaan;
		TextView textDiagnosa;
		TextView textPerlakuanPemeriksaan;
		TextView textIdPetugas;
	}
}
