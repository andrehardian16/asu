package com.andre.store.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.andre.store.adapter.AdapterListOrder;
import com.andre.store.dao.DaoOrder;
import com.andre.store.models.ModelOrder;
import com.andre.store.models.ModelStore;
import com.andre.store.view.Order;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogSpinner extends DialogFragment {

    ArrayList<ModelOrder> orderArrayList = new ArrayList<ModelOrder>();
    ArrayList<ModelOrder> order = new ArrayList<ModelOrder>();
    ListView listOrder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataOrder();
        return inflater.inflate(R.layout.list_order, null);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dataOrder();
        AlertDialog.Builder select = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.list_order, null);
        listOrder = (ListView) view.findViewById(R.id.orderList);
        AdapterListOrder adapterListOrder = new AdapterListOrder(getActivity(), orderArrayList);
        listOrder.setAdapter(adapterListOrder);
        select.setView(view);
        listOrder.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        AdapterListOrder adapterListOrder = (AdapterListOrder) listOrder.getAdapter();
                        ModelOrder modelOrder = (ModelOrder) adapterListOrder.getItem(i);
                        ModelStore modelStore = (ModelStore) getActivity().getIntent().getSerializableExtra("model");
                        modelOrder.setIdStore(modelStore.getIdStore());
                        ArrayList<ModelOrder> list = new ArrayList<ModelOrder>();
                        list.addAll(order);
                        for (ModelOrder modelOrder1 : order) {
                            if (modelOrder1.getCode().equals(modelOrder.getCode())) {
                                Toast.makeText(getActivity(), getString(R.string.alertAddItem),
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        order.add(modelOrder);
                        /*Order order1 = new Order();
                        order1.onListOrder(order);*/
                    }
                }
        );
        AlertDialog alertSelect = select.create();
        alertSelect.show();
        return alertSelect;

    }

    private void dataOrder() {
        new AsyncTask<Void, Void, ArrayList<ModelOrder>>() {
            @Override
            protected ArrayList<ModelOrder> doInBackground(Void... voids) {
                DaoOrder daoOrder = new DaoOrder(getActivity());
                try {
                    daoOrder.readData();
                    return daoOrder.getAllData(daoOrder.tableName, daoOrder.allColumns);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoOrder.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelOrder> modelOrders) {
                if (modelOrders != null) {
                    orderArrayList.addAll(modelOrders);
                }
            }
        }.execute();
    }

    public void addOnFilter(String code) {
        for (ModelOrder modelOrder : orderArrayList) {
            if (modelOrder.getCode().equals(code)) {
                for (ModelOrder modelOrder1 : order) {
                    if (modelOrder1.getCode().equals(modelOrder.getCode())) {
                        Toast.makeText(getActivity(), getString(R.string.alertAddItem), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                order.add(modelOrder);
                /*Order order1 = new Order();
                order1.onListOrder(order);*/
                return;
            } else {
                Toast.makeText(getActivity(), getString(R.string.noData), Toast.LENGTH_LONG).show();
            }
        }
    }

}
