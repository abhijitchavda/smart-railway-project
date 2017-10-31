package com.example.abhijit.helloandroid.com.example.abhijit.helloandroid.models;

/**
 * Created by abhijit on 3/18/2017.
 */

public class TrainGenModel  {
    private String scharr;

    private String actarr;

    private String name;

    private String schdep;

    private String actdep;

    private String number;

    private String delayarr;

    private String delaydep;

    public String getScharr ()
    {
        return scharr;
    }

    public void setScharr (String scharr)
    {
        this.scharr = scharr;
    }

    public String getActarr ()
    {
        return actarr;
    }

    public void setActarr (String actarr)
    {
        this.actarr = actarr;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSchdep ()
    {
        return schdep;
    }

    public void setSchdep (String schdep)
    {
        this.schdep = schdep;
    }

    public String getActdep ()
    {
        return actdep;
    }

    public void setActdep (String actdep)
    {
        this.actdep = actdep;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getDelayarr ()
    {
        return delayarr;
    }

    public void setDelayarr (String delayarr)
    {
        this.delayarr = delayarr;
    }

    public String getDelaydep ()
    {
        return delaydep;
    }

    public void setDelaydep (String delaydep)
    {
        this.delaydep = delaydep;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [scharr = "+scharr+", actarr = "+actarr+", name = "+name+", schdep = "+schdep+", actdep = "+actdep+", number = "+number+", delayarr = "+delayarr+", delaydep = "+delaydep+"]";
    }
}
