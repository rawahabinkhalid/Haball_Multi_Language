package com.haball.Distributor.ui.main.invoice;

import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Parent;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;

public class DistributorCreditDetailsModel {
    String CompanyId;
    String CreatedBy;
    String CreatedDate;
    String CreditNumber;
    String DistributorId;
    String DueDate;
    String ID;
    String InvoiceNumber;
    String LastChangedBy;
    String LastChangedDate;
    String PaymentChannel;
    String PaymentCharges;
    String ReferenceID;
    String ReferenceNumber;
    String SAPDocumentDate;
    String SAPDocumentNo;
    String SettlementDate;
    String SettlementId;
    String Status;
    String TotalPrice;

    public DistributorCreditDetailsModel(String companyId, String createdBy, String createdDate, String creditNumber, String distributorId, String dueDate, String ID, String invoiceNumber, String lastChangedBy, String lastChangedDate, String paymentChannel, String paymentCharges, String referenceID, String referenceNumber, String SAPDocumentDate, String SAPDocumentNo, String settlementDate, String settlementId, String status, String totalPrice) {
        CompanyId = companyId;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        CreditNumber = creditNumber;
        DistributorId = distributorId;
        DueDate = dueDate;
        this.ID = ID;
        InvoiceNumber = invoiceNumber;
        LastChangedBy = lastChangedBy;
        LastChangedDate = lastChangedDate;
        PaymentChannel = paymentChannel;
        PaymentCharges = paymentCharges;
        ReferenceID = referenceID;
        ReferenceNumber = referenceNumber;
        this.SAPDocumentDate = SAPDocumentDate;
        this.SAPDocumentNo = SAPDocumentNo;
        SettlementDate = settlementDate;
        SettlementId = settlementId;
        Status = status;
        TotalPrice = totalPrice;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreditNumber() {
        return CreditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        CreditNumber = creditNumber;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
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

    public String getLastChangedBy() {
        return LastChangedBy;
    }

    public void setLastChangedBy(String lastChangedBy) {
        LastChangedBy = lastChangedBy;
    }

    public String getLastChangedDate() {
        return LastChangedDate;
    }

    public void setLastChangedDate(String lastChangedDate) {
        LastChangedDate = lastChangedDate;
    }

    public String getPaymentChannel() {
        return PaymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        PaymentChannel = paymentChannel;
    }

    public String getPaymentCharges() {
        return PaymentCharges;
    }

    public void setPaymentCharges(String paymentCharges) {
        PaymentCharges = paymentCharges;
    }

    public String getReferenceID() {
        return ReferenceID;
    }

    public void setReferenceID(String referenceID) {
        ReferenceID = referenceID;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public String getSAPDocumentDate() {
        return SAPDocumentDate;
    }

    public void setSAPDocumentDate(String SAPDocumentDate) {
        this.SAPDocumentDate = SAPDocumentDate;
    }

    public String getSAPDocumentNo() {
        return SAPDocumentNo;
    }

    public void setSAPDocumentNo(String SAPDocumentNo) {
        this.SAPDocumentNo = SAPDocumentNo;
    }

    public String getSettlementDate() {
        return SettlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        SettlementDate = settlementDate;
    }

    public String getSettlementId() {
        return SettlementId;
    }

    public void setSettlementId(String settlementId) {
        SettlementId = settlementId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
}

