package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsKabupatenKota {
	@SerializedName("id_kabupaten_kota")
	int id_kabupaten_kota;
	@SerializedName("id_provinsi")
	int id_provinsi;
	@SerializedName("nama_kabupaten_kota")
	String nama_kabupaten_kota;
	
	public int getId_kabupaten_kota() {
		return id_kabupaten_kota;
	}
	public void setId_kabupaten_kota(int id_kabupaten_kota) {
		this.id_kabupaten_kota = id_kabupaten_kota;
	}
	public int getId_provinsi() {
		return id_provinsi;
	}
	public void setId_provinsi(int id_provinsi) {
		this.id_provinsi = id_provinsi;
	}
	public String getNama_kabupaten_kota() {
		return nama_kabupaten_kota;
	}
	public void setNama_kabupaten_kota(String nama_kabupaten_kota) {
		this.nama_kabupaten_kota = nama_kabupaten_kota;
	}
	
	
}
