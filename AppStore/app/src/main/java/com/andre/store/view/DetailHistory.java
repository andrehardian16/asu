package com.andre.store.view;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.andre.store.adapter.AdapterDetailHistory;
import com.andre.store.dao.DaoDetailHistory;
import com.andre.store.fragment.Detail;
import com.andre.store.models.ModelDetailHistory;
import com.andre.store.models.ModelHistory;

import java.util.ArrayList;


public class DetailHistory extends ActionBarActivity {
    ListView listDetailHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        listDetailHistory = (ListView) findViewById(R.id.listDetailHistory);
        dataDetail();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.pink700)));
    }


    private void dataDetail() {
        new AsyncTask<Void, Void, ArrayList<ModelDetailHistory>>() {
            @Override
            protected ArrayList<ModelDetailHistory> doInBackground(Void... voids) {
                DaoDetailHistory daoDetailHistory = new DaoDetailHistory(DetailHistory.this);
                Bundle bundle = getIntent().getExtras();
                String lastDate = bundle.getString("lastDate");
                String idStore = String.valueOf(bundle.getInt("idStore"));
                String[] arg = {idStore, lastDate};
                try {
                    daoDetailHistory.readData();
                                  return daoDetailHistory.getData(daoDetailHistory.ID_STORE,
                            daoDetailHistory.ROW_LAST_DATE, arg);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoDetailHistory.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelDetailHistory> modelDetailHistories) {
                if (modelDetailHistories != null) {
                    AdapterDetailHistory adapterDetailHistory = new AdapterDetailHistory(DetailHistory.this,
                            modelDetailHistories);
                    listDetailHistory.setAdapter(adapterDetailHistory);
                    adapterDetailHistory.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
