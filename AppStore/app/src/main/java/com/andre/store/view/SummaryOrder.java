package com.andre.store.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.andre.store.adapter.AdapterSummary;
import com.andre.store.dao.DaoDetailHistory;
import com.andre.store.dao.DaoHistory;
import com.andre.store.dao.DaoOrder;
import com.andre.store.models.ModelDetailHistory;
import com.andre.store.models.ModelHistory;
import com.andre.store.models.ModelOrder;

import java.util.ArrayList;
import java.util.Calendar;


public class SummaryOrder extends ActionBarActivity implements View.OnClickListener {
    ListView listSummary;
    ArrayList<ModelOrder> summaryData = new ArrayList<ModelOrder>();
    TextView totalPriceItem;
    TextView totalQuantityItem;
    Button buyItem;
    ModelHistory modelHistory = new ModelHistory();
    ModelDetailHistory modelDetailHistory = new ModelDetailHistory();
    ModelOrder modelOrder = new ModelOrder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_order);
        listSummary = (ListView) findViewById(R.id.listSummary);
        summaryData = (ArrayList) getIntent().getSerializableExtra("buy");
        totalPriceItem = (TextView) findViewById(R.id.totalItemSummary);
        totalQuantityItem = (TextView) findViewById(R.id.quantityItemSummary);
        buyItem = (Button) findViewById(R.id.buyItem);
        buyItem.setOnClickListener(this);
        summaryOrder(summaryData);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink700)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void summaryOrder(ArrayList<ModelOrder> list) {
        AdapterSummary adapterSummary = new AdapterSummary(SummaryOrder.this, list);
        listSummary.setAdapter(adapterSummary);
        int totalBuy = 0;
        int totalQuantity = 0;

        for (int total = 0; total < list.size(); total++) {
            totalBuy += ((list.get(total).getPrice()) * (list.get(total).getQuantity()));
            totalQuantity += (list.get(total).getQuantity());
        }
        modelHistory.setQuantity(totalQuantity);
        modelHistory.setTotal(totalBuy);

        totalPriceItem.setText("" + totalBuy);
        totalQuantityItem.setText("" + totalQuantity);
        adapterSummary.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        alertBUy();
    }

    private void alertBUy() {
        AlertDialog.Builder alertBuyItem = new AlertDialog.Builder(this);
        alertBuyItem.setMessage(getString(R.string.buyItem));
        alertBuyItem.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (summaryData != null && summaryData.size() != 0) {
                    Calendar calendar = Calendar.getInstance();
                    String lastDate = calendar.getTime().toString();
                    modelHistory.setLastDate(lastDate);
                    modelHistory.setIdStore(summaryData.get(0).getIdStore());
                    createHistory(modelHistory);

                    for (int pos = 0; pos < summaryData.size(); pos++) {
                        modelDetailHistory.setIdStore(summaryData.get(pos).getIdStore());
                        modelDetailHistory.setQuantity(summaryData.get(pos).getQuantity());
                        modelDetailHistory.setPrice(summaryData.get(pos).getPrice());
                        modelDetailHistory.setNameDetailHistory(summaryData.get(pos).getNameOrder());
                        modelDetailHistory.setCode(summaryData.get(pos).getCode());
                        modelDetailHistory.setLastDate(lastDate);
                        createDetailHistory(modelDetailHistory);
                    }
                    for (int pos = 0; pos < summaryData.size(); pos++) {
                        int stock = summaryData.get(pos).getStock() - summaryData.get(pos).getQuantity();
                        modelOrder.setStock(stock);
                        modelOrder.setId(summaryData.get(pos).getId());
                        updateDataOrder(modelOrder);
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG);
                    Intent intent = new Intent(SummaryOrder.this, StoreActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),getString(R.string.noData),Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertBuyItem.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = alertBuyItem.create();
        dialog.show();
    }

    private void createHistory(ModelHistory modelHistory) {
        DaoHistory daoHistory = new DaoHistory(this);

        try {
            daoHistory.writeData();
            try {
                daoHistory.createData(daoHistory.tableName, daoHistory.setValuesData(modelHistory));
            } finally {
                daoHistory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createDetailHistory(ModelDetailHistory modelDetailHistory) {
        DaoDetailHistory daoDetailHistory = new DaoDetailHistory(this);
        try {
            daoDetailHistory.writeData();
            try {
                daoDetailHistory.createData(daoDetailHistory.tableName, daoDetailHistory.
                        setValuesData(modelDetailHistory));
            } finally {
                daoDetailHistory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDataOrder(ModelOrder modelOrder) {
        DaoOrder daoOrder = new DaoOrder(this);
        try {
            daoOrder.writeData();
            try {
                daoOrder.updateData(daoOrder.tableName, daoOrder.valuesUpdate(modelOrder), daoOrder.ID_ORDER,
                        modelOrder.getId());
            } finally {
                daoOrder.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}