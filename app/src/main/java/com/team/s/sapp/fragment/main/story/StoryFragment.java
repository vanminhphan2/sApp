package com.team.s.sapp.fragment.main.story;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.s.sapp.R;
import com.team.s.sapp.adapter.main.story.StoriesFragment;
import com.team.s.sapp.adapter.main.story.StoryFragmentAdapter;
import com.team.s.sapp.adapter.main.story.custom.VerticalViewPager;
import com.team.s.sapp.api.ApiClient;
import com.team.s.sapp.api.GetStoryApi;
import com.team.s.sapp.model.story.Stories;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryFragment extends Fragment {


    public static int item;
    Unbinder unbinder;
    @BindView(R.id.vp_story)
    VerticalViewPager rcvStory;
    @BindView(R.id.fab_menu_story)
    FloatingActionButton fab_menu;
    @BindView(R.id.fab_create_story)
    FloatingActionButton fab_create;
    @BindView(R.id.layout)
    ConstraintLayout room_layout;
    @BindView(R.id.img_cancel_story)
    ImageView imgCancelStory;
    @BindView(R.id.img_heart_story)
    ImageView imgHeartStory;
    @BindView(R.id.txt_heart_story)
    TextView txtHeartStory;
    @BindView(R.id.img_cmt_story)
    ImageView imgCmtStory;
    @BindView(R.id.txt_cmt_story)
    TextView txtCmtStory;
    @BindView(R.id.img_follow_story)
    ImageView imgFollowStory;
    @BindView(R.id.txt_follow_story)
    TextView txtFollowStory;
    @BindView(R.id.img_change_story)
    ImageView imgChangeStory;
    @BindView(R.id.txt_change_story)
    TextView txtChangeStory;

    private List<Stories> storiesList;
    private StoryFragmentAdapter adapter;
    private FragmentManager fragmentManager;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragmentManager = getFragmentManager();
        loadStory();
        rcvStory.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.e("Pagescrolled i:", ""+i);
            }

            @Override
            public void onPageSelected(int i) {
                Log.e("PageSelected position:", ""+i);
                item = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (StoriesFragment.mediaPlayer != null) {
                    if (StoriesFragment.mediaPlayer.isPlaying()) {
                        StoriesFragment.mediaPlayer.pause();
                    }
                }

            }
        });
        return view;
    }



    //
    public void loadStory() {
        storiesList = new ArrayList<>();
        final GetStoryApi api = ApiClient.getClient().create(GetStoryApi.class);
        Call<List<Stories>> call = api.getStory();
        call.enqueue(new Callback<List<Stories>>() {
            @Override
            public void onResponse(Call<List<Stories>> call, Response<List<Stories>> response) {
                storiesList = response.body();
                // pager adapter
                adapter = new StoryFragmentAdapter(fragmentManager, storiesList);
                rcvStory.setAdapter(adapter);
                rcvStory.setOverScrollMode(View.OVER_SCROLL_NEVER);
            }

            @Override
            public void onFailure(Call<List<Stories>> call, Throwable t) {
                Toast.makeText(getContext(), "Fetching list failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // control event click
    @SuppressLint("RestrictedApi")
    @OnClick({R.id.fab_menu_story, R.id.fab_create_story, R.id.img_cancel_story, R.id.img_heart_story,
            R.id.img_cmt_story, R.id.img_follow_story, R.id.img_change_story})
    public void ShowMenu(final View view) {
        switch (view.getId()) {
            case R.id.fab_menu_story:
                ShowView();
                break;
            case R.id.img_cancel_story:
                HideView();
                break;
            case R.id.img_heart_story:
                imgHeartStory.setBackgroundResource(R.drawable.ic_heart_red);
                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_cmt_story:
                imgCmtStory.setBackgroundResource(R.drawable.ic_comment_blue);
                Toast.makeText(getContext(), "Commented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_follow_story:
                imgFollowStory.setBackgroundResource(R.drawable.ic_follow_us_blue);
                Toast.makeText(getContext(), "Followed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_change_story:
                Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // set animation for layout menu
    @SuppressLint("RestrictedApi")
    private void ShowView() {
        //animated fab
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.out_left);
        //animation.setDuration(1000);
        //animation.setFillAfter(false);
        fab_menu.startAnimation(animation);
        fab_menu.setVisibility(View.INVISIBLE);

        //show menu
        Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.in_right);
        room_layout.startAnimation(animation1);
        room_layout.setVisibility(View.VISIBLE);
    }

    //hide animation menu
    @SuppressLint("RestrictedApi")
    private void HideView() {

        //show and animated fab menu
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.in_right);
        fab_menu.startAnimation(animation);
        fab_menu.setVisibility(View.VISIBLE);
        //hide layout menu
        final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.out_left);
        room_layout.startAnimation(animation1);
        room_layout.setVisibility(View.GONE);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}

