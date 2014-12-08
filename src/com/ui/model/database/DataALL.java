package com.ui.model.database;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DataALL {
	@SerializedName("Data")
	getAllTable data;

	public getAllTable getData() {
		return data;
	}

	public void setData(getAllTable data) {
		this.data = data;
	}
	
}
