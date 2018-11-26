package com.tinymonster.heartbeat3.entity;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.tinymonster.heartbeat3.util.DateUtil;

import java.util.Date;

/**
 * Created by TinyMonster on 15/08/2018.
 */

public class BBSComment extends DroiObject{
    @DroiExpose
    String Username;
    @DroiExpose
    String key;
    @DroiExpose
    String Message;
    @DroiExpose
    Date date;
    @DroiExpose
    String other;
    public BBSComment(){

    }
    public BBSComment(String key,String Username,String Message,String other){
        this.key=key;
        this.Username=Username;
        this.Message=Message;
        this.other=other;
        this.date= DateUtil.getDate();
    }
    public String getName(){
        return this.Username;
    }
    public String getMessage(){
        return this.Message;
    }
    public String getTime(){
        return DateUtil.simpleDateFormat.format(this.date);
    }
}
