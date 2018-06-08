package com.dave.android.livenews.Fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.dave.android.livenews.Activities.BusinessActivity;
import com.dave.android.livenews.ItemCallback;
import com.dave.android.livenews.Items;
import com.dave.android.livenews.R;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * created by dave on 06/25/2018.
 */
public class Business extends Fragment implements ItemCallback{

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    public List<Items> listItem = new ArrayList<>();
    DataAdapter mAdapter;
    private static final String NEWS_BODY = "general";
    private static final String NEWS_IMG = "img";
    private static final String BUNDLE_EXTRAS = "extras";

    @Override
    public void onItemClick(int p) {

        Items items = listItem.get(p);

        Bundle bundle = new Bundle();
        bundle.putString(NEWS_BODY, items.getNewsTitle());
        bundle.putString(NEWS_IMG, items.getImageUrl());

        Intent intent = new Intent(getActivity(), BusinessActivity.class);
        intent.putExtra(BUNDLE_EXTRAS, bundle);
        startActivity(intent);
    }


    private class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataHolder> {

        private List<Items> mItems;
        ItemCallback mItemCallback;

        public void setItemCallback(final ItemCallback itemCallback) {
            this.mItemCallback = itemCallback;
        }

        public DataAdapter(List<Items> item) {
            mItems = item;
        }

        @Override
        public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.custom_view, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataHolder holder, int position) {
            Items items = mItems.get(position);
            holder.titleText.setText(items.getNewsTitle());
            holder.publishedAt.setText(items.getDatePublished());
            holder.time.setText(items.getTime());
            Glide
                    .with(getActivity())
                    .load(items.getImageUrl())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.gif.setVisibility(View.GONE);
                            holder.imagePhoto.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .centerCrop()
                    .crossFade()
                    .into(holder.imagePhoto);


        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }


        public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView titleText;
            private TextView publishedAt;
            private ImageView imagePhoto;
            private TextView time;
            private GifImageView gif;


            public DataHolder(View view) {
                super(view);
                titleText = (TextView)view.findViewById(R.id.title);
                imagePhoto = (ImageView)view.findViewById(R.id.photo);
                publishedAt = (TextView)view.findViewById(R.id.date);
                gif = (GifImageView)view.findViewById(R.id.gif);
                time = (TextView)view.findViewById(R.id.time);
                view.setOnClickListener(this);


            }

            @Override
            public void onClick(View v) {
                mItemCallback.onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        View view = inflater1.inflate(R.layout.business, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("business");

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        populateList();
        setupAdapter();
        mAdapter.setItemCallback(this);


        return view;

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true);


    }

    @Override
    public void onResume(){
        super.onResume();
        setupAdapter();
    }

    private void populateList() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    for (DataSnapshot var : data.getChildren()) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) var.getValue();
                        Items items = new Items();
                        String title = (String) hashMap.get("title");
                        items.setNewsTitle(title);

                        String description = (String) hashMap.get("description");
                        items.setNewsBody(description);

                        String dateTime = (String) hashMap.get("publishedAt");
                        if(dateTime != null) {
                            String date = dateTime.substring(0, 10);
                            String time = dateTime.substring(11, 16);
                            items.setDatePublished(date);
                            items.setTime(time);
                        }

                        String newsUrl = (String) hashMap.get("url");
                        items.setUrl(newsUrl);

                        String imageUrl = (String)hashMap.get("urlToImage");
                        items.setImageUrl(imageUrl);

                        listItem.add(items);

                        setupAdapter();
                    }
                }
//                Snackbar.make(getActivity().findViewById(android.R.id.content), "News Updated", Snackbar.LENGTH_SHORT);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    private void setupAdapter() {

        if (mAdapter==null) {
            mAdapter = new DataAdapter(getList());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }


    }

    private List<Items> getList() {
        return listItem;
    }

}
