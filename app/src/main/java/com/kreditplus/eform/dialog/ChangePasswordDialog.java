package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.ChangePassword;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 08/09/17.
 */

public class ChangePasswordDialog extends BaseDialog implements Validator.ValidationListener {

    @Password(min = 8)
    @BindView(R.id.edt_password_new)
    EditText edtPasswordNew;
    @ConfirmPassword
    @BindView(R.id.edt_password_again)
    EditText edtPasswordAgain;
    @BindView(R.id.tv_warning_password)
    TextView tvWarningPassword;
    @BindView(R.id.tv_warning_confirm)
    TextView tvWarningConfirm;

    private Validator validator;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        validator = new Validator(this);
        validator.setValidationListener(this);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_change_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.btn_change_password)
    public void updatePassword() {
        tvWarningPassword.setVisibility(View.GONE);
        tvWarningConfirm.setVisibility(View.GONE);
        validator.validate();
    }


    @Override
    public void onValidationSucceeded() {
        String newPass = edtPasswordNew.getText().toString();
        String confirmPass = edtPasswordAgain.getText().toString();

        if (BuildConfig.DEBUG) {
            Log.e("Password new", newPass);
            Log.e("Password confirm", confirmPass);
        }
        EventBus.getDefault().post(new ChangePassword(newPass));
        dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

            if (view instanceof EditText) {
                if (view == edtPasswordNew) {
                    tvWarningPassword.setVisibility(View.VISIBLE);
                } else {
                    tvWarningConfirm.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
