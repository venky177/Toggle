package android.venky.com.toggle;

import java.util.Date;

/**
 * Created by saurabh on 3/4/2016.
 */
public class Device {
    String name;
    long id;
    String drawable;
    int Volt;
    int state;
    int pin;
    String room;

    long startTime;
    int hoursOn=0;
    public Device() {
        name = "";
        drawable = "";
        Volt = 0;
        state = -1;
        id=0;
        pin = -1;
        room = "";
        hoursOn=0;
        startTime=0;
    }
    public Device(String name,String drawable, int Volt, int state,int pin, String room) {
        this.name = name;
        this.drawable = drawable;
        this.Volt = Volt;
        this.state = state;
        id=new Date().getTime();
        this.pin = pin;
        this.room = room;
        hoursOn=0;
        startTime=0;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getHoursOn() {
        return hoursOn;
    }

    public void setHoursOn(int hoursOn) {
        this.hoursOn = hoursOn;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getDrawable() {
        return drawable;
    }

    public int getVolt() {
        return Volt;
    }

    public int getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDrawable(String drawable) {
        this.drawable = drawable;
    }

    public void setVolt(int volt) {
        Volt = volt;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

