package iti.intake41.myapplication.models.user;

//
//	User.java
//
//	Create by Mukesh Yadav on 21/2/2021

import org.json.JSONException;
import org.json.JSONObject;


public class User{

	private String email;
	private String id;
	private String name;

	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}


	public User(){}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public User(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		email = jsonObject.opt("email").toString();
		id = jsonObject.opt("id").toString();
		name = jsonObject.opt("name").toString();
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("email", email);
			jsonObject.put("id", id);
			jsonObject.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}