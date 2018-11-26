package com.tinymonster.heartbeat3.entity;

import java.util.List;

/**
 * Created by TinyMonster on 27/06/2018.
 */

public class RecognizeEntity {
    public List<String> results_recognition;
    public Origin_result origin_result;
    public int error;
    public String best_result;
    public String result_type;
    public class Origin_result{
        public double corpus_no;
        public int err_no;
        public Result result;
        public String sn;
        public double voice_energy;
    }
    public class Result{
        public List<String> word;
    }
}
