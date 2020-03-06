package com.example.univisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSign;
    private TextView tvCreate;
    private static final String TAG = "MainActivity";
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSign = (Button) findViewById(R.id.btn_signin);
        tvCreate = (TextView) findViewById(R.id.tv_create_acct);

        btnSign.setOnClickListener(this);
        tvCreate.setOnClickListener(this);

        session = new Session(MainActivity.this);
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

    @Override
    protected void onStart() {
        super.onStart();
        if(session.isLoggin())
        {
            startActivity(new Intent(MainActivity.this, UserNavigationActivity.class));
        }

        Log.v(TAG, "onstart");
    }
    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG,"onResume invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();


        Log.d(TAG,"onStop invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(session.isLoggin())
        { startActivity(new Intent(MainActivity.this, UserNavigationActivity.class));
        }
        Log.d(TAG,"onPause invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG,"onRestart invoked");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }

}
