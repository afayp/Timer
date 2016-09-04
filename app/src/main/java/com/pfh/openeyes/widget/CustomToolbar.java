package com.pfh.openeyes.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;

/**
 * Created by Administrator on 2016/9/3.
 */
public class CustomToolbar extends LinearLayout {

    private Drawable right_drawable;
    private String center_text;
    private String left_text;

    private TextView tv_left;
    private ImageView right_icon;
    private TextView tv_center;


    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyleAttr, 0);
//        left_drawable = typedArray.getDrawable(R.styleable.CustomToolbar_left_text);
        left_text = typedArray.getString(R.styleable.CustomToolbar_left_text);
        center_text = typedArray.getString(R.styleable.CustomToolbar_center_text);
        right_drawable = typedArray.getDrawable(R.styleable.CustomToolbar_right_icon);
        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_custom_toolbar, null);
        tv_left = (TextView) view.findViewById(R.id.left_text);
        tv_center = (TextView) view.findViewById(R.id.center_text);
        right_icon = (ImageView) view.findViewById(R.id.right_icon);

        tv_left.setText(left_text);
        tv_center.setText(center_text);
        right_icon.setImageDrawable(right_drawable);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    /**
     * @param text null或者"" 表示隐藏
     */
    public void setLeftText(String text){
        if (TextUtils.isEmpty(text)){
            tv_left.setVisibility(GONE);
        }else {
            tv_left.setText(text);
        }
    }

    /**
     * @param text null或者"" 表示隐藏
     */
    public void setCenterText(String text){
        if (TextUtils.isEmpty(text)){
            tv_center.setVisibility(GONE);
        }else {
            tv_center.setText(text);
        }
    }

    /**
     * @param resId -1表示隐藏
     */
    public void setRightIcon(@DrawableRes int resId){
        if (resId == -1){
            right_icon.setVisibility(GONE);
        }else {
            right_icon.setBackgroundResource(resId);
        }
    }

    public void setLeftClickListener(final View.OnClickListener clickListener){
        if (clickListener != null){
            tv_left.setOnClickListener(clickListener);
        }
    }

    public void setCenterClickListener(final View.OnClickListener clickListener){
        if (clickListener != null){
            tv_center.setOnClickListener(clickListener);
        }
    }

    public void setRightClickListener(final View.OnClickListener clickListener){
        if (clickListener != null){
            right_icon.setOnClickListener(clickListener);
        }
    }


}
