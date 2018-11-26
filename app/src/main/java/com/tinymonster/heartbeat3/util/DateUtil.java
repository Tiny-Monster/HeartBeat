package com.tinymonster.heartbeat3.util;

import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TinyMonster on 15/08/2018.
 */

public class DateUtil {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");;
    public static Date getDate(){
        Date date=new Date(System.currentTimeMillis());
        return date;
    }
}
