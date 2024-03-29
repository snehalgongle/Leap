package com.snake.leap;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Mitesh on 2/20/2018.
 */


public class HalfCustomDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int mDividerHeight;
    private int mColor;

    private int mOffsets;
    private int mAdjustedOffsets;
    private boolean mToAdjustOffsets = false;


    public HalfCustomDividerItemDecoration(int dividerHeight, int color) {
        mDividerHeight = dividerHeight;
        mColor = color;
        mOffsets = mDividerHeight;
    }

    public HalfCustomDividerItemDecoration() {
        super();
        mDividerHeight = 1;
        mColor = 0x8a000000;
    }

    public HalfCustomDividerItemDecoration(int dividerHeight, int color, int offsets, boolean adjustOffsets) {
        super();

        if (offsets < dividerHeight) {
            throw new IllegalArgumentException("Offsets can not be less than divider height");
        } else if (adjustOffsets) {
            mToAdjustOffsets = true;
            mAdjustedOffsets = (offsets - dividerHeight) / 2;
        }

        mOffsets = offsets;
        mDividerHeight = dividerHeight;
        mColor = color;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            throw new RuntimeException("LayoutManager not found");
        }
        if (layoutManager.getPosition(view) != 0)
            outRect.top = mOffsets;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        Paint paint = new Paint();
        paint.setColor(mColor);

        int left = parent.getPaddingLeft()+((20*parent.getWidth())/100);
        int right = left + parent.getWidth() - parent.getPaddingRight();


        for (int i = 0; i < parent.getChildCount(); i++) {

            View child = parent.getChildAt(i);

            int top = 0;
            //try to place divider in the middle of the space between elements
            if (!mToAdjustOffsets)
                top = child.getBottom();
            else top = child.getBottom() + mAdjustedOffsets;
            int bottom = top + mDividerHeight;

            c.drawRect(left, top, right, bottom, paint);
        }
    }
}