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
import android.widget.EditText;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.SendEmailPo;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 23/05/17.
 */

public class SendEmailPoDialog extends BaseDialog implements Validator.ValidationListener {

    @Email(message = "Format email tidak sesuai")
    @NotEmpty(message = "Email harus diisi")
    @BindView(R.id.edt_send_email)
    EditText edtSendEmail;
    @BindView(R.id.btn_send_email)
    Button btnSendEmail;
    @BindView(R.id.tv_error_email)
    TextView tvErrorEmail;
    private Validator validator;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        validator = new Validator(this);
        validator.setValidationListener(this);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send_email_po, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btn_send_email)
    public void sendEmail(){
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        String email = edtSendEmail.getText().toString();
        EventBus.getDefault().post(new SendEmailPo(email));
        dismiss();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors){
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof EditText){
                ((EditText) view).setError(message);
            }
        }
    }
}
