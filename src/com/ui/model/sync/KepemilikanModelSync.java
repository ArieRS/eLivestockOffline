package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class KepemilikanModelSync {

	@SerializedName("kode_riwayat_kepemilikan")
	String kode_riwayat_kepemilikan;

	@SerializedName("idl_sebelum")
	String idl_sebelum;

	@SerializedName("idl_sesudah")
	String idl_sesudah;

	@SerializedName("tanggal_kejadian")
	String tanggal_kejadian;

	@SerializedName("nit")
	String nit;

}
