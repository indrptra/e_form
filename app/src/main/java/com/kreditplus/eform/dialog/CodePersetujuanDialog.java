package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kreditplus.eform.R;
import com.kreditplus.eform.model.bus.ConfirmCodeSignature;
import com.kreditplus.eform.model.bus.ResendCodeSignatureEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Iwan Nurdesa on 04/08/16.
 */
public class CodePersetujuanDialog extends BaseDialog {

    @BindView(R.id.tv_resend_token)
    TextView tvResendToken;
    @BindView(R.id.edt_code_signature)
    EditText edtCodeSignature;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.btn_resend)
    Button btnResend;
    @BindView(R.id.tv_handphone)
    TextView tvHandphone;
    @BindView(R.id.tv_isi_resend_code)
    TextView tvIsiResend;
    @BindView(R.id.tv_error_send_code)
    TextView tvErrorSendCode;
    @BindView(R.id.tv_error_send_code_length)
    TextView tvErrorSendCodeLength;

    String nomerHandphone = "NOMER_HANDPHONE";
    String countsignature = "COUNT";
    String statSensorPhoneNumber = "STAT_PHONE_NUMBER";
    CountDownTimer countDownt;
    int count;
    String handphone;
    String sensorPhoneNumber;
    String noHpSensor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_code_persetujuan, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        count = bundle.getInt(countsignature, count);
        handphone = bundle.getString(nomerHandphone, "");
        String leftPhoneNumber = null;
        String midPhoneNumber = null;
        String rightPhoneNumber = null;
        sensorPhoneNumber = bundle.getString(statSensorPhoneNumber, "");
//        12** ***8
//        123* **** 9
//        123* **** 90
        if (sensorPhoneNumber.equals("1")) {
            if (handphone.length() >= 9 && handphone.length() <= 20) {
                leftPhoneNumber = handphone.substring(0, 3);
                midPhoneNumber = handphone.replace(handphone, "*****");
                if (handphone.length() == 9)
                    rightPhoneNumber = handphone.substring(handphone.length() - 1);
                if (handphone.length() == 10)
                    rightPhoneNumber = handphone.substring(handphone.length() - 2);
                if (handphone.length() == 11)
                    rightPhoneNumber = handphone.substring(handphone.length() - 3);
                if (handphone.length() == 12)
                    rightPhoneNumber = handphone.substring(handphone.length() - 4);
                if (handphone.length() == 13)
                    rightPhoneNumber = handphone.substring(handphone.length() - 5);
                if (handphone.length() == 14)
                    rightPhoneNumber = handphone.substring(handphone.length() - 6);
                if (handphone.length() == 15)
                    rightPhoneNumber = handphone.substring(handphone.length() - 7);
                if (handphone.length() == 16)
                    rightPhoneNumber = handphone.substring(handphone.length() - 8);
                if (handphone.length() == 17)
                    rightPhoneNumber = handphone.substring(handphone.length() - 9);
                if (handphone.length() == 18)
                    rightPhoneNumber = handphone.substring(handphone.length() - 10);
                if (handphone.length() == 19)
                    rightPhoneNumber = handphone.substring(handphone.length() - 11);
                if (handphone.length() == 20)
                    rightPhoneNumber = handphone.substring(handphone.length() - 12);
            } else if (handphone.length() == 8) {
                leftPhoneNumber = handphone.substring(0, 2);
                midPhoneNumber = handphone.replace(handphone, "*****");
                rightPhoneNumber = handphone.substring(handphone.length() - 1);
            }
            noHpSensor = leftPhoneNumber + midPhoneNumber + rightPhoneNumber;
        } else {
            noHpSensor = handphone;
        }
        tvHandphone.setText(noHpSensor);
        CountSignature();
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
        if (count < 3){
            try {
                countDownt.cancel();
            }catch (Exception e){

            }
        }
    }

    @OnClick(R.id.btn_ok)
    public void ok() {
        tvErrorSendCode.setVisibility(View.GONE);
        tvErrorSendCodeLength.setVisibility(View.GONE);
        if ("".equalsIgnoreCase(edtCodeSignature.getText().toString())) {
            tvErrorSendCode.setVisibility(View.VISIBLE);
        } else if (edtCodeSignature.getText().toString().length() < 6) {
            tvErrorSendCodeLength.setVisibility(View.VISIBLE);
        } else {
            dismiss();
            EventBus.getDefault().post(new ConfirmCodeSignature(edtCodeSignature.getText().toString()));
        }

    }


    @OnClick(R.id.btn_resend)
    public void resend() {
        EventBus.getDefault().post(new ResendCodeSignatureEvent());
        dismiss();
    }

    public class countDownclass extends CountDownTimer {

        countDownclass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            String timerProgress = "" + String.format(Locale.getDefault(), "%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            tvTimer.setText(timerProgress);

        }

        @Override
        public void onFinish() {

            tvTimer.setText(R.string.timer_00);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnResend.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
            }
            btnResend.setEnabled(true);

        }

    }

    // cek apakah seudah mengirim token sebanyak 3x
    private void CountSignature() {
        if (count >= 2) {
            tvIsiResend.setVisibility(View.VISIBLE);
            tvTimer.setVisibility(View.GONE);
            tvResendToken.setText(R.string.tv_title_token2);

        } else {
            countDownt = new countDownclass(60000, 1000);
            countDownt.start();
        }
    }

}
