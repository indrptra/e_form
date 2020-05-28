package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.StatusSyncKendaraan;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditKOPDialog extends BaseDialog {

    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.btnLeft)
    Button btnLeft;
    @BindView(R.id.btnRight)
    Button btnRight;
    private String strstatus;
    private String strtvTitle;
    private String strtvDescription;
    private String strbtnLeft;
    private String strbtnRight;
    private String strform;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_yes_no, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        strstatus = getArguments().getString("status");
        strtvTitle = getArguments().getString("tvTitle");
        strtvDescription = getArguments().getString("tvDescription");
        strbtnLeft = getArguments().getString("btnLeft");
        strbtnRight = getArguments().getString("btnRight");
        strform = getArguments().getString("form");

        if (strform.equalsIgnoreCase("Edit")) btnLeft.setVisibility(View.GONE);

        tvTitle.setText(strtvTitle);
        tvDescription.setText(strtvDescription);
        btnLeft.setText(strbtnLeft);
        btnRight.setText(strbtnRight);
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btnClose, R.id.btnLeft, R.id.btnRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                dismiss();
                break;
            case R.id.btnLeft:
                EventBus.getDefault().post(new StatusSyncKendaraan(strstatus, strbtnLeft, strbtnRight));
                dismiss();
                break;
            case R.id.btnRight:
                EventBus.getDefault().post(new StatusSyncKendaraan(strstatus, strbtnLeft, strbtnRight));
                dismiss();
                break;
        }
    }
}
