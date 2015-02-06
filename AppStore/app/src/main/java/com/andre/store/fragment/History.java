package com.andre.store.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import com.andre.store.adapter.AdapterHistory;
import com.andre.store.dao.DaoHistory;
import com.andre.store.models.ModelHistory;
import com.andre.store.view.DetailHistory;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment implements AdapterView.OnItemClickListener {
    ListView listHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hitory, container, false);
        listHistory = (ListView)view.findViewById(R.id.listHistory);
        dataHistory();
        listHistory.setOnItemClickListener(this);
        return view;
    }

    private void adapter(ArrayList<ModelHistory> historyData){
        AdapterHistory adapterHistory = new AdapterHistory(getActivity(),historyData);
        listHistory.setAdapter(adapterHistory);
        adapterHistory.notifyDataSetChanged();
    }

    private void dataHistory(){
        new AsyncTask<Void, Void, ArrayList<ModelHistory>>() {
            @Override
            protected ArrayList<ModelHistory> doInBackground(Void... voids) {
                DaoHistory daoHistory = new DaoHistory(getActivity());
                try{
                    daoHistory.readData();
                    return daoHistory.getAllData(daoHistory.tableName,daoHistory.allColumns);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    daoHistory.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelHistory> modelHistories) {
                if (modelHistories != null && modelHistories.size() != 0 ){
                    adapter(modelHistories);
                }
            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ModelHistory modelHistory = (ModelHistory)listHistory.getAdapter().getItem(i);
        historyDetail(modelHistory.getLastDate(),modelHistory.getIdStore());
    }

    private void historyDetail(String last,int id){
        Intent historyDetails = new Intent(getActivity(), DetailHistory.class);
        Bundle bundle = new Bundle();
        bundle.putString("lastDate",last);
        bundle.putInt("idStore",id);
        startActivity(historyDetails);
    }
}
