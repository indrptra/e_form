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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kreditplus.eform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlacklistDialog extends BaseDialog {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.tv_no_agreement)
    TextView tvNoAgreement;
    @BindView(R.id.tv_time_delay)
    TextView tvTimeDelay;
    @BindView(R.id.tv_amount_of_fines)
    TextView tvAmountOfFines;
    @BindView(R.id.btn_ok_blacklist)
    Button btnOkBlacklist;
    @BindView(R.id.desc)
    LinearLayout desc;
    @BindView(R.id.tv_message_bucket)
    TextView tvMessageBucket;
    @BindView(R.id.ll_message_bucket)
    LinearLayout llMessageBucket;
    @BindView(R.id.ll_no_agreement)
    LinearLayout llNoAgreement;
    @BindView(R.id.ll_time_delay)
    LinearLayout llTimeDelay;
    @BindView(R.id.ll_amount_of_fines)
    LinearLayout llAmountOfFines;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_blacklist_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String bucketMessage = getArguments().getString("bucketMessage");
        String agreement = getArguments().getString("agreement");
        String timeDelay = getArguments().getString("timeDelay");
        String amountOfFines = getArguments().getString("amountOfFines");
        llMessageBucket.setVisibility(View.GONE);
        llNoAgreement.setVisibility(View.GONE);
        llTimeDelay.setVisibility(View.GONE);
        llAmountOfFines.setVisibility(View.GONE);

        tvMessageBucket.setText(": " + bucketMessage);
        tvNoAgreement.setText(": " + agreement);
        tvTimeDelay.setText(": " + timeDelay);
        tvAmountOfFines.setText(": " + amountOfFines);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @OnClick(R.id.btn_ok_blacklist)
    public void okButton() {
        dismiss();
//        startActivity(new Intent(getContext(), HomeActivity.class));
//        getActivity().finish();
    }
}
