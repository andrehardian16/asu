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

import java.util.ArrayList;

/**
 * Created by Andre on 2/3/2015.
 */
public class AdapterListOrder extends BaseAdapter {
    Context context;
    ArrayList<ModelOrder> orderArrayAdapter = new ArrayList<ModelOrder>();
    ArrayList<ModelOrder> filter = new ArrayList<ModelOrder>();

    public AdapterListOrder(Context context, ArrayList<ModelOrder> orderArrayList) {
        this.context = context;
        orderArrayAdapter = orderArrayList;
        filter = orderArrayList;
    }

    @Override
    public int getCount() {
        return orderArrayAdapter.size();
    }

    @Override
    public Object getItem(int i) {
        return orderArrayAdapter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_list_order, viewGroup, false);
        TextView codeOrder;
        TextView stockOrder;
        TextView nameOrder;
        TextView priceOrder;

        codeOrder = (TextView) view.findViewById(R.id.codeListOrder);
        stockOrder = (TextView) view.findViewById(R.id.stockListOrder);
        nameOrder = (TextView) view.findViewById(R.id.nameListOrder);
        priceOrder = (TextView) view.findViewById(R.id.priceListOrder);

        if (orderArrayAdapter != null) {
            codeOrder.setText(orderArrayAdapter.get(i).getCode());
            stockOrder.setText("" + orderArrayAdapter.get(i).getStock());
            nameOrder.setText(orderArrayAdapter.get(i).getNameOrder());
            priceOrder.setText("" + orderArrayAdapter.get(i).getPrice());
        }
        return view;
    }


}