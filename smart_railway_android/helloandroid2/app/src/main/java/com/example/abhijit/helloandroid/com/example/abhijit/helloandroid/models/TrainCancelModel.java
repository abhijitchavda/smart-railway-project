package com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models;

/**
 * Created by abhijit on 4/3/2017.
 */

public class TrainCancelModel {
    private Dest dest;

    private Source source;

    private Train train;

    public Dest getDest ()
    {
        return dest;
    }

    public void setDest (Dest dest)
    {
        this.dest = dest;
    }

    public Source getSource ()
    {
        return source;
    }

    public void setSource (Source source)
    {
        this.source = source;
    }

    public Train getTrain ()
    {
        return train;
    }

    public void setTrain (Train train)
    {
        this.train = train;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dest = "+dest+", source = "+source+", train = "+train+"]";
    }
    public class Source
    {
        private String name;

        private String code;

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getCode ()
        {
            return code;
        }

        public void setCode (String code)
        {
            this.code = code;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [name = "+name+", code = "+code+"]";
        }
    }
    public class Dest
    {
        private String name;

        private String code;

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getCode ()
        {
            return code;
        }

        public void setCode (String code)
        {
            this.code = code;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [name = "+name+", code = "+code+"]";
        }
    }
    public final static class Train
    {
        private String name;

        private String start_time;

        private String number;

        private String type;

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public String getStart_time ()
        {
            return start_time;
        }

        public void setStart_time (String start_time)
        {
            this.start_time = start_time;
        }

        public String getNumber ()
        {
            return number;
        }

        public void setNumber (String number)
        {
            this.number = number;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [name = "+name+", start_time = "+start_time+", number = "+number+", type = "+type+"]";
        }
    }
}
