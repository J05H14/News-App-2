package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class NewsItemRepository {
    protected List<NewsItem> newsItems;


    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemRepository(Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsDao = db.newsDao();
        mAllNewsItems = mNewsDao.loadAllNewsItems();
    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }

    public void insert (NewsItem item) {
        new getDataAsyncTask(mNewsDao).execute(item);
    }

    private static class getDataAsyncTask extends AsyncTask<NewsItem, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        getDataAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsItem... params) {
            mAsyncTaskDao.insert(Arrays.asList(params));
            return null;
        }
    }
    private class syncDataAsyncTask extends AsyncTask<URL, Void, String>{
        private final NewsItemDao mDao;
        syncDataAsyncTask(NewsRoomDatabase db){
            mDao = db.newsDao();
        }

        @Override
        protected String doInBackground(final URL... urls) {
            String results = "";
            try{
                results = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            }catch(IOException e){
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            newsItems = JsonUtils.parseNews(s);
            mDao.insert(newsItems);
        }
    }
}
