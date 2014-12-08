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
import com.ui.model.laporan.LaporanPenyembelihanModel;
import com.ui.model.laporan.LaporanPopulasiArea_LokasiModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanSapiDisembelihAdapter extends ArrayAdapter<LaporanPenyembelihanModel> {

	int resource;
	String response;
	Context mContext;

	public LaporanSapiDisembelihAdapter(Context context, int resource,
			ArrayList<LaporanPenyembelihanModel> listOfSapiDisembelih) {
		super(context, resource, listOfSapiDisembelih);
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
					.findViewById(R.id.tvNITLaporanPenyembelihan);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanPenyembelihan);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanPenyembelihan);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvTanggalKematianLaporanPenyembelihan);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanPenyembelihan);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanPenyembelihan);
			holder.textDetail6 = (TextView) convertView
					.findViewById(R.id.tvBentukWajahLaporanPenyembelihan);
			holder.textDetail7 = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanPenyembelihan);
			holder.textDetail8 = (TextView) convertView
					.findViewById(R.id.tvPunukLaporanPenyembelihan);
			holder.textDetail9 = (TextView) convertView
					.findViewById(R.id.tvAksesorisKakiLaporanPenyembelihan);
			holder.textDetail10 = (TextView) convertView
					.findViewById(R.id.tvKepemilikanLaporanPenyembelihan);
			holder.imageView = (ImageView)  convertView.findViewById(R.id.imageIconLaporanPenyembelihan);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		LaporanPenyembelihanModel itemDataSapi = (LaporanPenyembelihanModel) getItem(position);
		holder.textHeader.setText("NIT : " + itemDataSapi.getNit());
		// Relation
		holder.textDetail1.setText("Lokasi : " + itemDataSapi.getLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemDataSapi.getTanggalLahir());
		holder.textDetail3.setText("Tanggal Kematian : " + itemDataSapi.getTanggalKematian());
		holder.textDetail4.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.textDetail5.setText("NIT Induk : " + itemDataSapi.getNitInduk());
		holder.textDetail6.setText("Bentuk Wajah : " + itemDataSapi.getBentukWajah());
		holder.textDetail7.setText("Warna : " + itemDataSapi.getWarna());
		holder.textDetail8.setText("Status Punuk : " + itemDataSapi.getStatusPunuk());
		holder.textDetail9.setText("Status Aksesoris Kaki : " + itemDataSapi.getStatusAksesorisKaki());
		holder.textDetail10.setText("Status Kepemilikan : " + itemDataSapi.getStatusKepemilikan());
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
		TextView textDetail10;
		ImageView imageView;
	}

}
