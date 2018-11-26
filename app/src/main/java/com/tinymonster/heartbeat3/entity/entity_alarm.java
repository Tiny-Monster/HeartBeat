package com.tinymonster.heartbeat3.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TinyMonster on 02/07/2018.
 */
@Entity
public class entity_alarm {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String message;
    private int hour;
    private int minute;
    private String alarmID;
    @Generated(hash = 1850836780)
    public entity_alarm(Long id, String name, String message, int hour, int minute,
            String alarmID) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.hour = hour;
        this.minute = minute;
        this.alarmID = alarmID;
    }
    @Generated(hash = 1135038534)
    public entity_alarm() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getHour() {
        return this.hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinute() {
        return this.minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public String getAlarmID() {
        return this.alarmID;
    }
    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }
}
