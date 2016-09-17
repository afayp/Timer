package com.pfh.openeyes.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Slide());
//            getWindow().setExitTransition(new Slide());
//        }
        setContentView(R.layout.activity_search);
        loadHotKeyword();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        //...

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_search_defalut, null);
        FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.fl_content);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 15;
        lp.rightMargin = 15;
        lp.topMargin = 15;
        lp.bottomMargin = 15;
        for (int i = 0; i < textViewList.size(); i++) {
            flowLayout.addView(textViewList.get(i),lp);
        }
        ll_content.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        startAnim(ll_content);
    }

    private void startAnim(final View view){
        int height = view.getHeight();
        final int top = view.getTop();

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, height);
        valueAnimator.setDuration(4000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int dY = (int) valueAnimator.getAnimatedValue();
                view.setY(top+dY);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.start();

    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void handleChangeDateEvent(ChangeDateEvent event){

    }
}
