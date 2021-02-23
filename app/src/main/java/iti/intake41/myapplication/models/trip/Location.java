package iti.intake41.myapplication.models.trip;

//
//	EndPoint.java
//

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Location implements Parcelable {

	private String address, latitude, longitude;


	protected Location(Parcel in) {
		address = in.readString();
		latitude = in.readString();
		longitude = in.readString();
	}

	public static final Creator<Location> CREATOR = new Creator<Location>() {
		@Override
		public Location createFromParcel(Parcel in) {
			return new Location(in);
		}

		@Override
		public Location[] newArray(int size) {
			return new Location[size];
		}
	};

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
			mapObj.put("latitude", latitude);
			mapObj.put("longitude", longitude);
		return mapObj;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(address);
		dest.writeString(latitude);
		dest.writeString(longitude);
	}
}