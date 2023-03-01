package com.example.muzilla;

public class Track
{
    private String ImgUrl;
    private String Name;
    private String Musician;
    private Integer Duration;

    public void setImgUrl(String url)
    {
        ImgUrl = url;
    }
    public void setName(String name)
    {
        Name = name;
    }
    public void setMusician(String musician)
    {
        Musician = musician;
    }
    public void setDuration(Integer duration){Duration = duration;}


    public String getImgUrl()
    {
        return ImgUrl;
    }
    public String getName()
    {
        return Name;
    }
    public String getMusician()
    {
        return Musician;
    }
    public Integer getDuration(){return Duration;}

    public Track(String url,String name,String musician,Integer duration)
    {
        ImgUrl = url;
        Name = name;
        Musician = musician;
        Duration = duration;
    }
}
