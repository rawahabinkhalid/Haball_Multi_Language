package com.haball.Distributor.ui.main.invoice;

import com.bignerdranch.expandablerecyclerview.model.SimpleParent;
import com.haball.Distributor.ui.orders.OrdersTabsNew.Parent;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;

import java.util.List;
import java.util.UUID;

public class DistributorConsolidationDetailsParentModel extends SimpleParent<DistributorConsolidationDetailsModel> implements Parent<DistributorConsolidationDetailsModel> {
    private List<Object> myCHildrenList;
    private UUID _id;
    private String CategoryId;
    private String InvoiceNumber;
    private String ParentId;
    public Boolean expanded = false;

    public DistributorConsolidationDetailsParentModel(List<Object> myCHildrenList, UUID _id, String categoryId, String invoiceNumber, String parentId, Boolean expanded) {
        super(null);
        this.myCHildrenList = myCHildrenList;
        this._id = _id;
        CategoryId = categoryId;
        InvoiceNumber = invoiceNumber;
        ParentId = parentId;
        this.expanded = expanded;
    }

    protected DistributorConsolidationDetailsParentModel(List<DistributorConsolidationDetailsModel> childItemList) {
        super(childItemList);
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.InvoiceNumber = invoiceNumber;
    }

    @Override
    public List getChildList() {
        return myCHildrenList;
    }

    @Override
    public void setChildList(List list) {
        myCHildrenList = list;
    }
}
