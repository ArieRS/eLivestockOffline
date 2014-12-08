package com.ui.model.laporan;

public class Search_PerubahanKepemilikanModel {
	int kode_riwayat_kepemilikan;
	String KotaSebelumnya ;
	String KotaSesudahnya ;
	String TanggalKejadian;
	
	public int getKode_riwayat_kepemilikan() {
		return kode_riwayat_kepemilikan;
	}
	public void setKode_riwayat_kepemilikan(int kode_riwayat_kepemilikan) {
		this.kode_riwayat_kepemilikan = kode_riwayat_kepemilikan;
	}
	public String getKotaSebelumnya() {
		return KotaSebelumnya;
	}
	public void setKotaSebelumnya(String kotaSebelumnya) {
		KotaSebelumnya = kotaSebelumnya;
	}
	public String getKotaSesudahnya() {
		return KotaSesudahnya;
	}
	public void setKotaSesudahnya(String kotaSesudahnya) {
		KotaSesudahnya = kotaSesudahnya;
	}
	public String getTanggalKejadian() {
		return TanggalKejadian;
	}
	public void setTanggalKejadian(String tanggalKejadian) {
		TanggalKejadian = tanggalKejadian;
	}
}
