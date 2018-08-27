package com.team.s.sapp.fragment.utility;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.team.s.sapp.R;
import com.team.s.sapp.adapter.utility.ImageAdapter;
import com.team.s.sapp.inf.MyOnClickItem;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rv_gallery)
    RecyclerView rvGallery;
    MyOnClickItem onClick, onClickImage;
    ImageAdapter imageAdapter;

    public void setOnClickImage(MyOnClickItem onClickImage) {
        this.onClickImage = onClickImage;
    }

    public void setOnClick(MyOnClickItem onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClick.onClickItem(view,null,0);
            }
        });
        initRecyclerView(view);
        return view;
    }

    public void initRecyclerView(View view) {
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rvGallery.setLayoutManager(gridLayoutManager);
        rvGallery.setHasFixedSize(true);
        imageAdapter = new ImageAdapter(getContext());
        rvGallery.setAdapter(imageAdapter);
        imageAdapter.setMyOnClickItem(new MyOnClickItem() {
            @Override
            public void onClickItem(View view, Object object, int position) {

                onClickImage.onClickItem(view,object,position);
            }
        });
    }

}
