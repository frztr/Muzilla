package com.example.muzilla;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArrayListExtended<T>
{
    public ArrayList<T> arrayList = new ArrayList<T>();

    private ArrayList<Runnable> Listeners = new ArrayList<Runnable>();

    public void add(T item)
    {
        arrayList.add(item);
    }

    public void addListener(Runnable r)
    {
        Listeners.add(r);
    }

    public void removeListener(Runnable r)
    {
        Listeners.remove(r);
    }

    public void Update()
    {
        for (Runnable r:Listeners) {
            r.run();
        }
    }
}
