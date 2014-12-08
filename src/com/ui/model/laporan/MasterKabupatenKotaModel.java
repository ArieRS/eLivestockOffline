package com.ui.model.laporan;

public class MasterKabupatenKotaModel {
	int id_kabupaten_kota; 
	int id_provinsi;
	String Nama_Kabupaten_Kota;
	String Nama_Provinsi;
	
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
	public String getNama_Kabupaten_Kota() {
		return Nama_Kabupaten_Kota;
	}
	public void setNama_Kabupaten_Kota(String nama_Kabupaten_Kota) {
		Nama_Kabupaten_Kota = nama_Kabupaten_Kota;
	}
	public String getNama_Provinsi() {
		return Nama_Provinsi;
	}
	public void setNama_Provinsi(String nama_Provinsi) {
		Nama_Provinsi = nama_Provinsi;
	}
}
