package com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models;

/**
 * Created by abhijit on 4/4/2017.
 */

public class TrainRescheduleModel {
    private String number;

    private String name;

    private To to;

    private From from;

    private String rescheduled_date;

    private String rescheduled_time;

    private String time_diff;

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public To getTo() {
        return this.to;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public From getFrom() {
        return this.from;
    }

    public void setRescheduled_date(String rescheduled_date) {
        this.rescheduled_date = rescheduled_date;
    }

    public String getRescheduled_date() {
        return this.rescheduled_date;
    }

    public void setRescheduled_time(String rescheduled_time) {
        this.rescheduled_time = rescheduled_time;
    }

    public String getRescheduled_time() {
        return this.rescheduled_time;
    }

    public void setTime_diff(String time_diff) {
        this.time_diff = time_diff;
    }

    public String getTime_diff() {
        return this.time_diff;
    }


    public final static class From {
        private String code;

        private String name;

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

    }

    public final static class To
    {
        private String code;

        private String name;

        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
    }

}
