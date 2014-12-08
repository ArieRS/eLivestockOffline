package com.ui.list;

import java.util.ArrayList;

import com.ui.model.sync.MasterProvinsiModelSync;

public class ResponseMasterProvinsi {
//	@SerializedName("DataSapi")
	ArrayList<MasterProvinsiModelSync> DataSapi;

	public ResponseMasterProvinsi() {
		DataSapi = new ArrayList<MasterProvinsiModelSync>();
	}
	public ArrayList<MasterProvinsiModelSync> getContent(){
		return DataSapi;
	}
}
