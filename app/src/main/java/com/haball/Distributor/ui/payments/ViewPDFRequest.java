package com.haball.Distributor.ui.payments;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.DocumentsContract.Document;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.ProcessingError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class ViewPDFRequest {
    public String URL_PDF_VIEW_INVOICE = "https://uatdistributor.haball.pk/api/Invoices/printrecipt";
    public String URL_PDF_VIEW = "https://uatdistributor.haball.pk/api/prepaidrequests/printrecipt";
    public String DistributorId, Token;
    public Context mContext;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public ViewPDFRequest() {
    }

    public void viewPDF(final Context context, String paymentId) throws JSONException {
        final Loader loader = new Loader(context);
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
        // Log.i("DistributorId ", DistributorId);
        // Log.i("Token", Token);
        // Log.i("paymentId", paymentId);

//        URL_PDF_VIEW = URL_PDF_VIEW+paymentId;

        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0.1);
        map.put("ID", paymentId);
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_PDF_VIEW, new Response.Listener<String>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(String result) {
//                // Log.i("PDF VIEW..", result);
////                byte[] bytes = result.getBytes(Charset.forName("UTF-8"));
//////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//////                    try {
//////                        byte[] decodedString = Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8"));
//////                        String string = new String(decodedString);
//////                        // Log.i("PDF BYTE DECODED..", string);
//////
//////                    } catch (UnsupportedEncodingException e) {
//////                        e.printStackTrace();
//////                    }
//////
//////                }
//////                String string = new String(bytes);
//////                // Log.i("PDF BYTE ARRAY..", string);
////                saveToFile(bytes, "file.pdf");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(context).add(sr);

        loader.showLoader();
        final Context finalcontext = context;
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, URL_PDF_VIEW, map, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                // TODO handle the response
                loader.hideLoader();
                try {
                    // Log.i("responseByte", String.valueOf(response));
                    // Log.i("responseByte", String.valueOf(response.length));
                    if (response != null) {
//                        String dir = Environment.getExternalStorageDirectory() + "/Download/";
                        File dir = context.getExternalCacheDir();

                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
                        String name = dir + "/Invoice - " + timeStamp + ".pdf";
//                        ٍString filename = "Voucher - " + timeStamp + ".pdf";
                        FileOutputStream fPdf = new FileOutputStream(name);

                        fPdf.write(response);
                        fPdf.flush();
                        fPdf.close();
                        // Log.i("Download Complete", "Download complete.");
                        Toast.makeText(mContext, "File saved in Downloads", Toast.LENGTH_LONG).show();

                        File file = new File(name); // Here you declare your pdf path
                        Uri excelPath;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            excelPath = FileProvider.getUriForFile(Objects.requireNonNull(context),
                                    "com.haball.provider", file);
                        } else {
                            excelPath = Uri.fromFile(file);
                        }
                        if (file.exists()) { //Checking if the file exists or not
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(excelPath, "application/pdf");
                            PackageManager pm = context.getPackageManager();
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            sendIntent.setType("application/pdf");
                            Intent openInChooser = Intent.createChooser(intent, "Choose");
                            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
                            if (resInfo.size() > 0) {
                                try {
                                    context.startActivity(openInChooser);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                    Toast.makeText(context, "PDF apps are not installed", Toast.LENGTH_SHORT).show();
                                    // PDF apps are not installed
                                }
                            } else {
                                Toast.makeText(context, "PDF apps are not installed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "The file not exists! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();
                 new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
            }
        }, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
    }


    public void viewPDF_Invoice(final Context context, String paymentId) throws JSONException {
        final Loader loader = new Loader(context);
        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

        // if (!URL_PDF_VIEW_INVOICE.contains("/" + paymentId))
        //     URL_PDF_VIEW_INVOICE = URL_PDF_VIEW_INVOICE + paymentId;
        // Log.i("DistributorId ", DistributorId);
        // Log.i("Token", Token);
        // Log.i("paymentId", paymentId);

//        URL_PDF_VIEW = URL_PDF_VIEW+paymentId;

        JSONObject map = new JSONObject();
//        map.put("DistributorId", Integer.parseInt(DistributorId));
//        map.put("TotalRecords", 10);
//        map.put("PageNumber", 0.1);
        map.put("DistributorId", DistributorId);
        map.put("PageNumber", 0);
        map.put("TotalRecords", 10);
        map.put("ID", paymentId);
//        StringRequest sr = new StringRequest(Request.Method.POST, URL_PDF_VIEW, new Response.Listener<String>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(String result) {
//                // Log.i("PDF VIEW..", result);
////                byte[] bytes = result.getBytes(Charset.forName("UTF-8"));
//////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//////                    try {
//////                        byte[] decodedString = Base64.getDecoder().decode(new String(bytes).getBytes("UTF-8"));
//////                        String string = new String(decodedString);
//////                        // Log.i("PDF BYTE DECODED..", string);
//////
//////                    } catch (UnsupportedEncodingException e) {
//////                        e.printStackTrace();
//////                    }
//////
//////                }
//////                String string = new String(bytes);
//////                // Log.i("PDF BYTE ARRAY..", string);
////                saveToFile(bytes, "file.pdf");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                params.put("Content-Type", "application/json; charset=UTF-8");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(context).add(sr);

        loader.showLoader();
        final Context finalcontext = context;
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.POST, URL_PDF_VIEW_INVOICE, map, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                // TODO handle the response
                loader.hideLoader();
                try {
                    // Log.i("responseByte", String.valueOf(response));
                    // Log.i("responseByte", String.valueOf(response.length));
                    if (response != null) {
//                        String dir = Environment.getExternalStorageDirectory() + "/Download/";
                        File dir = context.getExternalCacheDir();

                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
                        String name = dir + "/Invoice - " + timeStamp + ".pdf";
//                        ٍString filename = "Voucher - " + timeStamp + ".pdf";
                        FileOutputStream fPdf = new FileOutputStream(name);

                        fPdf.write(response);
                        fPdf.flush();
                        fPdf.close();
                        // Log.i("Download Complete", "Download complete.");
                        Toast.makeText(mContext, "File saved in Downloads", Toast.LENGTH_LONG).show();

                        File file = new File(name); // Here you declare your pdf path
                        Uri excelPath;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            excelPath = FileProvider.getUriForFile(Objects.requireNonNull(context),
                                    "com.haball.provider", file);
                        } else {
                            excelPath = Uri.fromFile(file);
                        }
                        if (file.exists()) { //Checking if the file exists or not
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(excelPath, "application/pdf");
                            PackageManager pm = context.getPackageManager();
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            sendIntent.setType("application/pdf");
                            Intent openInChooser = Intent.createChooser(intent, "Choose");
                            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
                            if (resInfo.size() > 0) {
                                try {
                                    context.startActivity(openInChooser);
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                    Toast.makeText(context, "PDF apps are not installed", Toast.LENGTH_SHORT).show();
                                    // PDF apps are not installed
                                }
                            } else {
                                Toast.makeText(context, "PDF apps are not installed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "The file not exists! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    // Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
                error.printStackTrace();
                 new HaballError().printErrorMessage(context, error);
                new ProcessingError().showError(context);
            }
        }, null) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(context, new HurlStack());
        mRequestQueue.add(request);
    }
}