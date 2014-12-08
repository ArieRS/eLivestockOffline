package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsPetugas {
	@SerializedName("id_petugas")
	int id_petugas;
	
	@SerializedName("nama")
	String nama;
	
	@SerializedName("peran")
	String peran;
	@SerializedName("no_telepon")
	String no_telepon;
	@SerializedName("username")
	String username;
	@SerializedName("password")
	String password;
	@SerializedName("id_level_admin")
	int id_level_admin;
	@SerializedName("id_kabupaten_kota")
	int id_kabupaten_kota;
	@SerializedName("id_provinsi")
	int id_provinsi;
	
	public int getId_petugas() {
		return id_petugas;
	}
	public void setId_petugas(int id_petugas) {
		this.id_petugas = id_petugas;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getPeran() {
		return peran;
	}
	public void setPeran(String peran) {
		this.peran = peran;
	}
	public String getNo_telepon() {
		return no_telepon;
	}
	public void setNo_telepon(String no_telepon) {
		this.no_telepon = no_telepon;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId_level_admin() {
		return id_level_admin;
	}
	public void setId_level_admin(int id_level_admin) {
		this.id_level_admin = id_level_admin;
	}
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
	
	
}
