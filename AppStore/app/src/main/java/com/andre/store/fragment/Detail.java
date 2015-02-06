package com.andre.store.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.andre.store.adapter.AdapterEmployee;
import com.andre.store.dao.DaoEmployee;
import com.andre.store.models.ModelEmployee;
import com.andre.store.models.ModelStore;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment {
    private ImageView imageStore;
    private TextView nameStore;
    private TextView phoneStore;
    private TextView address;
    private TextView lastVisit;
    private TextView category;
    private ListView listEmployee;
    ModelStore modelStore = new ModelStore();


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        imageStore = (ImageView) view.findViewById(R.id.imageStoreDetail);
        nameStore = (TextView) view.findViewById(R.id.store_nameDetail);
        phoneStore = (TextView) view.findViewById(R.id.phone_storeDetail);
        address = (TextView) view.findViewById(R.id.address_storeDetail);
        lastVisit = (TextView) view.findViewById(R.id.lastVisitDetail);
        category = (TextView) view.findViewById(R.id.categoryDetail);
        listEmployee = (ListView) view.findViewById(R.id.listEmployee);

        modelStore = (ModelStore) getActivity().getIntent().getSerializableExtra("model");

        if (modelStore != null) {
            if (modelStore.getListImage() != null) {
                if (modelStore.getListImage().get(0).getImagesPath() != null) {
                    Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(modelStore.
                            getListImage().get(0).getImagesPath()), 200, 200, false);
                    imageStore.setImageBitmap(bitmap);
                } else {
                    imageStore.setImageResource(R.drawable.image_empty);
                }
            } else {
                imageStore.setImageResource(R.drawable.image_empty);
            }
            nameStore.setText(getString(R.string.storeName) + "     : " + modelStore.getStoreName());
            phoneStore.setText(getString(R.string.phone) + "      : " + modelStore.getPhone());
            address.setText(getString(R.string.address) + "        : " + modelStore.getAddress());
            lastVisit.setText(getString(R.string.lastVisit) + "     : " + modelStore.getLastVisit());
            category.setText(getString(R.string.category) + " : " + modelStore.getCategoryStore());

            AdapterEmployee adapterEmployee = new AdapterEmployee(getActivity(), modelStore.getListEmployee());
            adapterEmployee.notifyDataSetChanged();
            listEmployee.setAdapter(adapterEmployee);
            listEmployee.invalidateViews();
            setHeightList(listEmployee);

        }
        return view;
    }

    private void setHeightList(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null){
            return;
        }
        int totalHeight = listView.getPaddingTop()+listView.getPaddingBottom();
        for (int pos = 0;pos<listAdapter.getCount();pos++){
            View listItem = listAdapter.getView(pos,null,listView);
            if (listItem instanceof ViewGroup){
                listItem.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                listItem.measure(0,0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight()*(listAdapter.getCount()-1));
            listView.setLayoutParams(params);
        }

    }

}
