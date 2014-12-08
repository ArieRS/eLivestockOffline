package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.CariSapiBetinaModelSync;
import com.ui.model.sync.LaporanSapiBerdasarkanBangsaModelSync;

public class ResponseLaporanSapiBerdasarkanBangsa {
	@SerializedName("DataSapi")
	ArrayList<LaporanSapiBerdasarkanBangsaModelSync> DataSapi;

	public ResponseLaporanSapiBerdasarkanBangsa() {
		DataSapi = new ArrayList<LaporanSapiBerdasarkanBangsaModelSync>();
	}

	public ArrayList<LaporanSapiBerdasarkanBangsaModelSync> getContent() {
		return DataSapi;
	}
}
