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
import com.ui.model.laporan.LaporanLokasiSapiModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanLokasiSapiAdapter extends ArrayAdapter<LaporanLokasiSapiModel> {

	int resource;
	String response;
	Context mContext;

	public LaporanLokasiSapiAdapter(Context context, int resource,
			ArrayList<LaporanLokasiSapiModel> listOfLaporanLokasi) {
		super(context, resource, listOfLaporanLokasi);
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
					.findViewById(R.id.tvNamaKontakLaporanLokasi);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvKabupatenKotaLaporanLokasi);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvAlamatLaporanLokasi);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvTeleponLaporanLokasi);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvTypeLaporanLokasi);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvPetugasLaporanLokasi);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconLaporanLokasi);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		LaporanLokasiSapiModel itemDataLokasi = (LaporanLokasiSapiModel) getItem(position);
		holder.textHeader.setText("Nama Kontak : " + itemDataLokasi.getNamaKontak());
		// Relation
		holder.textDetail1.setText("Kabupaten/kota : " + itemDataLokasi.getKabupatenKota());
		holder.textDetail2.setText("Alamat : " + itemDataLokasi.getAlamat());
		holder.textDetail3.setText("No Telepon : " + itemDataLokasi.getNoTelepon());
		holder.textDetail4.setText("Type : " + itemDataLokasi.getType());
		holder.textDetail5.setText("Petugas : " + itemDataLokasi.getPetugas());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		
		return convertView;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	static class ViewHolder {
		TextView textHeader;
		TextView textDetail1;
		TextView textDetail2;
		TextView textDetail3;
		TextView textDetail4;
		TextView textDetail5;
		ImageView imageView;
	}

}
