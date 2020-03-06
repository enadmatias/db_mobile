package com.example.univisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAcctActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFname, etLname, etAddr, etPhone, etUsername, etEmail;
    private ShowHidePasswordEditText etPassw;
    private Button btnCreate;
    private TextView tvSignin;
    private String PATH_URL_REGISTER = "http://10.0.2.2:8080/dbproject/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acct);

        etFname = (EditText) findViewById(R.id.et_fname);
        etLname = (EditText) findViewById(R.id.et_lname);
        etAddr = (EditText) findViewById(R.id.et_addr);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etUsername = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassw = (ShowHidePasswordEditText) findViewById(R.id.et_passw);
        btnCreate = (Button) findViewById(R.id.btn_user_create);
        tvSignin = (TextView) findViewById(R.id.tv_user_sign);

        btnCreate.setOnClickListener(this);
        tvSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_user_create:
                if (isNotEmptyFields()) {
//                    toHome();
                    String fname = etFname.getText().toString();
                    String lname = etLname.getText().toString();
                    String address = etAddr.getText().toString();
                    String contact = etPhone.getText().toString();
                    String username = etUsername.getText().toString();
                    String email = etEmail.getText().toString();
                    String password = etPassw.getText().toString();
                    Register(fname,lname,address,contact,username,email,password);

                }
                break;
            case R.id.tv_user_sign:
                toSignin();
                break;
        }
    }

    private void Register(final String fname, final String lname, final String address, final String contact, final String username, final String email, final String password){
        StringRequest request = new StringRequest(Request.Method.POST, PATH_URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("success")){
                        Toast.makeText(CreateAcctActivity.this, "Successfully created your account. Please login.", Toast.LENGTH_SHORT).show();
                        toHome();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("address", address);
                params.put("contact", contact);
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }






    public void toSignin() {
        Intent intent = new Intent(CreateAcctActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    public void toHome() {
        Intent intent = new Intent(CreateAcctActivity.this, UserNavigationActivity.class);
        startActivity(intent);
    }

    public boolean isNotEmptyFields() {
        String lastname = etLname.getText().toString().trim();
        String firstname = etFname.getText().toString().trim();
        String address = etAddr.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassw.getText().toString().trim();

        if (lastname.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (firstname.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
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
