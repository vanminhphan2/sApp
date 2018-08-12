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
import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.MainViewPagerAdapter;
import com.team.s.sapp.fragment.main.chat.ChatFragment;
import com.team.s.sapp.fragment.main.story.StoryFragment;

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
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;
    StoryFragment storyFragment;
    ChatFragment chatFragment;
    MainFragment mainFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupViewPager();
        tabMain.setupWithViewPager(vpMain);
        mainFragment=this;
        return view;
    }

    private void setupViewPager() {

        storyFragment = new StoryFragment();
        chatFragment = new ChatFragment();
        mainViewPagerAdapter = new MainViewPagerAdapter(getFragmentManager());
        mainViewPagerAdapter.addFragment(storyFragment, "Story");
        mainViewPagerAdapter.addFragment(chatFragment, "Chat");
        vpMain.setAdapter(mainViewPagerAdapter);
    }

    //Event click fab button
    @OnClick(R.id.fab_button)
    public void onClickFabButton() {

        if (vpMain.getCurrentItem() == 0) {//If current pager is story fragment
            //do something
        } else if (vpMain.getCurrentItem() == 1) {//If current pager is chat fragment
            if (chatFragment != null) {
                chatFragment.setChangeLayout();
            }
        } else return;
    }

    public void setChangeIconFab(boolean bl){

        if (bl){
            fabButton.setImageResource(R.drawable.ic_call_pink);
        }else {
            fabButton.setImageResource(R.drawable.ic_msg_green);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
