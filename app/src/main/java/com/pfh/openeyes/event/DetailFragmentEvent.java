package com.pfh.openeyes.event;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DetailFragmentEvent {

    private Fragment fragment;

    public DetailFragmentEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
