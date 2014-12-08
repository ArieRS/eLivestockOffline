package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class MasterProvinsiModelSync {
	@SerializedName("id_provinsi")	
	String id_provinsi;
	
	@SerializedName("nama_provinsi")
	String nama_provinsi;

	public String getId_provinsi() {
		return id_provinsi;
	}

	public void setId_provinsi(String id_provinsi) {
		this.id_provinsi = id_provinsi;
	}

	public String getNama_provinsi() {
		return nama_provinsi;
	}

	public void setNama_provinsi(String nama_provinsi) {
		this.nama_provinsi = nama_provinsi;
	}
	
	

}
