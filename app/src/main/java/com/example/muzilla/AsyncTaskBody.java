package com.example.muzilla;

public class AsyncTaskBody
{
    public Runnable onSuccessExecute;
    public String Response;

    public void onSuccessExecute(Runnable onSuccessExecute)
    {
        this.onSuccessExecute = onSuccessExecute;
    }
}