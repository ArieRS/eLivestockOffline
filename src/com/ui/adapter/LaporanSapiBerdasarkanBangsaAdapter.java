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
import com.ui.model.sync.LaporanAIModelSync;
import com.ui.model.sync.LaporanBangsaSapiModelSync;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;
import com.ui.model.sync.LaporanSapiBerdasarkanBangsaModelSync;
import com.ui.model.sync.ListDataSapiModelSync;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterProvinsiModelSync;

public class LaporanSapiBerdasarkanBangsaAdapter extends ArrayAdapter<LaporanSapiBerdasarkanBangsaModelSync> {

	int resource;
	String response;
	Context mContext;

	public LaporanSapiBerdasarkanBangsaAdapter(Context context, int resource,
			ArrayList<LaporanSapiBerdasarkanBangsaModelSync> listOfProStat) {
		super(context, resource, listOfProStat);
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
					.findViewById(R.id.tvHeader);
			holder.textDetail = (TextView) convertView
					.findViewById(R.id.tvDetail);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageIcon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LaporanSapiBerdasarkanBangsaModelSync itemDataSapi = (LaporanSapiBerdasarkanBangsaModelSync) getItem(position);
		holder.textHeader.setText("NIT : " + itemDataSapi.getNit());
		holder.textDetail.setText("Bangsa : " + itemDataSapi.getBangsa());
		holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.laporan_ic));
		
		return convertView;
	}
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	static class ViewHolder {
		TextView textHeader;
		TextView textDetail;
		ImageView imageView;
	}

}
