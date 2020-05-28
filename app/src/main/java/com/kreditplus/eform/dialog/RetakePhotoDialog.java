package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kreditplus.eform.R;
import com.kreditplus.eform.activity.RetakePhotoActivity;
import com.kreditplus.eform.model.bus.FinishTransparent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iwan Nurdesa on 16/08/16.
 */
public class RetakePhotoDialog extends BaseDialog {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_retake_photo, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @OnClick(R.id.btn_lihat_application)
    public void lihatApplication() {
        dismiss();
        Bundle bundle = getArguments();

        Intent intentPengajuan = new Intent(getActivity(), RetakePhotoActivity.class);
        intentPengajuan.putExtras(bundle);
        getActivity().startActivity(intentPengajuan);

        EventBus.getDefault().post(new FinishTransparent());// finish transparent activity
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().post(new FinishTransparent());
    }
}
