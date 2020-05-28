package com.kreditplus.eform.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.ViewKreditmuActivity;
import com.kreditplus.eform.model.response.objecthelper.KreditmuObjt;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by apc-lap012 on 24/07/17.
 */

public class ViewKreditmuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Map<Integer, File> mapAttachment;
    private final Context context;
    private List<KreditmuObjt> kreditmuObjtList;
    private Map<String, String> map;
    private Map<String, String> mapSpinner;

    private static final int HEADER = 0;
    private static final int TEXT_VIEW = 1;
    private static final int ATTACHMENT = 2;
    private int checkbox = 3;
    private int disable = 4;



    public ViewKreditmuAdapter(List<KreditmuObjt> kreditmuObjtList, Map<String, String> mapForSubmit, Map<String, String> mapSpinner, Map<Integer, File> mapAttachment, ViewKreditmuActivity context) {
        this.context = context;
        this.kreditmuObjtList = kreditmuObjtList;
        this.map = mapForSubmit;
        this.mapSpinner = mapSpinner;
        this.mapAttachment = mapAttachment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType){
            case HEADER:
                view = inflater.inflate(R.layout.item_view_kreditmu_header,parent,false);
                return new ViewHeader(view);
            case TEXT_VIEW:
                view = inflater.inflate(R.layout.item_view_kreditmu_text,parent,false);
                return new ViewText(view);
            case ATTACHMENT:
                view = inflater.inflate(R.layout.item_kreditmu_photo,parent,false);
                return new ViewPicture(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case HEADER:
                ViewHeader  viewHeader = (ViewHeader) holder;
                viewHeader.txtViewKreditmuHeader.setText(kreditmuObjtList.get(holder.getAdapterPosition()).getName());
                if ("Data Pribadi".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())){
                    viewHeader.imgViewKreditmuHeader.setImageResource(R.drawable.ic_data_pribadi);
                }else if ("Alamat Pemohon".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())){
                    viewHeader.imgViewKreditmuHeader.setImageResource(R.drawable.ic_alamat);
                }else if ("Data Pekerjaan".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())){
                    viewHeader.imgViewKreditmuHeader.setImageResource(R.drawable.ic_pekerjaan);
                }else {
                    viewHeader.imgViewKreditmuHeader.setImageResource(R.drawable.ic_attachment);
                }
                break;
            case TEXT_VIEW:
                ViewText viewText = (ViewText) holder;
                setData(viewText, holder);
                break;
            case ATTACHMENT:
//                ViewPicture viewPicture  = (ViewPicture) holder;
                break;
        }
    }

    private void setData(ViewText viewText, RecyclerView.ViewHolder holder) {

        viewText.tvViewKreditmuName.setText(kreditmuObjtList.get(holder.getAdapterPosition()).getName());

        if ("Nomer telfon rumah".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())) {
            String phone = map.get("area") + map.get("phone");
            viewText.tvViewKreditmuValue.setText(phone);
        }else if ("Pendidikan".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())) {
            viewText.tvViewKreditmuValue.setText(mapSpinner.get("Pendidikan"));
        }else if ("Status pernikahan".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())) {
            viewText.tvViewKreditmuValue.setText(mapSpinner.get("Status pernikahan"));
        }else if ("Status kepemilikan rumah".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())){
            viewText.tvViewKreditmuValue.setText(mapSpinner.get("Status kepemilikan rumah"));
        }else if ("Kelurahan ktp".equalsIgnoreCase(kreditmuObjtList.get(holder.getAdapterPosition()).getName())) {
            String name = map.get(kreditmuObjtList.get(holder.getAdapterPosition()).getName());
            viewText.tvViewKreditmuValue.setText(name);
        }else {
            viewText.tvViewKreditmuValue.setText(map.get(kreditmuObjtList.get(holder.getAdapterPosition()).getName()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if ("header".equalsIgnoreCase(kreditmuObjtList.get(position).getType()) && !"Signature".equalsIgnoreCase(kreditmuObjtList.get(position).getName())) {
            return HEADER;
        }else if ("attachment".equalsIgnoreCase(kreditmuObjtList.get(position).getType())){
            return ATTACHMENT;
        }else {
            return TEXT_VIEW;
        }

    }

    @Override
    public int getItemCount() {
        return this.kreditmuObjtList.size();
    }

    class ViewHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_view_kreditmu_header)
        TextView txtViewKreditmuHeader;
        @BindView(R.id.img_view_kreditmu_header)
        ImageView imgViewKreditmuHeader;

        public ViewHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ViewText extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_view_kreditmu_name)
        TextView tvViewKreditmuName;
        @BindView(R.id.tv_view_kreditmu_value)
        TextView tvViewKreditmuValue;


        public ViewText(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewPicture extends RecyclerView.ViewHolder {

        @BindView(R.id.img_take_picture_kreditmu_1)
        ImageView imgTake1;
        @BindView(R.id.img_take_picture_kreditmu_2)
        ImageView imgTake2;
        @BindView(R.id.img_take_picture_kreditmu_3)
        ImageView imgTake3;
        @BindView(R.id.img_take_picture_kreditmu_4)
        ImageView imgTake4;
        @BindViews({R.id.img_take_picture_kreditmu_1,R.id.img_take_picture_kreditmu_2,R.id.img_take_picture_kreditmu_3,
                R.id.img_take_picture_kreditmu_4})
        List<View> imgList;

        public ViewPicture(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            for (int i = 0 ; i < mapAttachment.size();i++) {
                Glide.with(context).load(mapAttachment.get(i)).centerCrop().into((ImageView) imgList.get(i));
            }

        }
    }


}
