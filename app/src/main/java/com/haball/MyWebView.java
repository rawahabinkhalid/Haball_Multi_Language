package com.haball;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.RetailerPayment.RetailerViewInvoice;

public class MyWebView extends Fragment {
    public static String URL = "";
    public static int ContainerId = -1;
    public static String ReturnURL = "";
    public static Fragment PaidFragment;
    public static Fragment UnpaidFragment;
    Loader loader;
    private Context context;

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.webview, container, false);

        Log.i("url_debug", URL);

        context = getContext();

        final WebView webView = root.findViewById(R.id.webView1);
        loader = new Loader(context);

        // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
        WebStorage.getInstance().deleteAllData();

        // Clear all the cookies
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }

        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearSslPreferences();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setDomStorageEnabled(true);

        URL = URL.replaceAll("\\\"", "");
////        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
////        URL = "https://www.google.com/";
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
//        startActivity(browserIntent);
////        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {
            //        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // TODO Auto-generated method stub
//            view.loadUrl(url);
//            return true;
//        }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                view.setVisibility(View.GONE);
                loader.showLoader();
                Log.i("url_debug", url);
                if (url.contains(ReturnURL)) {
////                    new CustomToast().showToast(MyWebView.this, getResources().getString(R.string.invalid_password_error));
//                    Intent login_intent = new Intent(MyWebView.this, RetailorDashboard.class);
//                    startActivity(login_intent);
//                    finish();
                    Fragment tempFragment = (url.contains("error=true")) ? UnpaidFragment : PaidFragment;
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(ContainerId, tempFragment).addToBackStack("tag");
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:$(document).ajaxStart(function (event, request, settings) { " +
                        "ajaxHandler.ajaxBegin(); " + // Event called when an AJAX call begins
                        "});");
                loader.hideLoader();
                view.setVisibility(View.VISIBLE);
                view.loadUrl("javascript:$(document).ajaxComplete(function (event, request, settings) { " +
                        "ajaxHandler.ajaxDone(); " + // Event called when an AJAX call ends
                        "});");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.e("url_debug_Error", description);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);

        settings.setAllowContentAccess(true);
        settings.setDomStorageEnabled(true);

//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(URL);

        return root;
    }
}

