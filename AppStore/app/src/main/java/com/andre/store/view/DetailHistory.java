package com.andre.store.view;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.andre.store.dao.DaoDetailHistory;
import com.andre.store.models.ModelDetailHistory;
import com.andre.store.models.ModelHistory;

import java.util.ArrayList;


public class DetailHistory extends ActionBarActivity {
    ListView listDetailHistory;
    ArrayList<ModelDetailHistory> dataDetailHistory = new ArrayList<ModelDetailHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        listDetailHistory = (ListView) findViewById(R.id.listDetailHistory);
    }

    private void adapter_detail_history() {

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
                ArrayList<ModelDetailHistory> listDetail = new ArrayList<ModelDetailHistory>();
                ModelDetailHistory modelDetailHistory = new ModelDetailHistory();
                try {
                    daoDetailHistory.readData();
                    listDetail.add(daoDetailHistory.getData(daoDetailHistory.ID_STORE,
                            daoDetailHistory.ROW_LAST_DATE, arg));
                    return listDetail;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoDetailHistory.close();
                }
                return listDetail;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelDetailHistory> modelDetailHistories) {
                if (modelDetailHistories != null) {
                    dataDetailHistory = modelDetailHistories;
                }
            }
        }.execute();
    }
}
