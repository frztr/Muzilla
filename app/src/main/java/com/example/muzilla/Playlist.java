package com.example.muzilla;

public class Playlist
{
    private String ImgUrl;
    private String Name;
    private String Musician;

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

    public Playlist(String url,String name,String musician)
    {
        ImgUrl = url;
        Name = name;
        Musician = musician;
    }
}
