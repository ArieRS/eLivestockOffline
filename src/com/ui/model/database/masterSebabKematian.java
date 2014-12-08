package com.ui.model.database;

import com.google.gson.annotations.SerializedName;

public class masterSebabKematian {
	@SerializedName("id")
	int id;
	@SerializedName("value")
	String value;
	
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
	
}
