package com.ui.adapter;

import java.util.ArrayList;

import com.ui.adapter.CariSapiBetinaAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_DataSapiModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Search_DataSapiAdapter extends ArrayAdapter<Search_DataSapiModel> {

	int mResource;
	Context mContext;

	public Search_DataSapiAdapter(Context context, int resource,
			ArrayList<Search_DataSapiModel> listDataSapi) {
		super(context, resource, listDataSapi);
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
			holder.textNIT = (TextView) convertView
					.findViewById(R.id.tvNITSearch);
			holder.textLokasi = (TextView) convertView
					.findViewById(R.id.tvLokasiSearch);
			holder.textBangsa = (TextView) convertView
					.findViewById(R.id.tvBangsaSearch);
			holder.textTanggalLahir = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirSearch);
			holder.textNITInduk = (TextView) convertView
					.findViewById(R.id.tvNitIndukSearch);
			holder.textBentukWajah = (TextView) convertView
					.findViewById(R.id.tvBentukWajahSearch);
			holder.textWarna = (TextView) convertView
					.findViewById(R.id.tvWarnaSearch);
			holder.textStatusPunuk = (TextView) convertView
					.findViewById(R.id.tvStatusPunukSearch);
			holder.textStatusAksesorisKaki = (TextView) convertView
					.findViewById(R.id.tvStatusAksesorisKakiSearch);
			holder.textStatusKepemilikan = (TextView) convertView
					.findViewById(R.id.tvStatusKepemilikanSearch);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_DataSapiModel itemDataSapi = (Search_DataSapiModel) getItem(position);
		holder.textNIT.setText("NIT		: "+ itemDataSapi.getNIT());
		holder.textLokasi.setText("Lokasi		: "+itemDataSapi.getLokasi());
		holder.textBangsa.setText("Bangsa		: "+itemDataSapi.getBangsa());
		
		String tanggal = itemDataSapi.getTanggalLahir();
		if (itemDataSapi.getTanggalLahir().contains("0000")) 
			tanggal = "1970-01-01";
		
		holder.textTanggalLahir.setText("Tanggal Lahir		: "+tanggal);
		holder.textNITInduk.setText("NIT Induk		: "+ itemDataSapi.getNITInduk());
		holder.textBentukWajah.setText("Bentuk Wajah		: "+itemDataSapi.getBentukWajah());
		holder.textWarna.setText("Warna		: "+itemDataSapi.getWarna());
		holder.textStatusPunuk.setText("Status Punuk		: "+ itemDataSapi.getStatusPunuk());
		holder.textStatusAksesorisKaki.setText("Status Aksesoris Kaki		: "+itemDataSapi.getStatusAksesorisKaki());
		holder.textStatusKepemilikan.setText("Status Kepemilikan		: "+itemDataSapi.getStatusKepimilikan());
		
		return convertView;
	}

	static class ViewHolder {
		TextView textNIT;
		TextView textLokasi;
		TextView textBangsa;
		TextView textTanggalLahir;
		TextView textNITInduk;
		TextView textBentukWajah;
		TextView textWarna;
		TextView textStatusPunuk;
		TextView textStatusAksesorisKaki;
		TextView textStatusKepemilikan;
	}
}
