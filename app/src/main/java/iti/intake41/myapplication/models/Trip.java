package iti.intake41.myapplication.models;


//
//	RootClass.java
//
//	Create by Mukesh Yadav on 21/2/2021

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Trip {
    private String tripID, userID, title, endPoint, startPoint, status, date, time;
    private double endLat, endLong, startLat, startLong;
    private Map<String, Boolean> notes = new HashMap<>();

    public Trip(){
    }

    public Trip(String title, String tripID, String userID , String date,
                String startPoint, String endPoint, String status, String time,
                double endLat, double endLong, double startLat, double startLong,
                Map<String, Boolean> notes) {
        this.date = date;
        this.endLat = endLat;
        this.endLong = endLong;
        this.endPoint = endPoint;
        this.startLat = startLat;
        this.startLong = startLong;
        this.startPoint = startPoint;
        this.status = status;
        this.time = time;
        this.title = title;
        this.tripID = tripID;
        this.userID = userID;
        this.notes = notes;
    }

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setEndLat(float endLat){
        this.endLat = endLat;
    }
    public double getEndLat(){
        return this.endLat;
    }
    public void setEndLong(float endLong){
        this.endLong = endLong;
    }
    public double getEndLong(){
        return this.endLong;
    }
    public void setEndPoint(String endPoint){
        this.endPoint = endPoint;
    }
    public String getEndPoint(){
        return this.endPoint;
    }
    public void setStartLat(float startLat){
        this.startLat = startLat;
    }
    public double getStartLat(){
        return this.startLat;
    }
    public void setStartLong(float startLong){
        this.startLong = startLong;
    }
    public double getStartLong(){
        return this.startLong;
    }
    public void setStartPoint(String startPoint){
        this.startPoint = startPoint;
    }
    public String getStartPoint(){
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
    public void setTripID(String tripID){
        this.tripID = tripID;
    }
    public String getTripID(){
        return this.tripID;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }
    public String getUserID(){
        return this.userID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("endLat", endLat);
        result.put("endLong", endLong);
        result.put("endPoint", endPoint);
        result.put("startLat", startLat);
        result.put("startLong", startLong);
        result.put("startPoint", startPoint);
        result.put("status", status);
        result.put("time", time);
        result.put("title", title);
        result.put("tripID", tripID);
        result.put("userID", userID);
        result.put("notes", notes);
        return result;
    }

}



/*

// [START Trip_class]
@IgnoreExtraProperties
public class Trip {
    String tripID, tripStartPoint, tripEndPoint, tripName, tripDate, tripTime, tripStatus, userID;
    double tripStartPointLongitude, tripStartPointLatitude, tripEndPointLongitude, tripEndPointLatitude;

    public Map<String, Boolean> notes = new HashMap<>();

    public Trip() {
        // Default constructor required for calls to DataSnapshot.getValue(Trip.class)
    }

    public Trip(String uid, String author, String title, String body) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
    }

    // [START trip_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END trip_to_map]
}
// [END trip_class]
 */


/**
 * {
 *  "tripID": "1",
 * 	"tripStartPoint": "1",
 * 	"tripEndPoint": "1",
 * 	"tripName": "1",
 * 	"tripDate": "1",
 * 	"tripTime": "1",
 * 	"tripStatus": "1",
 * 	"userID": "1",
 * 	"tripStartPointLongitude":1.2,
 * 	"tripStartPointLatitude": 1.3,
 * 	 "tripEndPointLongitude": 1.4,
 * 	"tripEndPointLatitude": 1.6
 *
 * }
 *
 * */