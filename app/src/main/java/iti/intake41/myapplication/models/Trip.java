package iti.intake41.myapplication.models;

//
//	-MU5ULrYl55jEqCBhnkl.java
//
//	Create by Mukesh Yadav on 21/2/2021

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import iti.intake41.myapplication.models.trip.Location;


public class Trip implements Parcelable {
    private String date;
    private Location endPoint;
    private String id;
    private Location startPoint;
    private String status;
    private String time;
    private String title;

    public Trip(){}

    public Trip(String title, String date, String time, String status, Location endPoint, Location startPoint ) {
        this.date = date;
        this.endPoint = endPoint;
        this.startPoint = startPoint;
        this.status = status;
        this.time = time;
        this.title = title;
    }

    protected Trip(Parcel in) {
        date = in.readString();
        endPoint = in.readParcelable(Location.class.getClassLoader());
        id = in.readString();
        startPoint = in.readParcelable(Location.class.getClassLoader());
        status = in.readString();
        time = in.readString();
        title = in.readString();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setEndPoint(Location endPoint){
        this.endPoint = endPoint;
    }
    public Location getEndPoint(){
        return this.endPoint;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setStartPoint(Location startPoint){
        this.startPoint = startPoint;
    }
    public Location getStartPoint(){
        return this.startPoint;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public Trip(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        date = jsonObject.opt("date").toString();
        endPoint = new Location(jsonObject.optJSONObject("endPoint"));
        id = jsonObject.opt("id").toString();
        startPoint = new Location(jsonObject.optJSONObject("startPoint"));
        status = jsonObject.opt("status").toString();
        time = jsonObject.opt("time").toString();
        title = jsonObject.opt("title").toString();
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public Map<String, Object> toMap()
    {
        Map<String, Object> mapObj = new HashMap<>();
            mapObj.put("date", date);
            mapObj.put("endPoint", endPoint.toMap());
            mapObj.put("id", id);
            mapObj.put("startPoint", startPoint.toMap());
            mapObj.put("status", status);
            mapObj.put("time", time);
            mapObj.put("title", title);
        return mapObj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeParcelable(endPoint, flags);
        dest.writeString(id);
        dest.writeParcelable(startPoint, flags);
        dest.writeString(status);
        dest.writeString(time);
        dest.writeString(title);
    }
}