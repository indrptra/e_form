package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.fragment.PengajuanUnsyncFragment;
import com.kreditplus.eform.helper.Constant;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.AssetKendaraan;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.PengajuanBaru;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iwan Nurdesa on 13/11/15.
 */
public class PengajuanUnsyncAdapter extends RecyclerView.Adapter<PengajuanUnsyncAdapter.PengajuanUnsyncVH> {

    @Inject
    DatabaseService databaseService;

    private Context mContext;
    private List<PengajuanBaru> pengajuanBaruList;
    private PengajuanUnsyncFragment fragment;

    public PengajuanUnsyncAdapter(Context mContext, List<PengajuanBaru> pengajuanBaruList, PengajuanUnsyncFragment fragment) {
        App.appComponent().inject(this);
        this.mContext = mContext;
        this.pengajuanBaruList = pengajuanBaruList;
        this.fragment = fragment;
    }

    @Override
    public PengajuanUnsyncVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_pengajuan_unsync, parent, false);
        return new PengajuanUnsyncVH(view);
    }

    @Override
    public void onBindViewHolder(final PengajuanUnsyncVH holder, final int position) {
        if (pengajuanBaruList.get(position).getIdKpm() != null && !"00".equalsIgnoreCase(pengajuanBaruList.get(position).getIdKpm()) && !"".equalsIgnoreCase(pengajuanBaruList.get(position).getIdKpm())) {
            String kpm = pengajuanBaruList.get(position).getNamaLengkap0() + " - KPM";
            holder.txtName.setText(kpm);
        } else {
            holder.txtName.setText(pengajuanBaruList.get(position).getNamaLengkap0());
        }

        holder.txtAddress.setText(pengajuanBaruList.get(position).getAlamatTinggal1());
        holder.txtHp.setText(pengajuanBaruList.get(position).getMobilePhone0());
        holder.txtTime.setText("Date created: " + pengajuanBaruList.get(position).getCreatedAt());

        if ("draft_baru".equalsIgnoreCase(pengajuanBaruList.get(position).getFormType()) || "draft_edit".equalsIgnoreCase(pengajuanBaruList.get(position).getFormType())) {
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
        holder.imgOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(mContext)) {
                    checkMenuDelete(mContext, v);
                }
            }

            private void checkMenuDelete(Context mContext, View v) {
                PopupMenu popup = new PopupMenu(mContext, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_unsync_pengajuan, popup.getMenu());
                popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
                popup.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(mContext)) {
                    checkBtnEdit();
                }
            }

            private void checkBtnEdit() {
                Intent intent = new Intent(mContext, FormPengajuanActivity.class);
                intent.putExtra("form_type", pengajuanBaruList.get(position).getFormType());
                intent.putExtra("pengajuan_baru_id", pengajuanBaruList.get(position).getId());
                intent.putExtra("application_id", pengajuanBaruList.get(position).getApplicationId());
                intent.putExtra("form", "Draft");
                mContext.startActivity(intent);
            }

            private void checkBtnDraft() {
                Intent intent = new Intent(mContext, FormPengajuanActivity.class);
                intent.putExtra("form_type", pengajuanBaruList.get(position).getFormType());
                intent.putExtra("pengajuan_baru_id", pengajuanBaruList.get(position).getId());
                intent.putExtra("application_id", pengajuanBaruList.get(position).getApplicationId());
                intent.putExtra("tipe_form_pengajuan", "Draft");
                mContext.startActivity(intent);
            }
        });

        holder.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(mContext)) {
                    fragment.saveSyncCoordidate(holder, pengajuanBaruList.get(position).getId());
                    if (pengajuanBaruList.get(position).getStatusSync() == 1) {
                        fragment.submitAttachment(holder, pengajuanBaruList.get(position).getId());
                    } else if (pengajuanBaruList.get(position).getStatusSync() == 2) {
                    } else {
                        fragment.submitPengajuan(holder);
                    }
                }
            }
        });

        if (pengajuanBaruList.get(position).getStatusSync() == 1) {
            holder.imgStatusSyncData.setImageResource(R.drawable.ic_success);
            holder.imgStatusSyncImage.setImageResource(R.drawable.ic_failed);
        } else if (pengajuanBaruList.get(position).getStatusSync() == 2) {
            holder.imgStatusSyncData.setImageResource(R.drawable.ic_success);
            holder.imgStatusSyncImage.setImageResource(R.drawable.ic_success);
            holder.btnSync.setVisibility(View.GONE);
        } else {
            holder.imgStatusSyncData.setImageResource(R.drawable.ic_failed);
            holder.imgStatusSyncImage.setImageResource(R.drawable.ic_failed);
        }
    }


    @Override
    public int getItemCount() {
        return pengajuanBaruList.size();
    }

    public void setPengajuanBaruList(List<PengajuanBaru> pengajuanBaruList) {
        this.pengajuanBaruList = pengajuanBaruList;
        notifyDataSetChanged();
    }

    public class PengajuanUnsyncVH extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_address)
        TextView txtAddress;
        @BindView(R.id.txt_hp)
        TextView txtHp;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.btn_sync)
        Button btnSync;
        @BindView(R.id.btn_edit)
        Button btnEdit;
        @BindView(R.id.ln_status_sync)
        LinearLayout lnStatusSync;
        @BindView(R.id.rl_draft)
        RelativeLayout rlDraft;
        @BindView(R.id.img_status_sync_data)
        ImageView imgStatusSyncData;
        @BindView(R.id.img_status_sync_image)
        ImageView imgStatusSyncImage;
        @BindView(R.id.img_overflow)
        ImageView imgOverflow;
        @BindView(R.id.va_data_text)
        ViewAnimator vaDataText;
        @BindView(R.id.va_data_image)
        ViewAnimator vaDataImage;

        public PengajuanUnsyncVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ViewAnimator getVaDataText() {
            return vaDataText;
        }

        public ViewAnimator getVaDataImage() {
            return vaDataImage;
        }
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_delete:
                    try {
                        List<Attachment> imageAttachmentList = databaseService.getAttachmentDao().queryBuilder()
                                .where().eq("pengajuan_id", pengajuanBaruList.get(position)).query();

                        for (int i = 0; i < imageAttachmentList.size(); i++) {
                            FileUtils.deleteQuietly(new File(imageAttachmentList.get(i).getPath()));
                        }

                        DeleteBuilder<Attachment, Integer> deleteAttachmentBuilder = databaseService.getAttachmentDao().deleteBuilder();
                        deleteAttachmentBuilder.where().eq("pengajuan_id", pengajuanBaruList.get(position));
                        deleteAttachmentBuilder.delete();

                        if ("E".equalsIgnoreCase(pengajuanBaruList.get(position).getPengajuanType())) {
                            DeleteBuilder<AssetElektronik, String> deleteAssetBuilder = databaseService.getAssetDao().deleteBuilder();
                            deleteAssetBuilder.where().eq("pengajuan_id", pengajuanBaruList.get(position));
                            deleteAssetBuilder.delete();
                        } else {
                            DeleteBuilder<AssetKendaraan, String> deleteAssetKendaraanBuilder = databaseService.getAssetKendaraanDao().deleteBuilder();
                            deleteAssetKendaraanBuilder.where().eq("pengajuan_id", pengajuanBaruList.get(position));
                            deleteAssetKendaraanBuilder.delete();
                        }

                        DeleteBuilder<MaskingRate, String> rateStringDeleteBuilder = databaseService.getMaskingRateDao().deleteBuilder();
                        rateStringDeleteBuilder.where().eq("pengajuan_id", pengajuanBaruList.get(position));
                        rateStringDeleteBuilder.delete();

                        DeleteBuilder<MaskingTenor, String> tenorStringDeleteBuilder = databaseService.getMaskingTenorDao().deleteBuilder();
                        tenorStringDeleteBuilder.where().eq("pengajuan_id", pengajuanBaruList.get(position));
                        tenorStringDeleteBuilder.delete();

                        databaseService.getPengajuanBaruDao().delete(pengajuanBaruList.get(position));
                    } catch (java.sql.SQLException e) {
                        if (BuildConfig.DEBUG)
                            Log.e("Delete draft", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                    pengajuanBaruList.remove(position);
                    notifyItemRemoved(position);
                    EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());// resfresh list unsync draft agar positionnya ter-refresh
                    return true;
            }
            return false;
        }
    }
}
