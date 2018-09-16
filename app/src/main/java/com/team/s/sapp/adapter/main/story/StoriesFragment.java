package com.team.s.sapp.adapter.main.story;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.team.s.sapp.R;
import com.team.s.sapp.fragment.main.story.StoryFragment;
import com.team.s.sapp.model.story.Stories;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoriesFragment extends Fragment {

    private Stories stories;
    public static boolean dk = false;
    public static MediaPlayer mediaPlayer = null;
    public static int fragment_item;

    @BindView(R.id.constrant_audio)
    ConstraintLayout layout;
    @BindView(R.id.img_bg_story)
    ImageView background;
    @BindView(R.id.tv_title_story)
    TextView title;
    @BindView(R.id.tv_username_story)
    TextView username;
    @BindView(R.id.tv_topic_story)
    TextView topic_story;
    @BindView(R.id.btn_play_sound)
    Button play_audio;
    @BindView(R.id.btn_pause)
    Button pause;
    @BindView(R.id.seek_audio)
    SeekBar seekAudio;
    @BindView(R.id.tv_time_total)
    TextView timeTotal;
    @BindView(R.id.tv_time_play)
    TextView timePlay;

    public static Fragment newInstance(Stories stories)
    {
        Bundle args = new Bundle();
        args.putSerializable("stories", (Serializable) stories);
        StoriesFragment fragment = new StoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stories = (Stories)getArguments().getSerializable("stories");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_story, container, false);
        ButterKnife.bind(this, view);
        title.setText(stories.getTitle());
        topic_story.setText("#"+stories.getTopic());
        username.setText("@"+stories.getAccountName());
        Picasso.with(getContext()).load(stories.getImage()).into(background);

        play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayAudio();
                fragment_item = StoryFragment.item;
                Toast.makeText(getContext(), String.valueOf(fragment_item), Toast.LENGTH_SHORT).show();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               PauseAudio();
            }
        });

        //seek bar change
        seekAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekAudio.getProgress());
            }
        });

        if (dk == true)
        {
            layout.setVisibility(View.GONE);
            play_audio.setVisibility(View.VISIBLE);
        }
        return view;
    }


    // play audio
    public void PlayAudio()
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(stories.getAudio()));
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(stories.getAudio()));
            mediaPlayer.start();
        }
        //
        layout.setVisibility(View.VISIBLE);
        play_audio.setVisibility(View.GONE);

        // set time
        SetTimeTotal();
        // update time
        UpdateTimeAudio();
    }

    // pause audio
    public void PauseAudio()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            pause.setBackgroundResource(R.drawable.ic_play_arrow);
        }
        else {
            mediaPlayer.start();
            pause.setBackgroundResource(R.drawable.ic_pause);
        }
    }

    //set time total for audio
    public void SetTimeTotal()
    {
        // set time of audio for text view time
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        timeTotal.setText(format.format(mediaPlayer.getDuration()));
        //set max seek bar
        seekAudio.setMax(mediaPlayer.getDuration());
    }

    //update time audio
    public void UpdateTimeAudio()
    {
        //update time audio
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                timePlay.setText(format.format(mediaPlayer.getCurrentPosition()));
                seekAudio.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    //
    public void ChangePauseIcon()
    {
        pause.setBackgroundResource(R.drawable.ic_play_arrow);
    }
}
