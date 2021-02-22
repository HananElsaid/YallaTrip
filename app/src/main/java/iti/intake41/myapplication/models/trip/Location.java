package iti.intake41.myapplication.models.trip;

//
//	EndPoint.java
//

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Location{

	private String address, latitude, longitude;


	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	public String getLatitude(){
		return this.latitude;
	}
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	public String getLongitude(){
		return this.longitude;
	}

	public Location(){}

	public Location(String address, String latitude, String longitude) {
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Location(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		address = jsonObject.opt("address").toString();
		latitude = jsonObject.opt("latitude").toString();
		longitude = jsonObject.opt("longitude").toString();
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public Map<String,String> toMap()
	{
		Map<String,String> mapObj = new HashMap<>();
			mapObj.put("address", address);
			mapObj.put("lat", latitude);
			mapObj.put("long", longitude);
		return mapObj;
	}

}