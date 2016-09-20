package com.pfh.openeyes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pfh.openeyes.R;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ClickEditText extends LinearLayout {

    private ImageView imageView;
    private EditText editText;
    private boolean isEdit = false;

    public ClickEditText(Context context) {
        this(context,null);
    }

    public ClickEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.widget_click_edittext, null);
        imageView = (ImageView) view.findViewById(R.id.iv);
        editText = (EditText) view.findViewById(R.id.ed);

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    imageView.setBackgroundResource(R.drawable.delete);
                }else {
                    imageView.setBackgroundResource(R.drawable.pencil);
                }

            }
        });

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        addView(view,new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


    }
}
