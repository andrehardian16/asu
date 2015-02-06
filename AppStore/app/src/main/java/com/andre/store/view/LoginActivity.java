package com.andre.store.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.andre.store.dao.DaoUser;
import com.andre.store.models.ModelUser;
import com.andre.store.sessions.SessionUser;

import java.sql.SQLException;
import java.util.List;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText userName;
    private EditText password;
    private TextView register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        register = (TextView) findViewById(R.id.register);
        login = (Button) findViewById(R.id.button_login);
        getSupportActionBar().hide();


        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                try {
                    executeLogin();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.register:
                Intent registerUser = new Intent(this, RegisterActivity.class);
                startActivity(registerUser);
                break;
        }
    }

    private void executeLogin() throws SQLException {

        if (userName.getText().length() == 0 && password.getText().length() == 0) {
            userName.setError(getString(R.string.inputUser));
            password.setError(getString(R.string.inputPass));
        } else {
            DaoUser daoUser = new DaoUser(this);
            daoUser.readData();

            try {

                ModelUser user = daoUser.isUser(userName.getText().toString().trim(), password.getText().toString().trim());

                if (user != null) {
                    Intent Store = new Intent(this, StoreActivity.class);
                    SessionUser sessionUser = new SessionUser(this);
                    sessionUser.setId(user.getId());
                    Toast.makeText(getApplicationContext(),""+user.getId(),Toast.LENGTH_SHORT).show();
                    startActivity(Store);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "user not found",
                            Toast.LENGTH_SHORT).show();
                }

            } finally {
                daoUser.close();
            }
        }
    }

}
