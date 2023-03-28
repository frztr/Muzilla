package com.example.muzilla;

import android.util.Log;

public class Playlist
{
    private String ImgUrl;
    private String Name;
    private String Musician;
    private String Owner_id;
    private String Id;

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
    public void setOwner_id(String owner_id) {Owner_id = owner_id;}
    public void setId(String id){Id = id;}

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
    public String getOwner_id() {return Owner_id;}
    public String getId(){ return Id;}

    public Playlist(String url,String name,String musician,String owner_id,String playlist_id)
    {
        ImgUrl = url;
        Name = name;
        Musician = musician;
        Owner_id = owner_id;
        Id = playlist_id;
    }
}
