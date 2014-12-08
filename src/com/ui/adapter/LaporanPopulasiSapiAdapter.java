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
import com.ui.model.laporan.LaporanPopulasiArea_LokasiModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanPopulasiSapiAdapter extends ArrayAdapter<LaporanPopulasiArea_LokasiModel> {

	int resource;
	String response;
	Context mContext;

	public LaporanPopulasiSapiAdapter(Context context, int resource,
			ArrayList<LaporanPopulasiArea_LokasiModel> listOfPopulasiSapi) {
		super(context, resource, listOfPopulasiSapi);
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
					.findViewById(R.id.tvNITLaporanSapiArea);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanSapiArea);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanSapiArea);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanSapiArea);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanSapiArea);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvBentukWajahLaporanSapiArea);
			holder.textDetail6 = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanSapiArea);
			holder.textDetail7 = (TextView) convertView
					.findViewById(R.id.tvPunukLaporanSapiArea);
			holder.textDetail8 = (TextView) convertView
					.findViewById(R.id.tvAksesorisKakiLaporanSapiArea);
			holder.textDetail9 = (TextView) convertView
					.findViewById(R.id.tvKepemilikanLaporanSapiArea);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconLaporanSapiArea);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LaporanPopulasiArea_LokasiModel itemDataSapi = (LaporanPopulasiArea_LokasiModel) getItem(position);

		// Name
		holder.textHeader.setText("NIT : " + itemDataSapi.getNit());

		// Relation
		holder.textDetail1.setText("Lokasi : " + itemDataSapi.getLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemDataSapi.getTanggalLahir());
		holder.textDetail3.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.textDetail4.setText("NIT Induk : " + itemDataSapi.getNitInduk());
		holder.textDetail5.setText("Bentuk Wajah : " + itemDataSapi.getBentukWajah());
		holder.textDetail6.setText("Warna : " + itemDataSapi.getWarna());
		holder.textDetail7.setText("Status Punuk : " + itemDataSapi.getStatusPunuk());
		holder.textDetail8.setText("Status Aksesoris Kaki : " + itemDataSapi.getStatusAksesorisKaki());
		holder.textDetail9.setText("Status Kepemilikan : " + itemDataSapi.getStatusKepemilikan());
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
		TextView textDetail6;
		TextView textDetail7;
		TextView textDetail8;
		TextView textDetail9;
		ImageView imageView;
	}

}
