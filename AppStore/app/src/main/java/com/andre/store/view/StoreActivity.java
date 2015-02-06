package com.andre.store.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.andre.store.adapter.AdapterStore;
import com.andre.store.dao.*;
import com.andre.store.models.*;
import com.andre.store.sessions.SessionUser;

import java.util.ArrayList;


public class StoreActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView listStore;
    private String[] categoryStore;
    private String[] nameOrder;
    private int[] stockOrder;
    private String[] codeOrder;
    private int[] priceOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        listStore = (ListView) findViewById(R.id.list_store);
        listStore.setOnItemClickListener(this);
        listStore.setOnItemLongClickListener(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red100)));

        refreshList();
    }


    @Override
    public void onResume() {
        super.onResume();
        SessionUser session = new SessionUser(getApplicationContext());
        if (!session.isSession()) {
            Intent login = new Intent(StoreActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent detailStore = new Intent(this, DetailStore.class);
        ModelStore modelStore = (ModelStore) listStore.getAdapter().getItem(i);

        Bundle detail = new Bundle();
        detail.putSerializable("model", modelStore);
        detailStore.putExtras(detail);
        startActivity(detailStore);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_store, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AdditionalStore.class);
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                SessionUser sessionUser = new SessionUser(this);
                sessionUser.clearId();
                Intent logout = new Intent(this, LoginActivity.class);
                startActivity(logout);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        final ArrayList<ModelCategory> categories = new ArrayList<ModelCategory>();

        new AsyncTask<Void, Void, ArrayList<ModelCategory>>() {
            @Override
            protected ArrayList<ModelCategory> doInBackground(Void... voids) {
                DaoCategory daoCategory = new DaoCategory(StoreActivity.this);
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
                if (modelCategories.size() == 0) {
                    DaoCategory daoCategory = new DaoCategory(StoreActivity.this);
                    daoCategory.writeData();
                    categoryStore = getResources().getStringArray(R.array.category);
                    for (int input = 0; input < categoryStore.length; input++) {
                        ModelCategory modelCategory = new ModelCategory();
                        modelCategory.setCategory(categoryStore[input]);
                        daoCategory.createData(daoCategory.tableName, daoCategory.setValuesData(modelCategory));
                    }
                    daoCategory.close();

                }
            }
        }.execute();

        new AsyncTask<Void, Void, ArrayList<ModelOrder>>() {
            @Override
            protected ArrayList<ModelOrder> doInBackground(Void... voids) {
                DaoOrder daoOrder = new DaoOrder(StoreActivity.this);
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
                if (modelOrders.size() == 0) {
                    DaoOrder daoOrder = new DaoOrder(StoreActivity.this);
                    nameOrder = getResources().getStringArray(R.array.nameOrder);
                    codeOrder = getResources().getStringArray(R.array.codeOrder);
                    stockOrder = getResources().getIntArray(R.array.stock);
                    priceOrder = getResources().getIntArray(R.array.price);

                    for (int input = 0; input < nameOrder.length; input++) {
                        ModelOrder modelOrder = new ModelOrder();
                        modelOrder.setStock(stockOrder[input]);
                        modelOrder.setNameOrder(nameOrder[input]);
                        modelOrder.setCode(codeOrder[input]);
                        modelOrder.setPrice(priceOrder[input]);

                        daoOrder.writeData();
                        daoOrder.createData(daoOrder.tableName,daoOrder.setValuesData(modelOrder));
                    }
                    daoOrder.close();
                }
            }
        }.execute();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(this);
        alertDelete.setMessage(getString(R.string.delete));
        alertDelete.setCancelable(true);
        alertDelete.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ModelStore modelStore = (ModelStore) listStore.getAdapter().getItem(pos);
                deleteImage(modelStore.getIdStore());
                deleteEmployee(modelStore.getIdStore());
                deleteStore(modelStore.getIdStore());
                refreshList();
            }
        });
        alertDelete.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDelete.create();
        alertDialog.show();
        return false;
    }

    private void deleteImage(int id) {
        DaoImage daoImage = new DaoImage(StoreActivity.this);
        try {
            daoImage.writeData();
            daoImage.deleteData(daoImage.tableName, daoImage.ID_STORE, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoImage.close();
        }
    }

    private void deleteEmployee(int id) {
        DaoEmployee daoEmployee = new DaoEmployee(this);
        try {
            daoEmployee.writeData();
            daoEmployee.deleteData(daoEmployee.tableName, daoEmployee.ID_STORE, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoEmployee.close();
        }
    }

    private void deleteStore(int id) {
        DaoStore daoStore = new DaoStore(this);
        try {
            daoStore.writeData();
            daoStore.deleteData(daoStore.tableName, daoStore.ID_STORE, String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoStore.close();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void refreshList() {
        final DaoStore daoStore = new DaoStore(this);
        final DaoImage daoImage = new DaoImage(this);
        final DaoEmployee daoEmployee = new DaoEmployee(this);

        new AsyncTask<Void, Void, ArrayList<ModelStore>>() {
            @Override
            protected ArrayList<ModelStore> doInBackground(Void... voids) {
                ArrayList<ModelStore> listStore = new ArrayList<ModelStore>();
                ModelImages data = new ModelImages();
                try {
                    daoStore.readData();
                    listStore = daoStore.getAllData(daoStore.tableName, daoStore.allColumn);
                    for (int pos = 0; pos < listStore.size(); pos++) {
                        ArrayList<ModelImages> listImage = new ArrayList<ModelImages>();
                        ArrayList<ModelEmployee> listEmployee = new ArrayList<ModelEmployee>();
                        data = daoStore.getDataImage(daoImage.tableName, daoImage.allColumns, daoImage.ID_STORE,
                                String.valueOf(listStore.get(pos).getIdStore()));
                        listImage.add(data);
                        listStore.get(pos).setListImage(listImage);

                        listEmployee = daoStore.getAllDataEmployee(daoEmployee.tableName, daoEmployee.allColumn,
                                daoEmployee.ID_STORE, String.valueOf(listStore.get(pos).getIdStore()));
                        listStore.get(pos).setListEmployee(listEmployee);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoStore.close();
                }
                return listStore;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelStore> modelStores) {
                if (modelStores != null) {
                    AdapterStore adapterStore = new AdapterStore(StoreActivity.this, modelStores);
                    listStore.setAdapter(adapterStore);
                    adapterStore.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.noData), Toast.LENGTH_SHORT).show();
                }
            }

        }.execute();

    }
}