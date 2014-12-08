package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.LaporanPenyakitSapiModelSync;

public class ResponseLaporanPenyakitSapi {
	@SerializedName("DataSapi")
	ArrayList<LaporanPenyakitSapiModelSync> DataSapi;

	
	///model yang digunakan sama dengan yang dimodel
	public ResponseLaporanPenyakitSapi() {
		DataSapi = new ArrayList<LaporanPenyakitSapiModelSync>();
	}

	public ArrayList<LaporanPenyakitSapiModelSync> getContent() {
		return DataSapi;
	}
}
