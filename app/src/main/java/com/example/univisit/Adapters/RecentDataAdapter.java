package com.example.univisit.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.univisit.API;
import com.example.univisit.Data;
import com.example.univisit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecentDataAdapter extends RecyclerView.Adapter<RecentDataAdapter.ViewHolder> {
  List<Data> dataList;
  Context context;
  int position;


    public RecentDataAdapter(Context ctx, List<Data> data) {
        this.context = ctx;
        this.dataList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.layout_recent_data, parent, false);
//
//
//        return new ViewHolder(itemView);


        final View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recent_data, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.layout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                final String record_id = dataList.get(viewHolder.getAdapterPosition()).getNo();

                menu.add("DELETE").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                        dialog.setMessage("Are you sure you want to delete this record?");

                        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                position = viewHolder.getAdapterPosition();
                                deleteRecord(record_id);
                                dialog.dismiss();
                            }
                        });

                        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();

                        return true;
                    }
                });
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Data data = dataList.get(position);

         holder.ID.setText(data.getNo());
         holder.Purpose.setText(data.getPurpose());
         holder.DateTime.setText(data.getDateTime());
         holder.Status.setText(data.getStatus());


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ID, DateTime, Purpose, Status;
        public LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ID =  itemView.findViewById(R.id.textView4);
            Purpose = itemView.findViewById(R.id.textView5);
            DateTime = itemView.findViewById(R.id.textView6);
            Status = itemView.findViewById(R.id.textView7);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    public void deleteRecord(final String recordId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.URL_PATH_DELETE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message  = jsonObject.getString("message");
                    if(message.equals("success"))
                    {
                        Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        Toast.makeText(context, "Failed to delete!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("recordId", recordId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
