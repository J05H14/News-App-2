package com.example.rkjc.news_app_2;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    //BASE_URL and QUERY_PARAMETER constants
    final static String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=9a4e1c9d357a47b9afe4bbe54e2dfe2f";

    //Builds appropreiate URL based on URI Builder
    public static URL buildUrl(){
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput){
                return scanner.next();
            }
            else{
                return null;
            }
        } finally{
            urlConnection.disconnect();
        }
    }

}
