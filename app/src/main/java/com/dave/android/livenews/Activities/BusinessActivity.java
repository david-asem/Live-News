package com.dave.android.livenews.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dave.android.livenews.R;

public class BusinessActivity extends AppCompatActivity {

    private static final String NEWS_BODY = "general";
    private static final String NEWS_IMG = "img";
    private static final String BUNDLE_EXTRAS = "extras";
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        mImageView = (ImageView)findViewById(R.id.news_image);
        mTextView = (TextView)findViewById(R.id.news_title);

        Bundle bundle = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        mTextView.setText(bundle.getString(NEWS_BODY));

        Glide
                .with(this)
                .load(bundle.getString(NEWS_IMG))
                .centerCrop()
                .centerCrop()
                .placeholder(R.drawable.spin)
                .into(mImageView);


    }
}
