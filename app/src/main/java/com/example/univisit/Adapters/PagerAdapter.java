package com.example.univisit.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentListTitle = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get(position);
    }

    public void AddFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentListTitle.add(title);
    }
}
