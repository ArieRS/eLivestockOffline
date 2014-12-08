package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.LaporanPopulasiSapiModelSync;

public class ResponseLaporanPopulasi {
	@SerializedName("DataSapi")
	ArrayList<LaporanPopulasiSapiModelSync> DataSapi;

	public ResponseLaporanPopulasi() {
		DataSapi = new ArrayList<LaporanPopulasiSapiModelSync>();
	}

	public ArrayList<LaporanPopulasiSapiModelSync> getContent() {
		return DataSapi;
	}
}
