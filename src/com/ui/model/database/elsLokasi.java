package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsLokasi {
	@SerializedName("idl")
	int idl;
	@SerializedName("nama_kontak")
	String nama_kontak;
	@SerializedName("id_kabupaten_kota")
	int id_kabupaten_kota;
	@SerializedName("address")
	String address;
	@SerializedName("no_telepon")
	String no_telepon;
	@SerializedName("type")
	String type;
	@SerializedName("id_petugas")
	int id_petugas;
	
	public int getIdl() {
		return idl;
	}
	public void setIdl(int idl) {
		this.idl = idl;
	}
	public String getNama_kontak() {
		return nama_kontak;
	}
	public void setNama_kontak(String nama_kontak) {
		this.nama_kontak = nama_kontak;
	}
	public int getId_kabupaten_kota() {
		return id_kabupaten_kota;
	}
	public void setId_kabupaten_kota(int id_kabupaten_kota) {
		this.id_kabupaten_kota = id_kabupaten_kota;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNo_telepon() {
		return no_telepon;
	}
	public void setNo_telepon(String no_telepon) {
		this.no_telepon = no_telepon;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId_petugas() {
		return id_petugas;
	}
	public void setId_petugas(int id_petugas) {
		this.id_petugas = id_petugas;
	}
	
	
}
