package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsSapiBetina {
	@SerializedName("nit")
	int nit;
	@SerializedName("idl")
	int idl;
	@SerializedName("tanggal_lahir")
	String tanggal_lahir;
	@SerializedName("bangsa")
	String bangsa;
	@SerializedName("nit_induk")
	int nit_induk;
	@SerializedName("bentuk_wajah")
	String bentuk_wajah;
	@SerializedName("warna")
	String warna;
	@SerializedName("status_punuk")
	int status_punuk;
	@SerializedName("status_aksesoris_kaki")
	int status_aksesoris_kaki;
	@SerializedName("status_kepemilikan")
	int status_kepemilikan;
	
	public int getNit() {
		return nit;
	}
	public void setNit(int nit) {
		this.nit = nit;
	}
	public int getIdl() {
		return idl;
	}
	public void setIdl(int idl) {
		this.idl = idl;
	}
	public String getTanggal_lahir() {
		return tanggal_lahir;
	}
	public void setTanggal_lahir(String tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}
	public String getBangsa() {
		return bangsa;
	}
	public void setBangsa(String bangsa) {
		this.bangsa = bangsa;
	}
	public int getNit_induk() {
		return nit_induk;
	}
	public void setNit_induk(int nit_induk) {
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
	public int getStatus_punuk() {
		return status_punuk;
	}
	public void setStatus_punuk(int status_punuk) {
		this.status_punuk = status_punuk;
	}
	public int getStatus_aksesoris_kaki() {
		return status_aksesoris_kaki;
	}
	public void setStatus_aksesoris_kaki(int status_aksesoris_kaki) {
		this.status_aksesoris_kaki = status_aksesoris_kaki;
	}
	public int getStatus_kepemilikan() {
		return status_kepemilikan;
	}
	public void setStatus_kepemilikan(int status_kepemilikan) {
		this.status_kepemilikan = status_kepemilikan;
	}
	
	
}
