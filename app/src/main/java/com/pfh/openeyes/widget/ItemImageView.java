package com.pfh.openeyes.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.util.LogUtil;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ItemImageView extends LinearLayout {

    private TextView tv_title;
    private TextView tv_category;
    private TextView tv_duration;
    private ImageView iv_bg;
    private RelativeLayout rl_text;


    private int mHeight = 626;
    private int mWidth = 1080;//要根据屏幕分辨率算出来

    private ValueAnimator brightenAnimator;
    private ValueAnimator darkenAnimtor;
    private ObjectAnimator alphaAnimtor;

    private boolean flag;
    private boolean isMoved;

    private float brightness = 0f;
    private int mLastMotionX;
    private int mLastMotionY;
    private Runnable longClick;

    //移动的阈值
    private static final int TOUCH_SLOP = 1;
    private Runnable cancel;
    private LinearLayout ll_description;


    public ItemImageView(Context context) {
        this(context, null);
    }

    public ItemImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    private void initView(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_item_imageview, null);

        iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        rl_text = (RelativeLayout) view.findViewById(R.id.rl_text);
        ll_description = (LinearLayout) view.findViewById(R.id.ll_description);

        brightenAnimator = ValueAnimator.ofFloat(brightness, 20);
        brightenAnimator.setDuration(500);
        brightenAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        brightenAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                brightness = (float) valueAnimator.getAnimatedValue();
                changeLight(brightness);//变亮
            }
        });

        darkenAnimtor = ValueAnimator.ofFloat(brightness, 0);
        darkenAnimtor.setDuration(500);
//        darkenAnimtor.setInterpolator(new AccelerateDecelerateInterpolator());
        darkenAnimtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                brightness = (float) valueAnimator.getAnimatedValue();
                changeLight(brightness);
            }
        });


        alphaAnimtor = ObjectAnimator.ofFloat(rl_text, "alpha", 1.0f, 0f);
        alphaAnimtor.setDuration(500);

        longClick = new Runnable() {
            @Override
            public void run() {
                flag = true;
                brightenAnimator.start();
                alphaAnimtor.start();
            }
        };

        cancel = new Runnable() {
            @Override
            public void run() {
                flag = false;
                brightenAnimator.cancel();
                darkenAnimtor.start();
                alphaAnimtor.cancel();
                alphaAnimtor.reverse();
            }
        };


//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
//                LogUtil.e("click!");
//                gotoDetail();
//            }
//        });

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.e("action_down");
                        mLastMotionX = x;
                        mLastMotionY = y;
                        postDelayed(longClick,0);
                        isMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.e("action_move");
//                        if (isMoved){
//
//                            break;
//                        }
//                        if (flag){
                            post(cancel);//按下后没有0.1s内滑动，动画已经执行过了，取消longClick动画效果
//                        }

//                        if (Math.abs(mLastMotionX - x) > TOUCH_SLOP || Math.abs(mLastMotionY - y) > TOUCH_SLOP){
                            isMoved = true;
//                            removeCallbacks(longClick);//按下后0.1s内就滑动，取消动画
//                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.e("action_up");
//                        removeCallbacks(longClick);//按下后0.1s内就抬起，取消longClick动画执行
//                        if (flag){
                            post(cancel);//按下后0.1s后才抬起，动画已经执行，要取消动画效果
//                        }
                        if (!isMoved){
                            gotoDetail();
                        }
                        break;

                }
                return false;
            }
        });

        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setCategory(String category) {
        tv_category.setText(category);
    }

    public void setDuration(String duration) {
        tv_duration.setText(duration);
    }

    public ImageView getBg() {
        return iv_bg;
    }

    public void hideDescription(){
        ll_description.setVisibility(GONE);

    }

    //改变图片的亮度方法 0--原样 >0---调亮 <0---调暗
    private void changeLight(float brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, // 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        iv_bg.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    private void gotoDetail(){
        LogUtil.e("goto Detail");
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpceSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpceSize=MeasureSpec.getSize(heightMeasureSpec);
//
//        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
//            setMeasuredDimension(mWidth, mHeight);
//        }else if(widthSpecMode==MeasureSpec.AT_MOST){
//            setMeasuredDimension(mWidth, heightSpceSize);
//        }else if(heightSpecMode==MeasureSpec.AT_MOST){
//            setMeasuredDimension(widthSpceSize, mHeight);
//        }
//
//    }
}
