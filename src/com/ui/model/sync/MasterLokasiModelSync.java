package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class MasterLokasiModelSync {

	@SerializedName("idl")
	public String idl;
	
	@SerializedName("nama_kontak")
	public String nama_kontak;
	
	@SerializedName("id_kabupaten_kota")
	public String id_kabupaten_kota;
	
	@SerializedName("address")
	public String address;
	
	@SerializedName("no_telepon")
	public String no_telepon;
	
	@SerializedName("type")
	public String type;
	
	@SerializedName("id_petugas")
	public String id_petugas;
	
	@SerializedName("nama_petugas")
	public String nama_petugas;
	
	@SerializedName("nama_kabupaten_kota")
	public String nama_kabupaten_kota;
	
	@SerializedName("value_type")
	public String value_type;

	public String getIdl() {
		return idl;
	}

	public void setIdl(String idl) {
		this.idl = idl;
	}

	public String getNama_kontak() {
		return nama_kontak;
	}

	public void setNama_kontak(String nama_kontak) {
		this.nama_kontak = nama_kontak;
	}

	public String getId_kabupaten_kota() {
		return id_kabupaten_kota;
	}

	public void setId_kabupaten_kota(String id_kabupaten_kota) {
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

	public String getId_petugas() {
		return id_petugas;
	}

	public void setId_petugas(String id_petugas) {
		this.id_petugas = id_petugas;
	}

	public String getNama_petugas() {
		return nama_petugas;
	}

	public void setNama_petugas(String nama_petugas) {
		this.nama_petugas = nama_petugas;
	}

	public String getNama_kabupaten_kota() {
		return nama_kabupaten_kota;
	}

	public void setNama_kabupaten_kota(String nama_kabupaten_kota) {
		this.nama_kabupaten_kota = nama_kabupaten_kota;
	}

	public String getValue_type() {
		return value_type;
	}

	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}
	
}
