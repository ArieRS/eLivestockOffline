package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.ui.fragment.DetailPerubahanKepemilikanFragment;
import com.ui.model.laporan.LaporanKepemilikanModel;
import com.ui.model.laporan.Search_PerubahanKepemilikanModel;
import com.ui.model.laporan.Search_RiwayatKesehatanModel;
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanPerubahanKepemilikanModelSync;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;
import com.ui.model.sync.LaporanSapiProduktifModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanKepemilikanAdapter extends ArrayAdapter<LaporanKepemilikanModel> {

	int resource;
	String response;
	Context mContext;
	FragmentManager mFragmentManager;
	DatabaseHelper db;

	public LaporanKepemilikanAdapter(Context context, int resource,
			ArrayList<LaporanKepemilikanModel> listOfKepemilikan, FragmentManager fragmentManagerInput) {
		super(context, resource, listOfKepemilikan);
		this.resource = resource;
		this.mContext = context;
		mFragmentManager = fragmentManagerInput;
		db = new DatabaseHelper(mContext);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.textHeader = (TextView) convertView
					.findViewById(R.id.tvNITLaporanKepemilikan);
			holder.textDetail1 = (TextView) convertView
					.findViewById(R.id.tvIdLokasiLaporanKepemilikan);
			holder.textDetail2 = (TextView) convertView
					.findViewById(R.id.tvTanggalLahirLaporanKepemilikan);
			holder.textDetail3 = (TextView) convertView
					.findViewById(R.id.tvBangsaLaporanKepemilikan);
			holder.textDetail4 = (TextView) convertView
					.findViewById(R.id.tvNitIndukLaporanKepemilikan);
			holder.textDetail5 = (TextView) convertView
					.findViewById(R.id.tvBentukWajahLaporanKepemilikan);
			holder.textDetail6 = (TextView) convertView
					.findViewById(R.id.tvWarnaLaporanKepemilikan);
			holder.textDetail7 = (TextView) convertView
					.findViewById(R.id.tvStatusPunukLaporanKepemilikan);
			holder.textDetail8 = (TextView) convertView
					.findViewById(R.id.tvStatusAksesorisLaporanKepemilikan);
			holder.textDetail9 = (TextView) convertView
					.findViewById(R.id.tvStatusKepemilikanLaporanKepemilikan);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIconLaporanKepemilikan);
			holder.buttonDetail = (Button) convertView.findViewById(R.id.buttonDetailLaporanKepemilikan);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		LaporanKepemilikanModel itemKepemilikan = (LaporanKepemilikanModel) getItem(position);

		// Name
		holder.textHeader.setText("NIT : " + itemKepemilikan.getNit());

		// Relation
		holder.textDetail1.setText("ID Lokasi : " + itemKepemilikan.getIdLokasi());
		holder.textDetail2.setText("Tanggal Lahir : " + itemKepemilikan.getTanggalLahir());
		holder.textDetail3.setText("Bangsa : " + itemKepemilikan.getBangsa());
		holder.textDetail4.setText("NIT Induk : " + itemKepemilikan.getNit_induk());
		holder.textDetail5.setText("Bentuk Wajah : " + itemKepemilikan.getBentuk_wajah());
		holder.textDetail6.setText("Warna : " + itemKepemilikan.getWarna());
		holder.textDetail7.setText("Status Punuk : " + itemKepemilikan.getStatusPunuk());
		holder.textDetail8.setText("Status Aksesoris Kaki : " + itemKepemilikan.getStatusAksesorisKaki());
//		holder.textDetail9.setText("Status Kepemilikan : " + itemKepemilikan.getStatusKepemilikan());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		holder.buttonDetail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String stringNIT = getItem(position).getNit();
				int nit = Integer.parseInt(stringNIT);
				ArrayList<Search_PerubahanKepemilikanModel> listPerubahanKepemilikanModels = db.getPerubahanKepemilikan(nit);
				
				if (listPerubahanKepemilikanModels.size() > 0){
					Fragment fragment = new DetailPerubahanKepemilikanFragment(mContext,listPerubahanKepemilikanModels,stringNIT);
					General.replaceFragmentAddBackStack(fragment, mFragmentManager);
				}else{
					new General().showDialogSuccess(mContext, "Tidak Ada Data Perubahan Kepemilikan untuk NIT = "+ stringNIT, "Detail Perubahan Kepemilikan");
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
