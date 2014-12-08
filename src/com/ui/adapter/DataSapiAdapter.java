package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ui.elivestock.R;
import com.ui.model.laporan.Data_SapiModel;
import com.ui.model.laporan.LaporanSapiProduktifModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;
import com.ui.model.sync.LaporanSapiProduktifModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class DataSapiAdapter extends ArrayAdapter<Data_SapiModel> {

	int resource;
	String response;
	Context mContext;

	public DataSapiAdapter(Context context, int resource,
			ArrayList<Data_SapiModel> listOfDataSapi) {
		super(context, resource, listOfDataSapi);
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
			holder.textHeader = (TextView) convertView.findViewById(R.id.tvNITDataSapi);
			holder.textDetail1 = (TextView) convertView.findViewById(R.id.tvLokasiDataSapi);
			holder.textDetail2 = (TextView) convertView.findViewById(R.id.tvTanggalLahirDataSapi);
			holder.textDetail3 = (TextView) convertView.findViewById(R.id.tvBangsaDataSapi);
			holder.textDetail4 = (TextView) convertView.findViewById(R.id.tvNitIndukDataSapi);
			holder.textDetail5 = (TextView) convertView.findViewById(R.id.tvBentukWajahDataSapi);
			holder.textDetail6 = (TextView) convertView.findViewById(R.id.tvWarnaDataSapi);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Data_SapiModel itemDataSapi = (Data_SapiModel) getItem(position);

		// Name
		holder.textHeader.setText("NIT : " + itemDataSapi.getNIT());

		// Relation
		holder.textDetail1.setText("Lokasi : " + itemDataSapi.getLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemDataSapi.getTanggalLahir());
		holder.textDetail3.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.textDetail4.setText("NIT Induk : " + itemDataSapi.getNITInduk());
		holder.textDetail5.setText("Bentuk Wajah : " + itemDataSapi.getBentukWajah());
		holder.textDetail6.setText("Warna : " + itemDataSapi.getWarna());
		
		return convertView;
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
	}

}
