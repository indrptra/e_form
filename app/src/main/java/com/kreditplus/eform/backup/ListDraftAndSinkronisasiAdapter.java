package com.kreditplus.eform.backup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.crashlytics.android.Crashlytics;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.kreditplus.eform.App;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.AssetElektronik;
import com.kreditplus.eform.model.Attachment;
import com.kreditplus.eform.model.MaskingRate;
import com.kreditplus.eform.model.MaskingTenor;
import com.kreditplus.eform.model.MasterFormPengajuan;
import com.kreditplus.eform.model.TblAgunan;
import com.kreditplus.eform.model.TblAlamat;
import com.kreditplus.eform.model.TblAsuransi;
import com.kreditplus.eform.model.TblCaraPembayaran;
import com.kreditplus.eform.model.TblDataKartuKredit;
import com.kreditplus.eform.model.TblDataPasangan;
import com.kreditplus.eform.model.TblDataPekerjaan;
import com.kreditplus.eform.model.TblDataPerhitungan;
import com.kreditplus.eform.model.TblDataPerhitunganKendaraan;
import com.kreditplus.eform.model.TblDataPribadi;
import com.kreditplus.eform.model.TblDetailProduct;
import com.kreditplus.eform.model.TblKartuMembership;
import com.kreditplus.eform.model.TblKeterangan;
import com.kreditplus.eform.model.TblKontakDarurat;
import com.kreditplus.eform.model.TblKop;
import com.kreditplus.eform.model.TblLokasi;
import com.kreditplus.eform.model.TblRekomendasi;
import com.kreditplus.eform.model.TblTandaTangan;
import com.kreditplus.eform.model.bus.RefreshPengajuanUnsyncEvent;
import com.kreditplus.eform.service.DatabaseService;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDraftAndSinkronisasiAdapter extends RecyclerView.Adapter<ListDraftAndSinkronisasiAdapter.PengajuanUnsyncVH> {

    @Inject
    DatabaseService databaseService;

    private final Context mContext;
    private final ListDraftAndSinkronisasiFragment fragment;
    private List<MasterFormPengajuan> mMasterFormPengajuanList;
    private final MasterFormPengajuan mMasterFormPengajuan;

    public ListDraftAndSinkronisasiAdapter(Context mContext, MasterFormPengajuan mMasterFormPengajuan, List<MasterFormPengajuan> mMasterFormPengajuanList, ListDraftAndSinkronisasiFragment fragment) {
        App.appComponent().inject(this);
        this.mContext = mContext;
        this.mMasterFormPengajuan = mMasterFormPengajuan;
        this.mMasterFormPengajuanList = mMasterFormPengajuanList;
        this.fragment = fragment;
    }

    @Override
    public PengajuanUnsyncVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_pengajuan_unsync, parent, false);
        return new PengajuanUnsyncVH(view);
    }

    @Override
    public void onBindViewHolder(final PengajuanUnsyncVH holder, final int position) {
        for (int a = 0; a < mMasterFormPengajuanList.size(); a++) {
            List<TblDataPribadi> tblDataPribadis = new ArrayList<>();
            List<TblAlamat> tblAlamats = new ArrayList<>();

            try {
                tblDataPribadis = databaseService.getTblDataPribadiDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuanList.get(position)).query();
            } catch (SQLException eTblDataPribadi) {
                if (BuildConfig.DEBUG) Log.e("TblDataPribadi", String.valueOf(eTblDataPribadi));
                Crashlytics.logException(eTblDataPribadi);
            }
            try {
                tblAlamats = databaseService.getTblAlamatDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuanList.get(position)).query();
            } catch (SQLException eTblAlamat) {
                if (BuildConfig.DEBUG) Log.e("TblAlamat", String.valueOf(eTblAlamat));
                Crashlytics.logException(eTblAlamat);
            }

            for (int i = 0; i < tblDataPribadis.size(); i++) {
                if (mMasterFormPengajuanList.get(position).getIdKpm() != null && !mMasterFormPengajuanList.isEmpty() && !"00".equalsIgnoreCase(mMasterFormPengajuanList.get(position).getIdKpm())) {
                    String kpm = tblDataPribadis.get(i).getNamaLengkap();
                    holder.txtName.setText(kpm);
                } else {
                    holder.txtName.setText(tblDataPribadis.get(i).getNamaLengkap());
                }
                holder.txtHp.setText(tblDataPribadis.get(i).getNomorHandphone());
            }

            for (int i = 0; i < tblAlamats.size(); i++) {
                holder.txtAddress.setText(tblAlamats.get(i).getAlamatTinggal());
            }
        }
        holder.txtTime.setText("Date created: " + mMasterFormPengajuanList.get(position).getCreatedAt());

        if ("draft_baru".equalsIgnoreCase(mMasterFormPengajuanList.get(position).getTipeSaveData()) || "draft_edit".equalsIgnoreCase(mMasterFormPengajuanList.get(position).getTipeSaveData())) {
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
                popup.setOnMenuItemClickListener(new ListDraftAndSinkronisasiAdapter.MyMenuItemClickListener(position));
                popup.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(mContext)) {
                    Intent intent;
                    intent = new Intent(mContext, FormPengajuanActivity.class);
                    intent.putExtra("form_type", mMasterFormPengajuanList.get(position).getTipeSaveData());
                    intent.putExtra("pengajuan_baru_id", mMasterFormPengajuanList.get(position).getId());
                    intent.putExtra("application_id", mMasterFormPengajuanList.get(position).getApplicationId());
                    intent.putExtra("form", "Draft");
                    mContext.startActivity(intent);
                }
            }
        });

        holder.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.checkActiveLocation(mContext)) {
                    fragment.saveSyncCoordidate(holder, mMasterFormPengajuanList.get(position).getId());
                }
            }
        });

        if (mMasterFormPengajuanList.get(position).getStatusSync() == 1) {
            holder.imgStatusSyncData.setImageResource(R.drawable.ic_success);
            holder.imgStatusSyncImage.setImageResource(R.drawable.ic_failed);
        } else if (mMasterFormPengajuanList.get(position).getStatusSync() == 2) {
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
        return mMasterFormPengajuanList.size();
    }

    public void setMasterFormPengajuanList(List<MasterFormPengajuan> mMasterFormPengajuanList) {
        this.mMasterFormPengajuanList = mMasterFormPengajuanList;
        notifyDataSetChanged();
    }

    public class PengajuanUnsyncVH extends RecyclerView.ViewHolder {
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

        public PengajuanUnsyncVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
                        List<Attachment> imageAttachmentList = databaseService.getAttachmentDao().queryBuilder().where().eq("pengajuan_id", mMasterFormPengajuanList.get(position)).query();

                        for (int i = 0; i < imageAttachmentList.size(); i++)
                            FileUtils.deleteQuietly(new File(imageAttachmentList.get(i).getPath()));

                        DeleteBuilder<Attachment, Integer> deleteAttachmentBuilder = databaseService.getAttachmentDao().deleteBuilder();
                        deleteAttachmentBuilder.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteAttachmentBuilder.delete();

                        DeleteBuilder<TblTandaTangan, String> deleteTblTandaTangan = databaseService.getTblTandaTanganDao().deleteBuilder();
                        deleteTblTandaTangan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblTandaTangan.delete();

                        DeleteBuilder<TblLokasi, String> deleteTblLokasi = databaseService.getTblLokasiDao().deleteBuilder();
                        deleteTblLokasi.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblLokasi.delete();

                        DeleteBuilder<TblRekomendasi, String> deleteTblRekomendasi = databaseService.getTblRekomendasiDao().deleteBuilder();
                        deleteTblRekomendasi.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblRekomendasi.delete();

                        DeleteBuilder<TblKeterangan, String> deleteTblKeterangan = databaseService.getTblKeteranganDao().deleteBuilder();
                        deleteTblKeterangan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblKeterangan.delete();

                        DeleteBuilder<TblDataPerhitungan, String> deleteTblDataPerhitungan = databaseService.getTblDataPerhitunganDao().deleteBuilder();
                        deleteTblDataPerhitungan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataPerhitungan.delete();

                        DeleteBuilder<TblAsuransi, String> deleteTblAsuransi = databaseService.getTblAsuransiDao().deleteBuilder();
                        deleteTblAsuransi.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblAsuransi.delete();

                        DeleteBuilder<TblDetailProduct, String> deleteTblDetailProduct = databaseService.getTblDetailProductDao().deleteBuilder();
                        deleteTblDetailProduct.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDetailProduct.delete();

                        DeleteBuilder<TblKartuMembership, String> deleteTblKartuMembership = databaseService.getTblKartuMembershipDao().deleteBuilder();
                        deleteTblKartuMembership.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblKartuMembership.delete();

                        DeleteBuilder<TblDataKartuKredit, String> deleteTblDataKartuKredit = databaseService.getTblDataKartuKreditDao().deleteBuilder();
                        deleteTblDataKartuKredit.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataKartuKredit.delete();

                        DeleteBuilder<TblDataPekerjaan, String> deleteTblDataPekerjaan = databaseService.getTblDataPekerjaanDao().deleteBuilder();
                        deleteTblDataPekerjaan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataPekerjaan.delete();

                        DeleteBuilder<TblKontakDarurat, String> deleteTblKontakDarurat = databaseService.getTblKontakDaruratDao().deleteBuilder();
                        deleteTblKontakDarurat.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblKontakDarurat.delete();

                        DeleteBuilder<TblAlamat, String> deleteTblAlamat = databaseService.getTblAlamatDao().deleteBuilder();
                        deleteTblAlamat.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblAlamat.delete();

                        DeleteBuilder<TblDataPasangan, String> deleteTblDataPasangan = databaseService.getTblDataPasanganDao().deleteBuilder();
                        deleteTblDataPasangan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataPasangan.delete();

                        DeleteBuilder<TblDataPribadi, String> deleteTblDataPribadi = databaseService.getTblDataPribadiDao().deleteBuilder();
                        deleteTblDataPribadi.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataPribadi.delete();

                        DeleteBuilder<TblKop, String> deleteTblKop = databaseService.getTblKopDao().deleteBuilder();
                        deleteTblKop.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblKop.delete();

                        DeleteBuilder<TblAgunan, String> deleteTblAgunan = databaseService.getTblAgunanDao().deleteBuilder();
                        deleteTblAgunan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblAgunan.delete();

                        DeleteBuilder<TblDataPerhitunganKendaraan, String> deleteTblDataPerhitunganKendaraan = databaseService.getTblDataPerhitunganKendaraanDao().deleteBuilder();
                        deleteTblDataPerhitunganKendaraan.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblDataPerhitunganKendaraan.delete();

                        DeleteBuilder<TblCaraPembayaran, String> deleteTblCaraPembayaran = databaseService.getTblCaraPembayaranDao().deleteBuilder();
                        deleteTblCaraPembayaran.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteTblCaraPembayaran.delete();

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
                        DeleteBuilder<AssetElektronik, String> deleteAssetBuilder = databaseService.getAssetDao().deleteBuilder();
                        deleteAssetBuilder.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        deleteAssetBuilder.delete();

                        DeleteBuilder<MaskingRate, String> rateStringDeleteBuilder = databaseService.getMaskingRateDao().deleteBuilder();
                        rateStringDeleteBuilder.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        rateStringDeleteBuilder.delete();

                        DeleteBuilder<MaskingTenor, String> tenorStringDeleteBuilder = databaseService.getMaskingTenorDao().deleteBuilder();
                        tenorStringDeleteBuilder.where().eq("pengajuan_id", mMasterFormPengajuanList.get(position));
                        tenorStringDeleteBuilder.delete();

                        databaseService.getMasterFormPengajuanDao().delete(mMasterFormPengajuanList.get(position));
                    } catch (java.sql.SQLException e) {
                        if (BuildConfig.DEBUG) Log.e("Delete draft", String.valueOf(e));
                        Crashlytics.logException(e);
                    }
                    mMasterFormPengajuanList.remove(position);
                    notifyItemRemoved(position);
                    EventBus.getDefault().post(new RefreshPengajuanUnsyncEvent());// resfresh list unsync draft agar positionnya ter-refresh
                    return true;
            }
            return false;
        }
    }
}
