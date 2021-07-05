package com.haball;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyWebView extends AppCompatActivity {
    public static String URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);


        URL = URL.replaceAll("\\\"", "");
        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
//        URL = "https://www.google.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browserIntent);
    }

}