package com.haball.Distributor.ui.main.invoice;

import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Parent;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;

public class DistributorConsolidationDetailsModel {
    private String CreatedDate;
    private String DueDate;
    private String ID;
    private String InvoiceNumber;
    private String PaidAmount;
    private String PaidDate;
    private String ParentInvoice;
    private String ReferenceNumber;
    private String TotalPrice;


    public DistributorConsolidationDetailsModel(String createdDate, String dueDate, String ID, String invoiceNumber, String paidAmount, String paidDate, String parentInvoice, String referenceNumber, String totalPrice) {
        CreatedDate = createdDate;
        DueDate = dueDate;
        this.ID = ID;
        InvoiceNumber = invoiceNumber;
        PaidAmount = paidAmount;
        PaidDate = paidDate;
        ParentInvoice = parentInvoice;
        ReferenceNumber = referenceNumber;
        TotalPrice = totalPrice;
    }


    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getPaidAmount() {
        return PaidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        PaidAmount = paidAmount;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getParentInvoice() {
        return ParentInvoice;
    }

    public void setParentInvoice(String parentInvoice) {
        ParentInvoice = parentInvoice;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}
