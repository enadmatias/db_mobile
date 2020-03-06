package com.example.univisit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLastname, etFirstname, etPhone, etAddress, etUsername, etEmail, etPassword, etFilename;
    private CircularImageView civImage;
    private Button btnBrowse;
    Session session;

    private String lastname, firstname, phone, address, username, email, password, file_name;

    Uri profileUri;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        setTitle("My Account");
        session = new Session(this);

        //instantiate variables
        etLastname = findViewById(R.id.et_lastname);
        etFirstname = findViewById(R.id.et_firstname);
        etPhone = findViewById(R.id.et_phone_number);
        etAddress = findViewById(R.id.et_address);
        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email_address);
        etPassword = findViewById(R.id.et_password);
        civImage = findViewById(R.id.civ_user_image);
        btnBrowse = findViewById(R.id.btn_browse_image);
        etFilename = findViewById(R.id.editText);

        btnBrowse.setOnClickListener(this);
        etLastname.setText(session.getlname());
        etFirstname.setText(session.getfname());
        etPhone.setText(session.getcontact());
        etAddress.setText(session.getaddress());
        etUsername.setText(session.getuname());
        etEmail.setText(session.getemail());
        Picasso.get().load(session.getPath()).resize(50, 50).centerCrop().into(civImage);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_browse_image:
                openGalleryIntent();
                break;
        }
    }

    private void openGalleryIntent() {
        Intent intent = new Intent(); //blind intent
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            profileUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profileUri);
                civImage.setImageURI(profileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                if (isNotEmptyFields()) {
                    saveToDatabase();
                    this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToDatabase() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.URL_PATH_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String fname = object.getString("fname").trim();
                            String lname = object.getString("lname").trim();
                            String photo = object.getString("image").trim();
                            String contact = object.getString("contact").trim();
                            String username = object.getString("username").trim();
                            String address = object.getString("address").trim();
                             session.setfname(fname);
                             session.setlname(lname);
                             session.setaddress(address);
                             session.setcontact(contact);
                             session.setusername(username);
                             session.setuserPhoto(photo);
                            Toast.makeText(MyAccountActivity.this, "Account has been updated successfully", Toast.LENGTH_SHORT).show();
                             finish();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", session.toString());
                params.put("FNAME", firstname);
                params.put("LNAME", lastname);
                params.put("ADDRESS", address);
                params.put("CONTACT", phone);
                params.put("EMAIL", email);
                params.put("USERNAME", username);
                params.put("NAME", file_name);
                params.put("PHOTO", imagetoString(bitmap));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public boolean isNotEmptyFields() {
         lastname = etLastname.getText().toString().trim();
         firstname = etFirstname.getText().toString().trim();
         phone = etPhone.getText().toString().trim();
         address = etAddress.getText().toString().trim();
         username = etUsername.getText().toString().trim();
         email = etEmail.getText().toString().trim();
         password = etPassword.getText().toString().trim();
         file_name = etFilename.getText().toString().trim();

        if (lastname.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (firstname.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (address.isEmpty()) {
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

        if(file_name.isEmpty()){
            Toast.makeText(this, "Fields can not be empty", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private String imagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }

}
