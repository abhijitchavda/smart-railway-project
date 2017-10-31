package com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models;

/**
 * Created by abhijit on 3/19/2017.
 */

public class TrainGenRouteModel  {

        private String scharr;

        private String distance;

        private String no;

        private String route;

        private String state;

        private String schdep;

        private String lng;

        private String day;

        private String code;

        private String halt;

        private String fullname;

        private String lat;

        public String getScharr ()
        {
            return scharr;
        }

        public void setScharr (String scharr)
        {
            this.scharr = scharr;
        }

        public String getDistance ()
        {
            return distance;
        }

        public void setDistance (String distance)
        {
            this.distance = distance;
        }

        public String getNo ()
        {
            return no;
        }

        public void setNo (String no)
        {
            this.no = no;
        }

        public String getRoute ()
        {
            return route;
        }

        public void setRoute (String route)
        {
            this.route = route;
        }

        public String getState ()
        {
            return state;
        }

        public void setState (String state)
        {
            this.state = state;
        }

        public String getSchdep ()
        {
            return schdep;
        }

        public void setSchdep (String schdep)
        {
            this.schdep = schdep;
        }

        public String getLng ()
        {
            return lng;
        }

        public void setLng (String lng)
        {
            this.lng = lng;
        }

        public String getDay ()
        {
            return day;
        }

        public void setDay (String day)
        {
            this.day = day;
        }

        public String getCode ()
        {
            return code;
        }

        public void setCode (String code)
        {
            this.code = code;
        }

        public String getHalt ()
        {
            return halt;
        }

        public void setHalt (String halt)
        {
            this.halt = halt;
        }

        public String getFullname ()
        {
            return fullname;
        }

        public void setFullname (String fullname)
        {
            this.fullname = fullname;
        }

        public String getLat ()
        {
            return lat;
        }

        public void setLat (String lat)
        {
            this.lat = lat;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [scharr = "+scharr+", distance = "+distance+", no = "+no+", route = "+route+", state = "+state+", schdep = "+schdep+", lng = "+lng+", day = "+day+", code = "+code+", halt = "+halt+", fullname = "+fullname+", lat = "+lat+"]";
        }
    }



