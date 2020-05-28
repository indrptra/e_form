package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.IgnoreResult;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 16/05/17.
 */

public class IgnorereasonDialog extends BaseDialog {

    @BindView(R.id.rg_ignore)
    RadioGroup rgIgnore;
    @BindView(R.id.rb_area_ignore)
    RadioButton rbAreaIgnore;
    @BindView(R.id.rb_order_ganda_ignore)
    RadioButton rbOrderGandaIgnore;
    @BindView(R.id.rb_tidak_bisa_dihubungi_ignore)
    RadioButton rbTidakBisaDihubungiIgnore;
    @BindView(R.id.rb_lainnya_ignore)
    RadioButton rbLainnyaIgnore;
    @BindView(R.id.tv_error_kosong_ignore)
    TextView tvErrorKosongIgnore;
    @BindView(R.id.edt_lainnya)
    EditText edtLainnya;

    private String reason;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ignore_reason,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onRadioButtonChecked();
    }




    private void onRadioButtonChecked() {
        rgIgnore.clearCheck();
        rgIgnore.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1){
                    if ("Lainnya".equalsIgnoreCase(rb.getText().toString())){
                        edtLainnya.setVisibility(View.VISIBLE);
                        reason = "";
                    }else {
                        reason = rb.getText().toString();
                        edtLainnya.setVisibility(View.GONE);

                    }
                }
            }
        });
    }
    
    @OnClick(R.id.btn_submit_ignore)
    public void sumbitIgnore(){
        if (!"".equalsIgnoreCase(reason) && reason != null) {
            EventBus.getDefault().post(new IgnoreResult(reason));
            dismiss();
        }else if (reason == null){
            tvErrorKosongIgnore.setVisibility(View.VISIBLE);
        }else{
            if (rbLainnyaIgnore.isChecked()){
                cekLainnya();
            }
        }
    }

    private void cekLainnya() {
        if ("".equalsIgnoreCase(edtLainnya.getText().toString())){
            tvErrorKosongIgnore.setVisibility(View.VISIBLE);
        }else{
            reason = edtLainnya.getText().toString();
            EventBus.getDefault().post(new IgnoreResult(reason));
            dismiss();
        }

    }


}
