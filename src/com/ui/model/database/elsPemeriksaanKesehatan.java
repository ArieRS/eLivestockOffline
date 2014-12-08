package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsPemeriksaanKesehatan {
	@SerializedName("kode_pemeriksaan_kesehatan")
	int kode_pemeriksaan_kesehatan;
	@SerializedName("diagnosa")
	String diagnosa;
	@SerializedName("perlakuan")
	String perlakuan;
	@SerializedName("nit")
	int nit;
	@SerializedName("id_petugas")
	int id_petugas;
	@SerializedName("tanggal_periksa")
	String tanggal_periksa;
	
	public int getKode_pemeriksaan_kesehatan() {
		return kode_pemeriksaan_kesehatan;
	}
	public void setKode_pemeriksaan_kesehatan(int kode_pemeriksaan_kesehatan) {
		this.kode_pemeriksaan_kesehatan = kode_pemeriksaan_kesehatan;
	}
	public String getDiagnosa() {
		return diagnosa;
	}
	public void setDiagnosa(String diagnosa) {
		this.diagnosa = diagnosa;
	}
	public String getPerlakuan() {
		return perlakuan;
	}
	public void setPerlakuan(String perlakuan) {
		this.perlakuan = perlakuan;
	}
	public int getNit() {
		return nit;
	}
	public void setNit(int nit) {
		this.nit = nit;
	}
	public int getId_petugas() {
		return id_petugas;
	}
	public void setId_petugas(int id_petugas) {
		this.id_petugas = id_petugas;
	}
	public String getTanggalPeriksa() {
		return tanggal_periksa;
	}
	public void setTanggalPeriksa(String tanggal_periksa) {
		this.tanggal_periksa = tanggal_periksa;
	}
	
	
}
