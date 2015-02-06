package com.andre.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.andre.store.models.ModelOrder;
import com.andre.store.view.R;
import com.andre.store.view.SummaryOrder;

import java.util.ArrayList;

/**
 * Created by Andre on 2/6/2015.
 */
public class AdapterSummary extends BaseAdapter {
    Context context;
    ArrayList<ModelOrder> summaryData = new ArrayList<ModelOrder>();

    public AdapterSummary(Context context, ArrayList<ModelOrder> list) {
        this.context = context;
        summaryData = list;
    }

    @Override
    public int getCount() {
        return summaryData.size();
    }

    @Override
    public Object getItem(int i) {
        return summaryData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_summary, viewGroup, false);
        TextView codeSummary = (TextView) view.findViewById(R.id.codeSummary);
        TextView nameSummary = (TextView) view.findViewById(R.id.nameSummary);
        TextView stockSummary = (TextView) view.findViewById(R.id.stockSummary);
        TextView priceSummary = (TextView) view.findViewById(R.id.priceSummary);
        TextView quantitySummary = (TextView) view.findViewById(R.id.quantitySummary);
        TextView buySummary = (TextView) view.findViewById(R.id.buySummary);

        if (summaryData != null && summaryData.size() != 0) {
            codeSummary.setText(summaryData.get(i).getCode());
            nameSummary.setText(summaryData.get(i).getNameOrder());
            stockSummary.setText("" + summaryData.get(i).getStock());
            priceSummary.setText("" + summaryData.get(i).getPrice());
            quantitySummary.setText("" + summaryData.get(i).getQuantity());
            int totalBuy = (summaryData.get(i).getPrice() * summaryData.get(i).getQuantity());
            buySummary.setText(context.getString(R.string.rp) + totalBuy + ",00");
        }
        return view;
    }
}
