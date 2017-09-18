package com.liuxd.firstblood.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.liuxd.firstblood.R;
import com.liuxd.firstblood.constant.Constant;
import com.liuxd.firstblood.ui.base.BaseActivity;
import com.liuxd.firstblood.util.ImageLoadUtil;

import butterknife.BindView;

import static com.liuxd.firstblood.R.id.toolbar;

/**
 * Created by Liuxd on 2016/11/29 10:50.
 */

public class NewsDetailsActivity extends BaseActivity {
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.progressBar_news)
    ProgressBar mProgressBarNews;
    @BindView(R.id.wv_news)
    WebView mWvNews;
    @BindView(R.id.iv_news)
    ImageView mIvNews;

    @Override
    public int setLayoutId() {
        return R.layout.activity_newsdetails;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        String mUrl = getIntent().getStringExtra(Constant.BundleName.URL_NEWS);
        mIvNews.setTransitionName(Constant.Parameters.TRANSITION_NEWS_IMG);
        ImageLoadUtil.getInstance().load(this, getIntent().getStringExtra(Constant.BundleName.URL_IMAGE_NEWS), mIvNews);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarLayout.setTitle(getIntent().getStringExtra(Constant.BundleName.TITLE_NEWS));
        WebSettings settings = mWvNews.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWvNews.setWebChromeClient(new MyWebChromeClient());
        mWvNews.setWebViewClient(new MyWebViewClient());

        mWvNews.loadUrl(mUrl);
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mProgressBarNews != null) {
                mProgressBarNews.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
                mProgressBarNews.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mToolbarLayout.setTitle(title);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl() != null) view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWvNews.canGoBack()) {
                        mWvNews.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWvNews != null)
            mWvNews.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWvNews != null)
            mWvNews.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWvNews != null)
            mWvNews.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mWvNews.canGoBack()) {
                mWvNews.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
