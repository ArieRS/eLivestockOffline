package com.ui.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ui.common.DatabaseHelper;
import com.ui.common.General;
import com.ui.elivestock.R;
import com.ui.fragment.DetailDiagnosaFragment;
import com.ui.model.laporan.LaporanPenyakitModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;

public class LaporanPenyakitSapiAdapter extends ArrayAdapter<LaporanPenyakitModel> {

	int resource;
	String response;
	Context mContext;
	DatabaseHelper db;
	FragmentManager mFragmentManager;

	public LaporanPenyakitSapiAdapter(Context context, int resource,
			ArrayList<LaporanPenyakitModel> listPenyakit, FragmentManager fragmentManagerInput) {
		super(context, resource, listPenyakit);
		this.resource = resource;
		this.mContext = context;
		db = new DatabaseHelper(mContext);
		mFragmentManager = fragmentManagerInput;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.textHeader = (TextView) convertView
					.findViewById(R.id.tvNITLaporanPenyakit);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanPenyakit);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanPenyakit);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanPenyakit);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanPenyakit);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvBentukWajahLaporanPenyakit);
			holder.textDetail6 = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanPenyakit);
			holder.textDetail7 = (TextView) convertView
					.findViewById(R.id.tvStatusPunukLaporanPenyakit);
			holder.textDetail8 = (TextView) convertView
					.findViewById(R.id.tvStatusAksesorisLaporanPenyakit);
			holder.textDetail9 = (TextView) convertView
					.findViewById(R.id.tvStatusKepemilikanLaporanPenyakit);
			holder.buttonDetail = (Button) convertView.findViewById(R.id.buttonDetailLaporanPenyakit);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LaporanPenyakitModel itemDataSapi = (LaporanPenyakitModel) getItem(position);

		// Name
		holder.textHeader.setText("NIT	: " + itemDataSapi.getNit());

		// Relation
		holder.textDetail1.setText("Lokasi	: " + itemDataSapi.getLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemDataSapi.getTanggal_lahir());
		holder.textDetail3.setText("Bangsa	: " + itemDataSapi.getBangsa());
		holder.textDetail4.setText("NIT Induk	: " + itemDataSapi.getNit_induk());
		holder.textDetail5.setText("Bentuk Wajah	: " + itemDataSapi.getBentuk_wajah());
		holder.textDetail6.setText("Warna	: " + itemDataSapi.getWarna());
		holder.textDetail7.setText("Status Punuk	: " + itemDataSapi.getStatus_punuk());
		holder.textDetail8.setText("Status Aksesoris	: " + itemDataSapi.getStatus_aksesoris_kaki());
		holder.textDetail9.setText("Status Kepemilikan	: " + itemDataSapi.getStatus_kepemilikan());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		holder.buttonDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String stringNIT = getItem(position).getNit();
				int nit = Integer.parseInt(stringNIT);
				ArrayList<Search_RiwayatKesehatanModel> listRiwayatKesehatan = db.getPemeriksaanKesehatan(nit);
				
				if (listRiwayatKesehatan.size() > 0){
					Fragment fragment = new DetailDiagnosaFragment(mContext,listRiwayatKesehatan,stringNIT);
					General.replaceFragmentAddBackStack(fragment, mFragmentManager);
				}else{
					new General().showDialogSuccess(mContext, "Tidak Ada Diagnosa untuk NIT = "+ stringNIT, "Detail Diagnosa");
				}
			}
		});
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
		Button 	 buttonDetail;
		ImageView imageView;
	}
}
