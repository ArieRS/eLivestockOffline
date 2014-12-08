package com.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ui.adapter.LaporanPenyakitSapiAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.LaporanPenyakitModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

public class DetailDiagnosaAdapter extends ArrayAdapter<Search_RiwayatKesehatanModel>{

	int resource;
	String response;
	Context mContext;
	
	public DetailDiagnosaAdapter(Context context, int resource,ArrayList<Search_RiwayatKesehatanModel> listPenyakit) {
		super(context, resource, listPenyakit);
		this.resource = resource;
		this.mContext = context;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.textDiagnosa = (TextView) convertView
					.findViewById(R.id.tvDiagnosaPemeriksaan);
			holder.textPerlakuan = (TextView) convertView
					.findViewById(R.id.tvPerlakuanPeriksaan);
			holder.textTanggalPeriksa = (TextView) convertView
					.findViewById(R.id.tvTanggalPemeriksaan);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_RiwayatKesehatanModel itemDiagnosa = (Search_RiwayatKesehatanModel) getItem(position);

		// Name
		holder.textDiagnosa.setText("Diagnosa	: " + itemDiagnosa.getDiagnosa());

		// Relation
		holder.textPerlakuan.setText("Perlakuan	: " + itemDiagnosa.getPerlakuan());
		holder.textTanggalPeriksa.setText("Tanggal Pemeriksaan : " + itemDiagnosa.getTanggal_periksa());
		
		return convertView;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	static class ViewHolder {
		TextView textDiagnosa;
		TextView textPerlakuan;
		TextView textTanggalPeriksa;
	}
}
