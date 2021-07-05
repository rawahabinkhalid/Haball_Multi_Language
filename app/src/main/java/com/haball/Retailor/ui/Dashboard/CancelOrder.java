package com.haball.Retailor.ui.Dashboard;

        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Typeface;
        import android.text.SpannableString;
        import android.text.style.StyleSpan;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.HurlStack;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
        import com.haball.Loader;
        import com.haball.ProcessingError;
        import com.haball.R;
        import com.haball.Retailor.RetailorDashboard;
        import com.haball.Retailor.ui.Make_Payment.PaymentScreen3Fragment_Retailer;
        import com.haball.SSL_HandShake;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;
        import java.util.Map;

        import androidx.fragment.app.FragmentActivity;
        import androidx.fragment.app.FragmentTransaction;

public class CancelOrder {
    public String URL_CANCEL_ORDER = "https://175.107.203.97:4014/api/orders/cancelorder";
    public String Token;
    public Context mContext;
    private FragmentTransaction fragmentTransaction;
    private Loader loader;

    public CancelOrder() {
    }

    public void cancelOrder(final Context context, final String orderId, final String orderNumber) throws JSONException {
        loader = new Loader(context);
        loader.showLoader();

        mContext = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginToken",
                Context.MODE_PRIVATE);
        Token = sharedPreferences.getString("Login_Token", "");
        // Log.i("Token", Token);

        JSONObject map = new JSONObject();
        map.put("ID", orderId);
        map.put("Status", 4);

        final Context finalcontext = context;

        new SSL_HandShake().handleSSLHandshake();
//        final HurlStack hurlStack = new SSL_HandShake().handleSSLHandshake(context);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CANCEL_ORDER, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO handle the response
                loader.hideLoader();

                final Dialog fbDialogue = new Dialog(mContext);
                //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.password_updatepopup);
                TextView tv_pr1, txt_header1;
                txt_header1 = fbDialogue.findViewById(R.id.txt_header1);
                tv_pr1 = fbDialogue.findViewById(R.id.txt_details);
//                tv_pr1.setText("Your Order ID " + orderNumber + " has been cancelled successfully.");
//                txt_header1.setText("Order Cancelled");
//                tv_pr1.setText((R.string.your_order_id) + (orderNumber) + (R.string.cancelled_order_msg));
                String tempStr = context.getResources().getString(R.string.your_order_id) + " " + orderNumber + " " + context.getResources().getString(R.string.cancelled_order_msg);
                tv_pr1.setText(tempStr);
                txt_header1.setText(R.string.cancelled_order);
                fbDialogue.setCancelable(true);
                fbDialogue.getWindow().setGravity(Gravity.TOP | Gravity.START | Gravity.END);
                WindowManager.LayoutParams layoutParams = fbDialogue.getWindow().getAttributes();
                layoutParams.y = 200;
                layoutParams.x = -70;// top margin
                fbDialogue.getWindow().setAttributes(layoutParams);
                fbDialogue.show();

                ImageButton close_button = fbDialogue.findViewById(R.id.image_button);
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fbDialogue.dismiss();
                    }
                });

                fbDialogue.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        SharedPreferences tabsFromDraft = context.getSharedPreferences("OrderTabsFromDraft",
                                Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOrderTabsFromDraft = tabsFromDraft.edit();
                        editorOrderTabsFromDraft.putString("TabNo", "1");
                        editorOrderTabsFromDraft.apply();

                        Intent login_intent = new Intent(((FragmentActivity) context), RetailorDashboard.class);
                        ((FragmentActivity) context).startActivity(login_intent);
                        ((FragmentActivity) context).finish();
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.hideLoader();
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(request);

    }

}

