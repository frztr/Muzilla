package com.example.muzilla;

import android.util.Log;

import java.util.ArrayList;

public class Track
{
    private String Id;
    private String ContentId;
    private String ImgUrl;
    private String Name;
    private String Musician;
    private Integer Duration;
    private String MusicUrl;
    private ArrayList<Runnable> runnables = new ArrayList<Runnable>();
    private Integer Owner_id;

    private String Local_id;
    private Integer Local_owner;

    public void setLocal_id(String local_id)
    {
        Local_id = local_id;
    }

    public void setLocal_owner(Integer local_owner)
    {
        Local_owner = local_owner;
    }

    public Integer getLocal_owner()
    {
        return Local_owner;
    }

    public String getLocal_id()
    {
        return  Local_id;
    }

    public void setId(String id)
    {
        Id = id;
    }
    public void setContentId(String contentId) {ContentId = contentId;}
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
    public void setOwner_id(Integer owner_id)
    {
        Owner_id = owner_id;
    }
    public void addListener(Runnable r) {runnables.add(r);}

    public String getId(){return Id;}
    public String getContentId(){return ContentId;}
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
    public Integer getOwner_id(){return Owner_id;}

    public Track(String id,String contentId, String url,String name,String musician,Integer duration,Integer owner_id)
    {
        Id = id;
        ContentId = contentId;
        ImgUrl = url;
        Name = name;
        Musician = musician;
        Duration = duration;
        Owner_id = owner_id;
    }

    public void Loaded()
    {
        for (Runnable r :runnables)
        {
            r.run();
        }
    }
}
