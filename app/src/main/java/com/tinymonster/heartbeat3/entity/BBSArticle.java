package com.tinymonster.heartbeat3.entity;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.tinymonster.heartbeat3.util.DateUtil;

import java.util.Date;

/**
 * Created by TinyMonster on 15/08/2018.
 */

public class BBSArticle extends DroiObject{
    @DroiExpose
    String UserName;
    @DroiExpose
    String Title;
    @DroiExpose
    String Message;
    @DroiExpose
    Date date;
    @DroiExpose
    String other;
    @DroiExpose
    String key;
    public BBSArticle(){

    }
    public BBSArticle(String UserName,String Title,String Message,String other){
        this.UserName=UserName;
        this.Title=Title;
        this.Message=Message;
        this.other=other;
        this.date= DateUtil.getDate();
        this.key=this.UserName+DateUtil.simpleDateFormat.format(date);
    }
    public String getUserName(){
        return this.UserName;
    }
    public String getTitle(){
        return this.Title;
    }
    public String getMessage(){
        return this.Message;
    }
    public String getTime(){
        return DateUtil.simpleDateFormat.format(this.date);
    }
    public String getKey(){
        return this.key;
    }
}
