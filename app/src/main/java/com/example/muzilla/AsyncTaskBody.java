package com.example.muzilla;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AsyncTaskBody
{
    public Runnable onSuccessExecute;
    public String Response;

    public void onSuccessExecute(Runnable onSuccessExecute)
    {
        this.onSuccessExecute = onSuccessExecute;

    }
}