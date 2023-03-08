package com.example.muzilla;

import java.util.ArrayList;

public class Track
{
    private String Id;
    private String ImgUrl;
    private String Name;
    private String Musician;
    private Integer Duration;
    private String MusicUrl;
    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();

    public void setId(String id) {Id = id;}
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
    public void setMusicUrl(String musicUrl) {MusicUrl = musicUrl;}
    public void addListener(Runnable r) {runnables.add(r);}

    public String getId(){return Id;}
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
    public String getMusicUrl(){return MusicUrl;}
    public void removeListener(Runnable r)
    {
        runnables.remove(r);
    }

    public Track(String id, String url,String name,String musician,Integer duration)
    {
        Id = id;
        ImgUrl = url;
        Name = name;
        Musician = musician;
        Duration = duration;
    }

    public void Loaded()
    {
        for (Runnable r :runnables)
        {
            r.run();
        }
    }
}
