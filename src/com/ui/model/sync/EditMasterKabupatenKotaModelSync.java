package com.ui.model.sync;

public class EditMasterKabupatenKotaModelSync {
	int id_kabupaten_kota;
	int id_provinsi;
	String nama_kabupaten_kota;
	String user;
	String pass;
	String guid;

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
}
