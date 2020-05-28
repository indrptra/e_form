package com.kreditplus.eform.dialog;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
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
import com.kreditplus.eform.model.bus.ResendCodeSignatureEvent;

import org.greenrobot.eventbus.EventBus;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 01/03/17.
 */

public class DialogPersyaratan extends BaseDialog {

    @BindView(R.id.tv_isi_syarat)
    TextView tvIsiSyarat;
    @BindView(R.id.btn_ok_syarat)
    Button btnOkSyarat;
    @BindView(R.id.cbx_syarat)
    CheckBox cbxSyarat;

    private String descriptionSyarat = "DESCRIPTION_SYARAT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_syarat_ketentuan, container, false);
        ButterKnife.bind(this,view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        String syaratDescription = bundle.getString(descriptionSyarat, "");
        tvIsiSyarat.setText(Html.fromHtml(syaratDescription));

//        view.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));;


        if (cbxSyarat.isChecked()){
            btnOkSyarat.setEnabled(true);
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
//        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(dialog.getWindow().getAttributes());

        int dialogWindowWidth = (int) (displayWidth * 0.7f);
        int dialogWindowHeight = (int) (displayHeight * 0.7f);

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        dialog.getWindow().setAttributes(layoutParams);
*///        dialog.getWindow().setLayout(width, height);

//        WindowManager.LayoutParams inputParams = dialog.getWindow().getAttributes();
//        inputParams.gravity = Gravity.CENTER;
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        inputParams.width = (int) Math.round(((float) width) * 0.80);
//        dialog.getWindow().setAttributes(inputParams);

        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnCheckedChanged(R.id.cbx_syarat)
    public void checkBox(CompoundButton compoundButton, boolean isChecked){
        if (isChecked){
            btnOkSyarat.setEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnOkSyarat.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.colorPrimary));
            }
        }else{
            btnOkSyarat.setEnabled(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btnOkSyarat.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.bg_gray));
            }
        }
    }

    @OnClick(R.id.btn_ok_syarat)
    public void ok(){
        EventBus.getDefault().post(new ResendCodeSignatureEvent());
        dismiss();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
