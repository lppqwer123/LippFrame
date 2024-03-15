package com.first.frame.view.dotview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.first.frame.R;


public class DotView extends LinearLayout implements PagerIndicator {

    public interface OnDotClickHandler {
        void onDotClick(int index);
    }

    private int mLittleDotSize = -2;
    private int mDotSpan = 36;
    private float mDotRadius = 6f;

    private int mCurrent = 0;
    private int mTotal = 0;

//    private int mSelectedColor = 0xFF377BEE;
//    private int mUnSelectedColor = 0xFFC5CEDB;
    private OnDotClickHandler mOnDotClickHandler;

    private int dotSrc;

    public DotView(Context context) {
        super(context);
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER_HORIZONTAL);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, 0);
        if (arr != null) {
            if (arr.hasValue(R.styleable.DotView_dot_radius)) {
                mDotRadius = arr.getDimension(R.styleable.DotView_dot_radius, mDotRadius);
            }

            if (arr.hasValue(R.styleable.DotView_dot_span)) {
                mDotSpan = (int) arr.getDimension(R.styleable.DotView_dot_span, mDotSpan);
            }

            dotSrc = arr.getResourceId(R.styleable.DotView_dot_src,0);
            arr.recycle();
        }

//        mLittleDotSize = (int) (mDotSpan / 2 + mDotRadius * 2);
        mLittleDotSize = (int) (mDotRadius * 2);
    }

    @Override
    public final void setNum(int num) {
        if (num < 0)
            return;

        mTotal = num;

        removeAllViews();
        setOrientation(HORIZONTAL);
        for (int i = 0; i < num; i++) {
            ImageView dot = new ImageView(getContext());
            dot.setImageResource(dotSrc);
            dot.setSelected(i == 0);
            LayoutParams layoutParams = new LayoutParams(mLittleDotSize, (int) mDotRadius * 2, 1);
            layoutParams.leftMargin=mDotSpan;
            dot.setLayoutParams(layoutParams);
            dot.setClickable(true);
            final int index = i;
            dot.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof ImageView && null != mOnDotClickHandler) {
                        mOnDotClickHandler.onDotClick(index);
                    }
                }
            });
            addView(dot);
        }
    }

    @Override
    public int getTotal() {
        return mTotal;
    }

    @Override
    public int getCurrentIndex() {
        return mCurrent;
    }

    public void setOnDotClickHandler(OnDotClickHandler handler) {
        mOnDotClickHandler = handler;
    }

    public final void setSelected(int index) {
        if (index >= getChildCount() || index < 0 || mCurrent == index) {
            return;
        }
        if (mCurrent < getChildCount() && mCurrent >= 0) {
            ((ImageView) getChildAt(mCurrent)).setSelected(false);
        }
        ((ImageView) getChildAt(index)).setSelected(true);
        mCurrent = index;
    }

//    private class LittleDot extends View {
//
//        private int mColor;
//        private Paint mPaint;
//        private int mIndex;
//
//        public LittleDot(Context context, int index) {
//            super(context);
//            mPaint = new Paint();
//            mPaint.setAntiAlias(true);
//            mIndex = index;
//        }
//
//        public int getIndex() {
//            return mIndex;
//        }
//
//        public void setColor(int color) {
//            if (color == mColor)
//                return;
//            mColor = color;
//            invalidate();
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//            super.onDraw(canvas);
//            mPaint.setColor(mColor);
//            canvas.drawCircle(mLittleDotSize / 2, mDotRadius, mDotRadius, mPaint);
//        }
//    }
//
//    public void setSelectedColor(int color) {
//        if (mSelectedColor != color) {
//            mSelectedColor = color;
//            invalidate();
//        }
//    }
//
//    public void setColor(int selectedColor, int unSelectedColor) {
//        if (mSelectedColor != selectedColor || mUnSelectedColor != unSelectedColor) {
//            mSelectedColor = selectedColor;
//            mUnSelectedColor = unSelectedColor;
//            invalidate();
//        }
//    }
//
//    public void setUnSelectedColor(int color) {
//        if (mUnSelectedColor != color) {
//            mSelectedColor = color;
//            invalidate();
//        }
//    }
}
