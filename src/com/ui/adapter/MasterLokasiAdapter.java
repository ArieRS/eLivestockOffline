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
import com.ui.model.database.elsLokasi;
import com.ui.model.laporan.LaporanLokasiSapiModel;
import com.ui.model.sync.MasterLokasiModelSync;

public class MasterLokasiAdapter extends ArrayAdapter<LaporanLokasiSapiModel> {

	int resource;
	String response;
	Context mContext;

	public MasterLokasiAdapter(Context context, int resource,
			ArrayList<LaporanLokasiSapiModel> listOfLokasi) {
		super(context, resource, listOfLokasi);
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
			holder.iDLokasi = (TextView) convertView
					.findViewById(R.id.tvIdLokasiMasterLokasi);
			holder.namaKontak = (TextView) convertView
					.findViewById(R.id.tvNamaKontakMasterLokasi);
			holder.kabupatenKota = (TextView) convertView
					.findViewById(R.id.tvKabupatenKotaMasterLokasi);
			holder.alamat = (TextView) convertView
					.findViewById(R.id.tvAlamatMasterLokasi);
			holder.noTelepon = (TextView) convertView
					.findViewById(R.id.tvTeleponMasterLokasi);
			holder.type = (TextView) convertView
					.findViewById(R.id.tvTypeMasterLokasi);
			holder.petugas = (TextView) convertView
					.findViewById(R.id.tvPetugasMasterLokasi);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconMasterLokasi);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LaporanLokasiSapiModel itemDataSapi = (LaporanLokasiSapiModel) getItem(position);
		holder.iDLokasi.setText("ID Lokasi	: "+itemDataSapi.getIdLokasi());
		holder.namaKontak.setText("Nama Kontak	: "+itemDataSapi.getNamaKontak());
		holder.kabupatenKota.setText("Kabupaten/Kota	: "+itemDataSapi.getKabupatenKota());
		holder.alamat.setText("Alamat	: "+itemDataSapi.getAlamat());
		holder.noTelepon.setText("No Telepon	: "+itemDataSapi.getNoTelepon());
		holder.type.setText("Type	: "+itemDataSapi.getType());
		holder.petugas.setText("Petugas	: "+itemDataSapi.getPetugas());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.master_ic));
		
//		if (position%2==1){
//			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.itemSplitter));
//		}
		return convertView;
	}

	static class ViewHolder {
		TextView iDLokasi;
		TextView namaKontak;
		TextView kabupatenKota;
		TextView alamat;
		TextView noTelepon;
		TextView type;
		TextView petugas;
		ImageView imageView;
	}
}
