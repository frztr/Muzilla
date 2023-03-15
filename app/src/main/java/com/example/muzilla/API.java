package com.example.muzilla;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class API
{
    public String Token;
    public API(String token)
    {
        Token = token;
    }

    public void getMyAudio(ArrayListExtended<Track> tracks,  @Nullable int offset)
    {
        try {
            AsyncTaskBody atb = new AsyncTaskBody();
            atb.onSuccessExecute(() ->
            {
                JSONObject response = null;
                try {
                    response = new JSONObject(atb.Response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONArray tracklist = null;
                try {
                    tracklist = ((JSONArray) ((JSONObject) response.get("response")).getJSONArray("items"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < tracklist.length(); i++) {
                    try {
                        String track = "";
                        if (tracklist.getJSONObject(i).has("album")) {
                            track = tracklist.getJSONObject(i).getJSONObject("album").getJSONObject("thumb").getString("photo_300");
                        }
                        tracks.add(new Track(tracklist.getJSONObject(i).getJSONObject("ads").getString("content_id"),track, tracklist.getJSONObject(i).get("title").toString(), tracklist.getJSONObject(i).get("artist").toString(), ((Integer) tracklist.getJSONObject(i).get("duration"))));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                tracks.Update();
            });
            HttpQuery httpQuery = new HttpQuery(atb);
            httpQuery.execute("https://api.vk.com/method/audio.get?access_token=" + Token + "&v=5.131&offset=" + offset);
        }
        catch (Exception ex)
        {

        }
    }

    public void getPopularAudio(ArrayListExtended<Track> tracks, @Nullable int offset)
    {
        try {
        AsyncTaskBody atb = new AsyncTaskBody();
        atb.onSuccessExecute(()->
        {

            JSONObject response = null;
            try {
                response = new JSONObject(atb.Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray tracklist = null;
            try {
                tracklist = response.getJSONArray("response");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0;i<tracklist.length();i++)
            {
                try {
                    String track = "";
                    if(tracklist.getJSONObject(i).has("album"))
                    {
                        track = tracklist.getJSONObject(i).getJSONObject("album").getJSONObject("thumb").getString("photo_300");
                    }
                    tracks.add(new Track(tracklist.getJSONObject(i).getJSONObject("ads").getString("content_id"),track, tracklist.getJSONObject(i).get("title").toString(), tracklist.getJSONObject(i).get("artist").toString(), ((Integer) tracklist.getJSONObject(i).get("duration"))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            tracks.Update();
        });
        HttpQuery httpQuery = new HttpQuery(atb);
        httpQuery.execute("https://api.vk.com/method/audio.getPopular?access_token="+Token+"&v=5.131&offset="+offset);
        }
        catch (Exception ex)
        {

        }
    }

    public void getMyRecommendations(ArrayListExtended<Track> tracks,@Nullable int offset)
    {
        try{
        AsyncTaskBody atb = new AsyncTaskBody();
        atb.onSuccessExecute(()->
        {
            JSONObject response = null;
            try {
                response = new JSONObject(atb.Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray tracklist = null;
            try {
                tracklist = ((JSONArray)((JSONObject) response.get("response")).getJSONArray("items"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0;i<tracklist.length();i++)
            {
                try {
                    String track = "";
                    if(tracklist.getJSONObject(i).has("album"))
                    {
                        track = tracklist.getJSONObject(i).getJSONObject("album").getJSONObject("thumb").getString("photo_300");
                    }
                    tracks.add(new Track(tracklist.getJSONObject(i).getJSONObject("ads").getString("content_id"),track, tracklist.getJSONObject(i).get("title").toString(), tracklist.getJSONObject(i).get("artist").toString(), ((Integer) tracklist.getJSONObject(i).get("duration"))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            tracks.Update();
        });
        HttpQuery httpQuery = new HttpQuery(atb);
        httpQuery.execute("https://api.vk.com/method/audio.getRecommendations?access_token="+Token+"&v=5.131&offset="+offset);
        }
        catch (Exception ex)
        {

        }
    }

    public void getMyProfile(SharedPreferences sp)
    {
        try{
        AsyncTaskBody atb = new AsyncTaskBody();
        atb.onSuccessExecute(()->
        {
            JSONObject response = null;
            try {
                response = new JSONObject(atb.Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                sp.edit().putString("profileId",((JSONObject) response.get("response")).get("id").toString()).apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }




        });
        HttpQuery httpQuery = new HttpQuery(atb);
        httpQuery.execute("https://api.vk.com/method/account.getProfileInfo?access_token="+Token+"&v=5.131");
        }
        catch (Exception ex)
        {

        }
    }

    public void getMyPlaylists(ArrayListExtended<Playlist> playlists_list, Integer owner_id,@Nullable int offset)
    {
        try{
        AsyncTaskBody atb = new AsyncTaskBody();
        atb.onSuccessExecute(()->
        {
            JSONObject response = null;
            try {
                response = new JSONObject(atb.Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray list = null;
            JSONArray profiles = null;
            JSONArray groups = null;
            try {
                list = ((JSONArray)((JSONObject) response.get("response")).getJSONArray("items"));
                profiles = ((JSONArray)((JSONObject) response.get("response")).getJSONArray("profiles"));
                groups = ((JSONArray)((JSONObject) response.get("response")).getJSONArray("groups"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0;i<list.length();i++)
            {
                try {
                    String playlist = "";
                    if(list.getJSONObject(i).has("photo"))
                    {
                        playlist = list.getJSONObject(i).getJSONObject("photo").getString("photo_300");
                    }

                    String owner = "";
                    if(!list.getJSONObject(i).has("main_artists"))
                    {
                        int playlist_owner_id = list.getJSONObject(i).getJSONObject("original").getInt("owner_id");

                        if (playlist_owner_id > 0) {
                            for (int j = 0; j < profiles.length(); j++) {
                                if (profiles.getJSONObject(j).getInt("id") == playlist_owner_id) {
                                    owner = profiles.getJSONObject(j).getString("first_name") + " " + profiles.getJSONObject(j).getString("last_name");
                                }
                            }
                        } else {
                            for (int j = 0; j < groups.length(); j++) {
                                if (groups.getJSONObject(j).getInt("id") == Math.abs(playlist_owner_id)) {
                                    owner = groups.getJSONObject(j).getString("name");
                                }
                            }
                        }
                    }
                    else
                    {

                       JSONArray artists = list.getJSONObject(i).getJSONArray("main_artists");
                       for(int j = 0;j<artists.length();j++)
                       {
                         owner += artists.getJSONObject(j).getString("name") +", ";
                       }
                       owner = owner.substring(0,owner.length()-2);
                    }
                    playlists_list.add(new Playlist(playlist, list.getJSONObject(i).get("title").toString(), owner));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            playlists_list.Update();
        });
        HttpQuery httpQuery = new HttpQuery(atb);
        httpQuery.execute("https://api.vk.com/method/audio.getPlaylists?access_token="+Token+"&v=5.131&owner_id="+owner_id+"&extended=1&count=20&offset="+offset);
        }
        catch (Exception ex)
        {

        }
    }

    public void searchAudio(ArrayListExtended<Track> tracks, String query,@Nullable int offset)
    {
        try{
        AsyncTaskBody atb = new AsyncTaskBody();
        atb.onSuccessExecute(()->
        {

            JSONObject response = null;
            try {
                response = new JSONObject(atb.Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray tracklist = null;
            try {
                tracklist = response.getJSONObject("response").getJSONArray("items");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0;i<tracklist.length();i++)
            {
                try {
                    String track = "";
                    if(tracklist.getJSONObject(i).has("album"))
                    {
                        track = tracklist.getJSONObject(i).getJSONObject("album").getJSONObject("thumb").getString("photo_300");
                    }
                    tracks.add(new Track(tracklist.getJSONObject(i).getJSONObject("ads").getString("content_id"),track, tracklist.getJSONObject(i).get("title").toString(), tracklist.getJSONObject(i).get("artist").toString(), ((Integer) tracklist.getJSONObject(i).get("duration"))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            tracks.Update();

        });
        HttpQuery httpQuery = new HttpQuery(atb);
        httpQuery.execute("https://api.vk.com/method/audio.search?access_token="+Token+"&v=5.131&q="+query+"&offset="+offset);
        }
        catch (Exception ex)
        {

        }
    }
    public void getTrackById(Track track)
    {

        try{
            AsyncTaskBody atb = new AsyncTaskBody();
            atb.onSuccessExecute(()->
            {
                JSONObject response = null;
                try {
                    response = new JSONObject(atb.Response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    track.setMusicUrl(response.getJSONArray("response").getJSONObject(0).getString("url"));
                    track.Loaded();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            HttpQuery httpQuery = new HttpQuery(atb);
            httpQuery.execute("https://api.vk.com/method/audio.getById?access_token="+Token+"&v=5.131&audios="+track.getId());

        }
        catch (Exception ex)
        {

        }
    }
}





