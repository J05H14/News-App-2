package com.example.rkjc.news_app_2;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    class NewsQueryTask extends AsyncTask<URL, void, String>{

        @Override
        protected String doInBackground(URL... urls){
            String results = "";
            try{
                results = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            }catch(IOException e){
                e.printStackTrace();
            }
            return results;
        }
    }

    public boolean onCreateMenu(Menu menu){
        getMenuInflater().inflate(R.menu.get_news, menu);
        return true;
    }

}
