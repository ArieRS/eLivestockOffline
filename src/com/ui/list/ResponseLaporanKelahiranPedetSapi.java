package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.LaporaKelahiranPedetModelSync;

public class ResponseLaporanKelahiranPedetSapi {
	@SerializedName("DataSapi")
	ArrayList<LaporaKelahiranPedetModelSync> DataSapi;

	public ResponseLaporanKelahiranPedetSapi() {
		DataSapi = new ArrayList<LaporaKelahiranPedetModelSync>();
	}

	public ArrayList<LaporaKelahiranPedetModelSync> getContent() {
		return DataSapi;
	}
}
