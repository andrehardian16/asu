package com.andre.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.andre.store.dao.DaoImage;
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelStore;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 1/21/2015.
 */
public class AdapterStore extends BaseAdapter {
    protected Context context;
    protected ArrayList<ModelStore> listStore;
    ArrayList<ModelImages> listImage;


    public AdapterStore(Context context, ArrayList<ModelStore> modelStores) {
        this.context = context;
        listStore = modelStores;
    }


    @Override
    public int getCount() {
        return listStore.size();
    }

    @Override
    public Object getItem(int i) {
        return listStore.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_store, viewGroup, false);
        TextView nameStore;
        TextView addressStore;
        TextView lastVisit;
        TextView categoryStore;
        ImageView imageStoreList;
        listImage = listStore.get(i).getListImage();

        nameStore = (TextView) view.findViewById(R.id.store_name_list);
        addressStore = (TextView) view.findViewById(R.id.address_store_list);
        lastVisit = (TextView) view.findViewById(R.id.last_visit_list);
        categoryStore = (TextView) view.findViewById(R.id.category_store_list);
        imageStoreList = (ImageView) view.findViewById(R.id.imageListStore);

        if (listStore != null) {
            if (listImage != null) {
                if (listImage.get(0).getImagesPath() != null) {
                    Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(listImage.get(0).getImagesPath()),
                            100, 150, false);
                    imageStoreList.setImageBitmap(bitmap);
                } else {
                    imageStoreList.setImageResource(R.drawable.image_empty);
                }
            } else {
                imageStoreList.setImageResource(R.drawable.image_empty);
            }
            nameStore.setText(view.getResources().getString(R.string.storeName) + " : " + listStore.get(i).getStoreName());
            addressStore.setText(view.getResources().getString(R.string.address) + " : " + listStore.get(i).getAddress());
            lastVisit.setText(view.getResources().getString(R.string.lastVisit) + " : " + listStore.get(i).getLastVisit());
            categoryStore.setText(view.getResources().getString(R.string.category) + " : " + listStore.get(i).getCategoryStore());
        }

        return view;
    }

}
