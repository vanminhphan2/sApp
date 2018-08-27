package com.team.s.sapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.team.s.sapp.R;
import com.team.s.sapp.view.image.ImageMatrixTouchHandler;

public class ImageViewerActivity extends AppCompatActivity {

    ImageView imageView, imgDownload, imgShare;
    View view;
    public static ImageViewerActivity imageViewerActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_imageviewer);
        imageViewerActivity = this;
        Intent intent = getIntent();
        String linkImg = intent.getStringExtra("LINK_IMAGE");
        imageView = (ImageView) findViewById(R.id.imageView);
        view = findViewById(R.id.layout);

        if (linkImg != null) {
            Glide.with(this)
                    .load(linkImg).thumbnail(0.5f)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
                            .onlyRetrieveFromCache(true))
                    .into(imageView);
        }

        view.getBackground().setAlpha(255);

        ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(this, view);
        imageView.setOnTouchListener(imageMatrixTouchHandler);
    }

    public void exitActivity() {
        supportFinishAfterTransition();
    }
}
