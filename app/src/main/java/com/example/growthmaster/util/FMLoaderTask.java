package com.example.growthmaster.util;

import android.os.AsyncTask;
import android.webkit.DownloadListener;

public class FMLoaderTask extends AsyncTask<String, Integer, Integer> {

    private DownloadListener listener;

    public FMLoaderTask() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        return null;
    }
}
