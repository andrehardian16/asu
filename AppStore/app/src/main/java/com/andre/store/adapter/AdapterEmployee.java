package com.andre.store.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.andre.store.dao.DaoEmployee;
import com.andre.store.dao.DaoStore;
import com.andre.store.models.ModelEmployee;
import com.andre.store.view.R;

import java.util.ArrayList;

/**
 * Created by Andre on 2/1/2015.
 */
public class AdapterEmployee extends BaseAdapter {
    Context context;
    ArrayList<ModelEmployee> modelEmployees = new ArrayList<ModelEmployee>();

    public AdapterEmployee(Context context, ArrayList<ModelEmployee> employee) {
        this.context = context;
        modelEmployees.clear();
        modelEmployees = employee;
    }



    @Override
    public int getCount() {
        return modelEmployees.size();
    }

    @Override
    public Object getItem(int i) {
        return modelEmployees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.adapter_employee, viewGroup, false);

        TextView nameEmployee;
        TextView phoneEmployee;
        TextView position;
        Button editEmployee;
        Button deleteEmployee;

        nameEmployee = (TextView) view.findViewById(R.id.name_employeeDetail);
        phoneEmployee = (TextView) view.findViewById(R.id.phone_employeeDetail);
        position = (TextView) view.findViewById(R.id.position_employeeDetail);
        editEmployee = (Button) view.findViewById(R.id.editEmployee);
        deleteEmployee = (Button) view.findViewById(R.id.deleteEmployee);

        if (modelEmployees.get(i) != null) {
            nameEmployee.setText(context.getString(R.string.name_employee) + " : " + modelEmployees.get(i).getNameEmployee());
            phoneEmployee.setText(context.getString(R.string.phone) + " : " + modelEmployees.get(i).getPhone());
            position.setText(context.getString(R.string.position) + " : " + modelEmployees.get(i).getPosition());
        }
        editEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEmployee(context.getString(R.string.editData), i);
                refreshListEmployee(modelEmployees.get(i).getIdStore());
            }
        });

        deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(i);
                refreshListEmployee(modelEmployees.get(i).getIdStore());
            }
        });

        return view;
    }

    private void deleteEmployee(int pos) {
        DaoEmployee daoEmployee = new DaoEmployee(context);
        try {
            daoEmployee.writeData();
            daoEmployee.deleteData(daoEmployee.tableName, daoEmployee.ID_EMPLOYEE,
                    String.valueOf(modelEmployees.get(pos).getId()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoEmployee.close();
        }

    }

    private void editEmployee(String messageDialog, final int pos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View addEdit = inflater.inflate(R.layout.add_or_edit_employee, null);
        final EditText nameEmployee = (EditText) addEdit.findViewById(R.id.nameEmployeeInsert);
        final EditText position = (EditText) addEdit.findViewById(R.id.positionEmployeeInsert);
        final EditText phone = (EditText) addEdit.findViewById(R.id.phoneEmployeeInsert);
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(context.getString(R.string.editData));
        nameEmployee.setText(modelEmployees.get(pos).getNameEmployee());
        position.setText(modelEmployees.get(pos).getPosition());
        phone.setText(modelEmployees.get(pos).getPhone());
        alert.setView(addEdit);
        alert.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ModelEmployee modelEmployee = new ModelEmployee();
                modelEmployee.setNameEmployee(nameEmployee.getText().toString().trim());
                modelEmployee.setPhone(nameEmployee.getText().toString().trim());
                modelEmployee.setPosition(nameEmployee.getText().toString().trim());

                DaoEmployee daoEmployee = new DaoEmployee(context);
                try {
                    daoEmployee.writeData();
                    daoEmployee.updateData(daoEmployee.tableName, daoEmployee.setValuesData(modelEmployee),
                            daoEmployee.ID_EMPLOYEE, modelEmployees.get(pos).getId());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    daoEmployee.close();
                }

            }
        });
        alert.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    private void refreshListEmployee(final int idStore) {
        final DaoStore daoStore = new DaoStore(context);
        final DaoEmployee daoEmployee = new DaoEmployee(context);
        new AsyncTask<Void, Void, ArrayList<ModelEmployee>>() {
            @Override
            protected ArrayList<ModelEmployee> doInBackground(Void... voids) {
                try {
                    daoStore.writeData();
                    return daoStore.getAllDataEmployee(daoEmployee.tableName, daoEmployee.allColumn,
                            daoEmployee.ID_STORE, String.valueOf(idStore));
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    daoStore.close();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<ModelEmployee> modelEmployee) {
                if (modelEmployee != null) {
                    modelEmployees.clear();
                    modelEmployees.addAll(modelEmployee);
                    notifyDataSetChanged();
                }
            }
        }.execute();
    }

}
