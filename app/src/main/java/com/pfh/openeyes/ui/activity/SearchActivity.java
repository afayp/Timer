package com.pfh.openeyes.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
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
    private List<TextView> textViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        loadHotKeyword();
    }

    private void loadHotKeyword() {
        textViewList = new ArrayList<>();
        apiStores.loadHotKeyword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("SearchActivity network error:"+e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        Log.e("TAG",strings.toString());

                        for (int i = 0; i < strings.size(); i++) {
                            TextView tv = new TextView(mContext);
                            tv.setText(strings.get(i));
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.keyword_bg));
                            textViewList.add(tv);
                        }
                        initDefalutView();
                    }

                });
    }

    private void initDefalutView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_search_defalut, null);
        FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.fl_content);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < textViewList.size(); i++) {
            flowLayout.addView(textViewList.get(i),lp);
        }
        ll_content.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void handleChangeDateEvent(ChangeDateEvent event){

    }
}
