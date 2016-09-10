package com.pfh.openeyes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pfh.openeyes.R;

/**
 * Created by Administrator on 2016/9/3.
 */
public class MainActivityIndicator extends LinearLayout {

    private ImageView tab_1_solid;
    private ImageView tab_2_solid;
    private ImageView tab_3_solid;
    private ImageView tab_4_solid;
    private IndicatorListener indicatorListener;

    public MainActivityIndicator(Context context) {
        this(context,null);
    }

    public MainActivityIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MainActivityIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_viewpager_indicator, null);
        tab_1_solid = (ImageView) view.findViewById(R.id.tab_1_solid);
        tab_2_solid = (ImageView) view.findViewById(R.id.tab_2_solid);
        tab_3_solid = (ImageView) view.findViewById(R.id.tab_3_solid);
        tab_4_solid = (ImageView) view.findViewById(R.id.tab_4_solid);
        initAlpha();
        initClickListener();
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }



    private void initClickListener() {
        tab_1_solid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorListener.onClickInidcator(0);
            }
        });
        tab_2_solid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorListener.onClickInidcator(1);
            }
        });
        tab_3_solid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorListener.onClickInidcator(2);
            }
        });
        tab_4_solid.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorListener.onClickInidcator(3);
            }
        });

    }

    private void initAlpha() {
        tab_1_solid.setAlpha(1.0f);
        tab_2_solid.setAlpha(0f);
        tab_3_solid.setAlpha(0f);
        tab_4_solid.setAlpha(0f);
    }

    public void setupIndicator(int position,float offY){
        if (position == 0){//从第一页开始
            tab_1_solid.setAlpha(1-offY);//右滑offY从0.0-->1.0
            tab_2_solid.setAlpha(offY);
        } else if (position == 1){
            tab_2_solid.setAlpha(1-offY);
            tab_3_solid.setAlpha(offY);
        }else if (position == 2){
            tab_3_solid.setAlpha(1-offY);
            tab_4_solid.setAlpha(offY);
        }
    }


    public interface IndicatorListener{
        void onClickInidcator(int position);
    }

    public void setIndicatorListener(IndicatorListener indicatorListener){
        this.indicatorListener = indicatorListener;

    }

}
