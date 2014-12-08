package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsProvinsi {
	@SerializedName("id_provinsi")
	int id_provinsi;
	@SerializedName("nama_provinsi")
	String nama_provinsi;
	
	public int getId_provinsi() {
		return id_provinsi;
	}
	public void setId_provinsi(int id_provinsi) {
		this.id_provinsi = id_provinsi;
	}
	public String getNama_provinsi() {
		return nama_provinsi;
	}
	public void setNama_provinsi(String nama_provinsi) {
		this.nama_provinsi = nama_provinsi;
	}
	
	
}
