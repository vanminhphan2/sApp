package com.team.s.sapp.adapter.main.story;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.team.s.sapp.R;
import com.team.s.sapp.fragment.main.story.StoryFragment;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.story.Stories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoryAdapter extends PagerAdapter{

    private List<Stories> list_stories;
    private Context mcontext;
    private LayoutInflater inflater;
    public static MediaPlayer mediaPlayer;

    public StoryAdapter(List<Stories> stories, Context context)
    {
        this.list_stories = stories;
        this.mcontext = context;

    }

    @Override
    public int getCount() {
        return list_stories.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (ConstraintLayout) o) ;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_story, container, false);
        //
        final ConstraintLayout layout =  (ConstraintLayout)view.findViewById(R.id.constrant_audio);
        ImageView background = (ImageView)view.findViewById(R.id.img_bg_story);
        TextView title = (TextView) view.findViewById(R.id.tv_title_story);
        TextView username = (TextView) view.findViewById(R.id.tv_username_story);
        TextView topic_story = (TextView) view.findViewById(R.id.tv_topic_story);
        final Button play_audio = (Button) view.findViewById(R.id.btn_play_sound);
        final Button pause = (Button) view.findViewById(R.id.btn_pause);
        final SeekBar seekAudio = (SeekBar) view.findViewById(R.id.seek_audio);
        final TextView timeTotal = (TextView) view.findViewById(R.id.tv_time_total);
        final TextView timePlay = (TextView) view.findViewById(R.id.tv_time_play);
        //
        final Stories stories = list_stories.get(position);
        title.setText(stories.getTitle());
        topic_story.setText("#"+stories.getTopic());
        username.setText("@"+stories.getAccountName());
        Picasso.with(mcontext).load(stories.getImage()).into(background);
        play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Playing();
                if (mediaPlayer != null)
                {
                        mediaPlayer.release();
                        mediaPlayer = MediaPlayer.create(mcontext, Uri.parse(stories.getAudio()));
                        mediaPlayer.start();
                }
                else
                {
                    mediaPlayer = MediaPlayer.create(mcontext, Uri.parse(stories.getAudio()));
                    mediaPlayer.start();
                }

                //
                layout.setVisibility(View.VISIBLE);
                play_audio.setVisibility(View.GONE);

                // set time of audio for text view time
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                timeTotal.setText(format.format(mediaPlayer.getDuration()));
                //set max seek bar
                seekAudio.setMax(mediaPlayer.getDuration());
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
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        container.addView(view);
        return view;
    }


    public static boolean Playing()
    {
        return true;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ConstraintLayout)object);
    }

}
