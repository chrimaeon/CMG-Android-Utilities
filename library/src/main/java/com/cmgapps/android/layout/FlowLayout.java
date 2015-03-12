/*
 * Copyright 2013 Christian Grach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmgapps.android.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.cmgapps.android.R;

/**
 * A layout that aligns as many of its child views in a row/column as possible
 */
public class FlowLayout extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mHorizontalSpacing = 0;
    private int mVerticalSpacing = 0;
    private int mOrientation = 0;

    public FlowLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout);
        try {
            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_horizontalSpacing, 0);
            mVerticalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_verticalSpacing, 0);
            mOrientation = a.getInteger(R.styleable.FlowLayout_orientation, HORIZONTAL);
        } finally {
            a.recycle();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - this.getPaddingRight() - this.getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - this.getPaddingRight() - this.getPaddingLeft();

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int size;
        int mode;

        if (mOrientation == HORIZONTAL) {
            size = sizeWidth;
            mode = modeWidth;
        } else {
            size = sizeHeight;
            mode = modeHeight;
        }

        int lineThicknessWithSpacing = 0;
        int lineThickness = 0;
        int lineLengthWithSpacing = 0;
        int lineLength;

        int prevLinePosition = 0;

        int controlMaxLength = 0;
        int controlMaxThickness = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            child
                    .measure(MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST
                            : modeWidth), MeasureSpec.makeMeasureSpec(sizeHeight,
                            modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight));

            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int hSpacing = this.getHorizontalSpacing(lp);
            int vSpacing = this.getVerticalSpacing(lp);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int childLength;
            int childThickness;
            int spacingLength;
            int spacingThickness;

            if (mOrientation == HORIZONTAL) {
                childLength = childWidth;
                childThickness = childHeight;
                spacingLength = hSpacing;
                spacingThickness = vSpacing;
            } else {
                childLength = childHeight;
                childThickness = childWidth;
                spacingLength = vSpacing;
                spacingThickness = hSpacing;
            }

            lineLength = lineLengthWithSpacing + childLength;
            lineLengthWithSpacing = lineLength + spacingLength;

            boolean newLine = lp.mNewLine || (mode != MeasureSpec.UNSPECIFIED && lineLength > size);
            if (newLine) {
                prevLinePosition = prevLinePosition + lineThicknessWithSpacing;

                lineThickness = childThickness;
                lineLength = childLength;
                lineThicknessWithSpacing = childThickness + spacingThickness;
                lineLengthWithSpacing = lineLength + spacingLength;
            }

            lineThicknessWithSpacing = Math.max(lineThicknessWithSpacing, childThickness + spacingThickness);
            lineThickness = Math.max(lineThickness, childThickness);

            int posX;
            int posY;
            if (mOrientation == HORIZONTAL) {
                posX = getPaddingLeft() + lineLength - childLength;
                posY = getPaddingTop() + prevLinePosition;
            } else {
                posX = getPaddingLeft() + prevLinePosition;
                posY = getPaddingTop() + lineLength - childHeight;
            }
            lp.setPosition(posX, posY);

            controlMaxLength = Math.max(controlMaxLength, lineLength);
            controlMaxThickness = prevLinePosition + lineThickness;
        }

        if (mOrientation == HORIZONTAL) {
            this.setMeasuredDimension(resolveSize(controlMaxLength, widthMeasureSpec),
                    resolveSize(controlMaxThickness, heightMeasureSpec));
        } else {
            this.setMeasuredDimension(resolveSize(controlMaxThickness, widthMeasureSpec),
                    resolveSize(controlMaxLength, heightMeasureSpec));
        }
    }

    private int getVerticalSpacing(LayoutParams lp) {
        if (lp.verticalSpacingSpecified())
            return lp.mVerticalSpacing;

        return this.mVerticalSpacing;
    }

    private int getHorizontalSpacing(LayoutParams lp) {
        if (lp.horizontalSpacingSpecified())
            return lp.mHorizontalSpacing;

        return this.mHorizontalSpacing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.mX, lp.mY, lp.mX + child.getMeasuredWidth(), lp.mY + child.getMeasuredHeight());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /**
     * <p>
     * LayoutParams for the {@link FlowLayout} with special values for
     * horizontal/vertical spacing.
     * </p>
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        private static final int NO_SPACING = -1;

        private int mX;
        private int mY;
        private int mHorizontalSpacing = NO_SPACING;
        private int mVerticalSpacing = NO_SPACING;
        private boolean mNewLine = false;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.readStyleParameters(context, attributeSet);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public boolean horizontalSpacingSpecified() {
            return mHorizontalSpacing != NO_SPACING;
        }

        public boolean verticalSpacingSpecified() {
            return mVerticalSpacing != NO_SPACING;
        }

        public void setPosition(int x, int y) {
            this.mX = x;
            this.mY = y;
        }

        public void setHorizontalSpacing(int spacing) {
            mHorizontalSpacing = spacing;
        }

        public void setVerticalSpacing(int spacing) {
            mVerticalSpacing = spacing;
        }

        private void readStyleParameters(Context context, AttributeSet attributeSet) {
            TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.FlowLayout_LayoutParams);

            mHorizontalSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_horizontalSpacing,
                    NO_SPACING);
            mVerticalSpacing = a
                    .getDimensionPixelSize(R.styleable.FlowLayout_LayoutParams_layout_verticalSpacing, NO_SPACING);
            mNewLine = a.getBoolean(R.styleable.FlowLayout_LayoutParams_layout_newLine, false);

            a.recycle();

        }
    }
}
