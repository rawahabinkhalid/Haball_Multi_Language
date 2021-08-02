package com.haball.testWhatsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.home.HomeFragment;
import com.haball.Distributor.ui.payments.MyStringRequest;
import com.haball.MyWebView;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailor.ui.Dashboard.Dashboard_Tabs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pay_By_Make_Payment {

    public void payByMakePaymentsDistributor(final Context context, String PSID, double Amount, final Fragment PaidFragment, final Fragment UnpaidFragment, final int Type) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4013/api/payaxis/DapiAuthenticateCall";
        URL = "https://175.107.203.97:4013/api/Dapi/makepayment";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Amount", Amount);
        jsonObject.put("PSID", PSID);
        jsonObject.put("PaidReturnUrl", "https://175.107.203.97:4013/#/prepaidrequest");
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4013/#/prepaidrequest");
        jsonObject.put("Type", Type);
        jsonObject.put("key", "");

        /*
        *   NIFT	Test Account Details:

            Bank ID:	TEST Bank
            Test Accounts
            Valid Account	0002562478596921
            Invalid Account	0002156322782546
            Dormant Account	0002563292489223
            Low Balance	0002892462562155
            OTP
            Timeout OTP	888888
            Invalid OTP	654320
            OTP Expired:	242433
            Valid OTP	456980

            * NIFT test account for card based transaction

            1.	5123450000000008
            2.	4508750015741019

            * Jazz Cash: 03123456789


            EASYPAISA :-
                account no : 03400228878
                pin : 12345

            JazzCash Account details:-
                03123456789
                345678
                Successful

                03123456780
                345678
                Authentication Error
        * */

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                try {
                    MyWebView webview = new MyWebView();
                    webview.URL = response.getString("URL");
                    webview.ContainerId = R.id.main_container;
                    webview.ReturnURL = "https://175.107.203.97:4013/#/prepaidrequest";
                    webview.PaidFragment = PaidFragment;
                    webview.UnpaidFragment = UnpaidFragment;
//                Intent login_intent = new Intent(context, webview.getClass());
//                context.startActivity(login_intent);
////                String URL = response;
////                URL = URL.replaceAll("\\\"", "");
//////        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
//////        URL = "https://www.google.com/";
////                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
////                context.startActivity(browserIntent);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new MyWebView()).addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


    public void payByMakePaymentsRetailer(final Context context, String PSID, double Amount, final Fragment PaidFragment, final Fragment UnpaidFragment, final int Type) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4014/api/payaxis/DapiAuthenticateCall";
        URL = "https://175.107.203.97:4014/api/Dapi/Dapipay";
        URL = "https://175.107.203.97:4014/api/Dapi/makepayment";

        SharedPreferences login_token = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        String Online_Payments = login_token.getString("Online_Payments", "0");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Amount", Amount);
        jsonObject.put("PSID", PSID);
        jsonObject.put("PaidReturnUrl", "https://175.107.203.97:4014/#/user/dashboard");
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4014/#/user/dashboard");
        jsonObject.put("Type", Type);
        jsonObject.put("key", "");

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                try {
                    MyWebView webview = new MyWebView();
                    webview.URL = response.getString("URL");
                    webview.ContainerId = R.id.main_container_ret;
                    webview.ReturnURL = "https://175.107.203.97:4014/#/user/dashboard";
                    webview.PaidFragment = PaidFragment;
                    webview.UnpaidFragment = UnpaidFragment;
//                Intent login_intent = new Intent(context, webview.getClass());
//                context.startActivity(login_intent);
////                String URL = response;
////                URL = URL.replaceAll("\\\"", "");
//////        URL = "https://175.107.203.97:8009/#/user/payment-channels/3c688f4b688ecffb3639013061dda6e1";
//////        URL = "https://www.google.com/";
////                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
////                context.startActivity(browserIntent);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container_ret, new MyWebView()).addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


    public void toggleOnlinePayments_Retailer(final Context context, final Switch toggle_online_payment) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4014/api/Dapi/DapiAuthenticateCall";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4014/#/user/dashboard");

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                Log.i("dapi_response", response.toString());
                try {
                    if (response.has("UserType") && response.getString("UserType").equals("1")) {

                        SharedPreferences login_token = context.getSharedPreferences("LoginToken",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_token.edit();
                        editor.putString("Online_Payments", "1");
                        editor.apply();

                        toggle_online_payment.setChecked(true);
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tabs()).addToBackStack("tag");
                        fragmentTransaction.commit();
                    } else {

                        MyWebView webview = new MyWebView();
                        webview.URL = response.getString("URL") + "&FromMobile=1";
                        webview.ContainerId = R.id.main_container_ret;
                        webview.ReturnURL = "https://175.107.203.97:4014/#/user/dashboard";
                        webview.PaidFragment = new Dashboard_Tabs();
                        webview.UnpaidFragment = new Dashboard_Tabs();

                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.main_container_ret, new MyWebView()).addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } catch (JSONException e) {
                    new ProcessingError().showError(context);
                    e.printStackTrace();
                }
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

    public void toggleOnlinePayments_Distributor(final Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        final String Token = sharedPreferences.getString("Login_Token", "");
        String URL = "https://175.107.203.97:4013/api/Dapi/DapiAuthenticateCall";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ReturnUrl", "https://175.107.203.97:4013/#/prepaidrequest");

        final Context finalcontext = context;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
////                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                try {
                    MyWebView webview = new MyWebView();
                    webview.URL = response.getString("URL");
                    webview.ContainerId = R.id.main_container;
                    webview.ReturnURL = "https://175.107.203.97:4013/#/prepaidrequest";
                    webview.PaidFragment = new HomeFragment();
                    webview.UnpaidFragment = new HomeFragment();
//                    Intent login_intent = new Intent(context, webview.getClass());
//                    login_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(login_intent);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.main_container, new MyWebView()).addToBackStack(null);
                    fragmentTransaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Volley_Error", "Volley error occurred");
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
