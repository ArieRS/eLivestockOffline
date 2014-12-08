package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.MasterBangsaSapiModelSync;
import com.ui.model.sync.MasterLokasiModelSync;

public class ResponseMasterBangsaSapi {
	@SerializedName("DataSapi")
	ArrayList<MasterBangsaSapiModelSync> DataSapi;

	public ResponseMasterBangsaSapi() {
		DataSapi = new ArrayList<MasterBangsaSapiModelSync>();
	}

	public ArrayList<MasterBangsaSapiModelSync> getContent() {
		return DataSapi;
	}
}
