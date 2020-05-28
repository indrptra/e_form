package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.activity.FormPengajuanNonElcActivity;
import com.kreditplus.eform.fragment.ListDraftAndSinkronisasiNonElcFragment;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDraftAndSinkronisasiNonElcAdapter extends RecyclerView.Adapter<ListDraftAndSinkronisasiNonElcAdapter.ViewHolder> {

    private String mToken;
    private Context context;
    private List<Pengajuan> mPengajuanList;
    private ListDraftAndSinkronisasiNonElcFragment fragmentEditSync;
    private FirebaseAnalytics mFirebaseAnalytics;


    public ListDraftAndSinkronisasiNonElcAdapter(String mToken, Context context, List<Pengajuan> mPengajuanList, ListDraftAndSinkronisasiNonElcFragment fragmentEditSync) {
        this.mToken = mToken;
        this.context = context;
        this.mPengajuanList = mPengajuanList;
        this.fragmentEditSync = fragmentEditSync;
    }

    @NonNull
    @Override
    public ListDraftAndSinkronisasiNonElcAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pengajuan_unsync, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListDraftAndSinkronisasiNonElcAdapter.ViewHolder holder, final int i) {
        if (mPengajuanList.get(i).getIsDraft().equalsIgnoreCase("true")) {
            holder.rlDraft.setVisibility(View.VISIBLE);
            holder.lnStatusSync.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnSync.setVisibility(View.GONE);
        } else {
            holder.rlDraft.setVisibility(View.GONE);
            holder.lnStatusSync.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnSync.setVisibility(View.VISIBLE);
        }

        holder.txtName.setText(mPengajuanList.get(i).getFullName());
        holder.txtAddress.setText(mPengajuanList.get(i).getAddress());
        holder.txtHp.setText(mPengajuanList.get(i).getPhone());
        holder.txtTime.setText(mPengajuanList.get(i).getSubmitedDate());

        holder.imgOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(context)) {
                    PopupMenu popup = new PopupMenu(context, v);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.menu_list_unsync_pengajuan, popup.getMenu());
                    popup.setOnMenuItemClickListener(new ListDraftAndSinkronisasiNonElcAdapter.MyMenuItemClickListener(i));
                    popup.show();
                }
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(context)) {
                    Intent intent;
                    mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "edit_draft_click");
                    mFirebaseAnalytics.logEvent("edit_draft_click", bundle);

                    if (mPengajuanList.get(i).getOfferingType().equalsIgnoreCase("MTR")){
                        Bundle bundle1 = new Bundle();
                        bundle1.putString(FirebaseAnalytics.Param.ITEM_NAME, "edit_draft_motor_open");
                        mFirebaseAnalytics.logEvent("edit_draft_motor_open", bundle1);
                        intent = new Intent(context, FormPengajuanNonElcActivity.class);
                        intent.putExtra("pengajuan_type", "G");
                    }
                    else{
                        Bundle bundle2 = new Bundle();
                        bundle2.putString(FirebaseAnalytics.Param.ITEM_NAME, "edit_draft_elc_open");
                        mFirebaseAnalytics.logEvent("edit_draft_elc_open", bundle2);
                        intent = new Intent(context, FormPengajuanActivity.class);
                        intent.putExtra("pengajuan_type", "E");
                    }
                    intent.putExtra("app_id", mPengajuanList.get(i).getId());
                    intent.putExtra("type_data_offering", mPengajuanList.get(i).getOfferingType());
                    intent.putExtra("form", "Draft");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPengajuanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.img_overflow)
        ImageView imgOverflow;
        @BindView(R.id.txt_address)
        TextView txtAddress;
        @BindView(R.id.ln_address)
        LinearLayout lnAddress;
        @BindView(R.id.txt_hp)
        TextView txtHp;
        @BindView(R.id.ln_hp)
        LinearLayout lnHp;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.rl_draft)
        RelativeLayout rlDraft;
        @BindView(R.id.img_status_sync_data)
        ImageView imgStatusSyncData;
        @BindView(R.id.pb_data_text)
        ProgressBar pbDataText;
        @BindView(R.id.va_data_text)
        ViewAnimator vaDataText;
        @BindView(R.id.txt_status_sync_data)
        TextView txtStatusSyncData;
        @BindView(R.id.img_status_sync_image)
        ImageView imgStatusSyncImage;
        @BindView(R.id.pb_data_image)
        ProgressBar pbDataImage;
        @BindView(R.id.va_data_image)
        ViewAnimator vaDataImage;
        @BindView(R.id.txt_status_sync_image)
        TextView txtStatusSyncImage;
        @BindView(R.id.ln_status_sync)
        LinearLayout lnStatusSync;
        @BindView(R.id.btn_edit)
        Button btnEdit;
        @BindView(R.id.btn_sync)
        Button btnSync;
        @BindView(R.id.tl_pengajuan)
        LinearLayout tlPengajuan;
        @BindView(R.id.cv_pengajuan)
        CardView cvPengajuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int i;

        public MyMenuItemClickListener(int i) {
            this.i = i;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_delete:
                    fragmentEditSync.fragDeleteDraft(mPengajuanList.get(i).getId());
                    return true;
            }
            return false;
        }
    }
}
