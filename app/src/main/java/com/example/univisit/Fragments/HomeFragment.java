package com.example.univisit.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.univisit.AddVisitActivity;
import com.example.univisit.R;
import com.example.univisit.Session;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private Toolbar hToolbar;
    private CircularImageView civUser;
    private TextView tvName, tvPhone, tvEmail, tvAddr;
    private LinearLayout layoutMain, layoutBanilad, layoutLm;
    ImageLoader imageLoader;

    Session session;




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        hToolbar = view.findViewById(R.id.toolbar_home);
        ((AppCompatActivity)getActivity()).setSupportActionBar(hToolbar);

        civUser = view.findViewById(R.id.circularImageView);
        tvName = view.findViewById(R.id.tv_name);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAddr = view.findViewById(R.id.tv_addr);

        layoutMain = view.findViewById(R.id.layout_main);
        layoutBanilad = view.findViewById(R.id.layout_banilad);
        layoutLm = view.findViewById(R.id.layout_lm);

        layoutMain.setOnClickListener(this);
        layoutBanilad.setOnClickListener(this);
        layoutLm.setOnClickListener(this);

        session = new Session(view.getContext());
        tvName.setText(session.getfname() + " " + session.getlname());
        tvPhone.setText(session.getcontact());
        tvEmail.setText(session.getemail());
        tvAddr.setText(session.getaddress());

        Picasso.get().load(session.getPath()).resize(50, 50).centerCrop().into(civUser);


        return view;
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.layout_main:
                String strMain = "UC-Main";
                Intent toMain = new Intent(getActivity(), AddVisitActivity.class);
                toMain.putExtra("school_name", strMain);
                getActivity().startActivity(toMain);

                break;
            case R.id.layout_banilad:
                String strBanilad = "UC-Banilad";
                Intent toBanilad = new Intent(getActivity(), AddVisitActivity.class);
                toBanilad.putExtra("school_name", strBanilad);
                getActivity().startActivity(toBanilad);
                break;
            case R.id.layout_lm:
                String strLm = "UC-LM";

                Intent toLm = new Intent(getActivity(), AddVisitActivity.class);
                toLm.putExtra("school_name", strLm);
                getActivity().startActivity(toLm);
                break;
        }
     }
    }

