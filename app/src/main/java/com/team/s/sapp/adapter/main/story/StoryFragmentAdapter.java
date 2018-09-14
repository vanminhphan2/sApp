package com.team.s.sapp.adapter.main.story;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.team.s.sapp.model.story.Stories;

import java.util.List;

public class StoryFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Stories> list;

    public StoryFragmentAdapter(FragmentManager fm, List<Stories> stories) {
        super(fm);
        this.list = stories;
    }

    @Override
    public Fragment getItem(int position) {
        return StoriesFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
