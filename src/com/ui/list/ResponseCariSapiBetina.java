package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.CariSapiBetinaModelSync;

public class ResponseCariSapiBetina {
	@SerializedName("DataSapi")
	ArrayList<CariSapiBetinaModelSync> DataSapi;

	public ResponseCariSapiBetina() {
		DataSapi = new ArrayList<CariSapiBetinaModelSync>();
	}

	public ArrayList<CariSapiBetinaModelSync> getContent() {
		return DataSapi;
	}
}
