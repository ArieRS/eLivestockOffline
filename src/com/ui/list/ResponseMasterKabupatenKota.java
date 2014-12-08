package com.ui.list;

import java.util.ArrayList;

import com.ui.model.database.elsKabupatenKota;
import com.google.gson.annotations.SerializedName;

public class ResponseMasterKabupatenKota {
	@SerializedName("status")
	String status;
	
	@SerializedName("els_kabupaten_kota")
	ArrayList<elsKabupatenKota> DataSapi;

	public ResponseMasterKabupatenKota() {
		DataSapi = new ArrayList<elsKabupatenKota>();
	}
	public ArrayList<elsKabupatenKota> getContent(){
		return DataSapi;
	}
	
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	//	ArrayList<MasterKabupatenKotaModelSync> DataSapi;
//
//	public ResponseMasterKabupatenKota() {
//		DataSapi = new ArrayList<MasterKabupatenKotaModelSync>();
//	}
//	public ArrayList<MasterKabupatenKotaModelSync> getContent(){
//		return DataSapi;
//	}
}
