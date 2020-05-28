package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.DialogSyaratBus;

import org.greenrobot.eventbus.EventBus;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 01/03/17.
 */

public class PersetujuanTidakCancel extends BaseDialog {

    @BindView(R.id.tv_isi_persetujuan)
    TextView tvIsiPersetujuan;
    @BindView(R.id.btn_ok_persetujuan)
    Button btnOkPersetujuan;
    @BindView(R.id.cbx_persetujuan)
    CheckBox cbxPersetujuan;

    private String stringDescriptionSyarat;
    private String descriptionSyarat = "DESCRIPTION_SYARAT";
    private String descriptionCancel = "DESCRIPTION_CANCEL";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_persetujuan_tidak_cancel, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String syaratDescription = bundle.getString(descriptionCancel, "");
        tvIsiPersetujuan.setText(Html.fromHtml(syaratDescription));
        view.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));;

    }


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

    @OnCheckedChanged(R.id.cbx_persetujuan)
    public void checkbox(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            btnOkPersetujuan.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnOkPersetujuan.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
            }
        } else {
            btnOkPersetujuan.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnOkPersetujuan.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.bg_gray));
            }
        }
    }

    @OnClick(R.id.btn_ok_persetujuan)
    public void ok() {
        EventBus.getDefault().post(new DialogSyaratBus());
        dismiss();
    }


}
