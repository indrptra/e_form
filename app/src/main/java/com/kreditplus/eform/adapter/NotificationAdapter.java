package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.ChangePasswordActivity;
import com.kreditplus.eform.activity.FormPengajuanActivity;
import com.kreditplus.eform.activity.FormPengajuanNonElcActivity;
import com.kreditplus.eform.activity.HomeActivity;
import com.kreditplus.eform.activity.PengajuanDetailActivity;
import com.kreditplus.eform.activity.RetakePhotoActivity;
import com.kreditplus.eform.model.response.objecthelper.Notifikasi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iwan Nurdesa on 13/11/15.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotifikasiVH> {

    private Context mContext;
    private List<Notifikasi> mNotifikasiList;
    private String tipeCro;

    public NotificationAdapter(Context context, List<Notifikasi> notifikasiList, String tipeCro) {
        mContext = context;
        mNotifikasiList = notifikasiList;
        this.tipeCro = tipeCro;
    }

    @Override
    public NotifikasiVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_notifikasi, parent, false);
        return new NotifikasiVH(view);
    }

    @Override
    public void onBindViewHolder(NotifikasiVH holder, final int position) {
        holder.txtTitle.setText(mNotifikasiList.get(position).getTitle());
        holder.txtText.setText(mNotifikasiList.get(position).getText());
        holder.txtTime.setText(mNotifikasiList.get(position).getTime());
        holder.lnPengajuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mNotifikasiList.get(position).getType()) {
                    case "application":
                        Intent intenApplication = new Intent(mContext, HomeActivity.class);
                        intenApplication.putStringArrayListExtra("app_id_list",
                                (ArrayList<String>) mNotifikasiList.get(position).getAppIdList());
                        mContext.startActivity(intenApplication);
                        break;
                    case "change_password":
                        Intent intenChangePassword = new Intent(mContext, ChangePasswordActivity.class);
                        mContext.startActivity(intenChangePassword);
                        break;
                    case "retake":
                        Intent intentRetake = new Intent(mContext, RetakePhotoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentRetake.putExtras(bundle);
                        intentRetake.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intentRetake);
                        break;
                    case "assign_manual":
                        Intent intenAssignManual = null;
                        if (tipeCro.equalsIgnoreCase("cro")) {
                            intenAssignManual = new Intent(mContext, FormPengajuanActivity.class);
                        } else if (tipeCro.equalsIgnoreCase("cmo")) {
                            intenAssignManual = new Intent(mContext, FormPengajuanNonElcActivity.class);
                        } else if (tipeCro.equalsIgnoreCase("cmo-mobil")) {
                            intenAssignManual = new Intent(mContext, FormPengajuanNonElcActivity.class);
                        }
                        intenAssignManual.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intenAssignManual.putExtra("isAssignEdit", "isAssignEdit");
                        intenAssignManual.putExtra("form", "Draft");
                        mContext.startActivity(intenAssignManual);
                        break;
                    case "assign_update":
                        Intent intenEdit = null;
                        if (tipeCro.equalsIgnoreCase("cro")) {
                            intenEdit = new Intent(mContext, FormPengajuanActivity.class);
                        } else if (tipeCro.equalsIgnoreCase("cmo")) {
                            intenEdit = new Intent(mContext, FormPengajuanNonElcActivity.class);
                        } else if (tipeCro.equalsIgnoreCase("cmo-mobil")) {
                            intenEdit = new Intent(mContext, FormPengajuanNonElcActivity.class);
                        }
                        intenEdit.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intenEdit.putExtra("isAssignEdit", "isAssignEdit");
                        intenEdit.putExtra("form", "Edit");
                        mContext.startActivity(intenEdit);
                        break;
                    case "po":
                        Intent intentPO = new Intent(mContext, PengajuanDetailActivity.class);
                        intentPO.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentPO.putExtra("email", "true");
                        mContext.startActivity(intentPO);
                        break;
                    case "cancel":
                        Intent intentCancel = new Intent(mContext, PengajuanDetailActivity.class);
                        intentCancel.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentCancel.putExtra("email", "true");
                        mContext.startActivity(intentCancel);
                        break;
                    case "canceled":
                        Intent intentCanceled = new Intent(mContext, PengajuanDetailActivity.class);
                        intentCanceled.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentCanceled.putExtra("email", "true");
                        mContext.startActivity(intentCanceled);
                        break;
                    case "reject":
                        Intent intentReject = new Intent(mContext, PengajuanDetailActivity.class);
                        intentReject.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentReject.putExtra("email", "true");
                        mContext.startActivity(intentReject);
                        break;
                    case "rejected":
                        Intent intentRejected = new Intent(mContext, PengajuanDetailActivity.class);
                        intentRejected.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentRejected.putExtra("email", "true");
                        mContext.startActivity(intentRejected);
                        break;
                    case "principal_approval":
                        Intent intentPrincipalApproval = new Intent(mContext, PengajuanDetailActivity.class);
                        intentPrincipalApproval.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentPrincipalApproval.putExtra("email", "true");
                        mContext.startActivity(intentPrincipalApproval);
                        break;
                    case "principle approval":
                        Intent intentPrincipleApproval = new Intent(mContext, PengajuanDetailActivity.class);
                        intentPrincipleApproval.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentPrincipleApproval.putExtra("email", "true");
                        mContext.startActivity(intentPrincipleApproval);
                        break;
                    case "approved":
                        Intent intentApproved = new Intent(mContext, PengajuanDetailActivity.class);
                        intentApproved.putExtra("app_id", mNotifikasiList.get(position).getAppIdList().get(0));
                        intentApproved.putExtra("email", "true");
                        mContext.startActivity(intentApproved);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotifikasiList.size();
    }

    public void setNotifikasiList(List<Notifikasi> notifikasiList) {
        mNotifikasiList = notifikasiList;
        notifyDataSetChanged();
    }

    public class NotifikasiVH extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.txt_text)
        TextView txtText;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.tl_pengajuan)
        LinearLayout lnPengajuan;

        public NotifikasiVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
