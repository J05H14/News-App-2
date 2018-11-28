package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepository {

    private NewsRoomDatabase db;
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application){
        db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsDao = db.newsDao();
        get();
        mAllNewsItems = mNewsDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void get () {
        new getDataAsyncTask(mNewsDao).execute();
    }

    public void sync (Context context){
        URL url = NetworkUtils.buildUrl();
        new syncDataAsyncTask(db).execute(url);
    }

    private static class getDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        getDataAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mAsyncTaskDao.loadAllNewsItems();
            return null;
        }
    }
    protected static class syncDataAsyncTask extends AsyncTask<URL, Void, String>{
        private final NewsItemDao mDao;
        private List<NewsItem> mNewsItems = new ArrayList<NewsItem>();
        syncDataAsyncTask(NewsRoomDatabase db){
            mDao = db.newsDao();
        }

        @Override
        protected String doInBackground(final URL... urls) {
            mDao.clearAll();
            String results = "";
            try{
                results = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            }catch(IOException e){
                e.printStackTrace();
            }
            mNewsItems = JsonUtils.parseNews(results);
            mDao.insert(mNewsItems);
            return results;
        }

    }
}
