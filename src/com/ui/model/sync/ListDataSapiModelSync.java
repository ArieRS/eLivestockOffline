package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class ListDataSapiModelSync {

	@SerializedName("nit")
	private String nit;

	@SerializedName("lokasi")
	private String lokasi;

	@SerializedName("bangsa")
	private String bangsa;

	@SerializedName("tanggal_lahir")
	private String tanggal_lahir;

	@SerializedName("nit_induk")
	private String nit_induk;

	@SerializedName("bentuk_wajah")
	private String bentuk_wajah;

	@SerializedName("warna")
	private String warna;

	@SerializedName("status_punuk")
	private String status_punuk;

	@SerializedName("status_aksesoris_kaki")
	private String status_aksesoris_kaki;

	@SerializedName("status_kepemilikan_pemerintah")
	private String status_kepemilikan_pemerintah;

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getBangsa() {
		return bangsa;
	}

	public void setBangsa(String bangsa) {
		this.bangsa = bangsa;
	}

	public String getTanggal_lahir() {
		return tanggal_lahir;
	}

	public void setTanggal_lahir(String tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}

	public String getNit_induk() {
		return nit_induk;
	}

	public void setNit_induk(String nit_induk) {
		this.nit_induk = nit_induk;
	}

	public String getBentuk_wajah() {
		return bentuk_wajah;
	}

	public void setBentuk_wajah(String bentuk_wajah) {
		this.bentuk_wajah = bentuk_wajah;
	}

	public String getWarna() {
		return warna;
	}

	public void setWarna(String warna) {
		this.warna = warna;
	}

	public String getStatus_punuk() {
		return status_punuk;
	}

	public void setStatus_punuk(String status_punuk) {
		this.status_punuk = status_punuk;
	}

	public String getStatus_aksesoris_kaki() {
		return status_aksesoris_kaki;
	}

	public void setStatus_aksesoris_kaki(String status_aksesoris_kaki) {
		this.status_aksesoris_kaki = status_aksesoris_kaki;
	}

	public String getStatus_kepemilikan_pemerintah() {
		return status_kepemilikan_pemerintah;
	}

	public void setStatus_kepemilikan_pemerintah(
			String status_kepemilikan_pemerintah) {
		this.status_kepemilikan_pemerintah = status_kepemilikan_pemerintah;
	}

}
