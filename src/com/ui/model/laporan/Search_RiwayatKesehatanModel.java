package com.ui.model.laporan;

public class Search_RiwayatKesehatanModel {
	String KodePemeriksaanKesehatan;
	String Diagnosa;
	String Perlakuan;
	String tanggal_periksa;
	String id_petugas;
	
	
	public String getKodePemeriksaanKesehatan() {
		return KodePemeriksaanKesehatan;
	}
	public void setKodePemeriksaanKesehatan(String kodePemeriksaanKesehatan) {
		KodePemeriksaanKesehatan = kodePemeriksaanKesehatan;
	}
	public String getDiagnosa() {
		return Diagnosa;
	}
	public void setDiagnosa(String diagnosa) {
		Diagnosa = diagnosa;
	}
	public String getPerlakuan() {
		return Perlakuan;
	}
	public void setPerlakuan(String perlakuan) {
		Perlakuan = perlakuan;
	}
	public String getTanggal_periksa() {
		return tanggal_periksa;
	}
	public void setTanggal_periksa(String tanggal_periksa) {
		this.tanggal_periksa = tanggal_periksa;
	}
	public String getId_petugas() {
		return id_petugas;
	}
	public void setId_petugas(String id_petugas) {
		this.id_petugas = id_petugas;
	}
}
