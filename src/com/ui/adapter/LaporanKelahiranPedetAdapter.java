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
import com.ui.model.laporan.LaporanKelahiranPedetModel;

public class LaporanKelahiranPedetAdapter extends ArrayAdapter<LaporanKelahiranPedetModel> {

	int resource;
	String response;
	Context mContext;

	public LaporanKelahiranPedetAdapter(Context context, int resource,
			ArrayList<LaporanKelahiranPedetModel> listOfKelahiran) {
		super(context, resource, listOfKelahiran);
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
			holder.textNit = (TextView) convertView
					.findViewById(R.id.tvNitLaporanKelahiran);
			holder.textLokasi = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanKelahiran);
			holder.textTanggalLahir = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanKelahiran);
			holder.textBangsa = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanKelahiran);
			holder.textNitInduk = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanKelahiran);
			holder.textBentukWajah = (TextView) convertView
					.findViewById(R.id.tvNBentukWajahLaporanKelahiran);
			holder.textWarna = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanKelahiran);
			holder.textStatusPunuk = (TextView) convertView
					.findViewById(R.id.tvStatusPunukLaporanKelahiran);
			holder.textStatusAksesorisKaki = (TextView) convertView
					.findViewById(R.id.tvStatusAksesorisLaporanKelahiran);
			holder.textStatusKepemilikan = (TextView) convertView
					.findViewById(R.id.tvStatusKepemilikanLaporanKelahiran);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconLaporanKelahiran);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		LaporanKelahiranPedetModel itemDataSapi = (LaporanKelahiranPedetModel) getItem(position);
		// Name
		holder.textNit.setText("NIT : " + itemDataSapi.getNit());
		holder.textLokasi.setText("Lokasi : " + itemDataSapi.getLokasi());
		holder.textTanggalLahir.setText("Tanggal Lahir : " + itemDataSapi.getTanggalLahir());
		holder.textBangsa.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.textNitInduk.setText("NIT Induk : " + itemDataSapi.getNitInduk());
		holder.textBentukWajah.setText("Bentuk Wajah : " + itemDataSapi.getBentukWajah());
		holder.textWarna.setText("Warna : " + itemDataSapi.getWarna());
		holder.textStatusPunuk.setText("Status Punuk : " + itemDataSapi.getStatusPunuk());
		holder.textStatusAksesorisKaki.setText("Status Aksesoris Kaki : " + itemDataSapi.getStatusAksesorisKaki());
		holder.textStatusKepemilikan.setText("Status Kepemilikan : " + itemDataSapi.getStatusKepemilikan());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		
		return convertView;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	static class ViewHolder {
		TextView textNit;
		TextView textLokasi;
		TextView textTanggalLahir;
		TextView textBangsa;
		TextView textNitInduk;
		TextView textBentukWajah;
		TextView textWarna;
		TextView textStatusPunuk;
		TextView textStatusAksesorisKaki;
		TextView textStatusKepemilikan;
		ImageView imageView;
	}

}
