package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.DetailKpmActivity;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apc-lap012 on 10/05/17.
 */

public class ListJanjiBertemuAdapter extends RecyclerView.Adapter<ListJanjiBertemuAdapter.JanjiVH> {

    private Context mContext;
    private List<Pengajuan> mJanjiBertemuList ;

    public ListJanjiBertemuAdapter(Context context, List<Pengajuan> mJanjiBertemuList) {
        this.mContext = context;
        this.mJanjiBertemuList = mJanjiBertemuList;
    }

    @Override
    public JanjiVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_janji_bertemu, parent, false);
        return new JanjiVH(view);
    }

    @Override
    public void onBindViewHolder(JanjiVH holder, final int position) {

        holder.txtNameBertemu.setText(mJanjiBertemuList.get(position).getFullName());
        holder.txtAddressBertemu.setText(mJanjiBertemuList.get(position).getPlaceOfReminder());
        holder.txtTanggalBertemu.setText(mJanjiBertemuList.get(position).getReminderDate());
        holder.txtHpBertemu.setText(mJanjiBertemuList.get(position).getPhone());

        holder.lnItemJanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPengajuan = new Intent(mContext, DetailKpmActivity.class);
                intentPengajuan.putExtra("app_id", mJanjiBertemuList.get(position).getId());
                intentPengajuan.putExtra("status", mJanjiBertemuList.get(position).getStatus());
                mContext.startActivity(intentPengajuan);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mJanjiBertemuList.size();
    }

    public class JanjiVH extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name_bertemu)
        TextView txtNameBertemu;
        @BindView(R.id.txt_address_bertemu)
        TextView txtAddressBertemu;
        @BindView(R.id.txt_tanggal_bertemu)
        TextView txtTanggalBertemu;
        @BindView(R.id.txt_hp_bertemu)
        TextView txtHpBertemu;

        @BindView(R.id.ln_item_janji)
        LinearLayout lnItemJanji;

        public JanjiVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
