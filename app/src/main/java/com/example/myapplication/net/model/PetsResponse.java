package com.example.myapplication.net.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PetsResponse {

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"PetsResponse{" +
			"data = '" + data + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}