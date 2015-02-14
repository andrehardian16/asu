package com.andre.store.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import com.andre.store.adapter.AdapterCategory;
import com.andre.store.dao.DaoCategory;
import com.andre.store.dao.DaoEmployee;
import com.andre.store.dao.DaoImage;
import com.andre.store.dao.DaoStore;
import com.andre.store.gps.GpsTracker;
import com.andre.store.models.ModelCategory;
import com.andre.store.models.ModelEmployee;
import com.andre.store.models.ModelImages;
import com.andre.store.models.ModelStore;
import com.andre.store.sessions.SessionUser;
import com.google.zxing.client.result.EmailAddressParsedResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;


public class AdditionalStore extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText storeName;
    private EditText addressStore;
    private EditText phoneStore;
    private EditText emailStore;
    private EditText nameEmployee;
    private EditText positionEmployee;
    private EditText phoneEmployee;
    private Button saveDataStore;
    private ImageView imageStore;
    private Spinner category;
    ModelImages modelImages = new ModelImages();
    ModelStore modelStore = new ModelStore();
    ModelEmployee modelEmployee = new ModelEmployee();
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_store);

        storeName = (EditText) findViewById(R.id.store_name);
        addressStore = (EditText) findViewById(R.id.address_store);
        phoneStore = (EditText) findViewById(R.id.phone_store);
        emailStore = (EditText) findViewById(R.id.email_store);
        nameEmployee = (EditText) findViewById(R.id.name_employee);
        positionEmployee = (EditText) findViewById(R.id.position_employee);
        phoneEmployee = (EditText) findViewById(R.id.phone_employee);
        saveDataStore = (Button) findViewById(R.id.save_button);
        imageStore = (ImageView) findViewById(R.id.imageStore);
        category = (Spinner) findViewById(R.id.spinner_category);

        imageStore.setOnClickListener(this);
        saveDataStore.setOnClickListener(this);
        category.setOnItemSelectedListener(this);
        imageStore.setImageResource(R.drawable.camera);

        categoryOption();


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
                    AdapterCategory adapterCategory = new AdapterCategory(AdditionalStore.this, modelCategories);
                    category.setAdapter(adapterCategory);

                }
            }
        }.execute();

    }

   /* @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        dialogPict();


    }*/


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
            imageStore.setImageBitmap(imageScale);
            modelImages.setImagesPath(picturePath);
        }

    }

    private Long insertImage(ModelImages modelImages) {
        final DaoImage daoImage = new DaoImage(this);
        try {
            daoImage.writeData();
            try {
                Long result = daoImage.createData(daoImage.tableName, daoImage.setValuesData(modelImages));
                return result;
            } finally {
                daoImage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void createDataStore() {
            ModelStore store = new ModelStore();

            store.setIdStore(insertStore(modelStore).intValue());
            DaoImage daoImage = new DaoImage(this);
            DaoEmployee daoEmployee = new DaoEmployee(this);

            if (store.getIdStore() != 0) {

                modelImages.setIdStore(store.getIdStore());
                insertImage(modelImages);

                modelEmployee.setIdStore(store.getIdStore());
                modelEmployee.setPosition(positionEmployee.getText().toString());
                modelEmployee.setPhone(phoneEmployee.getText().toString());
                modelEmployee.setNameEmployee(nameEmployee.getText().toString());

                insertEmployee(modelEmployee);

            } else {
                Toast.makeText(getApplicationContext(), "images n employee no insert", Toast.LENGTH_SHORT).show();
            }

    }

    private Long insertStore(ModelStore modelStore) {
        final DaoStore daoStore = new DaoStore(this);
        try {
            daoStore.writeData();
            try {
                Long result = daoStore.createData(daoStore.tableName, daoStore.setValuesData(modelStore));
                return result;
            } finally {
                daoStore.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Long insertEmployee(ModelEmployee modelEmployee) {
        final DaoEmployee daoEmployee = new DaoEmployee(this);
        try {
            daoEmployee.writeData();
            try {
                Long result = daoEmployee.createData(daoEmployee.tableName, daoEmployee.setValuesData(modelEmployee));
                return result;
            } finally {
                daoEmployee.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageStore:
                dialogPict();
                break;
            case R.id.save_button:
                save();
                break;
        }
    }

    private void choseImage() {
        try {
            Intent exploreImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(exploreImage, RESULT_LOAD_IMAGE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "images not in internal memory", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this, StoreActivity.class);
        startActivity(back);
        finish();
    }

    private Boolean validationMail(CharSequence email) {
//        final String EmailPattern = "^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[_A-Za-z0-9]+(\\.[A-Za-z0-9])*(\\.[A-Za-z0-9]{0,2})$";
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void save() {
        Calendar calendar = Calendar.getInstance();
        GpsTracker gpsTracker = new GpsTracker(this);
        SessionUser sessionUser = new SessionUser(this);
        if (storeName.length() == 0) {
            storeName.setError(getString(R.string.insertStoreName));
        } else {
            modelStore.setIdUser(sessionUser.getId());
            modelStore.setStoreName(storeName.getText().toString());
            modelStore.setPhone(phoneStore.getText().toString());
            modelStore.setLastVisit(calendar.getTime().toString());
            modelStore.setEmail(emailStore.getText().toString());
            modelStore.setAddress(addressStore.getText().toString());
            if (gpsTracker.canGetLocation()) {
                modelStore.setLatitude(gpsTracker.getLatitude());
                modelStore.setLongitude(gpsTracker.getLongitude());
            } else {
                gpsTracker.showSettingAlert();
            }
            gpsTracker.stopUsingGPS();
        }


            if (!validationMail(modelStore.getEmail())) {
            Toast.makeText(getApplicationContext(), getString(R.string.notValid), Toast.LENGTH_SHORT).show();
        } else {
            createDataStore();
            Intent store = new Intent(this, StoreActivity.class);
            startActivity(store);
            finish();
        }
    }
}
