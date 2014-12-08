package com.ui.adapter;

import java.util.ArrayList;

import com.ui.adapter.Search_DataSapiAdapter.ViewHolder;
import com.ui.elivestock.R;
import com.ui.model.laporan.Search_KejadianBeranakModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Search_KejadianBeranakAdapter extends ArrayAdapter<Search_KejadianBeranakModel>{

	int mResource;
	Context mContext;

	public Search_KejadianBeranakAdapter(Context context, int resource,
			ArrayList<Search_KejadianBeranakModel> listKejadianBeranak) {
		super(context, resource, listKejadianBeranak);
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
					.findViewById(R.id.tvNITBeranak);
			holder.textTanggalBeranak = (TextView) convertView
					.findViewById(R.id.tvTanggalBeranak);
			holder.textBanyaknyaAnakBetina = (TextView) convertView.findViewById(R.id.tvJumlahAnakBetina);
			holder.textBanyaknyaAnakJantan = (TextView) convertView.findViewById(R.id.tvJumlahAnakJantan);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Search_KejadianBeranakModel mSearch_KejadianBeranakModel = (Search_KejadianBeranakModel) getItem(position);
		holder.textNIT.setText("NIT	= "+mSearch_KejadianBeranakModel.getNIT());
		holder.textTanggalBeranak.setText("Tanggal Beranak	= "+mSearch_KejadianBeranakModel.getTanggalBeranak());
		holder.textBanyaknyaAnakBetina.setText("Banyaknya Anak Betina	= "+mSearch_KejadianBeranakModel.getBanyaknyaAnakBetina());
		holder.textBanyaknyaAnakJantan.setText("Banyaknya Anak Jantan	= "+mSearch_KejadianBeranakModel.getBanyaknyaAnakJantan());
		return convertView;
	}

	static class ViewHolder {
		TextView textNIT;
		TextView textTanggalBeranak;
		TextView textBanyaknyaAnakBetina;
		TextView textBanyaknyaAnakJantan;
	}
}
