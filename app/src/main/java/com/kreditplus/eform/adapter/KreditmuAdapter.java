package com.kreditplus.eform.adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kreditplus.eform.BuildConfig;
import com.kreditplus.eform.R;
import com.kreditplus.eform.fragment.KreditmuFragment;
import com.kreditplus.eform.helper.NiceAutoCompleteTextView;
import com.kreditplus.eform.helper.Util;
import com.kreditplus.eform.model.response.KreditmuResponse;
import com.kreditplus.eform.model.response.objecthelper.AttachmentObjt;
import com.kreditplus.eform.model.response.objecthelper.KreditmuCity;
import com.kreditplus.eform.model.response.objecthelper.KreditmuEdu;
import com.kreditplus.eform.model.response.objecthelper.KreditmuHome;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobPosition;
import com.kreditplus.eform.model.response.objecthelper.KreditmuJobType;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKecamatan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuKelurahan;
import com.kreditplus.eform.model.response.objecthelper.KreditmuMarital;
import com.kreditplus.eform.model.response.objecthelper.KreditmuObjt;
import com.kreditplus.eform.model.response.objecthelper.KreditmuProfession;
import com.kreditplus.eform.view.IndonesianCurrencyEditText;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apc-lap012 on 12/07/17.
 */

public class KreditmuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CAMERA_1 = 101;
    private static final int CAMERA_2 = 102;
    private static final int CAMERA_3 = 103;
    private static final int CAMERA_4 = 104;

    private final KreditmuResponse dataKreditmu;
    private Context context;
    private List<KreditmuObjt> kreditmuList;
    private List<KreditmuObjt> kreditmus;
    private List<String> validationList;
    private List<KreditmuEdu> eduList;
    private List<KreditmuKecamatan> kecamatanList;
    private List<KreditmuKelurahan> kelurahanList;
    private List<KreditmuJobType> kreditmuJobTypeList;
    private ArrayAdapter<KreditmuEdu> adapterEdu;
    private ArrayAdapter<KreditmuHome> adapterHome;
    private ArrayAdapter<KreditmuMarital> adapterMarital;
    private ArrayAdapter<KreditmuCity> adapterCity;
    private ArrayAdapter<KreditmuCity> adapterCityKtp;
    private ArrayAdapter<KreditmuCity> adapterCityKerja;
    private ArrayAdapter<KreditmuProfession> adapterProfession;
    private ArrayAdapter<KreditmuKecamatan> adapterKecamatanDomisili;
    private ArrayAdapter<KreditmuKecamatan> adapterKecamatanKtp;
    private ArrayAdapter<KreditmuKecamatan> adapterKecamatanPekerjaan;
    private ArrayAdapter<KreditmuKelurahan> adapterKelurahanDomisili;
    private ArrayAdapter<KreditmuKelurahan> adapterKelurahanKtp;
    private ArrayAdapter<KreditmuKelurahan> adapterKelurahanPekerjaan;
    private ArrayAdapter<KreditmuJobType> adapterJobType;
    private ArrayAdapter<KreditmuJobPosition> adapterJobPosition;
    private ArrayAdapter<KreditmuKelurahan> adapterKelurahan;
    private KreditmuFragment fragment;

    private Map<String, String> map = new HashMap<>();
    private Map<String, String> mapJob = new HashMap<>();
    private Map<String, String> mapSpinner = new HashMap<>();
    private static final int HEADER = 0;
    private static final int EDIT_TEXT = 1;
    private static final int SPINNER = 2;
    private static final int PHONE = 3;
    private static final int INT_EDT = 4;
    private static final int CHECKBOX = 5;
    private static final int TANGGAL = 6;
    private static final int CURRENCY = 7;
    private static final int BUTTON = 8;
    private static final int KOTA = 9;
    private static final int KOTA_KTP = 10;
    private static final int KOTA_KERJA = 11;
    private static final int KECAMATAN_DOMISILI = 12;
    private static final int KELURAHAN_DOMISILI = 13;
    private static final int PROFESI = 14;
    private static final int JOB_TYPE = 15;
    private static final int JOB_POSITION = 16;
    private static final int KECAMATAN_KTP = 17;
    private static final int KELURAHAN_KTP = 18;
    private static final int KECAMATAN_PEKERJAAN = 19;
    private static final int KELURAHAN_PEKERJAAN = 20;
    private static final int ZIP = 21;
    private static final int BRANCH = 22;
    private static final int ZIP_KTP = 23;
    private static final int ZIP_KERJA = 24;
    private static final int ATTACHMENT = 25;
    private static final int SIGNATURE = 26;
    private static final int TINGGAL_TAHUN = 27;
    private static final int TINGGAL_BULAN = 28;
    private static final int KERJA_BULAN = 29;
    private static final int KERJA_TAHUN = 30;

    private boolean check = false;
    private boolean isHidePersonal = false;
    private boolean isHideKtp = false;
    private boolean isHidePekerjaan = false;
    private boolean isSuccessValidate = true;
    private boolean isKtpCorrect;
    private boolean isHandphoneCorrect;
    private boolean isEmailCorrect;
    private int countCollapse;
    private Validator validator;
    private VHKecamatan vhKec;
    private VHKecamatanKtp vhKecKtp;
    private VHKelurahanKtp vhKelKtp;
    private VHKecamatanKerja vhKecKer;
    private VHKelurahanKerja vhKelKer;
    private VHKelurahan vhKel;
    private VHJobType vhJobType;
    private VHPosition vhJobPos;
    private VHzipcode vhZip;
    private VHBranch vhBranch;
    private VHzipcodeKtp vhZipKtp;
    private VHzipcodeKerja vhZipKerja;
    private VHSignature vhSignature;
    private VHAttachmet vhAttachment;
    private VHTinggalBulan vhTinggalBulan;
    private VHKerjaBulan vhKerjaBulan;

    private String kotaNameDom;
    private String kotaNamektp;
    private String kotaNameKerja;
    private String kecNameDom;
    private String kecNameKtp;
    private String kecNameKerja;
    private String kelNameDom;
    private String kelNameKtp;
    private String kelNameKerja;
    private String profesiId;
    private String jobTypeId;
    private String message = "";
    private boolean isUserValid = false;
    private Map<Integer, File> mapAttachment = new HashMap<>();
    private Map<Integer, File> mapSignature = new HashMap<>();
    private List<AttachmentObjt> attachmentList;
    private List<AttachmentObjt> signatureList;
    private boolean isYearWorkSelected = false;
    private boolean isYearStaySelected = false;
    private String yearWork;
    private String yearStay;
    private boolean isNowYear = false;


    public KreditmuAdapter(Context context, List<KreditmuObjt> kreditmulist, KreditmuResponse kreditmuResponse, List<String> validationList, KreditmuFragment fragment) {
        this.context = context;
        this.kreditmuList = kreditmulist;
        this.dataKreditmu = kreditmuResponse;
        this.fragment = fragment;
        this.validationList = validationList;

        kreditmus = new ArrayList<>();
        for (int i = 0; i < kreditmuList.size(); i++) {
            KreditmuObjt objt = new KreditmuObjt();
            objt.setName(kreditmuList.get(i).getName());
            objt.setGroup(kreditmulist.get(i).getGroup());
            objt.setType(kreditmulist.get(i).getType());
            kreditmus.add(objt);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case HEADER:
                view = inflater.inflate(R.layout.item_kreditmu_header, parent, false);
                return new VHHeader(view);
            case EDIT_TEXT:
                view = inflater.inflate(R.layout.item_kreditmu_edt_text, parent, false);
                return new VHEdtText(view, new MyCustomTextWatcher());
            case CURRENCY:
                view = inflater.inflate(R.layout.item_kreditmu_currency, parent, false);
                return new VHCurency(view, new MyCustomTextWatcher());
            case SPINNER:
                view = inflater.inflate(R.layout.item_kreditmu_spinner, parent, false);
                return new VHSpinner(view);
            case PHONE:
                view = inflater.inflate(R.layout.item_kreditmu_phone, parent, false);
                return new VHPhone(view);
            case INT_EDT:
                view = inflater.inflate(R.layout.item_kreditmu_int_edt_text, parent, false);
                return new VHIntEdtText(view, new MyCustomTextWatcher());
            case CHECKBOX:
                view = inflater.inflate(R.layout.item_kreditmu_checkbox, parent, false);
                return new VHCheckBox(view);
            case TANGGAL:
                view = inflater.inflate(R.layout.item_kreditmu_tanggal, parent, false);
                return new VHTanggal(view, new MyCustomTextWatcher());
            case TINGGAL_TAHUN:
                view = inflater.inflate(R.layout.item_kreditmu_tahun_tinggal, parent, false);
                return new VHTinggalTahun(view, new MyCustomTextWatcher());
            case TINGGAL_BULAN:
                view = inflater.inflate(R.layout.item_kreditmu_bulan_tinggal, parent, false);
                vhTinggalBulan = new VHTinggalBulan(view, new MyCustomTextWatcher());
                return vhTinggalBulan;
            case KERJA_TAHUN:
                view = inflater.inflate(R.layout.item_kreditmu_tahun_kerja, parent, false);
                return new VHKerjaTahun(view, new MyCustomTextWatcher());
            case KERJA_BULAN:
                view = inflater.inflate(R.layout.item_kreditmu_bulan_kerja, parent, false);
                vhKerjaBulan = new VHKerjaBulan(view, new MyCustomTextWatcher());
                return vhKerjaBulan;
            case KOTA:
                view = inflater.inflate(R.layout.item_kreditmu_kota, parent, false);
                return new VHKota(view, new MyCustomTextWatcher());
            case KOTA_KTP:
                view = inflater.inflate(R.layout.item_kreditmu_kota_ktp, parent, false);
                return new VHKotaKtp(view, new MyCustomTextWatcher());
            case KOTA_KERJA:
                view = inflater.inflate(R.layout.item_kreditmu_kota_kerja, parent, false);
                return new VHKotaKerja(view, new MyCustomTextWatcher());
            case KECAMATAN_DOMISILI:
                view = inflater.inflate(R.layout.item_kreditmu_kecamatan, parent, false);
                vhKec = new VHKecamatan(view, new MyCustomTextWatcher());
                return vhKec;
            case KELURAHAN_DOMISILI:
                view = inflater.inflate(R.layout.item_kreditmu_kelurahan, parent, false);
                vhKel = new VHKelurahan(view, new MyCustomTextWatcher());
                return vhKel;
            case KECAMATAN_KTP:
                view = inflater.inflate(R.layout.item_kreditmu_kecamatan_ktp, parent, false);
                vhKecKtp = new VHKecamatanKtp(view, new MyCustomTextWatcher());
                return vhKecKtp;
            case KELURAHAN_KTP:
                view = inflater.inflate(R.layout.item_kreditmu_kelurahan_ktp, parent, false);
                vhKelKtp = new VHKelurahanKtp(view, new MyCustomTextWatcher());
                return vhKelKtp;
            case KECAMATAN_PEKERJAAN:
                view = inflater.inflate(R.layout.item_kreditmu_kecamatan_kerja, parent, false);
                vhKecKer = new VHKecamatanKerja(view, new MyCustomTextWatcher());
                return vhKecKer;
            case KELURAHAN_PEKERJAAN:
                view = inflater.inflate(R.layout.item_kreditmu_kelurahan_kerja, parent, false);
                vhKelKer = new VHKelurahanKerja(view, new MyCustomTextWatcher());
                return vhKelKer;
            case PROFESI:
                view = inflater.inflate(R.layout.item_kreditmu_profesi, parent, false);
                return new VHProfesi(view, new MyCustomTextWatcher());
            case JOB_TYPE:
                view = inflater.inflate(R.layout.item_kreditmu_job_type, parent, false);
                vhJobType = new VHJobType(view, new MyCustomTextWatcher());
                return vhJobType;
            case JOB_POSITION:
                view = inflater.inflate(R.layout.item_kreditmu_job_position, parent, false);
                vhJobPos = new VHPosition(view, new MyCustomTextWatcher());
                return vhJobPos;
            case BUTTON:
                view = inflater.inflate(R.layout.item_kreditmu_button, parent, false);
                return new VHButton(view);
            case ZIP:
                view = inflater.inflate(R.layout.item_kreditmu_zipcode, parent, false);
                vhZip = new VHzipcode(view, new MyCustomTextWatcher());
                return vhZip;
            case ZIP_KTP:
                view = inflater.inflate(R.layout.item_kreditmu_zip_ktp, parent, false);
                vhZipKtp = new VHzipcodeKtp(view, new MyCustomTextWatcher());
                return vhZipKtp;
            case ZIP_KERJA:
                view = inflater.inflate(R.layout.item_kreditmu_zip_kerja, parent, false);
                vhZipKerja = new VHzipcodeKerja(view, new MyCustomTextWatcher());
                return vhZipKerja;
            case BRANCH:
                view = inflater.inflate(R.layout.item_kreditmu_branch, parent, false);
                vhBranch = new VHBranch(view, new MyCustomTextWatcher());
                return vhBranch;
            case ATTACHMENT:
                view = inflater.inflate(R.layout.item_kreditmu_photo, parent, false);
                vhAttachment = new VHAttachmet(view);
                return vhAttachment;
            case SIGNATURE:
                view = inflater.inflate(R.layout.item_kreditmu_signature, parent, false);
                vhSignature = new VHSignature(view);
                return vhSignature;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String value;

        switch (holder.getItemViewType()) {
            case HEADER:
                VHHeader vhHeader = (VHHeader) holder;
                vhHeader.txtKreditmuHeader.setText(kreditmuList.get(position).getName());
                if ("Data Pribadi".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhHeader.imgKreditmuHeader.setImageResource(R.drawable.ic_data_pribadi);
                } else if ("Alamat Pemohon".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhHeader.imgKreditmuHeader.setImageResource(R.drawable.ic_alamat);
                } else if ("Data Pekerjaan".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhHeader.imgKreditmuHeader.setImageResource(R.drawable.ic_pekerjaan);
                } else if ("Attachment".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhHeader.imgKreditmuHeader.setImageResource(R.drawable.ic_attachment);
                } else if ("Signature".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhHeader.imgKreditmuHeader.setImageResource(R.drawable.ic_persetujuan);
                }
                break;
            case EDIT_TEXT:
                VHEdtText vhEdtText = (VHEdtText) holder;
                vhEdtText.tilKreditmuText.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhEdtText.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                if (BuildConfig.DEBUG && value != null)
                    Log.w("position edtText", value);
                vhEdtText.edtKreditmuText.setText(value);
                break;
            case CURRENCY:
                VHCurency vhCurency = (VHCurency) holder;
                vhCurency.tilKreditmuCurrency.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhCurency.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                if (BuildConfig.DEBUG && value != null)
                    Log.w("position currency", value);
                vhCurency.edtKreditmuCurrency.setText(value);
                break;
            case SPINNER:
                VHSpinner vhSpinner = (VHSpinner) holder;
                vhSpinner.tvKreditmuSpinner.setText(kreditmuList.get(holder.getAdapterPosition()).getName());

                if ("Pendidikan".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    if (dataKreditmu.getEduList() != null)
                        vhSpinner.spnKreditmuSpinner.setAdapter(adapterEdu);
                } else if ("Status Pernikahan".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    if (dataKreditmu.getMaritalList() != null)
                        vhSpinner.spnKreditmuSpinner.setAdapter(adapterMarital);
                } else {
                    if (dataKreditmu.getHomeList() != null)
                        vhSpinner.spnKreditmuSpinner.setAdapter(adapterHome);
                }

                vhSpinner.spnKreditmuSpinner.setSelection(Integer.parseInt(String.valueOf(map.get(kreditmuList.get(holder.getAdapterPosition()).getName()) == null ? 0 : map.get(kreditmuList.get(holder.getAdapterPosition()).getName()))));
                vhSpinner.spnKreditmuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int positionItem, long id) {
                        map.put(kreditmuList.get(holder.getAdapterPosition()).getName(), String.valueOf(positionItem));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case PHONE:
                VHPhone vhPhone = (VHPhone) holder;

                String valueArea = map.get("area");
                vhPhone.edtKreditmuAreaPhone.setText(valueArea);

                String valuePhone = map.get("phone");
                vhPhone.edtKreditmuPhone.setText(valuePhone);
                break;
            case INT_EDT:
                VHIntEdtText vhIntEdtText = (VHIntEdtText) holder;
                vhIntEdtText.tilKreditmuIntText.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhIntEdtText.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());

                if ("Nomor KTP".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhIntEdtText.edtKreditmuInt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
                } else if ("Nomor handphone".equalsIgnoreCase(kreditmuList.get(holder.getAdapterPosition()).getName())) {
                    vhIntEdtText.edtKreditmuInt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    vhIntEdtText.edtKreditmuInt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                }

                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                if (BuildConfig.DEBUG && value != null)
                    Log.w("position", value);
                vhIntEdtText.edtKreditmuInt.setText(value);
                break;
            case CHECKBOX:
                VHCheckBox vhCheckBox = (VHCheckBox) holder;
                vhCheckBox.cbxKreditmu.setText(kreditmuList.get(holder.getAdapterPosition()).getName());
                map.put(kreditmuList.get(holder.getAdapterPosition()).getName(), String.valueOf(check));
                break;
            case KOTA:
                VHKota vhKota = (VHKota) holder;
                vhKota.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKota.actKreditmuKota.setText(value);
                break;
            case KOTA_KTP:
                VHKotaKtp vhKotaKtp = (VHKotaKtp) holder;
                vhKotaKtp.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKotaKtp.actKreditmuKotaKtp.setText(value);
                break;
            case KOTA_KERJA:
                VHKotaKerja vhKotaKerja = (VHKotaKerja) holder;
                vhKotaKerja.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKotaKerja.actKreditmuKotaKerja.setText(value);
                break;
            case KECAMATAN_DOMISILI:
                VHKecamatan vhKecamatan = (VHKecamatan) holder;
                vhKecamatan.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKecamatan.actKreditmuKecamatan.setText(value);
                break;
            case KELURAHAN_DOMISILI:
                VHKelurahan vhKelurahan = (VHKelurahan) holder;
                vhKelurahan.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKelurahan.actKreditmuKelurahan.setText(value);
                break;
            case KECAMATAN_KTP:
                VHKecamatanKtp vhKecamatanKtp = (VHKecamatanKtp) holder;
                vhKecamatanKtp.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKecamatanKtp.actKreditmuKecamatanKtp.setText(value);
                break;
            case KELURAHAN_KTP:
                VHKelurahanKtp vhKelurahanKtp = (VHKelurahanKtp) holder;
                vhKelurahanKtp.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKelurahanKtp.actKreditmuKelurahanKtp.setText(value);
                break;
            case KECAMATAN_PEKERJAAN:
                VHKecamatanKerja vhKecamatanKerja = (VHKecamatanKerja) holder;
                vhKecamatanKerja.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKecamatanKerja.actKreditmuKecamatanKerja.setText(value);
                break;
            case KELURAHAN_PEKERJAAN:
                VHKelurahanKerja vhKelurahanKerja = (VHKelurahanKerja) holder;
                vhKelurahanKerja.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKelurahanKerja.actKreditmuKelurahanKerja.setText(value);
                break;
            case PROFESI:
                VHProfesi vhProfesi = (VHProfesi) holder;
                vhProfesi.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhProfesi.actKreditmuProfesi.setText(value);
                break;
            case JOB_TYPE:
                VHJobType vhJobType = (VHJobType) holder;
                vhJobType.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhJobType.actKreditmuType.setText(value);
                break;
            case JOB_POSITION:
                VHPosition vhPosition = (VHPosition) holder;
                vhPosition.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhPosition.actKreditmuPosition.setText(value);
                break;
            case TANGGAL:
                VHTanggal vhTanggal = (VHTanggal) holder;
                vhTanggal.tilKreditmuTanggal.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTanggal.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTanggal.edtKreditmuTanggal.setText(value);
                break;
            case TINGGAL_BULAN:
                VHTinggalBulan vhTinggalBulan = (VHTinggalBulan) holder;
                vhTinggalBulan.tilKreditmuBulanTinggal.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTinggalBulan.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTinggalBulan.edtKreditmuBulanTinggal.setText(value);
                break;
            case TINGGAL_TAHUN:
                VHTinggalTahun vhTinggalTahun = (VHTinggalTahun) holder;
                vhTinggalTahun.tilKreditmuTahunTinggal.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTinggalTahun.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhTinggalTahun.edtKreditmuTahunTinggal.setText(value);
                break;
            case KERJA_BULAN:
                VHKerjaBulan vhKerjaBulan = (VHKerjaBulan) holder;
                vhKerjaBulan.tilKreditmuBulanKerja.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKerjaBulan.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKerjaBulan.edtKreditmuBulanKerja.setText(value);
                break;
            case KERJA_TAHUN:
                VHKerjaTahun vhKerjaTahun = (VHKerjaTahun) holder;
                vhKerjaTahun.tilKreditmuTahunKerja.setHint(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKerjaTahun.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhKerjaTahun.edtKreditmuTahunKerja.setText(value);
                break;
            case BUTTON:
                VHButton vhButton = (VHButton) holder;
                vhButton.btnKreditmuSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        message = "";
                        validation();
                        if (isSuccessValidate) {
                            ktpValidation(map.get("Nomor KTP"));
                            if (isKtpCorrect) {
                                noHandphoneValidation(map.get("Nomor handphone"));
                                if (isHandphoneCorrect) {
                                    emailValidation(map.get("Alamat email"));
                                    if (isEmailCorrect) {
                                        imageAddToList();
                                        fragment.viewDataKreditmu(map, kreditmuList, mapJob, attachmentList, signatureList, mapAttachment);
                                    } else {
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, message + " Harus Lengkap", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                break;
            case ZIP:
                VHzipcode vHzipcode = (VHzipcode) holder;
                vHzipcode.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vHzipcode.edtKreditmuTextZip.setText(value);
                break;
            case ZIP_KTP:
                VHzipcodeKtp vhZipKtp = (VHzipcodeKtp) holder;
                vhZipKtp.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhZipKtp.edtKreditmuZipKtp.setText(value);
                break;
            case ZIP_KERJA:
                VHzipcodeKerja vHzipcodeKerja = (VHzipcodeKerja) holder;
                vHzipcodeKerja.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vHzipcodeKerja.edtKreditmuZipKerja.setText(value);
                break;
            case BRANCH:
                VHBranch vhBranch = (VHBranch) holder;
                vhBranch.customTextWatcher.updatePosition(kreditmuList.get(holder.getAdapterPosition()).getName());
                value = map.get(kreditmuList.get(holder.getAdapterPosition()).getName());
                vhBranch.edtKreditmuTextBranch.setText(value);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return this.kreditmuList.size();

    }

    @Override
    public int getItemViewType(int position) {

        if ("header".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return HEADER;
        } else if ("editText".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return EDIT_TEXT;
        } else if ("currency".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return CURRENCY;
        } else if ("spinner".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return SPINNER;
        } else if ("phone".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return PHONE;
        } else if ("IntEdtText".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return INT_EDT;
        } else if ("checkbox".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return CHECKBOX;
        } else if ("tanggal".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return TANGGAL;
        } else if ("kota".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KOTA;
        } else if ("kotaKtp".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KOTA_KTP;
        } else if ("kotaPekerjaan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KOTA_KERJA;
        } else if ("kecamatanDomisili".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KECAMATAN_DOMISILI;
        } else if ("kelurahanDomisili".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KELURAHAN_DOMISILI;
        } else if ("kecamatanKtp".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KECAMATAN_KTP;
        } else if ("kelurahanKtp".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KELURAHAN_KTP;
        } else if ("kecamatanPekerjaan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KECAMATAN_PEKERJAAN;
        } else if ("kelurahanPekerjaan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KELURAHAN_PEKERJAAN;
        } else if ("profesi".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return PROFESI;
        } else if ("jobtype".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return JOB_TYPE;
        } else if ("jobPosition".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return JOB_POSITION;
        } else if ("button".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return BUTTON;
        } else if ("zip".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return ZIP;
        } else if ("zipKtp".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return ZIP_KTP;
        } else if ("zipPekerjaan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return ZIP_KERJA;
        } else if ("branch".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return BRANCH;
        } else if ("ATTACHMENT".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return ATTACHMENT;
        } else if ("signature".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return SIGNATURE;
        } else if ("tinggalTahun".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return TINGGAL_TAHUN;
        } else if ("tinggalBulan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return TINGGAL_BULAN;
        } else if ("kerjaBulan".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KERJA_BULAN;
        } else if ("kerjaTahun".equalsIgnoreCase(kreditmuList.get(position).getType())) {
            return KERJA_TAHUN;
        }
        return -1;
    }


    // class layout
    class VHHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.rl_kreditmu_header)
        RelativeLayout rlKreditmuHeader;
        @BindView(R.id.txt_kreditmu_header)
        TextView txtKreditmuHeader;
        @BindView(R.id.img_kreditmu_header)
        ImageView imgKreditmuHeader;

        VHHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            rlKreditmuHeader.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (kreditmuList.get(getAdapterPosition()).getGroup() == 1) {
//                        showHideContent(1);
//                    } else if (kreditmuList.get(getAdapterPosition()).getGroup() == 2) {
//                        showHideContent(2);
//                    } else {
//                        showHideContent(3);
//                    }
//                }
//            });
        }
    }

    class VHEdtText extends RecyclerView.ViewHolder {

        @NotEmpty
        @BindView(R.id.edt_kreditmu_text)
        EditText edtKreditmuText;
        @BindView(R.id.til_kreditmu_text)
        TextInputLayout tilKreditmuText;

        private MyCustomTextWatcher customTextWatcher;

        VHEdtText(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            edtKreditmuText.addTextChangedListener(customTextWatcher);
        }

    }

    class VHSpinner extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_kreditmu_spinner)
        TextView tvKreditmuSpinner;
        @BindView(R.id.spn_kreditmu_spinner)
        Spinner spnKreditmuSpinner;

        VHSpinner(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            adapterEdu = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dataKreditmu.getEduList());
            adapterHome = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dataKreditmu.getHomeList());
            adapterMarital = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dataKreditmu.getMaritalList());
        }
    }

    class VHPhone extends RecyclerView.ViewHolder {

        @NotEmpty
        @BindView(R.id.edt_kreditmu_area_phone)
        EditText edtKreditmuAreaPhone;
        @BindView(R.id.edt_kreditmu_phone)
        EditText edtKreditmuPhone;

        VHPhone(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            edtKreditmuAreaPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    map.put("area", s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            edtKreditmuPhone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    map.put("phone", s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    class VHCheckBox extends RecyclerView.ViewHolder {

        @BindView(R.id.cbx_kreditmu)
        CheckBox cbxKreditmu;

        VHCheckBox(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            cbxKreditmu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        hideKtp();
                        check = true;
                        map.put(kreditmuList.get(getAdapterPosition()).getName(), String.valueOf(check));
                    } else {
                        showKtp();
                        check = false;
                        map.put("Sesuai Alamat Domisili", String.valueOf(check));
                    }
                }
            });
        }
    }

    class VHIntEdtText extends RecyclerView.ViewHolder {

        @NotEmpty
        @BindView(R.id.edt_kreditmu_int_edt_text)
        EditText edtKreditmuInt;
        @BindView(R.id.til_kreditmu_int_text)
        TextInputLayout tilKreditmuIntText;

        private MyCustomTextWatcher customTextWatcher;

        VHIntEdtText(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            edtKreditmuInt.addTextChangedListener(customTextWatcher);

        }
    }

    class VHCurency extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_currency)
        TextInputLayout tilKreditmuCurrency;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_currency)
        IndonesianCurrencyEditText edtKreditmuCurrency;

        private MyCustomTextWatcher customTextWatcher;

        VHCurency(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            edtKreditmuCurrency.addTextChangedListener(customTextWatcher);

        }
    }

    class VHTinggalTahun extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_tahun_tinggal)
        TextInputLayout tilKreditmuTahunTinggal;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_tahun_tinggal)
        EditText edtKreditmuTahunTinggal;

        private MyCustomTextWatcher customTextWatcher;
        int yearNow;

        public VHTinggalTahun(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            Calendar cal = Calendar.getInstance();
            yearNow = cal.get(Calendar.YEAR);
            edtKreditmuTahunTinggal.addTextChangedListener(customTextWatcher);
            edtKreditmuTahunTinggal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog.OnDateSetListener tahunTinggalListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            if (year == yearNow) {
                                isNowYear = true;
                            } else {
                                isNowYear = false;
                            }
                            if (year <= yearNow) {
                                String yearString = Integer.toString(year) + "";
                                map.put(kreditmuList.get(getAdapterPosition()).getName(), yearString);
                                edtKreditmuTahunTinggal.setText(yearString);
                                vhTinggalBulan.edtKreditmuBulanTinggal.setText("");
                            } else {
                                Toast.makeText(context, R.string.warning_tahun_lebih, Toast.LENGTH_SHORT).show();
                            }

                            map.put("tinggalBulan", "");
                        }
                    };
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, 0);
                    Date maxDate = calendar.getTime();

                    DatePickerDialog pickerDialog = customDatePicker(tahunTinggalListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            View.VISIBLE, View.GONE, View.GONE, true);
                    pickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
                    pickerDialog.show();
                }
            });
        }
    }

    class VHTinggalBulan extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_bulan_tinggal)
        TextInputLayout tilKreditmuBulanTinggal;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_bulan_tinggal)
        EditText edtKreditmuBulanTinggal;

        private MyCustomTextWatcher customTextWatcher;
        int nowMonth;

        public VHTinggalBulan(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            Calendar cal = Calendar.getInstance();
            nowMonth = cal.get(Calendar.MONTH);
            edtKreditmuBulanTinggal.addTextChangedListener(customTextWatcher);
            edtKreditmuBulanTinggal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog.OnDateSetListener bulanTinggalListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            if (isNowYear && month + 1 > nowMonth+1) {
                                Toast.makeText(context, R.string.warning_bulan_lebih, Toast.LENGTH_SHORT).show();
                            } else {
                                String monthString = Integer.toString(month + 1) + "";
                                map.put(kreditmuList.get(getAdapterPosition()).getName(), monthString);
                                edtKreditmuBulanTinggal.setText(monthString);
                            }
                        }
                    };
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    if (isNowYear) {
                        calendar.add(Calendar.YEAR, 0);
                    } else {
                        calendar.add(Calendar.YEAR, -1);
                    }
                    DatePickerDialog pickerDialog = customDatePicker(bulanTinggalListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, true);
                    pickerDialog.show();
                }
            });
        }
    }

    class VHKerjaTahun extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_tahun_kerja)
        TextInputLayout tilKreditmuTahunKerja;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_tahun_kerja)
        EditText edtKreditmuTahunKerja;

        private MyCustomTextWatcher customTextWatcher;
        int yearNow;

        public VHKerjaTahun(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            Calendar cal = Calendar.getInstance();
            yearNow = cal.get(Calendar.YEAR);
            edtKreditmuTahunKerja.addTextChangedListener(customTextWatcher);
            edtKreditmuTahunKerja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog.OnDateSetListener tahunKerjaListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            if (year == yearNow) {
                                isNowYear = true;
                            } else {
                                isNowYear = false;
                            }
                            if (year <= yearNow) {
                                String yearString = Integer.toString(year) + "";
                                map.put(kreditmuList.get(getAdapterPosition()).getName(), yearString);
                                edtKreditmuTahunKerja.setText(yearString);
                            } else {
                                Toast.makeText(context, R.string.warning_tahun_lebih, Toast.LENGTH_SHORT).show();
                            }
                            vhKerjaBulan.edtKreditmuBulanKerja.setText("");
                            map.put("kerjaBulan", "");
                        }
                    };

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.YEAR, 0);
                    Date maxDate = calendar.getTime();

                    DatePickerDialog pickerDialog = customDatePicker(tahunKerjaListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.VISIBLE, View.GONE, View.GONE, true);
                    pickerDialog.getDatePicker().setMaxDate(maxDate.getTime());
                    pickerDialog.show();
                }
            });
        }
    }

    class VHKerjaBulan extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_bulan_kerja)
        TextInputLayout tilKreditmuBulanKerja;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_bulan_kerja)
        EditText edtKreditmuBulanKerja;

        private MyCustomTextWatcher customTextWatcher;
        int nowMonth;

        public VHKerjaBulan(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;
            Calendar cal = Calendar.getInstance();
            nowMonth = cal.get(Calendar.MONTH);
            edtKreditmuBulanKerja.addTextChangedListener(customTextWatcher);
            edtKreditmuBulanKerja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerDialog.OnDateSetListener bulanKerjaListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            if (isNowYear && month + 1 > nowMonth+1) {
                                Toast.makeText(context, R.string.warning_bulan_lebih, Toast.LENGTH_SHORT).show();
                            } else {
                                String monthString = Integer.toString(month + 1) + "";
                                map.put(kreditmuList.get(getAdapterPosition()).getName(), monthString);
                                edtKreditmuBulanKerja.setText(monthString);
                            }
                        }
                    };

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    if (isNowYear) {
                        calendar.add(Calendar.YEAR, 0);
                    } else {
                        calendar.add(Calendar.YEAR, -1);
                    }
                    DatePickerDialog pickerDialog = customDatePicker(bulanKerjaListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), View.GONE, View.VISIBLE, View.GONE, true);
                    pickerDialog.show();
                }
            });
        }
    }

    class VHTanggal extends RecyclerView.ViewHolder {

        @BindView(R.id.til_kreditmu_tanggal)
        TextInputLayout tilKreditmuTanggal;
        @NotEmpty
        @BindView(R.id.edt_kreditmu_tanggal)
        EditText edtKreditmuTanggal;

        private MyCustomTextWatcher customTextWatcher;

        VHTanggal(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            Calendar calendar = Calendar.getInstance();
            edtKreditmuTanggal.addTextChangedListener(customTextWatcher);
            edtKreditmuTanggal.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(android.widget.DatePicker arg0, int year, int month, int day) {
                            month++;
                            String monthString = Integer.toString(month);
                            String dayString = Integer.toString(day);
                            String sMonth = month < 10 ? "0" + monthString : "" + monthString;
                            String sDay = day < 10 ? "0" + dayString : "" + dayString;
                            String value = year + "-" + sMonth + "-" + sDay;
                            map.put(kreditmuList.get(getAdapterPosition()).getName(), value);
                            edtKreditmuTanggal.setText(value);
                        }
                    };
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.YEAR, -17);
                    Date birthdayMaxDate = cal.getTime();

                    DatePickerDialog cdpBirdhDay = customDatePicker(myDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), false);
                    cdpBirdhDay.getDatePicker().setMaxDate(birthdayMaxDate.getTime());
                    cdpBirdhDay.show();
                }
            });
        }
    }

    public class VHKota extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kota)
        NiceAutoCompleteTextView actKreditmuKota;
        @BindView(R.id.pb_kreditmu_kota)
        ProgressBar pbKreditmuKota;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;
        private int kecamatanGroup;
        private String cityName;

        public VHKota(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKota);

            if (dataKreditmu.getCityList() != null) {
                adapterCity = new ArrayAdapter<KreditmuCity>(context, android.R.layout.simple_dropdown_item_1line, dataKreditmu.getCityList());
                actKreditmuKota.setAdapter(adapterCity);
            }
            actKreditmuKota.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterCity.getItem(position).getResidanceCity() != null)
                        kotaNameDom = adapterCity.getItem(position).getResidanceCity();

                    kecamatanGroup = 1;
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kotaNameDom);
                    if (vhKec != null && vhKel != null && vhBranch != null && vhZip != null) {
                        vhKec.pbKreditmuKecamatan.setVisibility(View.VISIBLE);
                        vhKec.actKreditmuKecamatan.setText("");
                        vhKel.actKreditmuKelurahan.setText("");
                        vhBranch.edtKreditmuTextBranch.setText("");
                        vhZip.edtKreditmuTextZip.setText("");
                    }
                    map.put("Kecamatan domisili", "");
                    map.put("Kelurahan domisili", "");
                    map.put("Kode pos domisili", "");
                    map.put("Cabang terdaftar", "");
                    fragment.getKecamatan(kecamatanGroup, kotaNameDom);
                }
            });
        }
    }

    public class VHKotaKtp extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kota_ktp)
        NiceAutoCompleteTextView actKreditmuKotaKtp;
        @BindView(R.id.pb_kreditmu_kota_ktp)
        ProgressBar pbKreditmuKotaKtp;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;
        private int kecamatanGroup;
        private String cityName;

        public VHKotaKtp(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKotaKtp);

            if (dataKreditmu.getCityList() != null) {
                adapterCityKtp = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, dataKreditmu.getCityList());
                actKreditmuKotaKtp.setAdapter(adapterCity);
            }
            actKreditmuKotaKtp.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKotaKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterCityKtp.getItem(position).getResidanceCity() != null)
                        kotaNamektp = adapterCity.getItem(position).getResidanceCity();

                    kecamatanGroup = 2;
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kotaNamektp);
                    if (vhKecKtp != null && vhKelKtp != null) {
                        vhKecKtp.pbKreditmuKecamatanKtp.setVisibility(View.VISIBLE);
                        vhKecKtp.actKreditmuKecamatanKtp.setText("");
                        vhKelKtp.actKreditmuKelurahanKtp.setText("");
                    }
                    map.put("Kecamatan ktp", "");
                    map.put("Kelurahan ktp", "");
                    fragment.getKecamatan(kecamatanGroup, kotaNamektp);
                }
            });
        }
    }

    public class VHKotaKerja extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kota_kerja)
        NiceAutoCompleteTextView actKreditmuKotaKerja;
        @BindView(R.id.pb_kreditmu_kota_kerja)
        ProgressBar pbKreditmuKotaKerja;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;
        private int kecamatanGroup;
        private String cityName;

        public VHKotaKerja(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKotaKerja);

            if (dataKreditmu.getCityList() != null) {
                adapterCityKerja = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, dataKreditmu.getCityList());
                actKreditmuKotaKerja.setAdapter(adapterCity);
            }
            actKreditmuKotaKerja.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKotaKerja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterCityKtp.getItem(position).getResidanceCity() != null)
                        kotaNameKerja = adapterCity.getItem(position).getResidanceCity();

                    kecamatanGroup = 3;
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kotaNameKerja);
                    if (vhKecKer != null && vhKelKer != null) {
                        vhKecKer.pbKreditmuKecamatanKerja.setVisibility(View.VISIBLE);
                        vhKecKer.actKreditmuKecamatanKerja.setText("");
                        vhKelKer.actKreditmuKelurahanKerja.setText("");
                    }
                    map.put("Kecamatan pekerjaan", "");
                    map.put("Kelurahan pekerjaan", "");
                    fragment.getKecamatan(kecamatanGroup, kotaNameKerja);
                }
            });
        }
    }

    public class VHKecamatan extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kecamtan)
        NiceAutoCompleteTextView actKreditmuKecamatan;
        @BindView(R.id.pb_kreditmu_kecamatan)
        ProgressBar pbKreditmuKecamatan;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;


        VHKecamatan(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKecamatan);
            actKreditmuKecamatan.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKecamatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterKecamatanDomisili.getItem(position).getNamaKecamatan() != null)
                        kecNameDom = adapterKecamatanDomisili.getItem(position).getNamaKecamatan();

                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kecNameDom);
                    vhKel.pbKreditmuKelurahan.setVisibility(View.VISIBLE);
                    if (vhKel != null && vhBranch != null && vhZip != null) {
                        vhKel.actKreditmuKelurahan.setText("");
                        vhBranch.edtKreditmuTextBranch.setText("");
                        vhZip.edtKreditmuTextZip.setText("");
                    }
                    map.put("Kelurahan domisili", "");
                    map.put("Kode pos domisili", "");
                    map.put("Cabang terdaftar", "");
                    fragment.getKelurahan(kreditmuList.get(getAdapterPosition()).getGroup(), kecNameDom, kotaNameDom);
                }
            });

        }
    }

    class VHKelurahan extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kelurahan)
        NiceAutoCompleteTextView actKreditmuKelurahan;
        @BindView(R.id.pb_kreditmu_kelurahan)
        ProgressBar pbKreditmuKelurahan;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        public VHKelurahan(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKelurahan);
            actKreditmuKelurahan.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKelurahan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterKelurahanDomisili.getItem(position).getNamaKelurahan() != null)
                        kelNameDom = adapterKelurahanDomisili.getItem(position).getNamaKelurahan();

                    if (vhBranch != null && vhZip != null) {
                        vhBranch.edtKreditmuTextBranch.setText("");
                        vhZip.edtKreditmuTextZip.setText("");
                    }
                    map.put("Kode pos domisili", "");
                    map.put("Cabang terdaftar", "");
                    vhBranch.pbKreditmuBranch.setVisibility(View.VISIBLE);
                    vhZip.pbKreditmuZip.setVisibility(View.VISIBLE);
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kelNameDom);
                    fragment.getZipCode(kotaNameDom, kecNameDom, kelNameDom, 1);
                }
            });
        }
    }

    public class VHKecamatanKtp extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kecamtan_ktp)
        NiceAutoCompleteTextView actKreditmuKecamatanKtp;
        @BindView(R.id.pb_kreditmu_kecamatan_ktp)
        ProgressBar pbKreditmuKecamatanKtp;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        VHKecamatanKtp(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKecamatanKtp);
            actKreditmuKecamatanKtp.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKecamatanKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterKecamatanKtp.getItem(position).getNamaKecamatan() != null)
                        kecNameKtp = adapterKecamatanKtp.getItem(position).getNamaKecamatan();

                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kecNameKtp);
                    vhKelKtp.pbKreditmuKelurahanKtp.setVisibility(View.VISIBLE);
                    if (vhKelKtp != null)
                        vhKelKtp.actKreditmuKelurahanKtp.setText("");
                    map.put("Kelurahan ktp", "");
                    fragment.getKelurahan(kreditmuList.get(getAdapterPosition()).getGroup(), kecNameKtp, kotaNamektp);
                }
            });

        }
    }

    class VHKelurahanKtp extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kelurahan_ktp)
        NiceAutoCompleteTextView actKreditmuKelurahanKtp;
        @BindView(R.id.pb_kreditmu_kelurahan_ktp)
        ProgressBar pbKreditmuKelurahanKtp;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        public VHKelurahanKtp(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKelurahanKtp);
            actKreditmuKelurahanKtp.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKelurahanKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterKelurahanKtp.getItem(position).getNamaKelurahan() != null)
                        kelNameKtp = adapterKelurahanKtp.getItem(position).getNamaKelurahan();
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kelNameKtp);
                    fragment.getZipCode(kotaNamektp, kecNameKtp, kelNameKtp, 2);
                    vhZipKtp.pbKreditmuZipKtp.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public class VHKecamatanKerja extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kecamatan_kerja)
        NiceAutoCompleteTextView actKreditmuKecamatanKerja;
        @BindView(R.id.pb_kreditmu_kecamatan_kerja)
        ProgressBar pbKreditmuKecamatanKerja;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        VHKecamatanKerja(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKecamatanKerja);
            actKreditmuKecamatanKerja.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKecamatanKerja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (adapterKecamatanPekerjaan.getItem(position).getNamaKecamatan() != null)
                        kecNameKerja = adapterKecamatanPekerjaan.getItem(position).getNamaKecamatan();

                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kecNameKerja);
                    vhKelKer.pbKreditmuKelurahanKerja.setVisibility(View.VISIBLE);
                    if (vhKelKer != null)
                        vhKelKer.actKreditmuKelurahanKerja.setText("");
                    map.put("Kelurahan pekerjaan", "");
                    fragment.getKelurahan(kreditmuList.get(getAdapterPosition()).getGroup(), kecNameKerja, kotaNameKerja);
                }
            });

        }
    }

    class VHKelurahanKerja extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_kelurahan_kerja)
        NiceAutoCompleteTextView actKreditmuKelurahanKerja;
        @BindView(R.id.pb_kreditmu_kelurahan_kerja)
        ProgressBar pbKreditmuKelurahanKerja;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        public VHKelurahanKerja(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuKelurahanKerja);
            actKreditmuKelurahanKerja.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuKelurahanKerja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterKelurahanPekerjaan.getItem(position).getNamaKelurahan() != null)
                        kelNameKerja = adapterKelurahanPekerjaan.getItem(position).getNamaKelurahan();
                    map.put(kreditmuList.get(getAdapterPosition()).getName(), kelNameKerja);
                    fragment.getZipCode(kotaNameKerja, kecNameKerja, kelNameKerja, 3);
                    vhZipKerja.pbKreditmuZipKerja.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    class VHProfesi extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_profesi)
        NiceAutoCompleteTextView actKreditmuProfesi;
        @BindView(R.id.pb_kreditmu_profesi)
        ProgressBar pbKreditmuProfesi;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;
        private String profname;

        VHProfesi(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuProfesi);

            if (dataKreditmu.getProfessionList() != null) {
                adapterProfession = new ArrayAdapter<KreditmuProfession>(context, android.R.layout.simple_dropdown_item_1line, dataKreditmu.getProfessionList());
                actKreditmuProfesi.setAdapter(adapterProfession);
            }
            actKreditmuProfesi.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuProfesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterProfession.getItem(position).getProfessionId() != null && adapterProfession.getItem(position).getProfessionName() != null) {
                        profesiId = adapterProfession.getItem(position).getProfessionId();
                        profname = adapterProfession.getItem(position).getProfessionName();
                    }

                    map.put(kreditmuList.get(getAdapterPosition()).getName(), profname);
                    mapJob.put(kreditmuList.get(getAdapterPosition()).getName(), profesiId);
                    vhJobType.pbKreditmuType.setVisibility(View.VISIBLE);
                    vhJobType.actKreditmuType.setText("");
                    vhJobPos.actKreditmuPosition.setText("");
                    map.put("Type pekerjaan", "");
                    map.put("Posisi pekerjaan", "");
                    fragment.getJobType(profesiId);
                }
            });
        }
    }

    class VHJobType extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_job_type)
        NiceAutoCompleteTextView actKreditmuType;
        @BindView(R.id.pb_kreditmu_job_type)
        ProgressBar pbKreditmuType;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;
        private String jobTypename;

        VHJobType(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuType);

            actKreditmuType.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterJobType.getItem(position).getJobTypeId() != null && adapterJobType.getItem(position).getJobTypeName() != null) {
                        jobTypeId = adapterJobType.getItem(position).getJobTypeId();
                        jobTypename = adapterJobType.getItem(position).getJobTypeName();
                    }

                    map.put(kreditmuList.get(getAdapterPosition()).getName(), jobTypename);
                    mapJob.put(kreditmuList.get(getAdapterPosition()).getName(), jobTypeId);
                    vhJobPos.pbKreditmuPosition.setVisibility(View.VISIBLE);
                    vhJobPos.actKreditmuPosition.setText("");
                    map.put("Posisi pekerjaan", "");
                    fragment.getJobPos(profesiId, jobTypeId);
                }
            });
        }
    }

    class VHPosition extends RecyclerView.ViewHolder {

        @BindView(R.id.act_kreditmu_job_position)
        NiceAutoCompleteTextView actKreditmuPosition;
        @BindView(R.id.pb_kreditmu_job_position)
        ProgressBar pbKreditmuPosition;

        private MyCustomTextWatcher customTextWatcher;
        private CustomOnFocusListener customOnFocusListener;

        VHPosition(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            customOnFocusListener = new CustomOnFocusListener();
            customOnFocusListener.actValue(actKreditmuPosition);
            actKreditmuPosition.setOnFocusChangeListener(customOnFocusListener);
            actKreditmuPosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (adapterJobPosition.getItem(position).getJobPositionName() != null && adapterJobPosition.getItem(position).getJobPositionId() != null) {
                        map.put(kreditmuList.get(getAdapterPosition()).getName(), adapterJobPosition.getItem(position).getJobPositionName());
                        mapJob.put(kreditmuList.get(getAdapterPosition()).getName(), adapterJobPosition.getItem(position).getJobPositionId());
                    }
                }
            });
        }
    }

    class VHButton extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_kreditmu_submit)
        Button btnKreditmuSubmit;


        VHButton(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class VHzipcode extends RecyclerView.ViewHolder {

        @BindView(R.id.edt_kreditmu_text_zipcode)
        EditText edtKreditmuTextZip;
        @BindView(R.id.til_kreditmu_text_zipcode)
        TextInputLayout tilKreditmuTextZip;
        @BindView(R.id.pb_kreditmu_zip)
        ProgressBar pbKreditmuZip;

        private MyCustomTextWatcher customTextWatcher;

        VHzipcode(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            edtKreditmuTextZip.addTextChangedListener(customTextWatcher);
        }
    }

    class VHzipcodeKtp extends RecyclerView.ViewHolder {

        @BindView(R.id.edt_kreditmu_zip_ktp)
        EditText edtKreditmuZipKtp;
        @BindView(R.id.til_kreditmu_zip_ktp)
        TextInputLayout tilKreditmuZipKtp;
        @BindView(R.id.pb_kreditmu_zip_ktp)
        ProgressBar pbKreditmuZipKtp;

        private MyCustomTextWatcher customTextWatcher;

        VHzipcodeKtp(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            edtKreditmuZipKtp.addTextChangedListener(customTextWatcher);
        }
    }

    class VHzipcodeKerja extends RecyclerView.ViewHolder {

        @BindView(R.id.edt_kreditmu_zip_kerja)
        EditText edtKreditmuZipKerja;
        @BindView(R.id.til_kreditmu_zipcode_kerja)
        TextInputLayout tilKreditmuZipcodeKerja;
        @BindView(R.id.pb_kreditmu_zip_kerja)
        ProgressBar pbKreditmuZipKerja;

        private MyCustomTextWatcher customTextWatcher;

        VHzipcodeKerja(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            edtKreditmuZipKerja.addTextChangedListener(customTextWatcher);
        }
    }

    class VHBranch extends RecyclerView.ViewHolder {

        @BindView(R.id.edt_kreditmu_text_branch)
        EditText edtKreditmuTextBranch;
        @BindView(R.id.til_kreditmu_text_branch)
        TextInputLayout tilKreditmuTextBranch;
        @BindView(R.id.pb_kreditmu_branch)
        ProgressBar pbKreditmuBranch;

        private MyCustomTextWatcher customTextWatcher;

        VHBranch(View itemView, MyCustomTextWatcher customTextWatcher) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.customTextWatcher = customTextWatcher;

            edtKreditmuTextBranch.addTextChangedListener(customTextWatcher);
        }
    }

    class VHAttachmet extends RecyclerView.ViewHolder {

        @BindView(R.id.img_take_picture_kreditmu_1)
        ImageView imgTake1;
        @BindView(R.id.img_delete_picture_kreditmu_1)
        ImageView imgDelete1;
        @BindView(R.id.img_take_picture_kreditmu_2)
        ImageView imgTake2;
        @BindView(R.id.img_delete_picture_kreditmu_2)
        ImageView imgDelete2;
        @BindView(R.id.img_take_picture_kreditmu_3)
        ImageView imgTake3;
        @BindView(R.id.img_delete_picture_kreditmu_3)
        ImageView imgDelete3;
        @BindView(R.id.img_take_picture_kreditmu_4)
        ImageView imgTake4;
        @BindView(R.id.img_delete_picture_kreditmu_4)
        ImageView imgDelete4;
        @BindView(R.id.tv_error_attachment_kreditmu)
        TextView tvErrorAttachment;


        VHAttachmet(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.img_take_picture_kreditmu_1, R.id.img_take_picture_kreditmu_2, R.id.img_take_picture_kreditmu_3, R.id.img_take_picture_kreditmu_4})
        public void ShowCamera(View view) {
            switch (view.getId()) {
                case R.id.img_take_picture_kreditmu_1:
                    fragment.permissionCamera(CAMERA_1);
                    break;
                case R.id.img_take_picture_kreditmu_2:
                    fragment.permissionCamera(CAMERA_2);
                    break;
                case R.id.img_take_picture_kreditmu_3:
                    fragment.permissionCamera(CAMERA_3);
                    break;
                case R.id.img_take_picture_kreditmu_4:
                    fragment.permissionCamera(CAMERA_4);
                    break;
            }
        }

        @OnClick({R.id.img_delete_picture_kreditmu_1, R.id.img_delete_picture_kreditmu_2, R.id.img_delete_picture_kreditmu_3, R.id.img_delete_picture_kreditmu_4})
        public void DeleteAttachment(View view) {
            switch (view.getId()) {
                case R.id.img_delete_picture_kreditmu_1:
                    imgTake1.setImageResource(0);
                    imgDelete1.setVisibility(View.GONE);
                    mapAttachment.remove(0);
                    break;
                case R.id.img_delete_picture_kreditmu_2:
                    imgTake2.setImageResource(0);
                    imgDelete2.setVisibility(View.GONE);
                    mapAttachment.remove(1);
                    break;
                case R.id.img_delete_picture_kreditmu_3:
                    imgTake3.setImageResource(0);
                    imgDelete3.setVisibility(View.GONE);
                    mapAttachment.remove(2);
                    break;
                case R.id.img_delete_picture_kreditmu_4:
                    imgTake4.setImageResource(0);
                    imgDelete4.setVisibility(View.GONE);
                    mapAttachment.remove(3);
                    break;
            }
        }

    }

    class VHSignature extends RecyclerView.ViewHolder {

        @BindView(R.id.img_ttd_pemohon_persetujuan_kerditmu)
        ImageView imgTtdPemohon;
        @BindView(R.id.txt_date_pemohon_persetujuan_kreditmu)
        TextView txtDatePemohon;
        @BindView(R.id.tv_error_signature_kreditmu)
        TextView tvErrorSignature;


        VHSignature(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            txtDatePemohon.setText(Util.listPengajuanTimeFormat(new DateTime()));

            imgTtdPemohon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isUserValid) {
                        if (BuildConfig.DEBUG)
                            Log.e("isuserValid", String.valueOf(isUserValid));
                        if (map.get("Nomor handphone") != null && !"".equalsIgnoreCase(map.get("Nomor handphone"))) {
                            fragment.SendCode(map.get("Nomor handphone"));
                        } else {
                            Toast.makeText(context, "Harap Isi Nomer Handphone", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (BuildConfig.DEBUG)
                            Log.e("isuserValid", String.valueOf(isUserValid));
                        fragment.ShowSignature();
                    }
                }
            });
        }

    }


    // class custom

    private class MyCustomTextWatcher implements TextWatcher {

        private String position;

        void updatePosition(String position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            map.put(position, s.toString());
            if (BuildConfig.DEBUG)
                Log.w("Text saved", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class CustomOnFocusListener implements View.OnFocusChangeListener {

        private NiceAutoCompleteTextView autoCompleteTextView;

        void actValue(NiceAutoCompleteTextView autoCompleteTextView) {
            this.autoCompleteTextView = autoCompleteTextView;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            autoCompleteTextView.showDropDown();
        }
    }

    public void addKecamatan(List<KreditmuKecamatan> kecamatanList, int kecamatan) {
        this.kecamatanList = kecamatanList;
        if (kecamatan == 1) {
            adapterKecamatanDomisili = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kecamatanList);
            vhKec.actKreditmuKecamatan.setAdapter(adapterKecamatanDomisili);
            vhKec.pbKreditmuKecamatan.setVisibility(View.GONE);
        } else if (kecamatan == 2) {
            adapterKecamatanKtp = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kecamatanList);
            vhKecKtp.actKreditmuKecamatanKtp.setAdapter(adapterKecamatanKtp);
            vhKecKtp.pbKreditmuKecamatanKtp.setVisibility(View.GONE);
        } else {
            adapterKecamatanPekerjaan = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kecamatanList);
            vhKecKer.actKreditmuKecamatanKerja.setAdapter(adapterKecamatanPekerjaan);
            vhKecKer.pbKreditmuKecamatanKerja.setVisibility(View.GONE);
        }
    }

    public void addKelurahan(List<KreditmuKelurahan> kelurahanList, int kelurahan) {
        this.kelurahanList = kelurahanList;
        if (kelurahan == 1) {
            adapterKelurahanDomisili = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kelurahanList);
            vhKel.actKreditmuKelurahan.setAdapter(adapterKelurahanDomisili);
            vhKel.pbKreditmuKelurahan.setVisibility(View.GONE);
        } else if (kelurahan == 2) {
            adapterKelurahanKtp = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kelurahanList);
            vhKelKtp.actKreditmuKelurahanKtp.setAdapter(adapterKelurahanKtp);
            vhKelKtp.pbKreditmuKelurahanKtp.setVisibility(View.GONE);
        } else {
            adapterKelurahanPekerjaan = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kelurahanList);
            vhKelKer.actKreditmuKelurahanKerja.setAdapter(adapterKelurahanPekerjaan);
            vhKelKer.pbKreditmuKelurahanKerja.setVisibility(View.GONE);
        }
    }

    public void addZipCode(String branchName, String zipcode, int group) {
        if (group == 1) {
            vhZip.edtKreditmuTextZip.setText(zipcode);
            vhBranch.edtKreditmuTextBranch.setText(branchName);
            vhZip.pbKreditmuZip.setVisibility(View.GONE);
            vhBranch.pbKreditmuBranch.setVisibility(View.GONE);
        } else if (group == 2) {
            vhZipKtp.edtKreditmuZipKtp.setText(zipcode);
            vhZipKtp.pbKreditmuZipKtp.setVisibility(View.GONE);
        } else {
            vhZipKerja.edtKreditmuZipKerja.setText(zipcode);
            vhZipKerja.pbKreditmuZipKerja.setVisibility(View.GONE);
        }

    }

    public void addJobType(List<KreditmuJobType> kreditmuJobTypeList) {
        this.kreditmuJobTypeList = kreditmuJobTypeList;
        adapterJobType = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kreditmuJobTypeList);
        vhJobType.actKreditmuType.setAdapter(adapterJobType);
        vhJobType.pbKreditmuType.setVisibility(View.GONE);
    }

    public void addJobPosition(List<KreditmuJobPosition> kreditmuJobPositionList) {
        adapterJobPosition = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, kreditmuJobPositionList);
        vhJobPos.actKreditmuPosition.setAdapter(adapterJobPosition);
        vhJobPos.pbKreditmuPosition.setVisibility(View.GONE);
    }

    private void showKtp() {
        for (int i = 0; i < kreditmus.size(); i++) {
            if (kreditmus.get(i).getGroup() == 2) {
                if (!"header".equalsIgnoreCase(kreditmus.get(i).getType()) && !"checkbox".equalsIgnoreCase(kreditmus.get(i).getType())) {
                    kreditmuList.add(i, kreditmus.get(i));
                    notifyItemRangeInserted(i, kreditmuList.size());
                    notifyDataSetChanged();
                }
            }
        }
    }

    private void hideKtp() {
        for (int i = kreditmuList.size() - 1; i > 0; i--) {
            if (kreditmuList.get(i).getGroup() == 2) {
                if (!"header".equalsIgnoreCase(kreditmuList.get(i).getType()) &&
                        !"checkbox".equalsIgnoreCase(kreditmuList.get(i).getType())) {
                    kreditmuList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i, kreditmuList.size());
                }
            }
        }
    }

    private void validation() {
        List<String> pribadiList = new ArrayList<>();
        List<String> ktpList = new ArrayList<>();
        List<String> kerjaList = new ArrayList<>();
        List<List> bigList = new ArrayList<>();
        Boolean isAttachmentexist;
        Boolean isDataExist = true;
        Boolean isSignatureExist = true;

        int countComa = 0;

        for (int i = 0; i < kreditmuList.size(); i++) {
            if (!"header".equalsIgnoreCase(kreditmuList.get(i).getType()) && !"checkbox".equalsIgnoreCase(kreditmuList.get(i).getType())) {
                if (map.get(kreditmuList.get(i).getName()) != null) {
                    if ("".equalsIgnoreCase(map.get(kreditmuList.get(i).getName()))) {
                        if (kreditmuList.get(i).getGroup() == 1) {
                            pribadiList.add(kreditmuList.get(i).getName());
                        } else if (kreditmuList.get(i).getGroup() == 2) {
                            if ("false".equalsIgnoreCase(map.get("Sesuai Alamat Domisili")))
                                ktpList.add(kreditmuList.get(i).getName());
                        } else if (kreditmuList.get(i).getGroup() == 3) {
                            kerjaList.add(kreditmuList.get(i).getName());
                        }
                    }
                } else {
                    if (kreditmuList.get(i).getGroup() == 1) {
                        if ("Nomer telfon rumah".equalsIgnoreCase(kreditmuList.get(i).getName())) {
                            if ("".equalsIgnoreCase(map.get("area")) || "".equalsIgnoreCase(map.get("phone")))
                                pribadiList.add(kreditmuList.get(i).getName());
                        } else {
                            pribadiList.add(kreditmuList.get(i).getName());
                        }
                    } else if (kreditmuList.get(i).getGroup() == 2) {
                        ktpList.add(kreditmuList.get(i).getName());
                    } else if (kreditmuList.get(i).getGroup() == 3) {
                        if (!"Submit".equalsIgnoreCase(kreditmuList.get(i).getName()))
                            kerjaList.add(kreditmuList.get(i).getName());
                    }
                }
            }
        }

        //validate ATTACHMENT
        vhAttachment.tvErrorAttachment.setVisibility(View.GONE);
        if (mapAttachment != null && !mapAttachment.isEmpty()) {
            if (mapAttachment.containsKey(0) && mapAttachment.containsKey(1)) {
                isAttachmentexist = true;
            } else {
                isAttachmentexist = false;
                vhAttachment.tvErrorAttachment.setVisibility(View.VISIBLE);
                if (BuildConfig.DEBUG)
                    Log.e("validation photo failed", "containskey");
            }

        } else {
            isAttachmentexist = false;
            vhAttachment.tvErrorAttachment.setVisibility(View.VISIBLE);
            if (BuildConfig.DEBUG)
                Log.e("validation photo failed", "null or empty");
        }

        // validation signature
        vhSignature.tvErrorSignature.setVisibility(View.GONE);

        if (mapSignature != null && mapSignature.isEmpty()) {
            isSignatureExist = false;
            vhSignature.tvErrorSignature.setVisibility(View.VISIBLE);
        } else {
            isSignatureExist = true;
        }

        bigList.add(pribadiList);
        bigList.add(ktpList);
        bigList.add(kerjaList);

        for (int i = 0; i < bigList.size(); i++) {
            if (!bigList.get(i).isEmpty()) {
                String a = validationList.get(i);
                countComa += 1;
                if (countComa > 1) {
                    message = message + ", " + a;
                } else {
                    message = a;
                }
                isDataExist = false;
            }
        }

        if (BuildConfig.DEBUG) {
            Log.e("isDatabaseExist", String.valueOf(isDataExist));
            Log.e("isAttachmentExist", String.valueOf(isAttachmentexist));
            Log.e("isSignatureExist", String.valueOf(isSignatureExist));
        }

        if (!isDataExist || !isAttachmentexist || !isSignatureExist) {
            isSuccessValidate = false;
        } else {
            isSuccessValidate = true;
        }

    }

    private void ktpValidation(String ktp) {
        if (ktp != null) {
            if (ktp.length() < 16) {
                message = "Nomer ktp harus 16 Digit";
                isKtpCorrect = false;
            } else {
                isKtpCorrect = true;
            }
        } else {
            message = "Nomer ktp harus diisi";
            isKtpCorrect = false;
        }
    }

    private void noHandphoneValidation(String handphone) {
        if (handphone != null) {
            if (handphone.length() < 11) {
                message = "Nomer handphone kurang dari 11 digit";
                isHandphoneCorrect = false;
            } else {
                isHandphoneCorrect = true;
            }
        } else {
            isHandphoneCorrect = false;
            message = "Nomer handphone harus diisi";
        }

    }

    private void emailValidation(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email != null) {

            if (!email.matches(emailPattern)) {
                isEmailCorrect = false;
                message = "Format email tidak sesuai";
            } else {
                isEmailCorrect = true;
            }
        } else {
            isEmailCorrect = false;
            message = "Format email tidak sesuai";
        }
    }

    private void showHideContent(int group) {
        if (group == 1) {
            if (isHidePersonal) {
                showContent(group);
            } else {
                hideContent(group);
            }
        } else if (group == 2) {
            if (isHideKtp) {
                showContent(group);
            } else {
                hideContent(group);
            }
        } else {
            if (isHidePekerjaan) {
                showContent(group);
            } else {
                hideContent(group);
            }
        }
    }

    private void showContent(int group) {

        if (check && group == 2) {
            for (int i = 0; i < kreditmus.size(); i++) {
                if (kreditmus.get(i).getGroup() == group && "checkbox".equalsIgnoreCase(kreditmus.get(i).getType())) {
                    kreditmuList.add(i, kreditmus.get(i));
                    notifyItemRangeInserted(i, kreditmuList.size());
                    notifyDataSetChanged();
                }
            }
            countCollapse -= 1;
            isHideKtp = false;
        } else {
            for (int i = 0; i < kreditmus.size(); i++) {
                if (kreditmus.get(i).getGroup() == group && !"header".equalsIgnoreCase(kreditmus.get(i).getType())) {
                    kreditmuList.add(i, kreditmus.get(i));
                    notifyItemRangeInserted(i, kreditmuList.size());
                    notifyDataSetChanged();
                }
            }
            countCollapse -= 1;
            if (group == 1) {
                isHidePersonal = false;
            } else if (group == 2) {
                isHideKtp = false;
            } else {
                isHidePekerjaan = false;
            }
        }
    }

    private void hideContent(int group) {

        if (countCollapse < 2) {
            if (check && group == 2) {
                for (int i = 0; i < kreditmuList.size(); i++) {
                    if (kreditmuList.get(i).getGroup() == group && "checkbox".equalsIgnoreCase(kreditmuList.get(i).getType())) {
                        kreditmuList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeRemoved(i, kreditmuList.size());
                    }
                }
            } else {
                for (int i = kreditmuList.size() - 1; i > 0; i--) {
                    if (kreditmuList.get(i).getGroup() == group && !"header".equalsIgnoreCase(kreditmuList.get(i).getType())) {
                        kreditmuList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeRemoved(i, kreditmuList.size());
                    }
                }
            }
            countCollapse += 1;
            if (group == 1) {
                isHidePersonal = true;
            } else if (group == 2) {
                isHideKtp = true;
            } else {
                isHidePekerjaan = true;
            }
        }
    }

    public void SetIsUserValid(boolean b) {
        isUserValid = b;
    }

    public void SetSignature(Bitmap bitmap) {
        vhSignature.imgTtdPemohon.setImageBitmap(bitmap);
        mapSignature.put(0, new File(Util.bitmapToFile(bitmap)));
    }

    public void InsertToImageBox(String path, int requestcode) {
        File fileUri = new File(path);

        switch (requestcode) {
            case CAMERA_1:
                Glide.with(context).load(fileUri).centerCrop().into(vhAttachment.imgTake1);
                vhAttachment.imgDelete1.setVisibility(View.VISIBLE);
                mapAttachment.put(0, fileUri);
                break;
            case CAMERA_2:
                Glide.with(context).load(fileUri).centerCrop().into(vhAttachment.imgTake2);
                vhAttachment.imgDelete2.setVisibility(View.VISIBLE);
                mapAttachment.put(1, fileUri);
                break;
            case CAMERA_3:
                Glide.with(context).load(fileUri).centerCrop().into(vhAttachment.imgTake3);
                vhAttachment.imgDelete3.setVisibility(View.VISIBLE);
                mapAttachment.put(2, fileUri);
                break;
            case CAMERA_4:
                Glide.with(context).load(fileUri).centerCrop().into(vhAttachment.imgTake4);
                vhAttachment.imgDelete4.setVisibility(View.VISIBLE);
                mapAttachment.put(3, fileUri);
                break;
        }
    }

    private void imageAddToList() {
        AttachmentObjt objt = new AttachmentObjt();
        attachmentList = new ArrayList<>();
        signatureList = new ArrayList<>();
        if (BuildConfig.DEBUG)
            Log.e("Attachment amount", String.valueOf(mapAttachment.size()));

        Iterator iterator = mapAttachment.keySet().iterator();
        while (iterator.hasNext()) {
            int i = (int) iterator.next();
            AttachmentObjt attachmentObjt = new AttachmentObjt();
            attachmentObjt.setId(i);
            attachmentObjt.setPath1(mapAttachment.get(i).getAbsolutePath());
            attachmentObjt.setPath2(mapAttachment.get(i).getPath());
            attachmentList.add(attachmentObjt);
        }

        objt.setId(0);
        objt.setPath1(mapSignature.get(0).getAbsolutePath());
        objt.setPath2(mapSignature.get(0).getPath());
        signatureList.add(objt);

    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener mDateSetListner,
                                              int mYear, int mMonth, int mDay, boolean isMaxNowDate) {
        return customDatePicker(mDateSetListner, mYear, mMonth, mDay, View.VISIBLE, View.VISIBLE, View.VISIBLE, isMaxNowDate);

    }

    private DatePickerDialog customDatePicker(DatePickerDialog.OnDateSetListener mDateSetListner,
                                              int mYear,
                                              int mMonth,
                                              int mDay,
                                              final int mModeViewYear,
                                              final int mModeViewMonth,
                                              final int mvModeViewDay, boolean isMaxNowDate) {
        DatePickerDialog dpd = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, mDateSetListner,
                mYear, mMonth, mDay) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                int day = getContext().getResources().getIdentifier("android:id/day", null, null);
                if (day != 0) {
                    View dayPicker = findViewById(day);
                    if (dayPicker != null) {
                        //Set Day view visibility Off/Gone
                        dayPicker.setVisibility(mvModeViewDay);
                    }
                }
                int month = getContext().getResources().getIdentifier("android:id/month", null, null);
                if (month != 0) {
                    View dayPicker = findViewById(month);
                    if (dayPicker != null) {
                        //Set Day view visibility Off/Gone
                        dayPicker.setVisibility(mModeViewMonth);
                    }
                }

                int year = getContext().getResources().getIdentifier("android:id/year", null, null);
                if (year != 0) {
                    View dayPicker = findViewById(year);
                    if (dayPicker != null) {
                        //Set Day view visibility Off/Gone
                        dayPicker.setVisibility(mModeViewYear);
                    }
                }
            }
        };

        //Set max year to current year
        if (isMaxNowDate)
            dpd.getDatePicker().setMaxDate(new Date().getTime());
        dpd.setTitle("");

        return dpd;
    }
}


