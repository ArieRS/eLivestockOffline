package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.CariSapiBetinaModelSync;
import com.ui.model.sync.LaporanAIModelSync;

public class ResponseLaporanAI {
	@SerializedName("DataSapi")
	ArrayList<LaporanAIModelSync> DataSapi;

	public ResponseLaporanAI() {
		DataSapi = new ArrayList<LaporanAIModelSync>();
	}

	public ArrayList<LaporanAIModelSync> getContent() {
		return DataSapi;
	}
}
