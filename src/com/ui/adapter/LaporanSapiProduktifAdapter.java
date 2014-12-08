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
import com.ui.model.laporan.LaporanSapiProduktifModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;
import com.ui.model.sync.LaporanSapiProduktifModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanSapiProduktifAdapter extends ArrayAdapter<LaporanSapiProduktifModel> {

	int resource;
	String response;
	Context mContext;

	public LaporanSapiProduktifAdapter(Context context, int resource,
			ArrayList<LaporanSapiProduktifModel> listOfSapiProduktif) {
		super(context, resource, listOfSapiProduktif);
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
					.findViewById(R.id.tvNITLaporanSapiProd);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanSapiProd);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanSapiProd);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanSapiProd);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanSapiProd);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvBentukWajahLaporanSapiProd);
			holder.textDetail6 = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanSapiProd);
			holder.textDetail7 = (TextView) convertView
					.findViewById(R.id.tvUmurLaporanSapiProd);
			holder.textDetail8 = (TextView) convertView
					.findViewById(R.id.tvBanyakAnakLaporanSapiProd);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconLaporanSapiProd);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LaporanSapiProduktifModel itemDataSapi = (LaporanSapiProduktifModel) getItem(position);
		/*
nit
lokasi
tanggal lahir
bangsa
nit induk
bentuk wajah
warna
umur(hari)
banyaknya anak
		 **/
		
		// Name
		holder.textHeader.setText("NIT : " + itemDataSapi.getNit());

		// Relation
		holder.textDetail1.setText("Lokasi : " + itemDataSapi.getLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemDataSapi.getTanggal_lahir());
		holder.textDetail3.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.textDetail4.setText("NIT Induk : " + itemDataSapi.getNit_induk());
		holder.textDetail5.setText("Bentuk Wajah : " + itemDataSapi.getBentuk_wajah());
		holder.textDetail6.setText("Warna : " + itemDataSapi.getWarna());
		holder.textDetail7.setText("Umur(hari) : " + itemDataSapi.getUmur_hari());
		holder.textDetail8.setText("Banyaknya Anak : " + itemDataSapi.getBanyaknya_anak());
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
		ImageView imageView;
	}

}
