package com.andre.store.view;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.andre.store.dao.DaoUser;
import com.andre.store.models.ModelUser;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText userName;
    private EditText password;
    private TextView register;
    private Button button_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
        button_register = (Button) findViewById(R.id.button_login);
        getSupportActionBar().hide();
        register.setVisibility(View.INVISIBLE);
        button_register.setText(getString(R.string.register));
        button_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (userName.getText().length() == 0 && password.getText().length() == 0) {
            userName.setError("input username");
            password.setError("input password");
        } else {
            if (readData() != false) {
                Toast.makeText(getApplicationContext(), getString(R.string.available), Toast.LENGTH_SHORT).show();
            } else {
                insertData();
                Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertData() {
        DaoUser daoUser = new DaoUser(this);
        ModelUser modelUser = new ModelUser();
        try {
            daoUser.writeData();
            try {
                modelUser.setUserName(userName.getText().toString().trim());
                modelUser.setPassword(password.getText().toString().trim());
                daoUser.createData(daoUser.tableName, daoUser.setValuesData(modelUser));

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } finally {
                daoUser.close();
            }
        } catch (Exception e) {
        }
    }

    private boolean readData() {
        DaoUser daoUser = new DaoUser(this);
        try {
            daoUser.readData();
            ModelUser modelUser = daoUser.isUser(userName.getText().toString().trim(), password.getText().toString().trim());
            if (modelUser.getUserName() != userName.getText().toString().trim()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daoUser.close();
        }
        return false;
    }
}
