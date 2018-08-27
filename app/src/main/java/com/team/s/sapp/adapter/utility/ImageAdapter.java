package com.team.s.sapp.adapter.utility;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.team.s.sapp.R;
import com.team.s.sapp.inf.MyOnClickItem;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    ArrayList<String> arrayList;
    Context context;
    MyOnClickItem myOnClickItem;

    public void setMyOnClickItem(MyOnClickItem myOnClickItem) {
        this.myOnClickItem = myOnClickItem;
    }

    public ImageAdapter(Context context) {

        ArrayList<String> arrayList = getAllShownImagesPath(context);
        Collections.reverse(arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView;
        rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context)
                .load(arrayList.get(position))
                .into(holder.imgGallery);
        holder.constraintImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOnClickItem.onClickItem(view, arrayList.get(position), 0);
            }
        });
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_gallery)
        ImageView imgGallery;
        @BindView(R.id.constraint_img)
        ConstraintLayout constraintImg;
        @BindView(R.id.tv_choose)
        TextView tv_choose;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    private ArrayList<String> getAllShownImagesPath(Context context) {
        Uri uri;
        Cursor cursor;
        int column_index_data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
