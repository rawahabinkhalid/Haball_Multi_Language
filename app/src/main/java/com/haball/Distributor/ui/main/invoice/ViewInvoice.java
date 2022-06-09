package com.haball.Distributor.ui.main.invoice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.Distributor.ui.main.invoice.ui.main.SectionsPagerAdapter_WithoutOrdersData;
import com.haball.Distributor.ui.main.ui.main.SectionsPagerAdapter_WithoutPayments;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;
import com.haball.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.haball.Distributor.ui.main.invoice.ui.main.SectionsPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewInvoice extends Fragment {
    private String InvoiceStatus;
    Loader loader;
    private String URL_Order_Data = "https://uatdistributor.haball.pk/api/Invoices/";
    String Token;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_view_order, container, false);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
//        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
//        tabs.setupWithViewPager(viewPager);
//        View root = inflater.inflate(R.layout.activity_retailer_main_view_order, container, false);
//        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("OrderId",
//                Context.MODE_PRIVATE);
//        InvoiceStatus = sharedPreferences3.getString("InvoiceStatus", "");
//        String OrderStatus = sharedPreferences3.getString("Status", "");
//        String InvoiceUpload = sharedPreferences3.getString("InvoiceUpload", "");
//        // Log.i("InvoiceStatus", InvoiceStatus);
//        // Log.i("OrderStatus", OrderStatus);
//
////        SectionsPagerAdapter sectionsPagerAdapter = null;
//        if (InvoiceStatus.equals("null") || InvoiceStatus.equals("Pending") || OrderStatus.equals("Pending") || ((InvoiceUpload.equals("0")) && OrderStatus.equals("Cancelled"))) {
//            SectionsPagerAdapter_WithoutPayments sectionsPagerAdapter = new SectionsPagerAdapter_WithoutPayments(getActivity(), getChildFragmentManager());
//            final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
//            viewPager.setOffscreenPageLimit(3);
//            viewPager.setAdapter(sectionsPagerAdapter);
//            TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
//            tabs.setupWithViewPager(viewPager);
//        } else {
        loader = new Loader(getContext());
        loader.showLoader();

        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("PaymentId",
                Context.MODE_PRIVATE);


        String orderID = sharedPreferences3.getString("PaymentId", "");
        String orderStatus = sharedPreferences3.getString("Status", "");
        // Log.i("PaymentId", orderID);
        // Log.i("orderStatus", orderStatus);
        if (!URL_Order_Data.contains(orderID)) {
            URL_Order_Data = URL_Order_Data + orderID;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                Log.i("Order Data response", String.valueOf(response));
                try {
                    if (String.valueOf(response.get("InvoiceDetails")).equals("[]")) {
                        SectionsPagerAdapter_WithoutOrdersData sectionsPagerAdapter = new SectionsPagerAdapter_WithoutOrdersData(getActivity(), getChildFragmentManager());
                        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.setAdapter(sectionsPagerAdapter);
                        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
                        tabs.setupWithViewPager(viewPager);
                    } else {
                        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
                        final ViewPager viewPager = root.findViewById(R.id.view_pager_ret_view_order);
                        viewPager.setOffscreenPageLimit(3);
                        viewPager.setAdapter(sectionsPagerAdapter);
                        TabLayout tabs = root.findViewById(R.id.tabs_ret_view_order);
                        tabs.setupWithViewPager(viewPager);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new HaballError().printErrorMessage(getContext(), error);
                        new ProcessingError().showError(getContext());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(stringRequest);

//        }

        return root;
    }
}