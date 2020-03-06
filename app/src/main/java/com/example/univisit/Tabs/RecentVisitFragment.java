package com.example.univisit.Tabs;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.univisit.API;
import com.example.univisit.Adapters.RecentDataAdapter;
import com.example.univisit.Data;
import com.example.univisit.R;
import com.example.univisit.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.univisit.R.id.layout_no_data;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentVisitFragment extends Fragment {

    private List<Data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecentDataAdapter recentDataAdapter;
    Session session;
    private LinearLayout linearLayout;


    public RecentVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        session = new Session(getContext());
        View view = inflater.inflate(R.layout.fragment_recent_visit, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recentDataAdapter = new RecentDataAdapter(getContext(), dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recentDataAdapter);
        linearLayout = view.findViewById(R.id.layout_no_data);

        dataList.clear();
        populateData();

        return view;
    }

    public void populateData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.URL_PATH_GET_DATA_ACCEPTED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if(message.equals("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("record_id").trim();
                            String purpose = object.getString("purpose").trim();
                            String date = object.getString("date").trim();
                            String time = object.getString("time").trim();
                            String status = object.getString("status").trim();
                            String datetime = date + " " + time;
                            dataList.add(new Data(id, purpose, datetime, status));
                            recentDataAdapter.notifyDataSetChanged();
                            linearLayout.setVisibility(View.GONE);

                        }
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                   Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", session.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

}
