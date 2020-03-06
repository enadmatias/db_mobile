package com.example.univisit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    String strSchoolName;
    private RadioGroup rdgVisitorType;
    private RadioButton rdbtnParent, rdbtnStudent, rdbtnAlumni, rdbtnOthers;
    private TextView tvSchoolName;
    private EditText etVisitDate, etVisitTime, etIDNumber, etFileName, etOtherSpecify, etOtherVisitors;
    private Spinner spinnerPurpose;
    private Button btnBrowse;
    private ImageView ivValidId;

    String selectedVisitorType, selectedPurpose;
    private int vYear, vMonth, vDay, vHour, vMinute, vSeconds;
    private String strVisitDate, strVisitTime, am_pm;
    private final Calendar c = Calendar.getInstance();
    Uri uriValidId;
    Bitmap bitmap;
    Session session;

    String getTextOthers, getTextOtherVisitors;

    private SimpleDateFormat sdf;
    private String final_time, final_date, file_name, validId;


    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        //instantiating the declared variables
        rdgVisitorType = findViewById(R.id.rg_visitor_type);
        rdbtnParent = findViewById(R.id.rdbtn_parent);
        rdbtnStudent = findViewById(R.id.rdbtn_student);
        rdbtnAlumni = findViewById(R.id.rdbtn_alumni);
        rdbtnOthers = findViewById(R.id.rdbtn_others);
        tvSchoolName = findViewById(R.id.tv_school_name);
        etVisitDate = findViewById(R.id.et_choose_date);
        etVisitTime = findViewById(R.id.et_choose_time);
        etIDNumber = findViewById(R.id.et_val_id_num);
        spinnerPurpose = findViewById(R.id.spinner_purpose);
        btnBrowse = findViewById(R.id.btn_browse);
        ivValidId = findViewById(R.id.iv_valid_id);
        etFileName = findViewById(R.id.et_file_name);
        etOtherSpecify = findViewById(R.id.et_specify);
        etOtherVisitors = findViewById(R.id.et_others_visitor_type);

        btnBrowse.setOnClickListener(this);

        rdgVisitorType.setOnCheckedChangeListener(this);
        etVisitDate.setOnClickListener(this);
        etVisitTime.setOnClickListener(this);
        spinnerPurpose.setOnItemSelectedListener(this);

        session = new Session(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        
        showDataIntent();
    }

    private void showDataIntent() {
        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            strSchoolName = bundle.getString("school_name");

            setTitle(strSchoolName);

            tvSchoolName.setText(strSchoolName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_confirm, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_confirm:
                if (isNotEmptyFields()) {

                    if (selectedPurpose.equals("Others")) {
                        getTextOthers = etOtherSpecify.getText().toString();
                        selectedPurpose = getTextOthers;
                    }

                    if (selectedVisitorType.equals("Other")) {
                        getTextOtherVisitors = etOtherVisitors.getText().toString();
                        selectedVisitorType = getTextOtherVisitors;
                    }

                    bookVisit();

            }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void showSuccessDialog(String id, String date, String time) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_success_dialog, null);
        builder.setView(customLayout);

        Button btnKeep = customLayout.findViewById(R.id.btn_keep);
        TextView tv_id = customLayout.findViewById(R.id.tv_visit_id);
        TextView tv_name = customLayout.findViewById(R.id.tv_name);
        TextView tv_datetime =customLayout.findViewById(R.id.tv_date_time);

        tv_id.setText(id);
        tv_name.setText(session.getfname() + " " + session.getlname());
        tv_datetime.setText(date + " | " + time);
        btnKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isNotEmptyFields() {

        String visit_date = etVisitDate.getText().toString().trim();
        String visit_time = etVisitTime.getText().toString().trim();
        validId = etIDNumber.getText().toString().trim();
         file_name =  etFileName.getText().toString().trim();

        if (visit_date.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (visit_time.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (validId.isEmpty()) {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(file_name.isEmpty()){
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        switch (id) {
            case R.id.btn_browse: 
                openGalleryIntent();
                break;
            case R.id.et_choose_date:
                getVisitDate();
                break;
            case R.id.et_choose_time:
                getVisitTime();
                break;
        }
        
    }

    private void openGalleryIntent() {
        Intent intent = new Intent(); //blind intent
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    //handles opening the gallery


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            uriValidId = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriValidId);
                ivValidId.setImageURI(uriValidId);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rdbtnAlumni.isChecked()) {
            selectedVisitorType = rdbtnAlumni.getText().toString();
            etOtherVisitors.setVisibility(View.GONE);
        }else if (rdbtnParent.isChecked()) {
            selectedVisitorType = rdbtnParent.getText().toString();
            etOtherVisitors.setVisibility(View.GONE);
        }else if (rdbtnStudent.isChecked()) {
            selectedVisitorType = rdbtnStudent.getText().toString();
            etOtherVisitors.setVisibility(View.GONE);
        }
        else if (rdbtnOthers.isChecked()){
            selectedVisitorType = rdbtnOthers.getText().toString();
            etOtherVisitors.setVisibility(View.VISIBLE);
        }
    }

    public void getVisitDate() {
            vYear = c.get(Calendar.YEAR);
            vMonth = c.get(Calendar.MONTH);
            vDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.set(year, month, dayOfMonth);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    strVisitDate = simpleDateFormat.format(mCalendar.getTime());

                    etVisitDate.setText(strVisitDate);

            }
        }, vYear, vMonth, vDay);
        datePickerDialog.show();
    }

    public void getVisitTime() {
        vHour = c.get(Calendar.HOUR_OF_DAY);
        vMinute = c.get(Calendar.MINUTE);
        vSeconds = c.get(Calendar.SECOND);
        final String sec = String.valueOf(vSeconds);

        TimePickerDialog dialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //to get AM or PM
                        if (c.get(Calendar.AM_PM) == Calendar.AM) {
                            am_pm = "AM";
                        } else if (c.get(Calendar.AM_PM) == Calendar.PM) {
                            am_pm = "PM";
                        }

                        strVisitTime= (hourOfDay+":"+minute +" "+am_pm);
                        etVisitTime.setText(strVisitTime);
                        final_time = (hourOfDay + ":" + minute + ":" + sec);
                    }
                }, vHour, vMinute, true);
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int sid = parent.getId();

        switch (sid) {
            case R.id.spinner_purpose:
                selectedPurpose = this.spinnerPurpose.getItemAtPosition(position).toString();

                if (selectedPurpose.equals("Others")) {
                    etOtherSpecify.setVisibility(View.VISIBLE);

                }else {
                    etOtherSpecify.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String imagetoString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }

    public void bookVisit(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.URL_PATH_UPLOAD_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(message.equals("success")){
                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String id = object.getString("record_id");
                            showSuccessDialog(id, date, time);

                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("id", session.toString());
                params.put("selectedType", selectedVisitorType);
                params.put("schoolName", strSchoolName);
                params.put("date", strVisitDate);
                params.put("time", final_time);
                params.put("purpose", selectedPurpose);
                params.put("image", imagetoString(bitmap));
                params.put("name", file_name);
                params.put("validId", validId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    }

