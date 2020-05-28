package com.kreditplus.eform.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.PengajuanDetailActivity;
import com.kreditplus.eform.activity.QrScanAvtivity;
import com.kreditplus.eform.activity.RetakePhotoActivity;
import com.kreditplus.eform.fragment.ListPengajuanFragment;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.objecthelper.Pengajuan;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Iwan Nurdesa on 13/11/15.
 */
public class PengajuanAdapter extends RecyclerView.Adapter<PengajuanAdapter.PengajuanVH> {

    private Context context;
    private List<Pengajuan> pengajuanList;
    private int position;
    private ListPengajuanFragment fragment;
    private boolean isEnableScan = true;
    private View view;

    public PengajuanAdapter(Context context, List<Pengajuan> pengajuanList, ListPengajuanFragment fragment) {
        this.context = context;
        this.pengajuanList = pengajuanList;
        this.fragment = fragment;
    }

    @Override
    public PengajuanVH onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_pengajuan, parent, false);
        return new PengajuanVH(view);
    }

    @Override
    public void onBindViewHolder(PengajuanVH holder, final int position) {
        if ("true".equalsIgnoreCase(pengajuanList.get(position).getIsEdit())) {
            String edit;
            if(pengajuanList.get(position).isCompleted()){
                edit = pengajuanList.get(position).getFullName() + " - Edited";
            }else{
                edit = pengajuanList.get(position).getFullName() + " - Uncompleted";
            }
//            String edit = pengajuanList.get(position).getFullName() + " - Edited";
            holder.txtNamaPemohon.setText(edit);

        } else {
            holder.txtNamaPemohon.setText(pengajuanList.get(position).getFullName());
        }
        holder.txtAlamat.setText(pengajuanList.get(position).getAddress());
        holder.txtTelepon.setText(pengajuanList.get(position).getPhone());
//            ((PengajuanVH) holder).txtTujuan.setText(pengajuanList.get(position).getFinancingPurpose());
        holder.txtTanggal.setText(pengajuanList.get(position).getSubmitedDate());

        double nominal = Double.parseDouble(pengajuanList.get(position).getTotalPinjaman());
        int totalPinjaman = (int) nominal;
        holder.txtTotal.setText(Util.formatNominal(totalPinjaman));
        holder.txtStatus.setText(pengajuanList.get(position).getStatus());

        if ("on surveyed".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_survey_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("New".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
                holder.imgStatus.setImageResource(R.drawable.ic_submit_pengajuan);
                holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pengajuanList.get(position).isCompleted()){
                            Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                            intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                            context.startActivity(intentPengajuan);
                        }else{
                            Intent intentPengajuan = new Intent(context, RetakePhotoActivity.class);
                            intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                            intentPengajuan.putExtra("is_uncompleted", true);
                            context.startActivity(intentPengajuan);
                        }

                    }
                });

        }
        if ("On Process".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_process_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("finished".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_finish_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Cancel".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_cancel_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Canceled".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_cancel_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Purchase Order".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_finish_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    intentPengajuan.putExtra("app_status", pengajuanList.get(position).getStatus());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Reject".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_cancel_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Rejected".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_cancel_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Delivered".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.img_delivered);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Invoice".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.img_invoice);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Funding Process".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.img_funding_process);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Transferred".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.img_transferred);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Principal Approval".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_finish_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Principle Approval".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_finish_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }
        if ("Approved".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_finish_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }

        if ("RCA".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
            holder.imgStatus.setImageResource(R.drawable.ic_process_pengajuan);
            holder.tlPengajuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPengajuan = new Intent(context, PengajuanDetailActivity.class);
                    intentPengajuan.putExtra("app_id", pengajuanList.get(position).getId());
                    context.startActivity(intentPengajuan);
                }
            });
        }

        holder.imgOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PengajuanAdapter.this.position = position;
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_list_pengajuan, popup.getMenu());

                // cek apakah dia approve, kalau approve munculkan item print yang ke dua
                if ("Purchase Order".equalsIgnoreCase(pengajuanList.get(position).getStatus())) {
                    popup.getMenu().findItem(R.id.menu_download_dua).setVisible(true);
                    if (isEnableScan) popup.getMenu().findItem(R.id.menu_qr_code).setVisible(false);
                }

                popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    class PengajuanVH extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView txtNamaPemohon;
        @BindView(R.id.txt_address)
        TextView txtAlamat;
        @BindView(R.id.txt_hp)
        TextView txtTelepon;
        //        @BindView(R.id.txt_tujuan)
//        TextView txtTujuan;
        @BindView(R.id.txt_date)
        TextView txtTanggal;
        @BindView(R.id.txt_total_pengajuan)
        TextView txtTotal;
        @BindView(R.id.img_status)
        ImageView imgStatus;
        @BindView(R.id.txt_status)
        TextView txtStatus;
        @BindView(R.id.img_overflow)
        ImageView imgOverflow;

        @BindView(R.id.ln_main)
        LinearLayout tlPengajuan;

        public PengajuanVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.menu_download) {
                fragment.getPdfurl(pengajuanList.get(position).getId(), pengajuanList.get(position).getPdfName(), 0);
            } else if (menuItem.getItemId() == R.id.menu_download_dua) {
                fragment.getPdfurl(pengajuanList.get(position).getId(), pengajuanList.get(position).getPdfName(), 1);
            } else if (menuItem.getItemId() == R.id.menu_qr_code) {
                Intent i = new Intent(context, QrScanAvtivity.class);
                i.putExtra("appId", pengajuanList.get(position).getId());
                context.startActivity(i);
            }
            return false;
        }
    }
}
