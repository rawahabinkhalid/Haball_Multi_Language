package com.haball.Distributor.ui.main.invoice;

import android.content.Context;
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

public class ParentListAdapter_ConsolidatedDetails extends ExpandableRecyclerAdapter<DistributorConsolidationDetailsParentModel, DistributorConsolidationDetailsModel, ConsolidatedDetails_VH, ConsolidatedDetailsChild_VH> {
    LayoutInflater inflater;
    private final Context context;
    private String object_string, object_stringqty;
    private int parentPosition = -1;
    private List<DistributorConsolidationDetailsModel> consolidationDetailsList;
    private List<DistributorConsolidationDetailsParentModel> parentItemList;

    public ParentListAdapter_ConsolidatedDetails(Context context, List<DistributorConsolidationDetailsParentModel> parentItemList, List<DistributorConsolidationDetailsModel> consolidationDetailsList) {
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
    public ConsolidatedDetails_VH onCreateParentViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.parentlist_retailer_order, viewGroup, false);
        return new ConsolidatedDetails_VH(view);

    }


    @Override
    public ConsolidatedDetailsChild_VH onCreateChildViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.consolidateddetailschildlist_expand, viewGroup, false);
        return new ConsolidatedDetailsChild_VH(view);

    }

    @Override
    public void onBindParentViewHolder(@NonNull final ConsolidatedDetails_VH consolidatedDetails_VH, final int position, @NonNull DistributorConsolidationDetailsParentModel o) {
        consolidatedDetails_VH._textview.setText("Payment ID: " + o.getInvoiceNumber());
        Log.i("debug_consolidate", String.valueOf("Payment ID: " + o.getInvoiceNumber()));
    }


    @Override
    public void onBindChildViewHolder(@NonNull final ConsolidatedDetailsChild_VH consolidatedDetailsChild_VH, int pos, final int i, @NonNull DistributorConsolidationDetailsModel o) {
        DistributorConsolidationDetailsModel distributorConsolidationDetailsModel = (DistributorConsolidationDetailsModel) o;
        final ConsolidatedDetailsChild_VH temp_orderChildList_vh = consolidatedDetailsChild_VH;

        setTextAndShow(consolidatedDetailsChild_VH.layout_reference, consolidatedDetailsChild_VH.txt_reference, String.valueOf(distributorConsolidationDetailsModel.getInvoiceNumber()));
        setTextAndShow(consolidatedDetailsChild_VH.layout_amount, consolidatedDetailsChild_VH.txt_amount, String.valueOf(distributorConsolidationDetailsModel.getTotalPrice()));
        setTextAndShow(consolidatedDetailsChild_VH.layout_created_date, consolidatedDetailsChild_VH.txt_created_date, String.valueOf(distributorConsolidationDetailsModel.getCreatedDate()).split("T")[0]);
    }

    private void setTextAndShow(TextInputLayout layout, TextInputEditText editText, String value) {
        if (!value.equals("null")) {
            layout.setVisibility(View.VISIBLE);
            editText.setText(value);
            editText.setTextColor(context.getResources().getColor(R.color.textcolor));
        }
    }
}
