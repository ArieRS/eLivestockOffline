package com.ui.model.database;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class getAllTable {
	@SerializedName("els_kabupaten_kota")
	ArrayList<elsKabupatenKota> listElsKabupatenKota;
	@SerializedName("els_kejadian_beranak")
	ArrayList<elsKejadianBeranak> listElsKejadianBeranak;
	@SerializedName("els_kejadian_ib")
	ArrayList<elsKejadianIb> listElsKejadianIb;
	@SerializedName("els_kejadian_kematian")
	ArrayList<elsKejadianKematian> listKejadianKematian;
	@SerializedName("els_lokasi")
	ArrayList<elsLokasi> listElsLokasi;
	@SerializedName("els_pemeriksaan_kesehatan")
	ArrayList<elsPemeriksaanKesehatan> listElsPemeriksaanKesehatan;
	@SerializedName("els_perubahan_kepemilikan")
	ArrayList<elsPerubahanKepemilikan> listPerubahanKepemilikan;
	@SerializedName("els_petugas")
	ArrayList<elsPetugas> listElsPetuga;
	@SerializedName("els_provinsi")
	ArrayList<elsProvinsi> listElsProvinsi;
	@SerializedName("els_sapi_betina")
	ArrayList<elsSapiBetina> listElsSapiBetina;
	@SerializedName("master_bangsa_sapi")
	ArrayList<masterBangsaSapi> listMasterBangsaSapi;
	@SerializedName("master_bentuk_wajah_sapi")
	ArrayList<masterBentukWajahSapi> listMasterBentukWajahSapi;
	@SerializedName("master_level_petugas")
	ArrayList<masterLevelPetugas> listMasterLevelPetugas;
	@SerializedName("master_penyakit_sapi")
	ArrayList<masterPenyakitSapi> listMasterPenyakitSapi;
	@SerializedName("master_peran_petugas")
	ArrayList<masterPeranPetugas> listMasterPeranPetugas;
	@SerializedName("master_sebab_kematian")
	ArrayList<masterSebabKematian> listMasterSebabKematian;
	@SerializedName("master_type_lokasi")
	ArrayList<masterTypeLokasi> listMasterTypeLokasi;
	@SerializedName("master_warna_sapi")
	ArrayList<masterWarnaSapi> listMasterWarnaSapi;
	
	public ArrayList<elsKabupatenKota> getListElsKabupatenKota() {
		return listElsKabupatenKota;
	}
	public void setListElsKabupatenKota(
			ArrayList<elsKabupatenKota> listElsKabupatenKota) {
		this.listElsKabupatenKota = listElsKabupatenKota;
	}
	public ArrayList<elsKejadianBeranak> getListElsKejadianBeranak() {
		return listElsKejadianBeranak;
	}
	public void setListElsKejadianBeranak(
			ArrayList<elsKejadianBeranak> listElsKejadianBeranak) {
		this.listElsKejadianBeranak = listElsKejadianBeranak;
	}
	public ArrayList<elsKejadianIb> getListElsKejadianIb() {
		return listElsKejadianIb;
	}
	public void setListElsKejadianIb(ArrayList<elsKejadianIb> listElsKejadianIb) {
		this.listElsKejadianIb = listElsKejadianIb;
	}
	public ArrayList<elsKejadianKematian> getListKejadianKematian() {
		return listKejadianKematian;
	}
	public void setListKejadianKematian(
			ArrayList<elsKejadianKematian> listKejadianKematian) {
		this.listKejadianKematian = listKejadianKematian;
	}
	public ArrayList<elsLokasi> getListElsLokasi() {
		return listElsLokasi;
	}
	public void setListElsLokasi(ArrayList<elsLokasi> listElsLokasi) {
		this.listElsLokasi = listElsLokasi;
	}
	public ArrayList<elsPemeriksaanKesehatan> getListElsPemeriksaanKesehatan() {
		return listElsPemeriksaanKesehatan;
	}
	public void setListElsPemeriksaanKesehatan(
			ArrayList<elsPemeriksaanKesehatan> listElsPemeriksaanKesehatan) {
		this.listElsPemeriksaanKesehatan = listElsPemeriksaanKesehatan;
	}
	public ArrayList<elsPerubahanKepemilikan> getListPerubahanKepemilikan() {
		return listPerubahanKepemilikan;
	}
	public void setListPerubahanKepemilikan(
			ArrayList<elsPerubahanKepemilikan> listPerubahanKepemilikan) {
		this.listPerubahanKepemilikan = listPerubahanKepemilikan;
	}
	public ArrayList<elsPetugas> getListElsPetuga() {
		return listElsPetuga;
	}
	public void setListElsPetuga(ArrayList<elsPetugas> listElsPetuga) {
		this.listElsPetuga = listElsPetuga;
	}
	public ArrayList<elsProvinsi> getListElsProvinsi() {
		return listElsProvinsi;
	}
	public void setListElsProvinsi(ArrayList<elsProvinsi> listElsProvinsi) {
		this.listElsProvinsi = listElsProvinsi;
	}
	public ArrayList<elsSapiBetina> getListElsSapiBetina() {
		return listElsSapiBetina;
	}
	public void setListElsSapiBetina(ArrayList<elsSapiBetina> listElsSapiBetina) {
		this.listElsSapiBetina = listElsSapiBetina;
	}
	public ArrayList<masterBangsaSapi> getListMasterBangsaSapi() {
		return listMasterBangsaSapi;
	}
	public void setListMasterBangsaSapi(
			ArrayList<masterBangsaSapi> listMasterBangsaSapi) {
		this.listMasterBangsaSapi = listMasterBangsaSapi;
	}
	public ArrayList<masterBentukWajahSapi> getListMasterBentukWajahSapi() {
		return listMasterBentukWajahSapi;
	}
	public void setListMasterBentukWajahSapi(
			ArrayList<masterBentukWajahSapi> listMasterBentukWajahSapi) {
		this.listMasterBentukWajahSapi = listMasterBentukWajahSapi;
	}
	public ArrayList<masterLevelPetugas> getListMasterLevelPetugas() {
		return listMasterLevelPetugas;
	}
	public void setListMasterLevelPetugas(
			ArrayList<masterLevelPetugas> listMasterLevelPetugas) {
		this.listMasterLevelPetugas = listMasterLevelPetugas;
	}
	public ArrayList<masterPenyakitSapi> getListMasterPenyakitSapi() {
		return listMasterPenyakitSapi;
	}
	public void setListMasterPenyakitSapi(
			ArrayList<masterPenyakitSapi> listMasterPenyakitSapi) {
		this.listMasterPenyakitSapi = listMasterPenyakitSapi;
	}
	public ArrayList<masterPeranPetugas> getListMasterPeranPetugas() {
		return listMasterPeranPetugas;
	}
	public void setListMasterPeranPetugas(
			ArrayList<masterPeranPetugas> listMasterPeranPetugas) {
		this.listMasterPeranPetugas = listMasterPeranPetugas;
	}
	public ArrayList<masterSebabKematian> getListMasterSebabKematian() {
		return listMasterSebabKematian;
	}
	public void setListMasterSebabKematian(
			ArrayList<masterSebabKematian> listMasterSebabKematian) {
		this.listMasterSebabKematian = listMasterSebabKematian;
	}
	public ArrayList<masterTypeLokasi> getListMasterTypeLokasi() {
		return listMasterTypeLokasi;
	}
	public void setListMasterTypeLokasi(
			ArrayList<masterTypeLokasi> listMasterTypeLokasi) {
		this.listMasterTypeLokasi = listMasterTypeLokasi;
	}
	public ArrayList<masterWarnaSapi> getListMasterWarnaSapi() {
		return listMasterWarnaSapi;
	}
	public void setListMasterWarnaSapi(
			ArrayList<masterWarnaSapi> listMasterWarnaSapi) {
		this.listMasterWarnaSapi = listMasterWarnaSapi;
	}
	
	
}
