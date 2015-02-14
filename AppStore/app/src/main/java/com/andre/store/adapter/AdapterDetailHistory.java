package com.andre.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.andre.store.models.ModelDetailHistory;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 2/6/2015.
 */
public class AdapterDetailHistory extends BaseAdapter {
    Context context;
    ArrayList<ModelDetailHistory> modelDetailHistories = new ArrayList<ModelDetailHistory>();

    public AdapterDetailHistory(Context context, ArrayList<ModelDetailHistory> modelDetailHistories) {
        this.context = context;
        this.modelDetailHistories = modelDetailHistories;
    }

    @Override
    public int getCount() {
        return modelDetailHistories.size();
    }

    @Override
    public Object getItem(int i) {
        return modelDetailHistories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_detail_history, viewGroup, false);
        TextView codeDetail = (TextView) view.findViewById(R.id.codeDetailHistory);
        TextView nameDetail = (TextView) view.findViewById(R.id.nameDetailHistory);
        TextView quantityDetail = (TextView) view.findViewById(R.id.quantityDetailHistory);
        TextView buyDetail = (TextView) view.findViewById(R.id.buyDetailHistory);
        TextView priceDetail = (TextView) view.findViewById(R.id.priceDetailHistory);
        if (modelDetailHistories != null) {
            codeDetail.setText(modelDetailHistories.get(i).getCode());
            nameDetail.setText(modelDetailHistories.get(i).getNameDetailHistory());
            quantityDetail.setText("" + modelDetailHistories.get(i).getQuantity());
            priceDetail.setText("" + modelDetailHistories.get(i).getPrice());
            int buy = ((modelDetailHistories.get(i).getQuantity())
                    * (modelDetailHistories.get(i).getPrice()));
            buyDetail.setText(context.getString(R.string.rp) + buy + ",00");
        }
        return view;
    }
}
