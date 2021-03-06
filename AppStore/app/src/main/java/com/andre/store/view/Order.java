package com.andre.store.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.andre.store.adapter.AdapterListOrder;
import com.andre.store.adapter.AdapterOrder;
import com.andre.store.dao.DaoOrder;
import com.andre.store.models.ModelOrder;
import com.andre.store.models.ModelStore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;


public class Order extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ArrayList<ModelOrder> orderArrayList = new ArrayList<ModelOrder>();
    ArrayList<ModelOrder> order = new ArrayList<ModelOrder>();
    ListView orderStore;
    static TextView totalPriceItem;
    static TextView totalQuantityItem;
    EditText search;
    Button qrCode;
    Button btnOrder;
    Button summaryOrder;
    Button searchBtn;
    ListView listOrder;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderStore = (ListView) findViewById(R.id.orderStore);
        qrCode = (Button) findViewById(R.id.qrCode);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        totalPriceItem = (TextView) findViewById(R.id.totalItem);
        totalQuantityItem = (TextView) findViewById(R.id.quantityItem);
        summaryOrder = (Button) findViewById(R.id.summaryOrder);
        searchBtn = (Button) findViewById(R.id.btnSearch);
        search = (EditText) findViewById(R.id.search);
        summaryOrder.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        qrCode.setOnClickListener(this);
        dataOrder();
        onListOrder(order);
        orderStore.setOnItemClickListener(this);
        searchBtn.setOnClickListener(this);
        search.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink700)));
    }

    private void dataOrder() {
        new AsyncTask<Void, Void, ArrayList<ModelOrder>>() {
            @Override
            protected ArrayList<ModelOrder> doInBackground(Void... voids) {
                DaoOrder daoOrder = new DaoOrder(Order.this);
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

    private AlertDialog selectOrder() {
        final AlertDialog.Builder select = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.list_order, null);
        listOrder = (ListView) view.findViewById(R.id.orderList);
        AdapterListOrder adapterListOrder = new AdapterListOrder(this, orderArrayList);
        listOrder.setAdapter(adapterListOrder);
        select.setCancelable(true);
        select.setView(view);

        final AlertDialog alertSelect = select.create();
        alertSelect.show();
        listOrder.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        AdapterListOrder adapterListOrder = (AdapterListOrder) listOrder.getAdapter();
                        ModelOrder modelOrder = (ModelOrder) adapterListOrder.getItem(i);
                        ModelStore modelStore = (ModelStore) getIntent().getSerializableExtra("model");
                        modelOrder.setIdStore(modelStore.getIdStore());
                        ArrayList<ModelOrder> list = new ArrayList<ModelOrder>();
                        list.addAll(order);
                        for (ModelOrder modelOrder1 : order) {
                            if (modelOrder1.getCode().equals(modelOrder.getCode())) {
                                Toast.makeText(getApplicationContext(), getString(R.string.alertAddItem),
                                        Toast.LENGTH_LONG).show();
                                alertSelect.dismiss();
                                return;
                            }
                        }
                        if (modelOrder.getStock() != 0) {
                            order.add(modelOrder);
                            onListOrder(order);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.sold), Toast.LENGTH_SHORT).show();
                        }
                        alertSelect.dismiss();
                    }
                }
        );
        return alertSelect;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnOrder:
                selectOrder();
                break;
            case R.id.summaryOrder:
                toBuy();
                break;
            case R.id.qrCode:
                scanQRCode();
                break;
            case R.id.search:
                search.setText("");
                break;
            case R.id.btnSearch:
                addOnFilter(search.getText().toString());
                break;
        }
    }

    public void addOnFilter(String code) {
        for (ModelOrder modelOrder : orderArrayList) {
            if (modelOrder.getCode().equals(code)) {
                for (ModelOrder modelOrder1 : order) {
                    if (modelOrder1.getCode().equals(code)) {
                        Toast.makeText(getApplicationContext(), getString(R.string.alertAddItem),
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                order.add(modelOrder);
                onListOrder(order);
                search.setText("");
                return;
            }

        }
        Toast.makeText(getApplicationContext(), getString(R.string.noData), Toast.LENGTH_LONG).show();
        return;

    }


    private void onListOrder(ArrayList<ModelOrder> list) {
        AdapterOrder adapterOrder = new AdapterOrder(Order.this, list);
        orderStore.setAdapter(adapterOrder);
        allOrder(list);
        adapterOrder.notifyDataSetChanged();
    }

    public void allOrder(ArrayList<ModelOrder> list) {
        int totalBuy = 0;
        int totalQuantity = 0;

        if (list != null && list.size() != 0) {
            for (int total = 0; total < list.size(); total++) {
                totalBuy += ((list.get(total).getPrice()) * (list.get(total).getQuantity()));
                totalQuantity += (list.get(total).getQuantity());
            }
        }


        totalPriceItem.setText("" + totalBuy);
        totalQuantityItem.setText("" + totalQuantity);
    }

    private void dialogQuantity(final int pos) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.alert_quantity, null);
        final EditText quantity = (EditText) view.findViewById(R.id.textAlert);
        quantity.setText("" + order.get(pos).getQuantity());
        AlertDialog.Builder alertQuantity = new AlertDialog.Builder(this);
        alertQuantity.setMessage(getString(R.string.alertQuantity));
        alertQuantity.setView(view);
        alertQuantity.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (Integer.parseInt(quantity.getText().toString()) <= orderArrayList.get(pos).getStock()) {
                    order.get(pos).setQuantity(Integer.parseInt(quantity.getText().toString()));
                    onListOrder(order);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.outStock), Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertQuantity.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertQuantity.create();
        alertQuantity.show();
    }

    private void back() {
        order.clear();
        ModelStore modelStore = (ModelStore) getIntent().getSerializableExtra("model");
        Intent back = new Intent(this, DetailStore.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", modelStore);
        back.putExtras(bundle);
        startActivity(back);
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }


   /* public void launchQRScanner() {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        *//*switch (requestCode){
            case ZBAR_QR_SCANNER_REQUEST:
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_LONG).show();
            case ZBAR_SCANNER_REQUEST:
        *//*
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED && data == null) {
                String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
                if (!TextUtils.isEmpty(error)) {
                    Toast.makeText(this, "text" + error, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "el", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
            }
        }
    }
          *//*      break;
        }*/

    private void toBuy() {
        Intent buy = new Intent(this, SummaryOrder.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("buy", order);
        buy.putExtras(bundle);
        startActivity(buy);
    }

    private void scanQRCode() {
        IntentIntegrator scan = new IntentIntegrator(this);
        scan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dialogQuantity(i);
    }

    private Animation animUpToDown() {
        final Animation upToDown = new TranslateAnimation(0, 0, -100, 0);
        upToDown.setDuration(1000);
        upToDown.setFillAfter(true);
        return upToDown;
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
