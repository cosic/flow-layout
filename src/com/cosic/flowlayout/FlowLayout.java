package com.cosic.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup
{
    // DUMMY is a dummy number, widthMeasureSpec should always be EXACTLY for FlowLayout
    private static final int DUMMY = 100;

    private int mPaddingHorizontal;
    private int mPaddingVertical;

    public FlowLayout(Context context)
    {
        this(context, null, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        TypedArray attributesArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyle, 0);
        mPaddingHorizontal = attributesArray.getDimensionPixelSize(
                R.styleable.FlowLayout_horizontalPadding, 0);
        mPaddingVertical = attributesArray.getDimensionPixelSize(
                R.styleable.FlowLayout_verticalPadding, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        // DUMMY is a dummy number, widthMeasureSpec should always be EXACTLY for FlowLayout
        int myWidth = resolveSize(DUMMY, widthMeasureSpec);
        int wantedHeight = 0;
        for (int i = 0; i < getChildCount(); i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
            {
                continue;
            }
            // let the child measure itself
            child.measure(getChildMeasureSpec(widthMeasureSpec, 0, child.getLayoutParams().width),
                    getChildMeasureSpec(heightMeasureSpec, 0, child.getLayoutParams().height));
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            // lineheight is the height of current line, should be the height of the heightest view
            lineHeight = Math.max(childHeight, lineHeight);
            if (childWidth + childLeft + getPaddingRight() > myWidth)
            {
                // wrap this line
                childLeft = getPaddingLeft();
                childTop += mPaddingVertical + lineHeight;
                lineHeight = 0;
            }
            childLeft += childWidth + mPaddingHorizontal;
        }
        wantedHeight += childTop + lineHeight + getPaddingBottom();
        setMeasuredDimension(myWidth, resolveSize(wantedHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int lineHeight = 0;
        int myWidth = right - left;
        for (int i = 0; i < getChildCount(); i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.GONE)
            {
                continue;
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            lineHeight = Math.max(childHeight, lineHeight);
            if (childWidth + childLeft + getPaddingRight() > myWidth)
            {
                childLeft = getPaddingLeft();
                childTop += mPaddingVertical + lineHeight;
                lineHeight = 0;
            }
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            childLeft += childWidth + mPaddingHorizontal;
        }
    }
}