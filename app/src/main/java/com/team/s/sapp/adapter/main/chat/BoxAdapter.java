package com.team.s.sapp.adapter.main.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.team.s.sapp.R;
import com.team.s.sapp.model.Box;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoxAdapter extends RecyclerSwipeAdapter<BoxAdapter.ViewHolder> {

    private ArrayList<Box> boxArrayList;
    private Context mContext;

    public BoxAdapter(ArrayList<Box> boxArrayList, Context mContext) {
        this.boxArrayList = boxArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Box item = boxArrayList.get(position);
        holder.tvUserName.setText("Minh pro IT ahihi");
        mItemManger.bindView(holder.itemView, position);
    }

    public void delItem(int pos) {

        boxArrayList.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return boxArrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe_box;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_call)
        ImageView imgCall;
        @BindView(R.id.rl_call)
        RelativeLayout rlCall;
        @BindView(R.id.img_remove)
        ImageView imgRemove;
        @BindView(R.id.rl_remove)
        RelativeLayout rlRemove;
        @BindView(R.id.ln_bottom_view)
        LinearLayout lnBottomView;
        @BindView(R.id.img_user)
        RoundedImageView imgUser;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_content_mess)
        TextView tvContentMess;
        @BindView(R.id.rlUserName)
        RelativeLayout rlUserName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.rl_item_box)
        RelativeLayout rlItemBox;
        @BindView(R.id.ln_top_view)
        LinearLayout lnTopView;
        @BindView(R.id.swipe_box)
        SwipeLayout swipeBox;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
