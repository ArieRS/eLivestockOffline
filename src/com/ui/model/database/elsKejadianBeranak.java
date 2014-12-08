package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class elsKejadianBeranak {
	@SerializedName("kode_kejadian_beranak")
	int kode_kejadian_beranak;
	@SerializedName("nit")
	int nit;
	@SerializedName("tanggal_beranak")
	String tanggal_beranak;
	@SerializedName("banyaknya_anak_betina")
	String banyaknya_anak_betina;
	@SerializedName("banyaknya_anak_jantan")
	String banyaknya_anak_jantan;
	
	public int getKode_kejadian_beranak() {
		return kode_kejadian_beranak;
	}
	public void setKode_kejadian_beranak(int kode_kejadian_beranak) {
		this.kode_kejadian_beranak = kode_kejadian_beranak;
	}
	public int getNit() {
		return nit;
	}
	public void setNit(int nit) {
		this.nit = nit;
	}
	public String getTanggal_beranak() {
		return tanggal_beranak;
	}
	public void setTanggal_beranak(String tanggal_beranak) {
		this.tanggal_beranak = tanggal_beranak;
	}
	public String getBanyaknya_anak_betina() {
		return banyaknya_anak_betina;
	}
	public void setBanyaknya_anak_betina(String banyaknya_anak_betina) {
		this.banyaknya_anak_betina = banyaknya_anak_betina;
	}
	public String getBanyaknya_anak_jantan() {
		return banyaknya_anak_jantan;
	}
	public void setBanyaknya_anak_jantan(String banyaknya_anak_jantan) {
		this.banyaknya_anak_jantan = banyaknya_anak_jantan;
	}
	
	
}
