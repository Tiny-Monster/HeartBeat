package com.tinymonster.heartbeat3.entity;

import android.graphics.Bitmap;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by TinyMonster on 30/06/2018.
 */
@Entity
public class entity_device {
    @Id(autoincrement = true)
    private Long id;
    private String device_name;
    private String device_password;
    private String nick_name;
    private String sex;
    private String bitmap;//当前图片
    private String heart_rate_now;//当前心率
    @Generated(hash = 1177167698)
    public entity_device(Long id, String device_name, String device_password,
            String nick_name, String sex, String bitmap, String heart_rate_now) {
        this.id = id;
        this.device_name = device_name;
        this.device_password = device_password;
        this.nick_name = nick_name;
        this.sex = sex;
        this.bitmap = bitmap;
        this.heart_rate_now = heart_rate_now;
    }
    @Generated(hash = 273731624)
    public entity_device() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDevice_name() {
        return this.device_name;
    }
    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
    public String getDevice_password() {
        return this.device_password;
    }
    public void setDevice_password(String device_password) {
        this.device_password = device_password;
    }
    public String getNick_name() {
        return this.nick_name;
    }
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBitmap() {
        return this.bitmap;
    }
    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }
    public String getHeart_rate_now() {
        return this.heart_rate_now;
    }
    public void setHeart_rate_now(String heart_rate_now) {
        this.heart_rate_now = heart_rate_now;
    }
}
