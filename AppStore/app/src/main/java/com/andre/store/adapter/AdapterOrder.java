package com.andre.store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.andre.store.models.ModelOrder;
import com.andre.store.view.Order;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 2/3/2015.
 */
public class AdapterOrder extends BaseAdapter{
    Context context;
    ArrayList<ModelOrder> orderArrayList = new ArrayList<ModelOrder>();

    public AdapterOrder(Context context, ArrayList<ModelOrder> order) {
        this.context = context;
        orderArrayList = order;
    }

    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_order_store, viewGroup, false);
        TextView codeOrder;
        TextView nameOrder;
        TextView priceOrder;
        TextView stockOrder;
        TextView quantityOrder;
        TextView buyOrder;
        Button delOrder;

        codeOrder = (TextView) view.findViewById(R.id.codeOrder);
        nameOrder = (TextView) view.findViewById(R.id.nameOrder);
        priceOrder = (TextView) view.findViewById(R.id.priceOrder);
        stockOrder = (TextView) view.findViewById(R.id.stockOrder);
        quantityOrder = (TextView) view.findViewById(R.id.quantityOrder);
        buyOrder = (TextView) view.findViewById(R.id.buy);
        delOrder = (Button)view.findViewById(R.id.delOrder);
        delOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderArrayList.remove(i);
                notifyDataSetChanged();
            }
        });

        if (orderArrayList != null) {
            codeOrder.setText(orderArrayList.get(i).getCode());
            nameOrder.setText(orderArrayList.get(i).getNameOrder());
            priceOrder.setText("" + orderArrayList.get(i).getPrice());
            stockOrder.setText("" + orderArrayList.get(i).getStock());
            quantityOrder.setText(""+orderArrayList.get(i).getQuantity());
            int buy = (Integer.parseInt(quantityOrder.getText().toString().trim()))
                    * (Integer.parseInt(priceOrder.getText().toString().trim()));
            buyOrder.setText(context.getString(R.string.rp) + buy + ",00");

            orderArrayList.get(i).setBuy(buy);
        }
        return view;
    }




}
