package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class MasterKabupatenKotaModelSync {
	@SerializedName("id_kabupaten_kota")
	String id_kabupaten_kota;

	@SerializedName("id_provinsi")
	String id_provinsi;

	@SerializedName("nama_kabupaten_kota")
	String nama_kabupaten_kota;

	@SerializedName("nama_provinsi")
	String nama_provinsi;

	public String getId_kabupaten_kota() {
		return id_kabupaten_kota;
	}

	public void setId_kabupaten_kota(String id_kabupaten_kota) {
		this.id_kabupaten_kota = id_kabupaten_kota;
	}

	public String getId_provinsi() {
		return id_provinsi;
	}

	public void setId_provinsi(String id_provinsi) {
		this.id_provinsi = id_provinsi;
	}

	public String getNama_kabupaten_kota() {
		return nama_kabupaten_kota;
	}

	public void setNama_kabupaten_kota(String nama_kabupaten_kota) {
		this.nama_kabupaten_kota = nama_kabupaten_kota;
	}

	public String getNama_provinsi() {
		return nama_provinsi;
	}

	public void setNama_provinsi(String nama_provinsi) {
		this.nama_provinsi = nama_provinsi;
	}

	

}
