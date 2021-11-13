package com.haball.Distributor.ui.main.invoice;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.R;

import java.util.List;

public class ParentListAdapter_CreditDetails extends ExpandableRecyclerAdapter<DistributorCreditDetailsParentModel, DistributorCreditDetailsModel, CreditDetails_VH, CreditDetailsChild_VH> {
    LayoutInflater inflater;
    private final Context context;
    private String object_string, object_stringqty;
    private int parentPosition = -1;
    private List<DistributorCreditDetailsModel> consolidationDetailsList;
    private List<DistributorCreditDetailsParentModel> parentItemList;

    public ParentListAdapter_CreditDetails(Context context, List<DistributorCreditDetailsParentModel> parentItemList, List<DistributorCreditDetailsModel> consolidationDetailsList) {
        super(parentItemList);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.parentItemList = parentItemList;
        this.consolidationDetailsList = consolidationDetailsList;

//        for (DistributorConsolidationDetailsParentModel ConsolidationDetail : parentItemList) {
//            Log.i("debug_consolidate", String.valueOf(ConsolidationDetail.getInvoiceNumber()));
//        }

    }

    @Override
    public CreditDetails_VH onCreateParentViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        return new CreditDetails_VH(view);

    }


    @Override
    public CreditDetailsChild_VH onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.creditdetailschildlist_expand, viewGroup, false);
        return new CreditDetailsChild_VH(view);

    }

    @Override
    public void onBindParentViewHolder(@NonNull final CreditDetails_VH creditDetails_VH, final int position, @NonNull DistributorCreditDetailsParentModel o) {
        creditDetails_VH._textview.setText("Authorization ID: " + o.getInvoiceNumber());
        Log.i("debug_consolidate", String.valueOf("Authorization ID: " + o.getInvoiceNumber()));
    }


    @Override
    public void onBindChildViewHolder(@NonNull final CreditDetailsChild_VH creditDetailsChild_VH, int pos, final int i, @NonNull DistributorCreditDetailsModel o) {
        DistributorCreditDetailsModel distributorCreditDetailsModel = (DistributorCreditDetailsModel) o;
        final CreditDetailsChild_VH temp_orderChildList_vh = creditDetailsChild_VH;

        setTextAndShow(creditDetailsChild_VH.layout_settlement_id, creditDetailsChild_VH.txt_settlement_id, String.valueOf(distributorCreditDetailsModel.getSettlementId()));
        setTextAndShow(creditDetailsChild_VH.layout_settlement_date, creditDetailsChild_VH.txt_settlement_date, String.valueOf(distributorCreditDetailsModel.getSettlementDate()).split("T")[0]);
        setTextAndShow(creditDetailsChild_VH.layout_transaction_date, creditDetailsChild_VH.txt_transaction_date, String.valueOf(distributorCreditDetailsModel.getLastChangedDate()).split("T")[0]);
        setTextAndShow(creditDetailsChild_VH.layout_payment_channel, creditDetailsChild_VH.txt_payment_channel, String.valueOf(distributorCreditDetailsModel.getPaymentChannel()));
        setTextAndShow(creditDetailsChild_VH.layout_paid_amount, creditDetailsChild_VH.txt_paid_amount, String.valueOf(distributorCreditDetailsModel.getTotalPrice()));
        setTextAndShow(creditDetailsChild_VH.layout_transaction_charges, creditDetailsChild_VH.txt_transaction_charges, String.valueOf(distributorCreditDetailsModel.getPaymentCharges()));
        setTextAndShow(creditDetailsChild_VH.layout_total_amount, creditDetailsChild_VH.txt_total_amount, String.valueOf(distributorCreditDetailsModel.getTotalPrice()));
    }

    private void setTextAndShow(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value);
            editText.setTextColor(context.getResources().getColor(R.color.textcolor));
        } else {
            layout.setVisibility(View.VISIBLE);
            layout.setDefaultHintTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.edit_text_hint_color)));
        }
    }
}
