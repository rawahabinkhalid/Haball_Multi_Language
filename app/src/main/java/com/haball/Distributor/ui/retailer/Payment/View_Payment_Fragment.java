package com.haball.Distributor.ui.retailer.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.HaballError;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.Retailer_Login.RetailerLogin;
import com.haball.Retailor.RetailorDashboard;
import com.haball.Retailor.ui.Make_Payment.CreatePaymentRequestFragment;
import com.haball.Retailor.ui.Make_Payment.ViewReceeiptPDFRequest;
import com.haball.SSL_HandShake;
import com.haball.TextField;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class View_Payment_Fragment extends Fragment {

    private String PaymentsRequestId;
    private String PAYMENT_REQUEST_URL = "https://uatdistributor.haball.pk/api/retailerprepaidrequest/";
    private String Token;
    private TextInputEditText txt_heading, txt_paymentid, txt_created_date, txt_transaction_date, txt_bname, txt_authorization, txt_settlement, txt_amount, txt_status, txt_transaction_charges, txt_total_amount;
    private Button btn_vreciept, btn_back;
    private TextInputLayout layout_txt_heading, layout_txt_paymentid, layout_created_date, layout_transaction_date,
            layout_txt_bname, layout_txt_authorization, layout_txt_settlement, layout_txt_amount,
            layout_txt_status, layout_txt_transaction_charges, layout_txt_total_amount;
    private FragmentTransaction fragmentTransaction;
    private TextView btn_make_payment;
    private RelativeLayout create_payment_rl;

    public View_Payment_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;
        root = inflater.inflate(R.layout.fragment_view__payment_, container, false);
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("paymentsRequestListID",
                Context.MODE_PRIVATE);
        PaymentsRequestId = sharedPreferences3.getString("paymentsRequestListID", "");
//        // Log.i("PaymentsRequestId12", PaymentsRequestId);
        if (!PAYMENT_REQUEST_URL.contains(PaymentsRequestId))
            PAYMENT_REQUEST_URL = PAYMENT_REQUEST_URL + PaymentsRequestId;

        layout_txt_heading = root.findViewById(R.id.layout_txt_heading);
        layout_txt_paymentid = root.findViewById(R.id.layout_txt_paymentid);
        layout_created_date = root.findViewById(R.id.layout_created_date);
        layout_transaction_date = root.findViewById(R.id.layout_transaction_date);
        layout_txt_bname = root.findViewById(R.id.layout_txt_bname);
        layout_txt_authorization = root.findViewById(R.id.layout_txt_authorization);
        layout_txt_settlement = root.findViewById(R.id.layout_txt_settlement);
        layout_txt_amount = root.findViewById(R.id.layout_txt_amount);
        layout_txt_status = root.findViewById(R.id.layout_txt_status);
        layout_txt_transaction_charges = root.findViewById(R.id.layout_txt_transaction_charges);
        layout_txt_total_amount = root.findViewById(R.id.layout_txt_total_amount);

        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        txt_heading = root.findViewById(R.id.txt_heading);
        txt_paymentid = root.findViewById(R.id.txt_paymentid);
        txt_created_date = root.findViewById(R.id.txt_created_date);
        txt_transaction_date = root.findViewById(R.id.txt_transaction_date);
        txt_bname = root.findViewById(R.id.txt_bname);
        txt_authorization = root.findViewById(R.id.txt_authorization);
        txt_settlement = root.findViewById(R.id.txt_settlement);
        txt_amount = root.findViewById(R.id.txt_amount);
        txt_status = root.findViewById(R.id.txt_status);
        txt_transaction_charges = root.findViewById(R.id.txt_transaction_charges);
        txt_total_amount = root.findViewById(R.id.txt_total_amount);

        create_payment_rl = root.findViewById(R.id.create_payment_rl);

        btn_make_payment = root.findViewById(R.id.btn_addpayment);
        btn_vreciept = root.findViewById(R.id.btn_vreciept);
        btn_back = root.findViewById(R.id.btn_back);

        create_payment_rl.setVisibility(View.GONE);

        new TextField().changeColor(this.getContext(), layout_txt_heading, txt_heading);
        new TextField().changeColor(this.getContext(), layout_txt_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(this.getContext(), layout_created_date, txt_created_date);
        new TextField().changeColor(this.getContext(), layout_transaction_date, txt_transaction_date);
        new TextField().changeColor(this.getContext(), layout_txt_bname, txt_bname);
        new TextField().changeColor(this.getContext(), layout_txt_authorization, txt_authorization);
        new TextField().changeColor(this.getContext(), layout_txt_settlement, txt_settlement);
        new TextField().changeColor(this.getContext(), layout_txt_amount, txt_amount);
        new TextField().changeColor(this.getContext(), layout_txt_status, txt_status);
        new TextField().changeColor(this.getContext(), layout_txt_settlement, txt_settlement);
        new TextField().changeColor(this.getContext(), layout_txt_transaction_charges, txt_transaction_charges);
        new TextField().changeColor(this.getContext(), layout_txt_paymentid, txt_paymentid);
        new TextField().changeColor(this.getContext(), layout_txt_total_amount, txt_total_amount);

        txt_heading.setEnabled(false);
        txt_paymentid.setEnabled(false);
        txt_created_date.setEnabled(false);
        txt_transaction_date.setEnabled(false);
        txt_bname.setEnabled(false);
        txt_authorization.setEnabled(false);
        txt_settlement.setEnabled(false);
        txt_amount.setEnabled(false);
        txt_status.setEnabled(false);
        txt_transaction_charges.setEnabled(false);
        txt_total_amount.setEnabled(false);

        btn_make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new CreatePaymentRequestFragment()).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        btn_vreciept.setVisibility(View.GONE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new RetailerPaymentDashboard()).addToBackStack(null);
                fragmentTransaction.commit();
//                Intent login_intent = new Intent(getActivity(), RetailorDashboard.class);
//                startActivity(login_intent);
//                getActivity().finish();
            }
        });

        btn_vreciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewReceeiptPDFRequest viewPDFRequest = new ViewReceeiptPDFRequest();
                try {
                    viewPDFRequest.viewPDF(getContext(), PaymentsRequestId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            fetchPaymentData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        PaymentsRequestId = getArguments().getString("PaymentsRequestId");
        return root;

    }

    private void fetchPaymentData() throws JSONException {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(getContext());

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, PAYMENT_REQUEST_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                // Log.i("resultInvoice", String.valueOf(result));
                try {
                    if (!String.valueOf(result.getJSONObject("Detail").get("CompanyName")).equals("null")) {
                        txt_heading.setText(String.valueOf(result.getJSONObject("Detail").get("CompanyName")));
                        txt_heading.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("PaymentID")).equals("null")) {
                        txt_paymentid.setText(String.valueOf(result.get("PaymentID")));
                        txt_paymentid.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("PaymentCreatedDate")).equals("null")) {
                        txt_created_date.setText(String.valueOf(result.get("PaymentCreatedDate")).split("T")[0]);
                        txt_created_date.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.get("RetailerInvoicePaidDate")).equals("null")) {
                        txt_transaction_date.setText(String.valueOf(result.get("RetailerInvoicePaidDate")).split("T")[0]);
                        txt_transaction_date.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("BankName")).equals("null")) {
                        txt_bname.setText(String.valueOf(result.getJSONObject("Detail").get("BankName")));
                        txt_bname.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("AuthID")).equals("null")) {
                        txt_authorization.setText(String.valueOf(result.getJSONObject("Detail").get("AuthID")));
                        txt_authorization.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("SettlementID")).equals("null")) {
                        txt_settlement.setText(String.valueOf(result.getJSONObject("Detail").get("SettlementID")));
                        txt_settlement.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("PaidAmount")).equals("null")) {
//                        txt_amount.setText(String.valueOf(result.get("PaidAmount")));
                        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                        String Formatted_TotalAmount = formatter1.format(Double.parseDouble(result.getJSONObject("Detail").getString("PaidAmount")));
                        txt_amount.setText("Rs. "+Formatted_TotalAmount);
                        txt_amount.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("Status")).equals("null")) {
                        txt_status.setText(String.valueOf(result.getJSONObject("Detail").get("Status")));
                        txt_status.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("TransactionCharges")).equals("null")) {
                        txt_transaction_charges.setText("Rs. "+String.valueOf(result.getJSONObject("Detail").get("TransactionCharges")));
                        txt_transaction_charges.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                    if (!String.valueOf(result.getJSONObject("Detail").get("TotalAmount")).equals("null")) {
                        DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                        String Formatted_TotalAmount = formatter1.format(Double.parseDouble(result.getJSONObject("Detail").getString("TotalAmount")));
                        txt_total_amount.setText("Rs. "+Formatted_TotalAmount);
                        txt_total_amount.setTextColor(getContext().getResources().getColor(R.color.textcolor));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new HaballError().printErrorMessage(getContext(), error);
                new ProcessingError().showError(getContext());
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "bearer " + Token);
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getContext()).add(sr);
    }

    // private void printErrMessage(VolleyError error) {
    //     if (getContext() != null) {
    //         if (error instanceof NetworkError) {
    //             Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ServerError) {
    //             Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof AuthFailureError) {
    //             Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof ParseError) {
    //             Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof NoConnectionError) {
    //             Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
    //         } else if (error instanceof TimeoutError) {
    //             Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
    //         }

    //         if (error.networkResponse != null && error.networkResponse.data != null) {
    //             try {
    //                 String message = "";
    //                 String responseBody = new String(error.networkResponse.data, "utf-8");
    //                 // Log.i("responseBody", responseBody);
    //                 JSONObject data = new JSONObject(responseBody);
    //                 // Log.i("data", String.valueOf(data));
    //                 Iterator<String> keys = data.keys();
    //                 while (keys.hasNext()) {
    //                     String key = keys.next();
    //                     message = message + data.get(key) + "\n";
    //                 }
    //                 Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    //             } catch (UnsupportedEncodingException e) {
    //                 e.printStackTrace();
    //             } catch (JSONException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    // }
}

