package com.andre.store.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TabHost;
import com.andre.store.adapter.AdapterEmployee;
import com.andre.store.dao.DaoEmployee;
import com.andre.store.dao.DaoStore;
import com.andre.store.fragment.Detail;
import com.andre.store.fragment.History;
import com.andre.store.map.MapsActivity;
import com.andre.store.models.ModelEmployee;
import com.andre.store.models.ModelStore;

import java.util.ArrayList;


public class DetailStore extends ActionBarActivity implements TabHost.OnTabChangeListener {

    public FragmentTabHost tabHost;
    ModelStore modelStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red100)));
        setTitle(getString(R.string.detail));
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);

        tabHost.setup(this, getSupportFragmentManager(), R.id.detail);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.detail)).setIndicator(getString(R.string.detail)),
                Detail.class, null);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.history)).setIndicator(getString(R.string.history)),
                History.class, null);
        tabHost.setOnTabChangedListener(this);

    }


    @Override
    public void onTabChanged(String s) {
        if (s.equalsIgnoreCase(getString(R.string.detail))) {
            setTitle(getString(R.string.detail));
        } else if (s.equalsIgnoreCase(getString(R.string.history))) {
            setTitle(getString(R.string.history));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(this, StoreActivity.class);
        startActivity(back);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail_store, menu);
        MenuItem addEmployee = menu.findItem(R.id.addEmployee);
        MenuItem maps = menu.findItem(R.id.maps);
        MenuItem editStore = menu.findItem(R.id.editStore);
        MenuItem order = menu.findItem(R.id.order);

        if (getTitle().toString().equals(R.string.history)) {
            addEmployee.setVisible(false);
            maps.setVisible(false);
            editStore.setVisible(false);
            order.setVisible(false);
        } else {
            addEmployee.setVisible(true);
            maps.setVisible(true);
            editStore.setVisible(true);
            order.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editStore:
                ModelStore modelStore = (ModelStore) getIntent().getSerializableExtra("model");
                Intent editStore = new Intent(this, EditStore.class);
                Bundle edit = new Bundle();
                edit.putSerializable("model", modelStore);
                editStore.putExtras(edit);
                startActivity(editStore);
                break;

            case R.id.addEmployee:
                addEmployee();
                break;
            case R.id.order:
                ModelStore modelStores = (ModelStore) getIntent().getSerializableExtra("model");
                Intent order = new Intent(this,Order.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",modelStores);
                order.putExtras(bundle);
                startActivity(order);
                finish();
                break;
            case R.id.maps:
                Intent mapAct = new Intent(this, MapsActivity.class);
                startActivity(mapAct);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEmployee() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View addEdit = inflater.inflate(R.layout.add_or_edit_employee, null);
        final EditText nameEmployee = (EditText) addEdit.findViewById(R.id.nameEmployeeInsert);
        final EditText position = (EditText) addEdit.findViewById(R.id.positionEmployeeInsert);
        final EditText phone = (EditText) addEdit.findViewById(R.id.phoneEmployeeInsert);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.insertData));
        alert.setView(addEdit);
        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DaoEmployee daoEmployee = new DaoEmployee(DetailStore.this);
                ModelEmployee modelEmployee = new ModelEmployee();
                Detail detail = new Detail();
                modelStore = (ModelStore) getIntent().getSerializableExtra("model");
                modelEmployee.setIdStore(modelStore.getIdStore());
                modelEmployee.setNameEmployee(nameEmployee.getText().toString().trim());
                modelEmployee.setPhone(phone.getText().toString().trim());
                modelEmployee.setPosition(position.getText().toString().trim());

                try {
                    daoEmployee.writeData();
                    daoEmployee.createData(daoEmployee.tableName, daoEmployee.setValuesData(modelEmployee));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoEmployee.close();
                }
                refreshListEmployee(modelStore.getIdStore());
            }
        });
        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    private void refreshListEmployee(final int idStore) {
        final DaoStore daoStore = new DaoStore(this);
        final DaoEmployee daoEmployee = new DaoEmployee(this);
        new AsyncTask<Void, Void, ArrayList<ModelEmployee>>() {
            @Override
            protected ArrayList<ModelEmployee> doInBackground(Void... voids) {
                try {
                    daoStore.writeData();
                    return daoStore.getAllDataEmployee(daoEmployee.tableName, daoEmployee.allColumn,
                            daoEmployee.ID_STORE, String.valueOf(idStore));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoStore.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelEmployee> modelEmployee) {
                if (modelEmployee != null) {

                    modelStore.setListEmployee(modelEmployee);
                    Bundle bundle = new Bundle();
                    Intent here = new Intent(DetailStore.this,DetailStore.class);
                    bundle.putSerializable("model",modelStore);
                    here.putExtras(bundle);
                    startActivity(here);
                    finish();
                }
            }
        }.execute();
    }

}
