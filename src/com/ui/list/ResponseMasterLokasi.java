package com.ui.list;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.ui.model.sync.MasterLokasiModelSync;

public class ResponseMasterLokasi {
//	@SerializedName("DataSapi")
	ArrayList<MasterLokasiModelSync> DataSapi;

	public ResponseMasterLokasi() {
		DataSapi = new ArrayList<MasterLokasiModelSync>();
	}
	public ArrayList<MasterLokasiModelSync> getContent(){
		return DataSapi;
	}
}
