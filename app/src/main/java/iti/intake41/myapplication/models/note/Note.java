package iti.intake41.myapplication.models.note;

//
//	-MU5ULrZw6cajzZefPPm.java
//
//	Create by Mukesh Yadav on 21/2/2021

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Note implements Serializable {

        private boolean done;
        private String id;
        private String title;

        public void setDone(boolean done){
                this.done = done;
        }
        public boolean getDone()
        {
                return this.done;
        }
        public void setId(String id){
                this.id = id;
        }
        public String getId(){
                return this.id;
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
        public Note(){}

        public Note(String id, String title , boolean done) {
                this.done = done;
                this.id = id;
                this.title = title;
        }

        public Note(JSONObject jsonObject){
                if(jsonObject == null){
                        return;
                }
                done = jsonObject.optBoolean("done");
                id = jsonObject.opt("id").toString();
                title = jsonObject.opt("title").toString();
        }

        /**
         * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
         */
        public Map<String, Object> toMap()
        {
                Map<String, Object> map = new HashMap<>();
                        map.put("done", done);
                        map.put("id", id);
                        map.put("title", title);

                return map;
        }

}