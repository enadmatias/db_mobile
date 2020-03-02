package com.example.univisit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etLastname, etFirstname, etPhone, etAddress, etUsername, etEmail, etPassword;
    private CircularImageView civImage;
    private Button btnBrowse;

    Uri profileUri;

    private static final int PICK_IMAGE = 100;

    Session session = new Session(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        setTitle("My Account");

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

        btnBrowse.setOnClickListener(this);


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
            civImage.setImageURI(profileUri);
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
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveToDatabase() {

    }

    public boolean isNotEmptyFields() {
        String lastname = etLastname.getText().toString().trim();
        String firstname = etFirstname.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        if (password.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
