package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class NewsItemRepository {
    private NewsItemDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    private static class insertAsyncTask extends AsyncTask<NewsItem, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NewsItem... params) {
            mAsyncTaskDao.insert(Arrays.asList(params));
            return null;
        }
    }
}
