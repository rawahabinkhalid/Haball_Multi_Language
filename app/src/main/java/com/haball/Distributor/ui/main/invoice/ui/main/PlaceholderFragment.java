package com.haball.Distributor.ui.main.invoice.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.haball.CustomToast;
import com.haball.Distributor.DistributorDashboard;
import com.haball.Distributor.StatusKVP;
import com.haball.Distributor.ui.main.invoice.DistributorConsolidationDetailsModel;
import com.haball.Distributor.ui.main.invoice.DistributorConsolidationDetailsParentModel;
import com.haball.Distributor.ui.main.invoice.DistributorCreditDetailsModel;
import com.haball.Distributor.ui.main.invoice.DistributorCreditDetailsParentModel;
import com.haball.Distributor.ui.main.invoice.Fragment_Distributor_Consolidation_Details;
import com.haball.Distributor.ui.main.invoice.ParentListAdapter_ConsolidatedDetails;
import com.haball.Distributor.ui.main.invoice.ParentListAdapter_CreditDetails;
import com.haball.Distributor.ui.main.invoice.ViewInvoiceAdapter.ViewInvoiceProductAdapter;
import com.haball.Distributor.ui.main.invoice.ViewInvoiceModel.ViewInvoiceProductModel;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.Distributor.ui.payments.CreatePaymentRequestFragment;
import com.haball.Distributor.ui.payments.PaymentScreen3Fragment;
import com.haball.Distributor.ui.payments.ViewPDFRequest;
import com.haball.Distributor.ui.payments.ViewVoucherRequest;
import com.haball.HaballError;
import com.haball.Loader;
import com.haball.MyWebView;
import com.haball.Payment.View_Payment_Fragment;
import com.haball.ProcessingError;
import com.haball.R;
import com.haball.SSL_HandShake;
import com.haball.TextField;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.testWhatsapp.Pay_By_Make_Payment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String orderID, orderStatus;
    private String URL_Order_Data = "https://175.107.203.97:4013/api/Invoices/";
    private PageViewModel pageViewModel;
    private TextInputEditText txt_companyName, txt_paymentID, txt_created_date, txt_transaction_date, txt_bank, txt_authorization_id, txt_settlement_id, txt_status, txt_amount, txt_transaction_charges, txt_total_amount;
    private RecyclerView rv_fragment_retailer_order_details, recyclerView;
    private TextInputLayout layout_txt_orderID, layout_txt_order_company, layout_txt_created_date_order, layout_txt_status_order, layout_txt_comments,
            layout_txt_companName, layout_txt_paymentID, layout_txt_created_date, layout_transaction_date,
            layout_txt_bank, layout_txt_authorization_id, layout_txt_settlement_id, layout_txt_status, layout_txt_order_reference, layout_txt_invoice_reference,
            layout_txt_amount, layout_txt_transaction_charges, layout_txt_total_amount, layout_due_date, layout_reference,
            layout_paid_amount, layout_remaining_amount;
    private TextInputEditText txt_orderID, txt_company_order, txt_created_date_order, txt_status_order, txt_comments, txt_confirm, txt_order_reference,
            txt_invoice_reference, txt_due_date, txt_reference, txt_paid_amount, txt_remaining_amount;
    private TextView tv_shipment_no_data;
    private RecyclerView.Adapter rv_productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<ViewInvoiceProductModel> invo_productList = new ArrayList<>();
    private String Token;
    private HashMap<String, String> RetailerOrderStatusKVP = new HashMap<>();
    private StatusKVP StatusKVPClass;
    private TextView discount_amount;
    private TextView total_amount, disclaimer_tv;
    private Button button_back, button_view_receipt;
    private FragmentTransaction fragmentTransaction;
    private Spinner spinner_banking_make_payment;
    private ArrayAdapter<String> arrayAdapter_banking_make_payment;

    private TextView tv_banking_channel, payment_id, btn_newpayment;

    private String DistributorId;
    private HashMap<String, String> OrderStatusKVP = new HashMap<>();
    private HashMap<String, String> InvoiceStatusKVP = new HashMap<>();
    private String PrePaidNumber = "", PrePaidId = "", CompanyName = "", Amount = "", CompanyId = "", MenuItem = "";
    private Button btn_voucher, btn_update, btn_back;
    private Spinner spinner_companyName;
    private HashMap<String, String> companyNameAndId = new HashMap<>();
    private ArrayAdapter<String> arrayAdapterPayments;
    private List<String> CompanyNames = new ArrayList<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String company_names;
    private Typeface myFont;
    private Loader loader;
    private RelativeLayout ln_login, rl_authorization_id;
    private RelativeLayout rl_jazz_cash;
    private String InvoiceStatus, invoiceID;
    private TextView discount, Rs_discount;
    private String URL_PAYMENT_REQUESTS_SELECT_COMPANY = "https://175.107.203.97:4013/api/company/ReadActiveCompanyContract/";
    private List<DistributorCreditDetailsModel> creditDetailsList;
    private List<DistributorCreditDetailsParentModel> titles = new ArrayList<>();
    private int lastExpandedPosition = -1;
    int width;
    int height;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        SharedPreferences sharedPreferences3 = getContext().getSharedPreferences("PaymentId",
                Context.MODE_PRIVATE);


        orderID = sharedPreferences3.getString("PaymentId", "");
        orderStatus = sharedPreferences3.getString("Status", "");
        // Log.i("PaymentId", orderID);
        // Log.i("orderStatus", orderStatus);
        if (!URL_Order_Data.contains(orderID)) {
            URL_Order_Data = URL_Order_Data + orderID;
            // Log.i("URL_Order_Data", URL_Order_Data);
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");

        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        DistributorId = sharedPreferences1.getString("Distributor_Id", "");

//        // Log.i("StatusKVP", String.valueOf(Token));

        StatusKVPClass = new StatusKVP(getContext(), Token);
        OrderStatusKVP = StatusKVPClass.getOrderStatus();
        InvoiceStatusKVP = StatusKVPClass.getInvoiceStatus();
        // Log.i("StatusKVP1", "String.valueOf(OrderStatusKVP)");
        // Log.i("StatusKVP1", String.valueOf(OrderStatusKVP));

        View rootView = null;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 2: {
                loader = new Loader(getContext());
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_tab, container, false);

                layout_txt_orderID = rootView.findViewById(R.id.layout_txt_orderID);
                layout_txt_order_company = rootView.findViewById(R.id.layout_txt_order_company);
                layout_txt_created_date_order = rootView.findViewById(R.id.layout_txt_created_date_order);
                layout_txt_status_order = rootView.findViewById(R.id.layout_txt_status_order);
                layout_txt_order_reference = rootView.findViewById(R.id.layout_txt_order_reference);
                layout_txt_invoice_reference = rootView.findViewById(R.id.layout_txt_invoice_reference);
                layout_txt_comments = rootView.findViewById(R.id.layout_txt_comments);
                txt_orderID = rootView.findViewById(R.id.txt_orderID);
                txt_company_order = rootView.findViewById(R.id.txt_company_order);
                txt_created_date_order = rootView.findViewById(R.id.txt_created_date_order);
                txt_status_order = rootView.findViewById(R.id.txt_status_order);
                txt_order_reference = rootView.findViewById(R.id.txt_order_reference);
                txt_invoice_reference = rootView.findViewById(R.id.txt_invoice_reference);
                txt_comments = rootView.findViewById(R.id.txt_comments);
                button_back = rootView.findViewById(R.id.button_back);

                layout_txt_order_reference.setVisibility(View.GONE);
                layout_txt_invoice_reference.setVisibility(View.GONE);

                new TextField().changeColor(this.getContext(), layout_txt_orderID, txt_orderID);
                new TextField().changeColor(this.getContext(), layout_txt_order_company, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_created_date_order, txt_company_order);
                new TextField().changeColor(this.getContext(), layout_txt_status_order, txt_status_order);
                new TextField().changeColor(this.getContext(), layout_txt_order_reference, txt_order_reference);
                new TextField().changeColor(this.getContext(), layout_txt_invoice_reference, txt_invoice_reference);
                new TextField().changeColor(this.getContext(), layout_txt_comments, txt_comments);

                txt_orderID.setEnabled(false);
                txt_company_order.setEnabled(false);
                txt_created_date_order.setEnabled(false);
                txt_status_order.setEnabled(false);
                txt_comments.setEnabled(false);

                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tab());
//                        fragmentTransaction.commit();

                        Intent login_intent = new Intent(getContext(), DistributorDashboard.class);
                        startActivity(login_intent);
                        getActivity().finish();
                    }
                });

                getOrderData();
                break;
            }
            case 3: {
                loader = new Loader(getContext());
                rootView = inflater.inflate(R.layout.fragment_retailer_orders_details_tab, container, false);
                rv_fragment_retailer_order_details = rootView.findViewById(R.id.rv_fragment_retailer_order_details);
                total_amount = rootView.findViewById(R.id.total_amount);
                rv_fragment_retailer_order_details.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(rootView.getContext());
                rv_fragment_retailer_order_details.setLayoutManager(layoutManager);
                disclaimer_tv = rootView.findViewById(R.id.disclaimer_tv);
                button_back = rootView.findViewById(R.id.button_back);
                discount = rootView.findViewById(R.id.discount);
                Rs_discount = rootView.findViewById(R.id.Rs_discount);
                discount_amount = rootView.findViewById(R.id.discount_amount);


                SharedPreferences sharedPreferences11 = getContext().getSharedPreferences("PaymentId",
                        Context.MODE_PRIVATE);
                InvoiceStatus = sharedPreferences11.getString("InvoiceStatus", "");
                String Status = sharedPreferences11.getString("Status", "");
                // Log.i("InvoiceStatus", InvoiceStatus);

//        SectionsPagerAdapter sectionsPagerAdapter = null;
                if ((!InvoiceStatus.equals("null") && !InvoiceStatus.equals("Pending")) || Status.equals("Cancelled")) {
                    disclaimer_tv.setVisibility(View.GONE);
                }


                button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.main_container_ret, new Dashboard_Tab());
//                        fragmentTransaction.commit();
                        Intent login_intent = new Intent(getContext(), DistributorDashboard.class);
                        startActivity(login_intent);
                        getActivity().finish();
                    }
                });

                getOrderDetailsData(rootView);
                break;
            }
            case 1: {
                loader = new Loader(getContext());

                SharedPreferences sharedPreferences11 = getContext().getSharedPreferences("PaymentId",
                        Context.MODE_PRIVATE);
                InvoiceStatus = sharedPreferences11.getString("InvoiceStatus", "");

//                getInvoiceStatusFromAPI();

                // Log.i("InvoiceStatus", InvoiceStatus);

//        SectionsPagerAdapter sectionsPagerAdapter = null;
//                if (InvoiceStatus.equals("Paid") || InvoiceStatus.equals("Invoiced")) {
                if (InvoiceStatus.equals("Unpaid")) {
                    rootView = inflater.inflate(R.layout.fragment_distributor_invoice_unpaid, container, false);
                    layout_txt_companName = rootView.findViewById(R.id.layout_company);
                    layout_txt_amount = rootView.findViewById(R.id.layout_amount);
                    layout_due_date = rootView.findViewById(R.id.layout_due_date);
                    layout_reference = rootView.findViewById(R.id.layout_reference);
                    button_back = rootView.findViewById(R.id.button_back);
                    button_view_receipt = rootView.findViewById(R.id.btn_voucher);

                    spinner_banking_make_payment = rootView.findViewById(R.id.spinner_banking_make_payment);

                    payment_id = rootView.findViewById(R.id.payment_id);

                    payment_id.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            cm.setText(payment_id.getText());
//                new CustomToast().showToast(getActivity(), "Payment ID: " + String.valueOf(payment_id.getText()) + " - Copied to clipboard");
                            new CustomToast().showToast(getActivity(), getResources().getString(R.string.psid_copied_clipboard));
                            return false;
                        }
                    });

                    txt_companyName = rootView.findViewById(R.id.txt_company);
                    txt_amount = rootView.findViewById(R.id.txt_amount);
                    txt_due_date = rootView.findViewById(R.id.txt_due_date);
                    txt_reference = rootView.findViewById(R.id.txt_reference);


                    new TextField().changeColor(getContext(), layout_txt_companName, txt_companyName);
                    new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
                    new TextField().changeColor(getContext(), layout_due_date, txt_due_date);
                    new TextField().changeColor(getContext(), layout_reference, txt_reference);

                    // fields hidden here
                    // layout_txt_companName.setVisibility(View.GONE);
                    // layout_txt_amount.setVisibility(View.GONE);
                    // layout_due_date.setVisibility(View.GONE);
                    // layout_reference.setVisibility(View.GONE);

                    button_view_receipt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkAndRequestPermissions()) {
                                try {
                                    viewReceiptPDF(getContext(), invoiceID);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

                    button_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent login_intent = new Intent(getContext(), DistributorDashboard.class);
                            startActivity(login_intent);
                            getActivity().finish();
                        }
                    });

                    List<String> banking_make_payment = new ArrayList<>();
                    banking_make_payment.add(getResources().getString(R.string.Txt_Payment));
                    banking_make_payment.add(getResources().getString(R.string.PSID));
                    banking_make_payment.add(getResources().getString(R.string.make_payment));

                    arrayAdapter_banking_make_payment = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, banking_make_payment) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            // TODO Auto-generated method stub
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
                            text.setTextSize((float) 13.6);
                            text.setPadding(50, 0, 50, 0);
                            text.setTypeface(myFont);
                            return view;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // TODO Auto-generated method stub
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
                            text.setTextSize((float) 13.6);
                            text.setPadding(50, 0, 50, 0);
                            return view;
                        }
                    };
                    spinner_banking_make_payment.setAdapter(arrayAdapter_banking_make_payment);

                    spinner_banking_make_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                try {
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } else if (i == 1) {

                                final AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
                                LayoutInflater inflater2 = LayoutInflater.from(getContext());
                                View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                alertDialog2.setView(view_popup2);
                                alertDialog2.show();
                                ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
                                TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                                payment_information_txt3.setText(PrePaidNumber);
                                Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                                btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkAndRequestPermissions()) {
                                            try {
                                                viewReceiptPDF(getContext(), invoiceID);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });

                                img_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog2.dismiss();
                                    }
                                });
                            } else if (i == 2) {
                                SharedPreferences PrePaidNumber_MakePayment = getContext().getSharedPreferences("PrePaidNumber",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_MakePayment = PrePaidNumber_MakePayment.edit();
                                editor_MakePayment.putString("PrePaidNumber", PrePaidNumber);
                                editor_MakePayment.putString("PrePaidId", invoiceID);
                                editor_MakePayment.putString("CompanyId", CompanyId);
                                editor_MakePayment.putString("CompanyName", CompanyName);
                                editor_MakePayment.putString("Amount", Amount);
                                editor_MakePayment.putString("MenuItem", "ViewInvoice");
                                editor_MakePayment.apply();

                                SharedPreferences paymentsRequestListID = ((FragmentActivity) getContext()).getSharedPreferences("paymentsRequestListID",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_MakePayment1 = paymentsRequestListID.edit();
                                editor_MakePayment1.putString("paymentsRequestListID", invoiceID);
                                editor_MakePayment1.apply();

                                try {
                                    new Pay_By_Make_Payment().payByMakePaymentsDistributor(getContext(), PrePaidNumber, Double.parseDouble(Amount), new View_Payment_Fragment(), new PaymentScreen3Fragment(), 1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

//                    layout_reference.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (String.valueOf(txt_reference.getText()).equals("Click here to view consolidated invoice references")) {
//                                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container, new Fragment_Distributor_Consolidation_Details()).addToBackStack(null);
//                                fragmentTransaction.commit();
//                            }
//                        }
//                    });
                    txt_reference.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (String.valueOf(txt_reference.getText()).equals("Click here to view consolidated invoice references")) {
                                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.main_container, new Fragment_Distributor_Consolidation_Details()).addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        }
                    });

                    getUnpaidInvoiceData();
                } else if (InvoiceStatus.equals("Paid") || InvoiceStatus.equals("Partially Paid")) {

                    rootView = inflater.inflate(R.layout.fragment_distributor_invoice_paid, container, false);
                    layout_txt_companName = rootView.findViewById(R.id.layout_company);
                    layout_txt_paymentID = rootView.findViewById(R.id.layout_payment_id);
                    layout_txt_created_date = rootView.findViewById(R.id.layout_created_date);
                    layout_due_date = rootView.findViewById(R.id.layout_due_date);
                    layout_txt_status = rootView.findViewById(R.id.layout_status);
                    layout_txt_amount = rootView.findViewById(R.id.layout_amount);
                    layout_paid_amount = rootView.findViewById(R.id.layout_paid_amount);
                    layout_remaining_amount = rootView.findViewById(R.id.layout_remaining_amount);
                    button_back = rootView.findViewById(R.id.button_back);
                    button_view_receipt = rootView.findViewById(R.id.btn_voucher);

                    txt_companyName = rootView.findViewById(R.id.txt_company);
                    txt_paymentID = rootView.findViewById(R.id.txt_payment_id);
                    txt_created_date = rootView.findViewById(R.id.txt_created_date);
                    txt_due_date = rootView.findViewById(R.id.txt_due_date);
                    txt_status = rootView.findViewById(R.id.txt_status);
                    txt_amount = rootView.findViewById(R.id.txt_amount);
                    txt_paid_amount = rootView.findViewById(R.id.txt_paid_amount);
                    txt_remaining_amount = rootView.findViewById(R.id.txt_remaining_amount);

                    rl_authorization_id = rootView.findViewById(R.id.rl_authorization_id);

                    recyclerView = rootView.findViewById(R.id.rv_credit_details_list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                    spinner_banking_make_payment = rootView.findViewById(R.id.spinner_banking_make_payment);

                    RelativeLayout ln_payment = rootView.findViewById(R.id.ln_payment);
                    if (InvoiceStatus.equals("Paid")) {
                        ln_payment.setVisibility(View.GONE);
                    }


                    new TextField().changeColor(getContext(), layout_txt_companName, txt_companyName);
                    new TextField().changeColor(getContext(), layout_txt_paymentID, txt_paymentID);
                    new TextField().changeColor(getContext(), layout_txt_created_date, txt_created_date);
                    new TextField().changeColor(getContext(), layout_due_date, txt_due_date);
                    new TextField().changeColor(getContext(), layout_txt_status, txt_status);
                    new TextField().changeColor(getContext(), layout_txt_amount, txt_amount);
                    new TextField().changeColor(getContext(), layout_paid_amount, txt_paid_amount);
                    new TextField().changeColor(getContext(), layout_remaining_amount, txt_remaining_amount);

                    // fields hidden here
                    // layout_txt_created_date.setVisibility(View.GONE);
                    // layout_transaction_date.setVisibility(View.GONE);
                    // layout_txt_bank.setVisibility(View.GONE);
                    // layout_txt_authorization_id.setVisibility(View.GONE);
                    // layout_txt_settlement_id.setVisibility(View.GONE);
                    // layout_txt_status.setVisibility(View.GONE);
                    // layout_txt_amount.setVisibility(View.GONE);
                    // layout_txt_transaction_charges.setVisibility(View.GONE);
                    // layout_txt_total_amount.setVisibility(View.GONE);

                    button_view_receipt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkAndRequestPermissions()) {
                                try {
                                    viewReceiptPDF(getContext(), invoiceID);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

                    button_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent login_intent = new Intent(getContext(), DistributorDashboard.class);
                            startActivity(login_intent);
                            getActivity().finish();
                        }
                    });

                    List<String> banking_make_payment = new ArrayList<>();
                    banking_make_payment.add(getResources().getString(R.string.Txt_Payment));
                    banking_make_payment.add(getResources().getString(R.string.PSID));
                    banking_make_payment.add(getResources().getString(R.string.make_payment));

                    arrayAdapter_banking_make_payment = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, banking_make_payment) {
                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent) {
                            // TODO Auto-generated method stub
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
                            text.setTextSize((float) 13.6);
                            text.setPadding(50, 0, 50, 0);
                            text.setTypeface(myFont);
                            return view;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            // TODO Auto-generated method stub
                            View view = super.getView(position, convertView, parent);
                            TextView text = (TextView) view.findViewById(android.R.id.text1);
                            text.setTextColor(getResources().getColor(R.color.text_color_selection));
                            text.setTextSize((float) 13.6);
                            text.setPadding(50, 0, 50, 0);
                            return view;
                        }
                    };
                    spinner_banking_make_payment.setAdapter(arrayAdapter_banking_make_payment);

                    spinner_banking_make_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i == 0) {
                                try {
                                    ((TextView) adapterView.getChildAt(0)).setTextColor(getResources().getColor(R.color.grey_color));
                                    ((TextView) adapterView.getChildAt(0)).setTextSize((float) 13.6);
                                    ((TextView) adapterView.getChildAt(0)).setPadding(30, 0, 30, 0);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } else if (i == 1) {

                                final AlertDialog alertDialog2 = new AlertDialog.Builder(getContext()).create();
                                LayoutInflater inflater2 = LayoutInflater.from(getContext());
                                View view_popup2 = inflater2.inflate(R.layout.payment_request_details, null);
                                alertDialog2.setView(view_popup2);
                                alertDialog2.show();
                                ImageButton img_close = view_popup2.findViewById(R.id.image_button_close);
                                TextView payment_information_txt3 = view_popup2.findViewById(R.id.payment_information_txt3);
                                payment_information_txt3.setText(PrePaidNumber);
                                Button btn_view_voucher = view_popup2.findViewById(R.id.btn_view_voucher);
                                btn_view_voucher.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkAndRequestPermissions()) {
                                            try {
                                                viewReceiptPDF(getContext(), invoiceID);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                });

                                img_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog2.dismiss();
                                    }
                                });
                            } else if (i == 2) {
                                SharedPreferences PrePaidNumber_MakePayment = getContext().getSharedPreferences("PrePaidNumber",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_MakePayment = PrePaidNumber_MakePayment.edit();
                                editor_MakePayment.putString("PrePaidNumber", PrePaidNumber);
                                editor_MakePayment.putString("PrePaidId", invoiceID);
                                editor_MakePayment.putString("CompanyId", CompanyId);
                                editor_MakePayment.putString("CompanyName", CompanyName);
                                editor_MakePayment.putString("Amount", Amount);
                                editor_MakePayment.putString("MenuItem", "ViewInvoice");
                                editor_MakePayment.apply();

                                SharedPreferences paymentsRequestListID = ((FragmentActivity) getContext()).getSharedPreferences("paymentsRequestListID",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_MakePayment1 = paymentsRequestListID.edit();
                                editor_MakePayment1.putString("paymentsRequestListID", invoiceID);
                                editor_MakePayment1.apply();

                                try {
                                    new Pay_By_Make_Payment().payByMakePaymentsDistributor(getContext(), PrePaidNumber, Double.parseDouble(Amount), new View_Payment_Fragment(), new PaymentScreen3Fragment(), 1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

//                    layout_reference.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (String.valueOf(txt_reference.getText()).equals("Click here to view consolidated invoice references")) {
//                                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container, new Fragment_Distributor_Consolidation_Details()).addToBackStack(null);
//                                fragmentTransaction.commit();
//                            }
//                        }
//                    });
//                    txt_reference.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (String.valueOf(txt_reference.getText()).equals("Click here to view consolidated invoice references")) {
//                                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.main_container, new Fragment_Distributor_Consolidation_Details()).addToBackStack(null);
//                                fragmentTransaction.commit();
//                            }
//                        }
//                    });
                    getPaidInvoiceData();
                }
                break;
            }
        }
        return rootView;
    }

    private void viewReceiptPDF(Context context, String ID) throws JSONException {
        ViewPDFRequest viewPDFRequest = new ViewPDFRequest();
        viewPDFRequest.viewPDF_Invoice(context, ID);
    }

//
//    private void fetchCompanyData() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//
//        // Log.i("Token", Token);
//        new SSL_HandShake().handleSSLHandshake();
//
//        JsonArrayRequest sr = new JsonArrayRequest(Request.Method.GET, URL_PAYMENT_REQUESTS_SELECT_COMPANY, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray result) {
//                loader.hideLoader();
//                try {
//                    getUnPaidInvoiceData();
//
//                    JSONObject jsonObject = null;
//                    for (int i = 0; i < result.length(); i++) {
//                        jsonObject = result.getJSONObject(i);
//                        CompanyNames.add(jsonObject.getString("CompanyName"));
//                        companyNameAndId.put(jsonObject.getString("CompanyName"), jsonObject.getString("DealerCode"));
//                    }
//
//                    arrayAdapterPayments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    arrayAdapterPayments.notifyDataSetChanged();
//                    spinner_companyName.setAdapter(arrayAdapterPayments);
//
//                    // txt_amount.setText(Amount);
//                    // Log.i("Debugging", String.valueOf(CompanyNames));
//                    // Log.i("Debugging", String.valueOf(CompanyNames.indexOf(CompanyName)));
//                    // Log.i("Debugging", String.valueOf(CompanyName));
////        int spinnerPosition = arrayAdapterPayments.getPosition(CompanyName);
////                    spinner_companyName.setSelection(CompanyNames.indexOf(CompanyName));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                // Log.e("RESPONSE OF COMPANY ID", result.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                new HaballError().printErrorMessage(getContext(), error);
//                new ProcessingError().showError(getContext());
//                error.printStackTrace();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        sr.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 1000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });
//        Volley.newRequestQueue(getContext()).add(sr);
//    }
//
//    private void viewPDF(Context context, String ID) throws JSONException {
//        ViewVoucherRequest viewPDFRequest = new ViewVoucherRequest();
//        viewPDFRequest.viewPDF(context, ID);
//    }

    private boolean checkAndRequestPermissions() {
        int permissionRead = ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionRead != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
//
//
//    private void getInvoiceStatusFromAPI() {
//        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice", Token);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject result) {
//                loader.hideLoader();
//                // Log.i("Order Data response", String.valueOf(result));
//                try {
//                    if (result.has("Invoice")) {
//                        JSONObject response = result.getJSONObject("Invoice");
//                        InvoiceStatus = InvoiceStatusKVP.get(response.getString("Status"));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        new HaballError().printErrorMessage(getContext(), error);
//                        new ProcessingError().showError(getContext());
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

    private void getOrderData() {
        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice", Token);
        // Log.i("status_order_111", String.valueOf(txt_status_order));
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                // Log.i("Order Data response", String.valueOf(response));
                try {

//                    txt_orderID.setText(String.valueOf(response.get("OrderNumber")));
//                    txt_company_order.setText(String.valueOf(response.get("CompanyName")));
//                    txt_created_date_order.setText(String.valueOf(response.get("CreatedDate")).split("T")[0]);
//                    txt_status_order.setText(OrderStatusKVP.get(String.valueOf(response.get("Status"))));

//                    JSONObject response = result.getJSONObject("OrderPaymentDetails");
                    if (!String.valueOf(response.get("OrderNumber")).equals("") && !String.valueOf(response.get("OrderNumber")).equals("null"))
                        txt_orderID.setText(String.valueOf(response.get("OrderNumber")));
                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_company_order.setText(String.valueOf(response.get("CompanyName")));
                    if (!String.valueOf(response.get("CreatedDate")).equals("") && !String.valueOf(response.get("CreatedDate")).equals("null"))
                        txt_created_date_order.setText(String.valueOf(response.get("CreatedDate")).split("T")[0]);
                    if (!String.valueOf(response.get("orderStatus")).equals("") && !String.valueOf(response.get("orderStatus")).equals("null")) {
                        if (String.valueOf(response.get("orderStatus")).equals("0"))
                            orderStatus = "Pending";
                        else if (String.valueOf(response.get("orderStatus")).equals("1"))
                            orderStatus = "Approved";
                        else if (String.valueOf(response.get("orderStatus")).equals("2"))
                            orderStatus = "Rejected";
                        else if (String.valueOf(response.get("orderStatus")).equals("3"))
                            orderStatus = "Draft";
                        else if (String.valueOf(response.get("orderStatus")).equals("4"))
                            orderStatus = "Cancelled";

                    }
                    txt_status_order.setText(orderStatus);

                    if (!String.valueOf(response.get("OrderNumber")).equals("") && !String.valueOf(response.get("OrderNumber")).equals("null"))
                        txt_orderID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_company_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("CreatedDate")).equals("") && !String.valueOf(response.get("CreatedDate")).equals("null"))
                        txt_created_date_order.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("orderStatus")).equals("") && !String.valueOf(response.get("orderStatus")).equals("null"))
                        txt_status_order.setTextColor(getResources().getColor(R.color.textcolor));
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

    }

    private void getOrderDetailsData(View rootView) {
        tv_shipment_no_data = rootView.findViewById(R.id.tv_shipment_no_data);
        tv_shipment_no_data.setVisibility(View.GONE);

        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<ViewInvoiceProductModel>>() {
//                }.getType();
//                try {
//                    invo_productList = gson.fromJson(response.get("OrderDetails").toString(), type);
//                    // Log.i("OrderDetails", String.valueOf(response.get("OrderDetails")));
//                    ViewInvoiceProductAdapter productAdapter = new ViewInvoiceProductAdapter(getContext(), invo_productList);
//                    rv_fragment_retailer_order_details.setAdapter(productAdapter);
//                    if (invo_productList.size() != 0) {
//                        tv_shipment_no_data.setVisibility(View.GONE);
//                    } else {
//                        tv_shipment_no_data.setVisibility(View.VISIBLE);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                Gson gson = new Gson();
                Type type = new TypeToken<List<ViewInvoiceProductModel>>() {
                }.getType();
                try {
//                    JSONObject OrderPaymentDetails = response.getJSONObject("OrderPaymentDetails");
                    double totalPrice = 0;
                    double totalDiscount = 0;
                    invo_productList = gson.fromJson(response.get("InvoiceDetails").toString(), type);
//                    for (int i = 0; i < invo_productList.size(); i++) {
//                        if (!String.valueOf(invo_productList.get(i).getTotalPrice()).equals("null"))
//                            totalPrice += Double.parseDouble(invo_productList.get(i).getTotalPrice());
//                    }
                    for (int i = 0; i < invo_productList.size(); i++) {
                        if (!String.valueOf(invo_productList.get(i).getDiscount()).equals("null"))
                            totalDiscount += Double.parseDouble(invo_productList.get(i).getDiscount());
                    }
                    // Log.i("OrderDetails", String.valueOf(response.get("InvoiceDetails")));
                    ViewInvoiceProductAdapter productAdapter = new ViewInvoiceProductAdapter(getContext(), invo_productList);
                    rv_fragment_retailer_order_details.setAdapter(productAdapter);
                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
                    String TotalAmount = "";
//                    if (totalPrice != 0)
                    TotalAmount = formatter1.format(0);
                    if (String.valueOf(response.getString("TotalPrice")).equals("null"))
                        TotalAmount = formatter1.format(Double.parseDouble(response.getString("TotalPrice")));
                    total_amount.setText("Rs. " + TotalAmount);
                    if (!response.getString("Discount").equals("null") && !response.getString("Discount").equals("0")) {
                        String OrderTotalDiscount = formatter1.format(Double.parseDouble(response.getString("Discount")));
                        discount_amount.setText("Rs. " + OrderTotalDiscount);
                    } else if (totalDiscount == 0) {
                        discount.setVisibility(View.GONE);
//                        Rs_discount.setVisibility(View.GONE);
                        discount_amount.setVisibility(View.GONE);
                    } else {
                        String OrderTotalDiscount = formatter1.format(totalDiscount);
                        discount_amount.setText("Rs. " + OrderTotalDiscount);
                    }

//                    String TotalAmount = "";
//                    if (!response.getString("Discount").equals("null") && !response.getString("Discount").equals("0")) {
//                        String OrderTotalDiscount = formatter1.format(Double.parseDouble(response.getString("Discount")));
//                        discount_amount.setText(OrderTotalDiscount);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice - Double.parseDouble(response.getString("Discount")));
//                        total_amount.setText(TotalAmount);
//                    } else if (totalDiscount == 0) {
//                        discount.setVisibility(View.GONE);
//                        Rs_discount.setVisibility(View.GONE);
//                        discount_amount.setVisibility(View.GONE);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice);
//                        total_amount.setText(TotalAmount);
//                    } else {
//                        String OrderTotalDiscount = formatter1.format(totalDiscount);
//                        discount_amount.setText(OrderTotalDiscount);
//
//                        if (totalPrice != 0)
//                            TotalAmount = formatter1.format(totalPrice - totalDiscount);
//                        total_amount.setText(TotalAmount);
//                    }

                    if (invo_productList.size() != 0) {
                        tv_shipment_no_data.setVisibility(View.GONE);
                    } else {
                        tv_shipment_no_data.setVisibility(View.VISIBLE);
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
    }

//    private void getPaidInvoiceData() {
//        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice", Token);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                loader.hideLoader();
//                // Log.i("Order Data response2", String.valueOf(response));
//                try {
////                    if (result.has("Invoice")) {
//////                        JSONObject responseInv = response.getJSONObject("Invoice");
//////                        txt_companyName.setText(String.valueOf(response.get("CompanyName")));
//////                        txt_paymentID.setText(String.valueOf(responseInv.get("InvoiceNumber")));
//////                        setTextAndShow(layout_txt_created_date, txt_created_date, String.valueOf(responseInv.get("PaidDate")).split("T")[0]);
//////                        setTextAndShow(layout_txt_amount, txt_amount, String.valueOf(responseInv.get("PaidAmount")));
//////                        setTextAndShow(layout_txt_status, txt_status, String.valueOf(InvoiceStatusKVP.get(responseInv.getString("Status"))));
//////                        setTextAndShow(layout_txt_amount, txt_amount, String.valueOf(responseInv.get("PaidAmount")));
////                        JSONObject response = result.getJSONObject("Invoice");
//                    invoiceID = response.getString("ID");
//                    txt_companyName.setText(String.valueOf(response.get("CompanyName")));
//                    txt_paymentID.setText(String.valueOf(response.get("InvoiceNumber")));
//                    setTextAndShowDate(layout_txt_created_date, txt_created_date, String.valueOf(response.get("CreatedDate")).split("T")[0]);
////                    setTextAndShow(layout_txt_amount, txt_amount, String.valueOf(response.get("InvoiceTotalAmount")));
//                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");
//                    String Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("PaidAmount")));
//                    setTextAndShow(layout_txt_amount, txt_amount, "Rs. " + Formatted_TotalAmount);
//
////                    setTextAndShow(layout_txt_status, txt_status, String.valueOf(InvoiceStatusKVP.get(response.getString("invoiceStatus"))));
//                    setTextAndShow(layout_txt_status, txt_status, String.valueOf(response.getString("InvoiceStatusValue")));
//                    setTextAndShow(layout_transaction_date, txt_confirm, String.valueOf(response.getString("PaidDate")).split("T")[0]);
////                        setTextAndShow(layout_txt_bank, txt_bank, String.valueOf(response.getString("BankName")));
////                        setTextAndShow(layout_txt_authorization_id, txt_authorization_id, String.valueOf(response.getString("AuthID")));
////                        setTextAndShow(layout_txt_settlement_id, txt_settlement_id, String.valueOf(response.getString("SettlementID")));
////                    setTextAndShow(layout_txt_total_amount, txt_total_amount, String.valueOf(response.getString("TotalAmount")));
//                    Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("TotalPrice")));
//                    setTextAndShow(layout_txt_total_amount, txt_total_amount, "Rs. " + Formatted_TotalAmount);
//
////                        setTextAndShow(layout_txt_transaction_charges, txt_transaction_charges, String.valueOf(response.getString("TransactionCharges")));
//
//                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
//                        txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("InvoiceNumber")).equals("") && !String.valueOf(response.get("InvoiceNumber")).equals("null"))
//                        txt_paymentID.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("CreatedDate")).split("T")[0].equals("") && !String.valueOf(response.get("CreatedDate")).split("T")[0].equals("null"))
//                        txt_created_date.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("PaidDate")).equals("") && !String.valueOf(response.get("PaidDate")).equals("null"))
//                        txt_confirm.setTextColor(getResources().getColor(R.color.textcolor));
////                        if (!String.valueOf(response.get("BankName")).equals("") && !String.valueOf(response.get("BankName")).equals("null"))
////                            txt_bank.setTextColor(getResources().getColor(R.color.textcolor));
////                        if (!String.valueOf(response.get("AuthID")).equals("") && !String.valueOf(response.get("AuthID")).equals("null"))
////                            txt_authorization_id.setTextColor(getResources().getColor(R.color.textcolor));
////                        if (!String.valueOf(response.get("SettlementID")).equals("") && !String.valueOf(response.get("SettlementID")).equals("null"))
////                            txt_settlement_id.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(InvoiceStatusKVP.get(response.getString("Status"))).equals("") && !String.valueOf(InvoiceStatusKVP.get(response.getString("Status"))).equals("null"))
//                        txt_status.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("PaidAmount")).equals("") && !String.valueOf(response.get("PaidAmount")).equals("null"))
//                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
////                        if (!String.valueOf(response.get("TransactionCharges")).equals("") && !String.valueOf(response.get("TransactionCharges")).equals("null"))
////                            txt_transaction_charges.setTextColor(getResources().getColor(R.color.textcolor));
//                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("TotalPrice")).equals("null"))
//                        txt_total_amount.setTextColor(getResources().getColor(R.color.textcolor));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        new HaballError().printErrorMessage(getContext(), error);
//                        new ProcessingError().showError(getContext());
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

    private void getUnpaidInvoiceData() {
        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                try {
                    invoiceID = response.getString("ID");
                    PrePaidNumber = response.getString("InvoiceNumber");
                    CompanyId = response.getString("CompanyId");
                    CompanyName = response.getString("CompanyName");
                    Amount = response.getString("TotalPrice");

                    String ConsolidateDetails = response.getString("ConsolidateDetails");
                    if (ConsolidateDetails.equals("[]")) {
                        setTextAndShow(layout_reference, txt_reference, String.valueOf(response.getString("ReferenceNumber")));
                        if (!String.valueOf(response.get("ReferenceNumber")).equals("") && !String.valueOf(response.get("ReferenceNumber")).equals("null"))
                            txt_reference.setTextColor(getResources().getColor(R.color.textcolor));
                    } else {
                        setTextAndShow(layout_reference, txt_reference, "Click here to view consolidated invoice references");
                        txt_reference.setTextColor(getResources().getColor(R.color.hyperlink));

                        SharedPreferences sharedPreferences = ((FragmentActivity) getContext()).getSharedPreferences("Consolidation_Details",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Consolidation_Details", ConsolidateDetails);
                        editor.apply();
                    }

                    txt_companyName.setText(String.valueOf(response.get("CompanyName")));
                    payment_id.setText(String.valueOf(response.get("InvoiceNumber")));

                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");

                    setTextAndShow(layout_due_date, txt_due_date, String.valueOf(response.getString("DueDate")).split("T")[0]);
                    String Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("TotalPrice")));
                    setTextAndShow(layout_txt_amount, txt_amount, "Rs. " + Formatted_TotalAmount);

                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("DueDate")).equals("") && !String.valueOf(response.get("DueDate")).equals("null"))
                        txt_due_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("TotalPrice")).equals("null"))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));

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

    }

    private void getPaidInvoiceData() {
        // Log.i("DistributorId invoice", DistributorId);
        // Log.i("Token invoice", Token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loader.hideLoader();
                try {
                    invoiceID = response.getString("ID");
                    PrePaidNumber = response.getString("InvoiceNumber");
                    CompanyId = response.getString("CompanyId");
                    CompanyName = response.getString("CompanyName");
                    Amount = response.getString("TotalPrice");

//                    String ConsolidateDetails = response.getString("ConsolidateDetails");
//                    if (ConsolidateDetails.equals("[]")) {
//                        setTextAndShow(layout_reference, txt_reference, String.valueOf(response.getString("ReferenceNumber")));
//                        if (!String.valueOf(response.get("ReferenceNumber")).equals("") && !String.valueOf(response.get("ReferenceNumber")).equals("null"))
//                            txt_reference.setTextColor(getResources().getColor(R.color.textcolor));
//                    } else {
//                        setTextAndShow(layout_reference, txt_reference, "Click here to view consolidated invoice references");
//                        txt_reference.setTextColor(getResources().getColor(R.color.hyperlink));
//
//                        SharedPreferences sharedPreferences = ((FragmentActivity) getContext()).getSharedPreferences("Consolidation_Details",
//                                Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("Consolidation_Details", ConsolidateDetails);
//                        editor.apply();
//                    }

                    txt_companyName.setText(String.valueOf(response.get("CompanyName")));
                    txt_paymentID.setText(String.valueOf(response.get("InvoiceNumber")));

                    if(String.valueOf(response.get("CreditDetails")).equals("[]")) {
                        rl_authorization_id.setVisibility(View.GONE);
                    } else {
                        setCreditDetails(String.valueOf(response.get("CreditDetails")));
                    }

                    DecimalFormat formatter1 = new DecimalFormat("#,###,##0.00");

                    setTextAndShow(layout_txt_created_date, txt_created_date, String.valueOf(response.getString("CreatedDate")).split("T")[0]);
                    setTextAndShow(layout_due_date, txt_due_date, String.valueOf(response.getString("DueDate")).split("T")[0]);
                    setTextAndShow(layout_txt_status, txt_status, InvoiceStatus);
                    String Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("TotalPrice")));
                    setTextAndShow(layout_txt_amount, txt_amount, "Rs. " + Formatted_TotalAmount);
                    Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("PaidAmount")));
                    setTextAndShow(layout_paid_amount, txt_paid_amount, "Rs. " + Formatted_TotalAmount);
                    Formatted_TotalAmount = formatter1.format(Double.parseDouble(response.getString("RemainingAmount")));
                    setTextAndShow(layout_remaining_amount, txt_remaining_amount, "Rs. " + Formatted_TotalAmount);

                    if (!String.valueOf(response.get("CompanyName")).equals("") && !String.valueOf(response.get("CompanyName")).equals("null"))
                        txt_companyName.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("InvoiceNumber")).equals("") && !String.valueOf(response.get("InvoiceNumber")).equals("null"))
                        txt_paymentID.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("CreatedDate")).equals("") && !String.valueOf(response.get("CreatedDate")).equals("null"))
                        txt_created_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("DueDate")).equals("") && !String.valueOf(response.get("DueDate")).equals("null"))
                        txt_due_date.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(InvoiceStatus).equals("") && !String.valueOf(InvoiceStatus).equals("null"))
                        txt_status.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("TotalPrice")).equals("null"))
                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("PaidAmount")).equals("null"))
                        txt_paid_amount.setTextColor(getResources().getColor(R.color.textcolor));
                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("RemainingAmount")).equals("null"))
                        txt_remaining_amount.setTextColor(getResources().getColor(R.color.textcolor));

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

    }

    private void setCreditDetails(String Credit_Details) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(Credit_Details);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<DistributorCreditDetailsModel>>() {
        }.getType();
        Gson gson = new Gson();
        if (jsonArray != null) {
            creditDetailsList = gson.fromJson(jsonArray.toString(), type);
        }

        titles = new ArrayList<>();
        // Log.i("result", String.valueOf(result));
        for (DistributorCreditDetailsModel ConsolidationDetail : creditDetailsList) {
////            Log.i("debug_consolidate", ConsolidationDetail.getInvoiceNumber());
//            Object item = ConsolidationDetail;
////            if (item instanceof JSONObject) {
//                DistributorConsolidationDetailsParentModel tempModel = gson.fromJson(((JSONObject) item).toString(), DistributorConsolidationDetailsParentModel.class);
//                titles.add(tempModel);
////            }
            DistributorCreditDetailsParentModel tempModel = new DistributorCreditDetailsParentModel(null, UUID.nameUUIDFromBytes(ConsolidationDetail.getID().getBytes()),null,ConsolidationDetail.getCreditNumber(),ConsolidationDetail.getID(),null);
            titles.add(tempModel);
        }

        final ParentListAdapter_CreditDetails adapter = new ParentListAdapter_CreditDetails(getActivity(), initData(), creditDetailsList);
        adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @UiThread
            @Override
            public void onParentExpanded(int parentPosition) {
                if (lastExpandedPosition != -1
                        && parentPosition != lastExpandedPosition) {
                    adapter.collapseParent(lastExpandedPosition);
                }
                lastExpandedPosition = parentPosition;
                if (height < 1500) {
//                    recyclerView.setPadding(0, 0, 0, 500);
                }
            }

            @UiThread
            @Override
            public void onParentCollapsed(int parentPosition) {
                if (height < 1500) {
//                    recyclerView.setPadding(0, 0, 0, 0);
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }


    private List<DistributorCreditDetailsParentModel> initData() {
        List<DistributorCreditDetailsParentModel> parentObjects = new ArrayList<>();
        int i = 0;
        for (DistributorCreditDetailsParentModel title : titles) {
            List<Object> childlist = new ArrayList<>();
//            for (DistributorConsolidationDetailsModel product : consolidationDetailsList) {
            childlist.add(creditDetailsList.get(i++));
//            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }
//
//    private void getUnPaidInvoiceData() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginToken",
//                Context.MODE_PRIVATE);
//        Token = sharedPreferences.getString("Login_Token", "");
//        if (!URL_Order_Data.contains("/" + orderID)) {
//            URL_Order_Data = URL_Order_Data + orderID;
//            // Log.i("URL_Payment_Data", URL_Order_Data);
//        }
////        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("LoginToken",
////                Context.MODE_PRIVATE);
////        DistributorId = sharedPreferences1.getString("Distributor_Id", "");
////        // Log.i("DistributorId invoice", DistributorId);
//        // Log.i("Token invoice12", Token);
//        new SSL_HandShake().handleSSLHandshake();
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL_Order_Data, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject result) {
//                loader.hideLoader();
//                // Log.i("Order Data_UnPaid", String.valueOf(result));
//                try {
//                    JSONObject response = result.getJSONObject("Invoice");
//                    invoiceID = response.getString("ID");
//                    CompanyName = String.valueOf(result.get("CompanyName"));
//                    spinner_companyName.setSelection(CompanyNames.indexOf(CompanyName));
//                    PrePaidNumber = String.valueOf(response.get("InvoiceNumber"));
//                    Amount = String.valueOf(response.get("TotalPrice"));
//                    txt_amount.setText(Amount);
//                    payment_id.setText(PrePaidNumber);
////                    PrePaidId = String.valueOf(response.get("InvoiceTotalAmount"));
//                    if (!String.valueOf(response.get("TotalPrice")).equals("") && !String.valueOf(response.get("TotalPrice")).equals("null"))
//                        txt_amount.setTextColor(getResources().getColor(R.color.textcolor));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        new HaballError().printErrorMessage(getContext(), error);
//                        new ProcessingError().showError(getContext());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "bearer " + Token);
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getContext()).add(stringRequest);
//
//    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
//                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    Intent login_intent = new Intent(((FragmentActivity) getContext()), DistributorDashboard.class);
                    ((FragmentActivity) getContext()).startActivity(login_intent);
                    ((FragmentActivity) getContext()).finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void setTextAndShow(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value);
        }
    }

    private void setTextAndShowDate(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value.split("T")[0]);
        }
    }

    private void printErrMessage(VolleyError error) {
        if (getContext() != null) {
            if (error instanceof NetworkError) {
                Toast.makeText(getContext(), "Network Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ServerError) {
                Toast.makeText(getContext(), "Server Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof AuthFailureError) {
                Toast.makeText(getContext(), "Auth Failure Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof ParseError) {
                Toast.makeText(getContext(), "Parse Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof NoConnectionError) {
                Toast.makeText(getContext(), "No Connection Error !", Toast.LENGTH_LONG).show();
            } else if (error instanceof TimeoutError) {
                Toast.makeText(getContext(), "Timeout Error !", Toast.LENGTH_LONG).show();
            }

            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    String message = "";
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    // Log.i("responseBody", responseBody);
                    JSONObject data = new JSONObject(responseBody);
                    // Log.i("data", String.valueOf(data));
                    Iterator<String> keys = data.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        message = message + data.get(key) + "\n";
                    }
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}