package com.tinymonster.heartbeat3.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TinyMonster on 01/07/2018.
 */
@Entity
public class Rate {
    @Id(autoincrement = true)
    private Long id;
    private int beatNum;
    private String beatTime;
    private String Device_name;
    @Generated(hash = 373183471)
    public Rate(Long id, int beatNum, String beatTime, String Device_name) {
        this.id = id;
        this.beatNum = beatNum;
        this.beatTime = beatTime;
        this.Device_name = Device_name;
    }
    @Generated(hash = 1992118559)
    public Rate() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getBeatNum() {
        return this.beatNum;
    }
    public void setBeatNum(int beatNum) {
        this.beatNum = beatNum;
    }
    public String getBeatTime() {
        return this.beatTime;
    }
    public void setBeatTime(String beatTime) {
        this.beatTime = beatTime;
    }
    public String getDevice_name() {
        return this.Device_name;
    }
    public void setDevice_name(String Device_name) {
        this.Device_name = Device_name;
    }
}
