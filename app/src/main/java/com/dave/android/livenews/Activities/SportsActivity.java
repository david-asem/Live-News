package com.dave.android.livenews.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import com.bumptech.glide.request.target.Target;
import com.dave.android.livenews.R;

import org.w3c.dom.Text;


/**
 * created by dave on 06/25/2018.
 */
public class SportsActivity extends AppCompatActivity {

    private static final String NEWS_BODY = "body";
    private static final String BUNDLE_EXTRAS = "extras";
    private static final String NEWS_IMG = "img";
    private static final String NEWS_URL = "url";
    private static final String NEWS_TITLE = "title";
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mTitle;
    private  Bundle extras;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_sports);

        extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        mImageView = (ImageView) findViewById(R.id.news_image);
        mTextView = (TextView)findViewById(R.id.body);
        mTitle = (TextView)findViewById(R.id.news_title);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
       // mCollapsingToolbarLayout.setTitle(extras.getString(NEWS_TITLE));

        setSupportActionBar(mToolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Glide
            .with(this)
            .load(extras.getString(NEWS_IMG))
            .listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    mTitle.setText(extras.getString(NEWS_TITLE));
                    mTextView.setText(extras.getString(NEWS_BODY));
                    return false;
                }
            })
            .centerCrop()
            .crossFade()
            .into(mImageView);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.share:
                Intent data = new Intent(Intent.ACTION_SEND);
                data.setType("text/plain");
                data.putExtra(Intent.EXTRA_TEXT, extras.getString(NEWS_URL));
                data.putExtra(Intent.EXTRA_SUBJECT, extras.getString(NEWS_TITLE));
                Intent openWith = Intent.createChooser(data, "Share via");
                startActivity(openWith);
                return true;
            case R.id.browser:
                Intent browser = new Intent(Intent.ACTION_VIEW);
                browser.setData(Uri.parse(extras.getString(NEWS_URL)));
                Intent chooser = Intent.createChooser(browser, "Open with");
                startActivity(chooser);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}



