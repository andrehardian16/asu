package com.andre.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.andre.store.models.ModelHistory;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 2/6/2015.
 */
public class AdapterHistory extends BaseAdapter{
    Context context;
    ArrayList<ModelHistory> historyData;
    public AdapterHistory(Context context, ArrayList<ModelHistory> historyData) {
        this.context = context;
        this.historyData = historyData;
    }

    @Override
    public int getCount() {
        return historyData.size();
    }

    @Override
    public Object getItem(int i) {
        return historyData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_history,viewGroup,false);
        TextView lastDate = (TextView)view.findViewById(R.id.lastDate);
        TextView quantity = (TextView)view.findViewById(R.id.quantityHistory);
        TextView total = (TextView) view.findViewById(R.id.totalHistory);

        if (historyData != null && historyData.size() != 0){
            lastDate.setText(historyData.get(i).getLastDate());
            quantity.setText(historyData.get(i).getQuantity());
            total.setText(historyData.get(i).getTotal());
        }
        return null;
    }
}
