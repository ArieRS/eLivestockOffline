package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.CariSapiBetinaModelSync;

public class ResponseLaporanPerubahanKepemilikan {
	@SerializedName("DataSapi")
	ArrayList<CariSapiBetinaModelSync> DataSapi;

	public ResponseLaporanPerubahanKepemilikan() {
		DataSapi = new ArrayList<CariSapiBetinaModelSync>();
	}

	public ArrayList<CariSapiBetinaModelSync> getContent() {
		return DataSapi;
	}
}
