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
import com.ui.model.laporan.LaporanAIModel;
import com.ui.model.sync.LaporanAIModelSync;

public class LaporanAIAdapter extends ArrayAdapter<LaporanAIModel> {

	int resource;
	Context mContext;

	public LaporanAIAdapter(Context context, int resource,
			ArrayList<LaporanAIModel> listOfAI) {
		super(context, resource, listOfAI);
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
			holder.textKodeStraw = (TextView) convertView
					.findViewById(R.id.tvKodeStrawLaporanAI);
			holder.textNit = (TextView) convertView
					.findViewById(R.id.tvNITLaporanAI);
			holder.textIdLokasi = (TextView) convertView
					.findViewById(R.id.tvIdLokasiLaporanAI);
			holder.textNamaKontak = (TextView) convertView
					.findViewById(R.id.tvNamaKontakLaporanAI);
			holder.textLokasi = (TextView) convertView
					.findViewById(R.id.tvLokasiLaporanAI);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageIconLaporanAI);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		LaporanAIModel itemAI = (LaporanAIModel) getItem(position);
		holder.textKodeStraw.setText("Kode Straw : " + itemAI.getKodeStraw());
		// Relation
		holder.textNit.setText("NIT : " + itemAI.getNit());
		holder.textIdLokasi.setText("IDLokasi : " + itemAI.getIdLokasi());
		holder.textNamaKontak.setText("Nama Kontak : " + itemAI.getNamaKontak());
		holder.textLokasi.setText("Lokasi : " + itemAI.getLokasi());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		return convertView;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	static class ViewHolder {
		TextView textKodeStraw;
		TextView textNit;
		TextView textIdLokasi;
		TextView textNamaKontak;
		TextView textLokasi;
		ImageView imageView;
	}

}
