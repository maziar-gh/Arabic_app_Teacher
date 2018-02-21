package com.arabic_app_teacher.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arabic_app_teacher.R;

import im.delight.android.webview.AdvancedWebView;

public class PaymentActivity extends Activity implements AdvancedWebView.Listener {

    private AdvancedWebView mWebView;
    private Button btnDarsname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        String url = "http://www.google.com";

        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.loadUrl(url);


        btnDarsname = (Button)findViewById(R.id.btn_darsname);
        btnDarsname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentActivity.this , MainActivity.class);
                startActivity(i);
            }
        });
    }


    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) {
            return;
        }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }

}
