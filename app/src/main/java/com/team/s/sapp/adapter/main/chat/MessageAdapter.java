package com.team.s.sapp.adapter.main.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.makeramen.roundedimageview.RoundedImageView;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.team.s.sapp.MainActivity;
import com.team.s.sapp.R;
import com.team.s.sapp.inf.MyOnClickItem;
import com.team.s.sapp.model.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Message> messageArrayList;
    private Context mContext;
    ImageViewer imageViewer;
    private final int VIEW_TYPE_LOADING = 0;
    private final int MESS_SEND = 1;
    private final int MESS_GET = 2;
    private int widthItem;

//    public void setOnClickItem(MyOnClickItem onClickItem) {
//        this.onClickItem = onClickItem;
//    }

    MyOnClickItem onClickItem;


    public MessageAdapter(ArrayList<Message> messageArrayList, Context mContext, MyOnClickItem myOnClickItem) {
        this.messageArrayList = messageArrayList;
        this.mContext = mContext;
        this.onClickItem=myOnClickItem;
        widthItem = MainActivity.WIDTHDEVICE - parseDpToPx(110);
    }

    public int parseDpToPx(int dp) {

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale + 0.5f);
        return px;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Fresco.initialize(mContext);
        View view;

        if (viewType == MESS_SEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_send, parent, false);
            return new VHItemSend(view);
        } else if (viewType == MESS_GET) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess_get, parent, false);
            return new VHItemGet(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final Message item = messageArrayList.get(position);

        if (holder instanceof VHItemSend) {

            if (!item.getMessImage().equals("")) {

                int newH = (int) (widthItem * item.getRatioImage());
                ConstraintLayout.LayoutParams lp= new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, newH);
                lp.bottomToTop=((VHItemSend) holder).tvTimeSent.getId();
                ((VHItemSend) holder).imgMess.setLayoutParams(lp);
                ((VHItemSend) holder).imgMess.requestLayout();

                ((VHItemSend) holder).imgMess.setVisibility(View.VISIBLE);
                ((VHItemSend) holder).tvMess.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(item.getMessImage())
                        .into(((VHItemSend) holder).imgMess);

                ((VHItemSend) holder).tvTimeSent.setText(item.getTimeSend());
                Glide.with(mContext)
                        .load(item.getMessImage()).thumbnail(0.5f)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .skipMemoryCache(false)
                                .error(R.color.colorAccent)
                                .placeholder(R.color.colorPrimary)
                                .transforms(new RoundedCorners(parseDpToPx(10)))
                        )
                        .into(((VHItemSend) holder).imgMess);
//                ViewCompat.setTransitionName(((VHItemSend) holder).imgMess, "Transition"+String.valueOf(position));
                ViewCompat.setTransitionName(((VHItemSend) holder).imgMess, "image");

                ((VHItemSend) holder).imgMess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onClickItem.onClickItem(((VHItemSend) holder).imgMess,item.getMessImage(),position);
                    }
                });

            } else {

                ((VHItemSend) holder).imgMess.setVisibility(View.GONE);
                ((VHItemSend) holder).tvMess.setVisibility(View.VISIBLE);

                ((VHItemSend) holder).tvMess.setText(item.getMessText());
                ((VHItemSend) holder).tvTimeSent.setText(item.getTimeSend());
            }

        } else if (holder instanceof VHItemGet) {

            if (!item.getMessImage().equals("")) {


                int newH = (int) (widthItem * item.getRatioImage());
                ConstraintLayout.LayoutParams lp= new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, newH);
                lp.bottomToTop=((VHItemGet) holder).tvTimeSent.getId();
                ((VHItemGet) holder).imgMess.setLayoutParams(lp);
                ((VHItemGet) holder).imgMess.requestLayout();

                ((VHItemGet) holder).imgMess.setVisibility(View.VISIBLE);
                ((VHItemGet) holder).tvMess.setVisibility(View.GONE);
                Glide.with(mContext)
                        .load(item.getMessImage())
                        .into(((VHItemGet) holder).imgMess);
                ((VHItemGet) holder).tvTimeSent.setText(item.getTimeSend());
                Glide.with(mContext)
                        .asBitmap()
                        .load(item.getMessImage()).thumbnail(0.5f)
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .skipMemoryCache(false)
                                .error(R.color.colorAccent)
                                .placeholder(R.color.colorPrimary)
                                .transforms(new RoundedCorners(parseDpToPx(10))))
                        .into(((VHItemGet) holder).imgMess);
//                ViewCompat.setTransitionName(((VHItemGet) holder).imgMess, "Transition"+String.valueOf(position));
                ViewCompat.setTransitionName(((VHItemGet) holder).imgMess, "image");
                ((VHItemGet) holder).imgMess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        onClickItem.onClickItem(((VHItemGet) holder).imgMess,item.getMessImage(),position);
                    }
                });
            } else {

                ((VHItemGet) holder).imgMess.setVisibility(View.GONE);
                ((VHItemGet) holder).tvMess.setVisibility(View.VISIBLE);
                ((VHItemGet) holder).tvMess.setText(item.getMessText());
                ((VHItemGet) holder).tvTimeSent.setText(item.getTimeSend());
            }
        } else if (holder instanceof LoadingViewHolder) {

        }

    }

    @Override
    public int getItemCount() {

        return messageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (messageArrayList.get(position) != null)
            if (isPositionItemSend(position)) {
                return MESS_SEND;
            } else
                return MESS_GET;
        else
            return VIEW_TYPE_LOADING;
    }

    private boolean isPositionItemSend(int position) {
        if (messageArrayList.get(position).getIdSender().equals("ABC"))
            return true;
        else return false;
    }

    class VHItemSend extends RecyclerView.ViewHolder {

        @BindView(R.id.img_mess)
        ImageView imgMess;
        @BindView(R.id.constraint_mess)
        ConstraintLayout constraint_mess;
        @BindView(R.id.tv_mess)
        TextView tvMess;
        @BindView(R.id.tv_time_sent)
        TextView tvTimeSent;

        public VHItemSend(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class VHItemGet extends RecyclerView.ViewHolder {

        @BindView(R.id.img_mess)
        ImageView imgMess;
        @BindView(R.id.constraint_mess)
        ConstraintLayout constraint_mess;
        @BindView(R.id.tv_mess)
        TextView tvMess;
        @BindView(R.id.tv_time_sent)
        TextView tvTimeSent;

        public VHItemGet(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }


}
