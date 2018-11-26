package com.tinymonster.heartbeat3.entity;

/**
 * Created by TinyMonster on 27/06/2018.
 */

public class HealthTipEntitySuccess {
    public String msg;
    public String question_id_internal;
    public String question_processor;
    public HealthTipParam requestParams;
    public String statu;
    public String doctitle=" ";
    public class HealthTipParam{
        public String ext=" ";
        public String product;
        public String jscallback=" ";
        public String uuid;
        public String qid;
        public String content;
    }
}
