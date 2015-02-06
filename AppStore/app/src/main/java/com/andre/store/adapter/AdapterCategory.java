package com.andre.store.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.andre.store.models.ModelCategory;
import com.andre.store.view.AdditionalStore;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 1/25/2015.
 */
public class AdapterCategory extends BaseAdapter {
    Context context;
    ArrayList<ModelCategory> categories;
    public AdapterCategory(Context context, ArrayList<ModelCategory> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.categories_layout,viewGroup,false);

        TextView listCategories;
        listCategories = (TextView)view.findViewById(R.id.category_list);
        listCategories.setText(categories.get(i).getCategory());
        return view;
    }
}
