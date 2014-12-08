package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsKejadianKematian {
	@SerializedName("tanggal_kematian")
	String tanggal_kematian;
	@SerializedName("sebab_kematian")
	String sebab_kematian;
	@SerializedName("idl_kematian")
	String idl_kematian;
	@SerializedName("nit")
	String nit;
	@SerializedName("id_petugas")
	String id_petugas;
	
	
	public String getTanggal_kematian() {
		return tanggal_kematian;
	}
	public void setTanggal_kematian(String tanggal_kematian) {
		this.tanggal_kematian = tanggal_kematian;
	}
	public String getSebab_kematian() {
		return sebab_kematian;
	}
	public void setSebab_kematian(String sebab_kematian) {
		this.sebab_kematian = sebab_kematian;
	}
	public String getIdl_kematian() {
		return idl_kematian;
	}
	public void setIdl_kematian(String idl_kematian) {
		this.idl_kematian = idl_kematian;
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
	
	
}
