package com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models;

import java.util.List;

/**
 * Created by abhijit on 2/25/2017.
 */

public class TrainModel {
    private List<Classes> classesList;
    //private Classes[] classes;

    private To to;

    private List<Days> daysList;

    private int no;

    private String name;

    private String dest_arrival_time;

    private int number;

    private From from;

    private String travel_time;

    private String src_departure_time;


    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
    }

    public List<Days> getDaysList() {
        return daysList;
    }

    public void setDaysList(List<Days> daysList) {
        this.daysList = daysList;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDest_arrival_time() {
        return dest_arrival_time;
    }

    public void setDest_arrival_time(String dest_arrival_time) {
        this.dest_arrival_time = dest_arrival_time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getSrc_departure_time() {
        return src_departure_time;
    }

    public void setSrc_departure_time(String src_departure_time) {
        this.src_departure_time = src_departure_time;
    }

    @Override
    public String toString() {
        return "TrainModel [classes = " + classesList + ", to = " + to + ", days = " + daysList + ", no = " + no + ", name = " + name + ", dest_arrival_time = " + dest_arrival_time + ", number = " + number + ", from = " + from + ", travel_time = " + travel_time + ", src_departure_time = " + src_departure_time + "]";
    }

    public final static class Classes {
        private String class_code;

        private String available;

        public String getClass_code() {
            return class_code;
        }

        public void setClass_code(String class_code) {
            this.class_code = class_code;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return "TrainModel [class_code = " + class_code + ", available = " + available + "]";
        }
    }

    public final static class To {
        private String name;

        private String code;

        public String getName2() {
            return name;
        }

        public void setName2(String name) {
            this.name = name;
        }

        public String getCode2() {
            return code;
        }

        public void setCode2(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "TrainModel [name = " + name + ", code = " + code + "]";
        }
    }

    public final static class From {
        private String name;

        private String code;

        public String getName1() {
            return name;
        }

        public void setName1(String name) {
            this.name = name;
        }

        public String getCode1() {
            return code;
        }

        public void setCode1(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "name=" + name + "-" + code;
        }
    }

    public final static class Days {
        private String day_code;

        private String runs;

        public String getDay_code() {
            return day_code;
        }

        public void setDay_code(String day_code) {
            this.day_code = day_code;
        }

        public String getRuns() {
            return runs;
        }

        public void setRuns(String runs) {
            this.runs = runs;
        }

        @Override
        public String toString() {
            return "TrainModel [day-code = " + day_code + ", runs = " + runs + "]";
        }
    }
}
