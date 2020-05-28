package com.kreditplus.eform.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kreditplus.eform.R;
import com.kreditplus.eform.adapter.ViewKreditmuAdapter;
import com.kreditplus.eform.model.bus.SendDataKreditmu;
import com.kreditplus.eform.model.response.objecthelper.KreditmuObjt;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 24/07/17.
 */

public class ViewKreditmuActivity extends BaseActivity {

    @BindView(R.id.rv_view_kreditmu)
    RecyclerView rvViewKreditmu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindArray(R.array.kreditmu_field_name_personal)
    String[] arrayKreditmuFieldNamePersonal;
    @BindArray(R.array.kreditmu_field_type_personal)
    String[] arrayKreditmuFieldTypePersonal;
    @BindArray(R.array.kreditmu_field_name_alamat)
    String[] arrayKreditmuFieldNameAlamat;
    @BindArray(R.array.kreditmu_field_type_alamat)
    String[] arrayKreditmuFieldTypeAlamat;
    @BindArray(R.array.kreditmu_field_name_pekerjaan)
    String[] arrayKreditmuFieldNamePekerjaan;
    @BindArray(R.array.kreditmu_field_type_pekerjaan)
    String[] arrayKreditmuFieldTypePekerjaan;
    @BindArray(R.array.kreditmu_field_name_button)
    String[] arrayKreditmuFieldNameButton;
    @BindArray(R.array.kreditmu_field_type_button)
    String[] arrayKreditmuFieldTypeButton;
    @BindArray(R.array.kreditmu_first_data)
    String[] arrayKreditmuFirstData;
    @BindArray(R.array.kreditmu_field_name_picture)
    String[] arrayKreditmuPicture;
    @BindArray(R.array.kreditmu_field_type_picture)
    String[] arrayKreditmuTypePicture;

    private Map<String, String> mapForSubmit;
    private Map<String, String> mapJob;
    private Map<String, String> mapSpinner;
    private List<KreditmuObjt> kreditmuList;

    private ViewKreditmuAdapter adapter;
    private Map<Integer, File> mapAttachment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CEK DATA KREDITMU");

        mapForSubmit = new HashMap<>();
        mapJob = new HashMap<>();
        mapSpinner = new HashMap<>();

        Intent intent = getIntent();
        mapForSubmit = (Map<String, String>) intent.getExtras().getSerializable("mapForSubmit");
        mapJob = (Map<String, String>) intent.getExtras().getSerializable("mapJob");
        mapSpinner = (Map<String, String>) intent.getExtras().getSerializable("mapSpinner");
        mapAttachment = (Map<Integer, File>) intent.getExtras().getSerializable("mapAttachment");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvViewKreditmu.setLayoutManager(layoutManager);

        dataField();
    }

    @Override
    public Context getApplicationContext() {
        return this;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_view_kreditmu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_process_view_kreditmu)
    public void process(){
        finish();
        EventBus.getDefault().post(new SendDataKreditmu());
    }

    private void dataField() {

        kreditmuList = new ArrayList<>();

        for (int i = 0; i < arrayKreditmuFieldNamePersonal.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNamePersonal[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypePersonal[i]);
            kreditmuObjt.setGroup(1);
            kreditmuList.add(kreditmuObjt);
        }

        for (int i = 0; i < arrayKreditmuFieldNameAlamat.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            if(!"checkbox".equalsIgnoreCase(arrayKreditmuFieldTypeAlamat[i])){
                kreditmuObjt.setName(arrayKreditmuFieldNameAlamat[i]);
                kreditmuObjt.setType(arrayKreditmuFieldTypeAlamat[i]);
                kreditmuObjt.setGroup(2);
                kreditmuList.add(kreditmuObjt);
            }
        }

        for (int i = 0; i < arrayKreditmuFieldNamePekerjaan.length; i++) {
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            kreditmuObjt.setName(arrayKreditmuFieldNamePekerjaan[i]);
            kreditmuObjt.setType(arrayKreditmuFieldTypePekerjaan[i]);
            kreditmuObjt.setGroup(3);
            kreditmuList.add(kreditmuObjt);
        }

        for (int i = 0 ; i < arrayKreditmuPicture.length; i++){
            KreditmuObjt kreditmuObjt = new KreditmuObjt();
            if (!"Signature".equalsIgnoreCase(arrayKreditmuPicture[i])){
                kreditmuObjt.setName(arrayKreditmuPicture[i]);
                kreditmuObjt.setType(arrayKreditmuTypePicture[i]);
                kreditmuObjt.setGroup(4);
                kreditmuList.add(kreditmuObjt);
            }

        }


        adapter = new ViewKreditmuAdapter(kreditmuList,mapForSubmit,mapSpinner,mapAttachment,this);
        rvViewKreditmu.setAdapter(adapter);
    }
}
