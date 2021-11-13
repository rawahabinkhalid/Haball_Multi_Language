package com.haball.Distributor.ui.main.invoice;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ChildViewHolder;
import com.haball.R;

public class ConsolidatedDetailsChild_VH extends ChildViewHolder {
    TextInputLayout layout_reference, layout_amount, layout_created_date;
    TextInputEditText txt_reference, txt_amount, txt_created_date;

    public ConsolidatedDetailsChild_VH(View itemView) {
        super(itemView);
        layout_reference = itemView.findViewById(R.id.layout_reference);
        layout_amount = itemView.findViewById(R.id.layout_amount);
        layout_created_date = itemView.findViewById(R.id.layout_created_date);

        txt_reference = itemView.findViewById(R.id.txt_reference);
        txt_amount = itemView.findViewById(R.id.txt_amount);
        txt_created_date = itemView.findViewById(R.id.txt_created_date);
    }
}

