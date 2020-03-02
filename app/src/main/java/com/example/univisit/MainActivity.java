package com.example.univisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSign;
    private TextView tvCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSign = (Button) findViewById(R.id.btn_signin);
        tvCreate = (TextView) findViewById(R.id.tv_create_acct);

        btnSign.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_signin:
                signinIntent();
                break;
            case R.id.tv_create_acct:
                createIntent();
                break;
        }
    }

    private void signinIntent() {
        Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    private void createIntent() {
        Intent intent = new Intent(MainActivity.this, CreateAcctActivity.class);
        startActivity(intent);
    }
}
