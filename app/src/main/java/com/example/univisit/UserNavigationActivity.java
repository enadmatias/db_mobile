package com.example.univisit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.univisit.Fragments.HomeFragment;
import com.example.univisit.Fragments.VisitsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView userBottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_navigation);

        userBottomView = (BottomNavigationView) findViewById(R.id.navbar_user);
        userBottomView.setOnNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_content_container, new HomeFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_account:
                toMyAccount();
                return true;
            case R.id.action_support:
                return true;
            case R.id.action_logout:
                showLogoutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserNavigationActivity.this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("LOG OUT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toMainPage();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void toMyAccount() {
        Intent intent = new Intent(UserNavigationActivity.this, MyAccountActivity.class);
        startActivity(intent);
    }

    public void toMainPage() {
        Intent intent = new Intent(UserNavigationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;

        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_visits:
                selectedFragment = new VisitsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.user_content_container, selectedFragment).commit();

        return true;
    }
}
