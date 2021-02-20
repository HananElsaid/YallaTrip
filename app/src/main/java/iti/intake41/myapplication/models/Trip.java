package iti.intake41.myapplication.models;

import java.util.Date;

public class Trip {
    Date date;
    String title;

    public Trip(Date date, String title) {
        this.date = date;
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
