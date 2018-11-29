package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.net.URL;
import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {



    protected NewsItemRepository mRepository;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel (Application application) {
        super(application);
        mRepository = new NewsItemRepository(application);
        mAllNewsItems = mRepository.getAllNewsItems();
    }

    public void syncDB(Context context){
        mRepository.sync(context);
    }

    public LiveData<List<NewsItem>> getmAllNewsItems() {
        return mAllNewsItems;
    }

    public NewsItemRepository getmRepository() {
        return mRepository;
    }

    public void setmRepository(NewsItemRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void setmAllNewsItems(LiveData<List<NewsItem>> mAllNewsItems) {
        this.mAllNewsItems = mAllNewsItems;
    }
}