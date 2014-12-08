package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class masterPeranPetugas {
	@SerializedName("id")
	int id;
	@SerializedName("value")
	String value;
	@SerializedName("description")
	String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
