package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.LaporanBangsaSapiModelSync;
import com.ui.model.sync.LaporanSapiBerdasarkanBangsaModelSync;

public class ResponseLaporanBangsaSapi {
	@SerializedName("DataSapi")
	ArrayList<LaporanSapiBerdasarkanBangsaModelSync> DataSapi;

	public ResponseLaporanBangsaSapi() {
		DataSapi = new ArrayList<LaporanSapiBerdasarkanBangsaModelSync>();
	}

	public ArrayList<LaporanSapiBerdasarkanBangsaModelSync> getContent() {
		return DataSapi;
	}
}
