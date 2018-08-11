package com.team.s.sapp.fragment.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.MainViewPagerAdapter;
import com.team.s.sapp.fragment.main.chat.ChatFragment;
import com.team.s.sapp.fragment.main.story.StoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    Unbinder unbinder;

    MainViewPagerAdapter mainViewPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupViewPager();
        tabMain.setupWithViewPager(vpMain);
        return view;
    }

    private void setupViewPager(){

        mainViewPagerAdapter= new MainViewPagerAdapter(getFragmentManager());
        mainViewPagerAdapter.addFragment(new StoryFragment(), "Story");
        mainViewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        vpMain.setAdapter(mainViewPagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
