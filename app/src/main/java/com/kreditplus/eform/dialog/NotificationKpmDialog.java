package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.DetailKpmActivity;
import com.kreditplus.eform.model.bus.FinishTransparent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 13/05/17.
 */

public class NotificationKpmDialog extends BaseDialog {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_notifikasi_kpm,container,false);
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

    @OnClick(R.id.btn_lihat_application)
    public void lihaApplication(){
        dismiss();
        Bundle bundle = getArguments();
        String appId = bundle.getString("application_id");
        Intent intent = new Intent(getActivity(), DetailKpmActivity.class);
        intent.putExtra("app_id",appId);
        intent.putExtra("status","waiting on surveyed");
        getActivity().startActivity(intent);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().post(new FinishTransparent());
    }
}
