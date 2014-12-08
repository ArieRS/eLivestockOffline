package com.ui.model.sync;

import com.google.gson.annotations.SerializedName;

public class MasterBangsaSapiModelSync {
	@SerializedName("id")
	String id;

	@SerializedName("value")
	String value;

	public String getID() {
		return id;
	}

	public void setID(String iD) {
		id = iD;
	}

	public String getVALUE() {
		return value;
	}

	public void setVALUE(String vALUE) {
		value = vALUE;
	}

}
