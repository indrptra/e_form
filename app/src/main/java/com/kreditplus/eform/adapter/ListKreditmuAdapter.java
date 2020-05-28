package com.kreditplus.eform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.response.objecthelper.KreditmuList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apc-lap012 on 23/08/17.
 */

public class ListKreditmuAdapter extends RecyclerView.Adapter<ListKreditmuAdapter.DataKreditmuList> {

    private final Context context;
    private List<KreditmuList> kreditmuList;


    public ListKreditmuAdapter(Context context, List<KreditmuList> kreditmulist) {
        this.kreditmuList =kreditmulist;
        this.context = context;
    }

    @Override
    public DataKreditmuList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_kreditmu,parent,false);
        return new DataKreditmuList(view);
    }

    @Override
    public void onBindViewHolder(DataKreditmuList holder, int position) {
        holder.txtTanggal.setText(kreditmuList.get(position).getTanggal());
        holder.txtNama.setText(kreditmuList.get(position).getNama());
        holder.txtOrderId.setText(kreditmuList.get(position).getOrderId());
    }

    @Override
    public int getItemCount() {
        return kreditmuList.size();
    }

    public void setItemList(List<KreditmuList> kreditmuList){
        this.kreditmuList = kreditmuList;
        notifyDataSetChanged();
    }

    class DataKreditmuList extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_tanggal_list_kreditmu)
        TextView txtTanggal;
        @BindView(R.id.txt_nama_list_kreditmu)
        TextView txtNama;
        @BindView(R.id.txt_order_id_kreditmu)
        TextView txtOrderId;

        DataKreditmuList(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
