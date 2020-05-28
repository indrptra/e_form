package com.kreditplus.eform.model.bus;

import com.kreditplus.eform.fragment.KreditmuFragment;

/**
 * Created by apc-lap012 on 06/09/17.
 */

public class ContextKreditmuFragment {

    private KreditmuFragment kreditmuFragment;

    public ContextKreditmuFragment(KreditmuFragment kreditmuFragment) {
        this.kreditmuFragment = kreditmuFragment;
    }

    public KreditmuFragment getKreditmuFragment() {
        return kreditmuFragment;
    }

    public void setKreditmuFragment(KreditmuFragment kreditmuFragment) {
        this.kreditmuFragment = kreditmuFragment;
    }
}
