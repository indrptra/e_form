package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.DetailKpmActivity;
import com.kreditplus.eform.activity.PengajuanDetailActivity;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apc-lap012 on 17/04/17.
 */

public class ListKpmAdapter extends RecyclerView.Adapter<ListKpmAdapter.KpmList> {

    private Context context;
    private List<Pengajuan> pengajuanList;
    private int position;

    public ListKpmAdapter(Context context, List<Pengajuan> pengajuanList) {
        this.context = context;
        this.pengajuanList = pengajuanList;
    }

    @Override
    public KpmList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_kpm, parent, false);
        return new KpmList(view);
    }

    @Override
    public void onBindViewHolder(KpmList holder, final int position) {
        holder.txtNameKpm.setText(pengajuanList.get(position).getFullName());
        holder.txtAddressKpm.setText(pengajuanList.get(position).getAddress());
        holder.txtHpKpm.setText(pengajuanList.get(position).getPhone());
        holder.txtTanggalKpm.setText(pengajuanList.get(position).getReminderDate() == null ? "-" :
                pengajuanList.get(position).getReminderDate());

        if ("on surveyed".equalsIgnoreCase(pengajuanList.get(position).getStatus()) && pengajuanList.get(position).getReminderDate() == null) {
            holder.txtStatusKpm.setText(R.string.status_process_waiting);
            holder.imgStatusKpm.setImageResource(R.drawable.ic_waiting_schedule);
            holder.lnMainItemKpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailKpmActivity.class);
                    intent.putExtra("app_id", pengajuanList.get(position).getId());
                    intent.putExtra("status", pengajuanList.get(position).getStatus());
                    context.startActivity(intent);
                }
            });
        } else if ("waiting on survey".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.txtStatusKpm.setText(R.string.status_unprocess);
            holder.imgStatusKpm.setImageResource(R.drawable.ic_unprocessed);
            holder.lnMainItemKpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailKpmActivity.class);
                    intent.putExtra("app_id", pengajuanList.get(position).getId());
                    intent.putExtra("status", pengajuanList.get(position).getStatus());
                    context.startActivity(intent);
                }
            });
        } else if ("submitted".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.txtStatusKpm.setText(pengajuanList.get(position).getStatus());
            holder.imgStatusKpm.setImageResource(R.drawable.ic_submit_pengajuan);
            holder.lnMainItemKpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        } else if ("on surveyed".equalsIgnoreCase(pengajuanList.get(position).getStatus()) && pengajuanList.get(position).getReminderDate() != null) {
            holder.txtStatusKpm.setText(R.string.status_process);
            holder.imgStatusKpm.setImageResource(R.drawable.ic_submit);
            holder.lnMainItemKpm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class KpmList extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name_kpm)
        TextView txtNameKpm;
        @BindView(R.id.txt_address_kpm)
        TextView txtAddressKpm;
        @BindView(R.id.txt_hp_kpm)
        TextView txtHpKpm;
        @BindView(R.id.img_status_kpm)
        ImageView imgStatusKpm;
        @BindView(R.id.txt_status_kpm)
        TextView txtStatusKpm;
        @BindView(R.id.txt_tanggal_kpm)
        TextView txtTanggalKpm;

        @BindView(R.id.ln_main_item_kpm)
        LinearLayout lnMainItemKpm;

        public KpmList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}


