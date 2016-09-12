package com.pfh.openeyes.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.event.ChangeDateEvent;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.widget.FlowLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/12.
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initDefalutView();
        loadHotKeyword();
    }

    private void loadHotKeyword() {
        apiStores.loadHotKeyword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        ll_content.addView(flowLayout);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("SearchActivity network error:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.leftMargin = 5;
                        lp.rightMargin = 5;
                        lp.topMargin = 5;
                        lp.bottomMargin = 5;
                        for (int i = 0; i < strings.size(); i++) {
                            TextView view = new TextView(mContext);
                            view.setText(strings.get(i));
                            view.setTextColor(Color.WHITE);
                            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.keyword_bg));
                            flowLayout.addView(view,lp);
                        }
                    }
                });
    }

    private void initDefalutView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_search_defalut, null);
        flowLayout = (FlowLayout) view.findViewById(R.id.fl_content);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void handleChangeDateEvent(ChangeDateEvent event){

    }
}
