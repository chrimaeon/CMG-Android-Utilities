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
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.cmgapps.android.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A layout which container is square
 */
@SuppressWarnings("UnusedDeclaration")
public class RelativeSquareLayout extends RelativeLayout {

    @IntDef({WIDTH, HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LayoutConstraint{}
    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    private int mConstraint = WIDTH;

    public RelativeSquareLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RelativeSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RelativeSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelativeSquareLayout);
        mConstraint = a.getInt(R.styleable.RelativeSquareLayout_constraint, WIDTH);
        a.recycle();
    }


    /**
     * Sets the constraint for the layout
     *
     * @param constraint the constraint to set (<code>0 = WIDTH, 1 = HEIGHT</code>)
     */
    public void setConstraint(@LayoutConstraint int constraint) {
        mConstraint = constraint;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (mConstraint) {
            case WIDTH:
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                return;
            case HEIGHT:
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
                return;
            default:
                throw new RuntimeException("SquareLayout constraint has no valid value");
        }
    }
}
