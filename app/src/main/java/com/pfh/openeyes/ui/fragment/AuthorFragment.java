package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfh.openeyes.R;
import com.pfh.openeyes.ui.base.BaseFragment;

/**
 * Created by Administrator on 2016/9/3.
 */
public class AuthorFragment extends BaseFragment {

    public static AuthorFragment newInstance() {

        Bundle args = new Bundle();

        AuthorFragment fragment = new AuthorFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_author, container, false);
        return view;
    }
}
