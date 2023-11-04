package com.example.ukladajzwyciezaj.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class CenterInsideLayout extends FrameLayout {
        public CenterInsideLayout(Context context) {
            super(context);
        }

        public CenterInsideLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CenterInsideLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            // Umieść dziecko na środku układu
            View child = getChildAt(0);
            if (child != null) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                int parentWidth = right - left;
                int parentHeight = bottom - top;

                int childLeft = (parentWidth - childWidth) / 2;
                int childTop = (parentHeight - childHeight) / 2;
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }
        }
    }

