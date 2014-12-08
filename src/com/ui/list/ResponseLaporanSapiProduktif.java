package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.LaporanSapiProduktifModelSync;

public class ResponseLaporanSapiProduktif {
	@SerializedName("DataSapi")
	ArrayList<LaporanSapiProduktifModelSync> DataSapi;

	public ResponseLaporanSapiProduktif() {
		DataSapi = new ArrayList<LaporanSapiProduktifModelSync>();
	}

	public ArrayList<LaporanSapiProduktifModelSync> getContent() {
		return DataSapi;
	}
}
