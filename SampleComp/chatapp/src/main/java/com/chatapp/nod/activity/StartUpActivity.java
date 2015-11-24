package com.chatapp.nod.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatapp.nod.R;
import com.chatapp.nod.util.Constants;
import com.chatapp.nod.util.ParseProxyObject;
import com.chatapp.nod.util.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class StartUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText user;
    private EditText pwd;
    private Button btnLogin;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        initUI();
    }

    private void initUI() {
        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnReg = (Button) findViewById(R.id.btnReg);
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                validateLogin();
                break;
            case R.id.btnReg:
                goToregister();
                break;
        }
    }

    private void goToregister() {
    }

    private void validateLogin() {
        String userName = user.getText().toString();
        String pass = pwd.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass)) {
            Utils.showDialog(this, "Please fill all the fields.");
            return;
        }
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        ParseUser.logInInBackground(userName, pass, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                dia.dismiss();
                if (user != null) {
                    ActiveUser.INSTANCE.activeUser = user;
                    startActivity(new Intent(StartUpActivity.this, UserListing.class));
                    finish();
                } else {
                    Utils.showDialog(
                            StartUpActivity.this,
                            getString(R.string.err_login) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
