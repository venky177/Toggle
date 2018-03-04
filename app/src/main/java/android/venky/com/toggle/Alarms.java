package android.venky.com.toggle;

import android.app.PendingIntent;

import java.util.Date;

/**
 * Created by root on 31/1/16.
 */
public class Alarms {
    Date date;
    PendingIntent PI;
    int ImageID;
    int State;
    int id;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name,room;


    Alarms()
    {
        date=null;
        PI=null;
        ImageID=0;
        State=0;
        room="";
        name="";
        id=0;
    }
    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public PendingIntent getPI() {
        return PI;
    }

    public void setPI(PendingIntent PI) {
        this.PI = PI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    Alarms(Date date,PendingIntent PI,int ImageID, int State,int pin,String Name,String Room) {

        this.date = date;
        this.PI=PI;
        this.ImageID=ImageID;
        this.State=State;
        this.id=pin;
        this.name=Name;
        this.room=Room;

    }
    Alarms(Date date,int ImageID, int State,int pin,String Name,String Room) {

        this.date = date;
        this.ImageID=ImageID;
        this.State=State;
        this.id=pin;
        this.name=Name;
        this.room=Room;

    }


}
