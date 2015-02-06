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
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelJoin;
import com.andre.store.view.AdditionalStore;
import com.andre.store.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 1/25/2015.
 */
public class AdapterImage extends BaseAdapter {
    Context context;
    ArrayList<ModelJoin> rowImage = new ArrayList<ModelJoin>();

    public AdapterImage(Context context, ArrayList<ModelJoin> rowImage) {
        this.context = context;
        this.rowImage = rowImage;
    }


    @Override
    public int getCount() {
        return rowImage.size();
    }

    @Override
    public Object getItem(int i) {
        return rowImage.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.adapter_image, viewGroup, false);
        ImageView imageStore;
        imageStore = (ImageView) view.findViewById(R.id.imageAdapter);
        if (rowImage != null) {
                if (rowImage.get(i).getImagesPath() != null) {
                    Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(rowImage.get(i).getImagesPath()), 200, 300, false);
                    imageStore.setImageBitmap(bitmap);
                } else {
                    imageStore.setImageResource(R.drawable.image_empty);
                }

        } else {
            imageStore.setImageResource(R.drawable.image_empty);
        }
        return view;
    }

}
