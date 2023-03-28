package com.example.muzilla;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpQuery extends AsyncTask<String,Void,String> {

    public AsyncTaskBody atb;

    public HttpQuery(@Nullable AsyncTaskBody asyncTaskBody)
    {
        atb = asyncTaskBody;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            connection.setDoOutput(true);
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            try {
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader rd = null;
            try {
                rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = "", line = "";
            while (true) {
                try {
                    if (!((line = rd.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content += line + "\n";
            }

            return content;
        }
        catch(Exception ex)
        {
            return  null;
        }
    }

    protected void onPostExecute(String result) {
       // Log.e("Console", result + "" );
        atb.Response = result;
        atb.onSuccessExecute.run();
    }
}
