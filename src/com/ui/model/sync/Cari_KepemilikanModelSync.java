package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class Cari_KepemilikanModelSync {

	@SerializedName("kode_riwayat_kepemilikan")
	String kode_riwayat_kepemilikan;

	@SerializedName("idl_sebelum")
	String idl_sebelum;

	@SerializedName("idl_sesudah")
	String idl_sesudah;

	@SerializedName("tanggal_kejadian")
	String tanggal_kejadian;

	@SerializedName("nit")
	String nit;

	@SerializedName("nama_kabupaten_kota_sebelumnya")
	String nama_kabupaten_kota_sebelumnya;

	@SerializedName("nama_kabupaten_kota_sesudahnya")
	String nama_kabupaten_kota_sesudahnya;

	public String getKode_riwayat_kepemilikan() {
		return kode_riwayat_kepemilikan;
	}

	public void setKode_riwayat_kepemilikan(String kode_riwayat_kepemilikan) {
		this.kode_riwayat_kepemilikan = kode_riwayat_kepemilikan;
	}

	public String getIdl_sebelum() {
		return idl_sebelum;
	}

	public void setIdl_sebelum(String idl_sebelum) {
		this.idl_sebelum = idl_sebelum;
	}

	public String getIdl_sesudah() {
		return idl_sesudah;
	}

	public void setIdl_sesudah(String idl_sesudah) {
		this.idl_sesudah = idl_sesudah;
	}

	public String getTanggal_kejadian() {
		return tanggal_kejadian;
	}

	public void setTanggal_kejadian(String tanggal_kejadian) {
		this.tanggal_kejadian = tanggal_kejadian;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNama_kabupaten_kota_sebelumnya() {
		return nama_kabupaten_kota_sebelumnya;
	}

	public void setNama_kabupaten_kota_sebelumnya(
			String nama_kabupaten_kota_sebelumnya) {
		this.nama_kabupaten_kota_sebelumnya = nama_kabupaten_kota_sebelumnya;
	}

	public String getNama_kabupaten_kota_sesudahnya() {
		return nama_kabupaten_kota_sesudahnya;
	}

	public void setNama_kabupaten_kota_sesudahnya(
			String nama_kabupaten_kota_sesudahnya) {
		this.nama_kabupaten_kota_sesudahnya = nama_kabupaten_kota_sesudahnya;
	}

}
