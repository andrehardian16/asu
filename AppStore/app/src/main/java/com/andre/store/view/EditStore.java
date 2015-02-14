package com.andre.store.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.andre.store.adapter.AdapterCategory;
import com.andre.store.dao.DaoCategory;
import com.andre.store.dao.DaoEmployee;
import com.andre.store.dao.DaoImage;
import com.andre.store.dao.DaoStore;
import com.andre.store.fragment.Detail;
import com.andre.store.gps.GpsTracker;
import com.andre.store.models.ModelCategory;
import com.andre.store.models.ModelEmployee;
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelStore;
import com.andre.store.sessions.SessionUser;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Andre on 2/1/2015.
 */
public class EditStore extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText storeNameEdit;
    private EditText addressStoreEdit;
    private EditText phoneStoreEdit;
    private EditText emailStoreEdit;
    private Button saveDataStoreEdit;
    private ImageView imageStoreEdit;
    private Spinner categoryEdit;
    private EditText oldCategory;
    ModelStore modelStore = new ModelStore();
    ModelImages modelImages = new ModelImages();


    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_store);
        storeNameEdit = (EditText) findViewById(R.id.store_edit);
        addressStoreEdit = (EditText) findViewById(R.id.address_edit);
        phoneStoreEdit = (EditText) findViewById(R.id.phone_edit);
        emailStoreEdit = (EditText) findViewById(R.id.email_edit);
        oldCategory = (EditText) findViewById(R.id.oldCategory);
        imageStoreEdit = (ImageView) findViewById(R.id.imageEdit);
        categoryEdit = (Spinner) findViewById(R.id.spinner_category);
        saveDataStoreEdit = (Button) findViewById(R.id.save_buttonEdit);

        modelStore = (ModelStore) getIntent().getSerializableExtra("model");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink700)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageStoreEdit.setOnClickListener(this);
        saveDataStoreEdit.setOnClickListener(this);
        categoryEdit.setOnItemSelectedListener(this);
        imageStoreEdit.setImageResource(R.drawable.camera);
        saveDataStoreEdit.setOnClickListener(this);
        categoryOption();

        if (modelStore != null) {
            storeNameEdit.setText(modelStore.getStoreName());
            addressStoreEdit.setText(modelStore.getAddress());
            phoneStoreEdit.setText(modelStore.getPhone());
            emailStoreEdit.setText(modelStore.getEmail());
            oldCategory.setText(modelStore.getCategoryStore());
            oldCategory.setEnabled(false);

            if (modelStore.getListImage() != null) {
                if (modelStore.getListImage().get(0).getImagesPath() != null) {
                    modelImages.setId(modelStore.getListImage().get(0).getId());
                    modelImages.setImagesPath(modelStore.getListImage().get(0).getImagesPath());
                    Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(modelStore.getListImage().
                            get(0).getImagesPath()), 100, 150, false);
                    imageStoreEdit.setImageBitmap(bitmap);
                } else {
                    imageStoreEdit.setImageResource(R.drawable.image_empty);
                }
            } else {
                imageStoreEdit.setImageResource(R.drawable.image_empty);
            }
        }
    }

    private void categoryOption() {
        final DaoCategory daoCategory = new DaoCategory(this);
        new AsyncTask<Void, Void, ArrayList<ModelCategory>>() {
            @Override
            protected ArrayList<ModelCategory> doInBackground(Void... voids) {
                try {
                    daoCategory.readData();
                    return daoCategory.getAllData(daoCategory.tableName, daoCategory.allColumns);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoCategory.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelCategory> modelCategories) {
                if (modelCategories != null) {
                    AdapterCategory adapterCategory = new AdapterCategory(EditStore.this, modelCategories);
                    categoryEdit.setAdapter(adapterCategory);
                }
            }
        }.execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap imageScale = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(String.valueOf(picturePath)), 200, 200, false);
            imageStoreEdit.setImageBitmap(imageScale);
            modelImages.setImagesPath(picturePath);

        }
    }

    private void updateImage(ModelImages modelImages) {
        final DaoImage daoImage = new DaoImage(this);
        try {
            daoImage.writeData();
            try {
                daoImage.updateData(daoImage.tableName, daoImage.valuesUpdate(modelImages), daoImage.ID_IMAGE,
                        modelImages.getId());
            } finally {
                daoImage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateDataStore() {

        modelStore.setStoreName(storeNameEdit.getText().toString());
        modelStore.setPhone(phoneStoreEdit.getText().toString());
        modelStore.setEmail(emailStoreEdit.getText().toString());
        modelStore.setAddress(addressStoreEdit.getText().toString());
        ArrayList<ModelImages> modelImagesArrayList = new ArrayList<ModelImages>();
        modelImagesArrayList.add(modelImages);
        modelStore.setListImage(modelImagesArrayList);

        updateStore(modelStore);
        updateImage(modelImages);


    }

    private void updateStore(ModelStore modelStore) {
        final DaoStore daoStore = new DaoStore(this);
        try {
            daoStore.writeData();
            try {
                daoStore.updateData(daoStore.tableName, daoStore.valuesUpdate(modelStore),
                        daoStore.ID_STORE, modelStore.getIdStore());

            } finally {
                daoStore.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageEdit:
                dialogPict();
                break;
            case R.id.save_buttonEdit:
                updateDataStore();
                Intent detail = new Intent(this, DetailStore.class);
                Bundle dataStore = new Bundle();

                dataStore.putSerializable("model", modelStore);
                detail.putExtras(dataStore);
                startActivity(detail);
                finish();
                break;
        }

    }

    private void choseImage() {
        try {
            Intent exploreImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(exploreImage, RESULT_LOAD_IMAGE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.notExternal), Toast.LENGTH_SHORT).show();
        }
    }

    private void camera() {
        Intent cameraAct = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraAct, 1);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ModelCategory modelCategory = (ModelCategory) adapterView.getAdapter().getItem(i);
        modelStore.setCategoryStore(modelCategory.getCategory());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void dialogPict() {
        AlertDialog.Builder option = new AlertDialog.Builder(this);
        option.setTitle(getString(R.string.addPhoto));
        String[] optionPict = getResources().getStringArray(R.array.optionPict);
        option.setItems(optionPict, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                switch (i) {
                    case 0:
                        camera();
                        break;
                    case 1:
                        choseImage();
                        break;
                }
            }
        });
        AlertDialog alertDialog = option.create();
        alertDialog.show();

    }

    private void back(){
        Intent back = new Intent(this, DetailStore.class);
        Bundle dataStore = new Bundle();
        dataStore.putSerializable("model", modelStore);
        back.putExtras(dataStore);
        startActivity(back);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                back();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
