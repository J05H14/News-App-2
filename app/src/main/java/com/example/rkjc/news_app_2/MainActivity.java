package com.example.rkjc.news_app_2;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> newsItems = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private NewsItemViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Called");

        mRecyclerView = (RecyclerView)findViewById(R.id.news_recyclerview);
        mAdapter = new NewsRecyclerViewAdapter(this, newsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
    }

//    class NewsQueryTask extends AsyncTask<URL, Void, String>{
//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//            Log.d(TAG, "onPreExecute: onPreExecute Called");
//        }
//
//        @Override
//        protected String doInBackground(URL... urls){
//            String results = "";
//            try{
//                results = NetworkUtils.getResponseFromHttpUrl(urls[0]);
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//            Log.d(TAG, "doInBackground: " + results);
//            return results;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            newsItems = JsonUtils.parseNews(s);
//            mAdapter.mNewsItems.addAll(newsItems);
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    private URL makeURL(){
        URL url = NetworkUtils.buildUrl();
        String urlString = url.toString();
        Log.d("TAG", "makeURL: " + urlString);
        return url;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemThatWasClickedId = item.getItemId();
        if(itemThatWasClickedId == R.id.action_search) {
            URL url = makeURL();
            mViewModel.mRepository.sync(url);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

}
