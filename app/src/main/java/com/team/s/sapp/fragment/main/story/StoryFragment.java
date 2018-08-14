package com.team.s.sapp.fragment.main.story;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.team.s.sapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class StoryFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.img_bg_story)        ImageView imgbg;
    @BindView(R.id.img_cancel_story)    ImageView cancel_menu;
    @BindView(R.id.img_heart_story)     ImageView like;
    @BindView(R.id.img_cmt_story)       ImageView comment;
    @BindView(R.id.img_follow_story)    ImageView follow;
    @BindView(R.id.img_change_story)    ImageView change;
    @BindView(R.id.fab_menu_story)      FloatingActionButton fab_menu;
    @BindView(R.id.fab_create_story)    FloatingActionButton fab_create;
    @BindView(R.id.layout)              ConstraintLayout room_layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_story, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // control event click
    @SuppressLint("RestrictedApi")
    @OnClick({R.id.fab_menu_story, R.id.fab_create_story, R.id.img_cancel_story, R.id.img_heart_story,
            R.id.img_cmt_story, R.id.img_follow_story, R.id.img_change_story})
    public void ShowMenu(final View view)
    {
        switch (view.getId())
        {
            case R.id.fab_menu_story:
                ShowView();
                break;
            case R.id.img_cancel_story:
                HideView();
                break;
            case R.id.img_heart_story:
                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_cmt_story:
                Toast.makeText(getContext(), "Commented", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_follow_story:
                Toast.makeText(getContext(), "Followed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_change_story:
                Toast.makeText(getContext(), "Changed", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // set animation for layout menu
    @SuppressLint("RestrictedApi")
    private void ShowView(){
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
    private void HideView(){

        //show and animated fab menu
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.in_right);
        fab_menu.startAnimation(animation);
        fab_menu.setVisibility(View.VISIBLE);
        //hide layout menu
        final Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.out_left);
        room_layout.startAnimation(animation1);
        room_layout.setVisibility(View.GONE);


    }
}

