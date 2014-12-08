package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class LaporanBangsaSapiModelSync {

	@SerializedName("kode_kejadian_ib")
	String kode_kejadian_ib;

	@SerializedName("kode_straw")
	String kode_straw;

	@SerializedName("tanggal_ib")
	String tanggal_ib;

	@SerializedName("nit")
	String nit;

	@SerializedName("id_petugas")
	String id_petugas;

	@SerializedName("idl")
	String idl;

	@SerializedName("nama_kontak")
	String nama_kontak;

	@SerializedName("nama_kabupaten_kota")
	String nama_kabupaten_kota;

	public String getKode_kejadian_ib() {
		return kode_kejadian_ib;
	}

	public void setKode_kejadian_ib(String kode_kejadian_ib) {
		this.kode_kejadian_ib = kode_kejadian_ib;
	}

	public String getKode_straw() {
		return kode_straw;
	}

	public void setKode_straw(String kode_straw) {
		this.kode_straw = kode_straw;
	}

	public String getTanggal_ib() {
		return tanggal_ib;
	}

	public void setTanggal_ib(String tanggal_ib) {
		this.tanggal_ib = tanggal_ib;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getId_petugas() {
		return id_petugas;
	}

	public void setId_petugas(String id_petugas) {
		this.id_petugas = id_petugas;
	}

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

	public String getNama_kabupaten_kota() {
		return nama_kabupaten_kota;
	}

	public void setNama_kabupaten_kota(String nama_kabupaten_kota) {
		this.nama_kabupaten_kota = nama_kabupaten_kota;
	}
	
	
}
