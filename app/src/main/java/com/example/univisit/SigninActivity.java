package com.example.univisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername, etPassw;
    private Button btnSignin;
    private TextView tvCreate;
    Session session;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassw = (EditText) findViewById(R.id.et_passw);
        btnSignin = (Button) findViewById(R.id.btn_user_sign);
        tvCreate = (TextView) findViewById(R.id.tv_user_create);

        btnSignin.setOnClickListener(this);
        tvCreate.setOnClickListener(this);
        session = new Session(this);




    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_user_sign:
                if (isNotEmptyFields()) {
                    String uname = etUsername.getText().toString();
                    String pwd = etPassw.getText().toString();
                    Login(uname,pwd);
                }
                break;
            case R.id.tv_user_create:
                toCreate();
                break;
        }
    }

    private void Login(final String name, final String pwd){
        StringRequest request = new StringRequest(Request.Method.POST, API.PATH_URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id").trim();
                            String username = object.getString("username").trim();
                            String password = object.getString("password").trim();
                            String fname = object.getString("fname").trim();
                            String lname = object.getString("lname").trim();
                            String address = object.getString("address").trim();
                            String contact = object.getString("contact").trim();
                            String email = object.getString("email").trim();
                            String path = object.getString("path").trim();
                            Session session = new Session(getApplicationContext());

                                toHome();
                                session.createSession(id, fname, lname, address, contact, email, username, path);


                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), " " + response, Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("pwd", pwd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }



    private void toHome() {
        Intent intent = new Intent(SigninActivity.this, UserNavigationActivity.class);
        startActivity(intent);
    }

    private void toCreate() {
        Intent intent = new Intent(SigninActivity.this, CreateAcctActivity.class);
        startActivity(intent);
    }

    private boolean isNotEmptyFields() {
        String username = etUsername.getText().toString().trim();
        String password = etPassw.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
}
