package com.haball.Distributor.ui.main.invoice;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haball.Distributor.ui.orders.OrdersTabsNew.ExpandableRecyclerAdapter;
import com.haball.R;
import com.haball.Retailor.ui.Place_Order.ui.main.Adapters.ParentListAdapter;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderChildlist_Model;
import com.haball.Retailor.ui.Place_Order.ui.main.Models.OrderParentlist_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Fragment_Distributor_Consolidation_Details extends Fragment {

    private Typeface myFont;
    private FragmentTransaction fragmentTransaction;
    private Button close_button;
    private List<DistributorConsolidationDetailsModel> consolidationDetailsList;
    private List<DistributorConsolidationDetailsParentModel> titles = new ArrayList<>();
    private List<String> totalCategoryTitle = new ArrayList<>();
    private HashMap<String, String> Categories = new HashMap<>();
    private int lastExpandedPosition = -1;
    int width;
    int height;
    RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_distributor_consolidation_details, container, false);
        myFont = ResourcesCompat.getFont(getContext(), R.font.open_sans);
        close_button = root.findViewById(R.id.close_button);
        recyclerView = root.findViewById(R.id.rv_consolidation_details_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.main_container, new ViewInvoice()).addToBackStack("tag");
                fragmentTransaction.commit();
            }
        });

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Consolidation_Details",
                Context.MODE_PRIVATE);
        String Consolidation_Details = sharedPreferences.getString("Consolidation_Details", "");

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(Consolidation_Details);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<DistributorConsolidationDetailsModel>>() {
        }.getType();
        Gson gson = new Gson();
        if (jsonArray != null) {
            consolidationDetailsList = gson.fromJson(jsonArray.toString(), type);
        }

        titles = new ArrayList<>();
        // Log.i("result", String.valueOf(result));
        for (DistributorConsolidationDetailsModel ConsolidationDetail : consolidationDetailsList) {
////            Log.i("debug_consolidate", ConsolidationDetail.getInvoiceNumber());
//            Object item = ConsolidationDetail;
////            if (item instanceof JSONObject) {
//                DistributorConsolidationDetailsParentModel tempModel = gson.fromJson(((JSONObject) item).toString(), DistributorConsolidationDetailsParentModel.class);
//                titles.add(tempModel);
////            }
                DistributorConsolidationDetailsParentModel tempModel = new DistributorConsolidationDetailsParentModel(null,UUID.nameUUIDFromBytes(ConsolidationDetail.getID().getBytes()),null,ConsolidationDetail.getInvoiceNumber(),ConsolidationDetail.getID(),null);
                titles.add(tempModel);
        }

        final ParentListAdapter_ConsolidatedDetails adapter = new ParentListAdapter_ConsolidatedDetails(getActivity(), initData(), consolidationDetailsList);
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


        return root;
    }

    private List<DistributorConsolidationDetailsParentModel> initData() {
        List<DistributorConsolidationDetailsParentModel> parentObjects = new ArrayList<>();
        int i = 0;
        for (DistributorConsolidationDetailsParentModel title : titles) {
            List<Object> childlist = new ArrayList<>();
//            for (DistributorConsolidationDetailsModel product : consolidationDetailsList) {
            childlist.add(consolidationDetailsList.get(i++));
//            }
            title.setChildList(childlist);
            parentObjects.add(title);
        }
        return parentObjects;
    }
}


