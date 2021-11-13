package com.haball.Distributor.ui.main.invoice;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ChildViewHolder;
import com.haball.R;

public class CreditDetailsChild_VH extends ChildViewHolder {
    TextInputLayout layout_settlement_id, layout_settlement_date, layout_transaction_date, layout_payment_channel, layout_paid_amount, layout_transaction_charges, layout_total_amount;
    TextInputEditText txt_settlement_id, txt_settlement_date, txt_transaction_date, txt_payment_channel, txt_paid_amount, txt_transaction_charges, txt_total_amount;

    public CreditDetailsChild_VH(View itemView) {
        super(itemView);
        layout_settlement_id = itemView.findViewById(R.id.layout_settlement_id);
        layout_settlement_date = itemView.findViewById(R.id.layout_settlement_date);
        layout_transaction_date = itemView.findViewById(R.id.layout_transaction_date);
        layout_payment_channel = itemView.findViewById(R.id.layout_payment_channel);
        layout_paid_amount = itemView.findViewById(R.id.layout_paid_amount);
        layout_transaction_charges = itemView.findViewById(R.id.layout_transaction_charges);
        layout_total_amount = itemView.findViewById(R.id.layout_total_amount);

        txt_settlement_id = itemView.findViewById(R.id.txt_settlement_id);
        txt_settlement_date = itemView.findViewById(R.id.txt_settlement_date);
        txt_transaction_date = itemView.findViewById(R.id.txt_transaction_date);
        txt_payment_channel = itemView.findViewById(R.id.txt_payment_channel);
        txt_paid_amount = itemView.findViewById(R.id.txt_paid_amount);
        txt_transaction_charges = itemView.findViewById(R.id.txt_transaction_charges);
        txt_total_amount = itemView.findViewById(R.id.txt_total_amount);
    }
}

