package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {

    Context mContext;
    List<NewsItem> mNewsItems;
    private static final String TAG = "NewsRecyclerViewAdapter";

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> newsItems){
        this.mContext = context;
        this.mNewsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent,shouldAttachToParentImmediately);
        NewsViewHolder newsHolder = new NewsViewHolder(view);

        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView date;
        ImageView img;

        public NewsViewHolder(View itemView){
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            date = (TextView) itemView.findViewById(R.id.date);
            img = (ImageView) itemView.findViewById(R.id.img);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex){
            final String TITLE = mNewsItems.get(listIndex).getTitle();
            final String DESC = mNewsItems.get(listIndex).getDescription();
            final String DATE = mNewsItems.get(listIndex).getPublishedAt();
            String imgURL = mNewsItems.get(listIndex).getUrlToImage();

            title.setText(TITLE);
            description.setText(DESC);
            date.setText(DATE);

            Log.d(TAG, "bind: "+ imgURL);
            if(imgURL != null){
                Picasso.get().load(imgURL).into(img);
            }


        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: LINK CLICKED");
            String urlString = mNewsItems.get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            mContext.startActivity(intent);
        }
    }
}

