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
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dave.android.livenews.PageAdapter;
import com.dave.android.livenews.R;
import com.victor.loading.book.BookLoading;

import org.w3c.dom.Text;

public class GeneralActivity extends AppCompatActivity {

    private static final String NEWS_BODY = "body";
    private static final String NEWS_URL = "url";
    private static final String BUNDLE_EXTRAS = "extras";
    private static final String NEWS_IMG = "img";
    private static final String NEWS_TITLE = "title";

    private ImageView mImageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private TextView body;
    private TextView title;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);
        mImageView = (ImageView)findViewById(R.id.news_image);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        body = (TextView)findViewById(R.id.body);
        title = (TextView)findViewById(R.id.news_title);

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
                    title.setText(extras.getString(NEWS_TITLE));
                    body.setText(extras.getString(NEWS_BODY));
                    return false;
                }
            })  .centerCrop()
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
