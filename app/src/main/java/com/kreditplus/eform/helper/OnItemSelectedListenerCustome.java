package com.kreditplus.eform.helper;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by ricky on 25/07/16.
 */
public class OnItemSelectedListenerCustome implements AdapterView.OnItemSelectedListener {

    private View parentView;
    private CustomeOnItemSelected customeOnItemSelected;

    public OnItemSelectedListenerCustome(View parentView, CustomeOnItemSelected customeOnItemSelected) {
        this.parentView = parentView;
        this.customeOnItemSelected = customeOnItemSelected;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (customeOnItemSelected != null) {
            customeOnItemSelected.onItemSelected(parentView, parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface CustomeOnItemSelected {
        public abstract void onItemSelected(View parentView, AdapterView<?> parent, View view, int position, long id);
    }
}
