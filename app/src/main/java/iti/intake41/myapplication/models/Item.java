package iti.intake41.myapplication.models;

import java.util.Date;

public class Item {
    Date date;
    String title;

    public Item(Date date, String title) {
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
