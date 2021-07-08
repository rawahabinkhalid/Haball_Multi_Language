package com.haball.testWhatsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.payments.MyStringRequest;
import com.haball.MyWebView;
import com.haball.ProcessingError;
import com.haball.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pay_By_Make_Payment {

    public void payByMakePaymentsDistributor(final Context context, String PSID, double Amount, final Fragment PaidFragment, final Fragment UnpaidFragment) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4013/api/payaxis/DapiAuthenticateCall";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Amount", Amount);
        jsonObject.put("PSID", PSID);
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4013/#/prepaidrequest");
        jsonObject.put("key", "");

        final Context finalcontext = context;
        MyStringRequest request = new MyStringRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                MyWebView webview = new MyWebView();
                webview.URL = response;
                webview.ContainerId = R.id.main_container;
                webview.ReturnURL = "https://175.107.203.97:4013/#/prepaidrequest";
                webview.PaidFragment = PaidFragment;
                webview.UnpaidFragment = UnpaidFragment;
                Intent login_intent = new Intent(context, webview.getClass());
                context.startActivity(login_intent);
//                String URL = response;
//                URL = URL.replaceAll("\\\"", "");
////        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
////        URL = "https://www.google.com/";
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
//                context.startActivity(browserIntent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new ProcessingError().showError(context);
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }


    public void payByMakePaymentsRetailer(final Context context, String PSID, double Amount, final Fragment PaidFragment, final Fragment UnpaidFragment) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4014/api/payaxis/DapiAuthenticateCall";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Amount", Amount);
        jsonObject.put("PSID", PSID);
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4014/#/user/dashboard");
        jsonObject.put("key", "");

        final Context finalcontext = context;
        MyStringRequest request = new MyStringRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                MyWebView webview = new MyWebView();
                webview.URL = response;
                webview.ContainerId = R.id.main_container_ret;
                webview.ReturnURL = "https://175.107.203.97:4014/#/user/dashboard";
                webview.PaidFragment = PaidFragment;
                webview.UnpaidFragment = UnpaidFragment;
                Intent login_intent = new Intent(context, webview.getClass());
                context.startActivity(login_intent);
//                String URL = response;
//                URL = URL.replaceAll("\\\"", "");
////        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
////        URL = "https://www.google.com/";
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
//                context.startActivity(browserIntent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new ProcessingError().showError(context);
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);

    }

}
