package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.DetailKpmActivity;
import com.kreditplus.eform.fragment.ListJanjiBertemuFragment;
import com.kreditplus.eform.model.bus.FinishTransparent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 13/05/17.
 */

public class NotificationJanjiBertemuDialog extends BaseDialog {

    @BindView(R.id.txt_desc)
    TextView txtDesc;

    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_notifikasi_janji_bertemu,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        String nama = R.string.txt_notifikasi_janji_bertemu_2+" "+bundle.getString("nama");
        txtDesc.setText(nama);
    }

    @OnClick(R.id.btn_lihat_application)
    public void LihatJadwal(){
        dismiss();
        String appId = bundle.getString("application_id");
        Intent intent = new Intent(getActivity(), DetailKpmActivity.class);
        intent.putExtra("app_id",appId);
        intent.putExtra("status","on surveyed");
        getActivity().startActivity(intent);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().post(new FinishTransparent());
    }
}
