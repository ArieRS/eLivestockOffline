package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class Cari_RiwayatKesehatanModelSync {

	@SerializedName("kode_pemeriksaan_kesehatan")
	String kode_pemeriksaan_kesehatan;

	@SerializedName("diagnosa")
	String diagnosa;

	@SerializedName("perlakuan")
	String perlakuan;

	@SerializedName("nit")
	String nit;

	@SerializedName("id_petugas")
	String id_petugas;

	@SerializedName("tanggal")
	String tanggal;

	public String getKode_pemeriksaan_kesehatan() {
		return kode_pemeriksaan_kesehatan;
	}

	public void setKode_pemeriksaan_kesehatan(String kode_pemeriksaan_kesehatan) {
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

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

}
