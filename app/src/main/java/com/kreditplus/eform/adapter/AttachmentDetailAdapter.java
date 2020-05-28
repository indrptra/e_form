package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.AttachmentZoomPagerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Iwan Nurdesa on 13/11/15.
 */
public class AttachmentDetailAdapter extends RecyclerView.Adapter<AttachmentDetailAdapter.AttachmentVH> {

    private Context mContext;
    private List<String> attachmentList;

    public AttachmentDetailAdapter(Context mContext, List<String> attachmentList) {
        this.mContext = mContext;
        this.attachmentList = attachmentList;
    }

    @Override
    public AttachmentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_take_picture, parent, false);
        return new AttachmentVH(view);
    }

    @Override
    public void onBindViewHolder(final AttachmentVH holder, final int position) {
        Glide.with(mContext).load(attachmentList.get(position)).centerCrop().into(holder.imgTakePicture);
        holder.imgTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AttachmentZoomPagerActivity.class);
                intent.putStringArrayListExtra("attachments", (ArrayList<String>) attachmentList);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }

    public void setAttachmentList(List<String> attachmentList) {
        this.attachmentList = attachmentList;
        notifyDataSetChanged();
    }

    public class AttachmentVH extends RecyclerView.ViewHolder {

        @BindView(R.id.img_take_picture)
        ImageView imgTakePicture;

        public AttachmentVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
