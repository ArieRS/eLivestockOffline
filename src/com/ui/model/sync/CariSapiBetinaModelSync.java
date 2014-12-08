package com.ui.model.sync;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class CariSapiBetinaModelSync {

	
	@SerializedName("id_param")
	String id_param;

	@SerializedName("nit")
	String nit;

	@SerializedName("lokasi")
	String lokasi;

	@SerializedName("bangsa")
	String bangsa;

	@SerializedName("tanggal_lahir")
	String tanggal_lahir;

	@SerializedName("nit_induk")
	String nit_induk;

	@SerializedName("bentuk_wajah")
	String bentuk_wajah;

	@SerializedName("warna")
	String warna;

	@SerializedName("status_punuk")
	String status_punuk;

	@SerializedName("status_aksesoris_kaki")
	String status_aksesoris_kaki;

	@SerializedName("status_kepemilikan_pemerintah")
	String status_kepemilikan_pemerintah;

	@SerializedName("list_kejadian_beranak")
	ArrayList<Cari_KejadianBeranakModelSync> list_kejadian_beranak;

	@SerializedName("list_kejadian_kematian")
	ArrayList<Cari_KejadianKematianModelSync> list_kejadian_kematian;

	@SerializedName("list_riwayat_kesehatan")
	ArrayList<Cari_RiwayatKesehatanModelSync> list_riwayat_kesehatan;

	@SerializedName("list_kejadian_ib")
	ArrayList<Cari_KejadianIBModelSync> list_kejadian_ib;

	@SerializedName("list_kepemilikan")
	Cari_KepemilikanModelSync list_kepemilikan;

	public String getId_param() {
		return id_param;
	}

	public void setId_param(String id_param) {
		this.id_param = id_param;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getBangsa() {
		return bangsa;
	}

	public void setBangsa(String bangsa) {
		this.bangsa = bangsa;
	}

	public String getTanggal_lahir() {
		return tanggal_lahir;
	}

	public void setTanggal_lahir(String tanggal_lahir) {
		this.tanggal_lahir = tanggal_lahir;
	}

	public String getNit_induk() {
		return nit_induk;
	}

	public void setNit_induk(String nit_induk) {
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

	public String getStatus_punuk() {
		return status_punuk;
	}

	public void setStatus_punuk(String status_punuk) {
		this.status_punuk = status_punuk;
	}

	public String getStatus_aksesoris_kaki() {
		return status_aksesoris_kaki;
	}

	public void setStatus_aksesoris_kaki(String status_aksesoris_kaki) {
		this.status_aksesoris_kaki = status_aksesoris_kaki;
	}

	public String getStatus_kepemilikan_pemerintah() {
		return status_kepemilikan_pemerintah;
	}

	public void setStatus_kepemilikan_pemerintah(
			String status_kepemilikan_pemerintah) {
		this.status_kepemilikan_pemerintah = status_kepemilikan_pemerintah;
	}

	public ArrayList<Cari_KejadianBeranakModelSync> getList_kejadian_beranak() {
		return list_kejadian_beranak;
	}

	public void setList_kejadian_beranak(
			ArrayList<Cari_KejadianBeranakModelSync> list_kejadian_beranak) {
		this.list_kejadian_beranak = list_kejadian_beranak;
	}

	public ArrayList<Cari_KejadianKematianModelSync> getList_kejadian_kematian() {
		return list_kejadian_kematian;
	}

	public void setList_kejadian_kematian(
			ArrayList<Cari_KejadianKematianModelSync> list_kejadian_kematian) {
		this.list_kejadian_kematian = list_kejadian_kematian;
	}

	public ArrayList<Cari_RiwayatKesehatanModelSync> getList_riwayat_kesehatan() {
		return list_riwayat_kesehatan;
	}

	public void setList_riwayat_kesehatan(
			ArrayList<Cari_RiwayatKesehatanModelSync> list_riwayat_kesehatan) {
		this.list_riwayat_kesehatan = list_riwayat_kesehatan;
	}

	public ArrayList<Cari_KejadianIBModelSync> getList_kejadian_ib() {
		return list_kejadian_ib;
	}

	public void setList_kejadian_ib(ArrayList<Cari_KejadianIBModelSync> list_kejadian_ib) {
		this.list_kejadian_ib = list_kejadian_ib;
	}

	// public ArrayList<Cari_Kepemilikan> getList_kepemilikan() {
	// return list_kepemilikan;
	// }
	//
	// public void setList_kepemilikan(ArrayList<Cari_Kepemilikan>
	// list_kepemilikan) {
	// this.list_kepemilikan = list_kepemilikan;
	// }
}
