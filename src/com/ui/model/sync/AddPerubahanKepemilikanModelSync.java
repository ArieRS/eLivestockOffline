package com.ui.model.sync;

public class AddPerubahanKepemilikanModelSync {
	int kode_riwayat_kepemilikan;
	int	idl_sebelum; 
	int idl_sesudah;
	String tanggal_kejadian;
	int nit;
	String user;            
	String pass;        
	String guid;
	
	public int getKode_riwayat_kepemilikan() {
		return kode_riwayat_kepemilikan;
	}
	public void setKode_riwayat_kepemilikan(int kode_riwayat_kepemilikan) {
		this.kode_riwayat_kepemilikan = kode_riwayat_kepemilikan;
	}
	public int getIdl_sebelum() {
		return idl_sebelum;
	}
	public void setIdl_sebelum(int idl_sebelum) {
		this.idl_sebelum = idl_sebelum;
	}
	public int getIdl_sesudah() {
		return idl_sesudah;
	}
	public void setIdl_sesudah(int idl_sesudah) {
		this.idl_sesudah = idl_sesudah;
	}
	public String getTanggal_kejadian() {
		return tanggal_kejadian;
	}
	public void setTanggal_kejadian(String tanggal_kejadian) {
		this.tanggal_kejadian = tanggal_kejadian;
	}
	public int getNit() {
		return nit;
	}
	public void setNit(int nit) {
		this.nit = nit;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
