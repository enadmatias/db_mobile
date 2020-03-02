package com.example.univisit.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.univisit.Adapters.PagerAdapter;
import com.example.univisit.R;
import com.example.univisit.Tabs.AllVisitFragment;
import com.example.univisit.Tabs.RecentVisitFragment;
import com.example.univisit.Tabs.UpcomingVisitFragment;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class VisitsFragment extends Fragment {

    private Toolbar vToolbar;
    private TabLayout vTabLayout;
    private ViewPager vViewPager;


    public VisitsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_visits, container, false);

        vToolbar = view.findViewById(R.id.toolbar_visits);
        ((AppCompatActivity)getActivity()).setSupportActionBar(vToolbar);

        vTabLayout = view.findViewById(R.id.tablayout);
        vViewPager = view.findViewById(R.id.viewpager);

        PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        adapter.AddFragment(new UpcomingVisitFragment(), "Upcoming");
        adapter.AddFragment(new RecentVisitFragment(), "Recent");
        adapter.AddFragment(new AllVisitFragment(), "All");

        vViewPager.setAdapter(adapter);
        vTabLayout.setupWithViewPager(vViewPager);

        return view;
    }



}
