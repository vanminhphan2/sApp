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

import com.melnykov.fab.FloatingActionButton;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.MainViewPagerAdapter;
import com.team.s.sapp.fragment.account.EditProfileFragment;
import com.team.s.sapp.fragment.main.chat.ChatFragment;
import com.team.s.sapp.fragment.main.story.StoryFragment;
import com.team.s.sapp.model.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    Unbinder unbinder;

    MainViewPagerAdapter mainViewPagerAdapter;
    StoryFragment storyFragment;
    ChatFragment chatFragment;
    MainFragment mainFragment;

    public Profile user;

    public static MainFragment newInstance(Profile profile) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable("USER_PROFILE", profile);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(getArguments()!=null){
            user= (Profile) getArguments().getSerializable("USER_PROFILE");
        }
        setupViewPager();
        tabMain.setupWithViewPager(vpMain);
        mainFragment=this;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(MainActivity.mainActivity.loadingDialog!=null)
            if (MainActivity.mainActivity.loadingDialog.isShowing())
                MainActivity.mainActivity.loadingDialog.hide();
    }

    private void setupViewPager() {

        storyFragment = new StoryFragment();
        chatFragment = new ChatFragment();
        mainViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager());
        mainViewPagerAdapter.addFragment(storyFragment, "Story");
        mainViewPagerAdapter.addFragment(chatFragment, "Chat");
        vpMain.setAdapter(mainViewPagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
