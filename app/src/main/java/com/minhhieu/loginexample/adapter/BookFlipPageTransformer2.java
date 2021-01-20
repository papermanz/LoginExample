package com.minhhieu.loginexample.adapter;

import android.view.View;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

class BookFlipPageTransformer2 implements ViewPager2.PageTransformer {
    private final int LEFT = -1;
    private final int RIGHT = 1;
    private final int CENTER = 0;

    private float scaleAmountPercent = 5f;
    private boolean enableScale = true;

    @Override
    public void transformPage(@NonNull View page, float position) {
        float percentage = 1 - Math.abs(position);
        ViewPager2 viewPager = requireViewPager(page);
        // Don't move pages once they are on left or right
        if (position > CENTER && position <= RIGHT)
        {
            if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL)
            {
                // This is behind page
                page.setTranslationX(-position * (page.getWidth()));
                page.setTranslationY(0);
                page.setTranslationZ(-1);
                page.setRotation(0);
                if (enableScale)
                {
                    float amount = ((100 - scaleAmountPercent) + ( scaleAmountPercent * percentage)) / 100;
                    setSize(page, position, amount);
                }
            }
            else
            {
                // This is behind page
                page.setTranslationY(-position * (page.getHeight()));
                page.setTranslationX(0);
                page.setTranslationZ(-1);
                page.setRotation(0);
                if (enableScale)
                {
                    float amount = ((100 - scaleAmountPercent) + ( scaleAmountPercent * percentage)) / 100;
                    setSize(page, position, amount);
                }
            }

        }
        // Otherwise flip the current page
        else
        {
            page.setVisibility(View.VISIBLE);
            flipPage(page, position, percentage);
        }
    }

    private void flipPage(View page, float position, float percentage)
    {

        // Flip this page
        page.setCameraDistance(-30000);
        setVisibility(page, position);
        setTranslation(page);
        if (requireViewPager(page).getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL)
        {
            setPivot(page, 0f, page.getHeight() * 0.5f);
        }
        else if (requireViewPager(page).getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
        {
            setPivot(page, page.getWidth() * 0.5f, 0f);
        }

        setRotation(page, position, percentage);
    }

    private void setPivot(View page, float pivotX, float pivotY)
    {
        page.setPivotX(pivotX);
        page.setPivotY(pivotY);
    }

    private void setVisibility(View page, float position) {
        if (position < 0.5 && position > -0.5) {
            page.setVisibility(View.VISIBLE);
        } else {
            page.setVisibility(View.INVISIBLE);
        }
    }

    private void setTranslation(View page) {
        ViewPager2 viewPager = requireViewPager(page);
        if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL)
        {
            int scroll = viewPager.getScrollX() - page.getLeft();
            page.setTranslationX(scroll);
        }
        else if (viewPager.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
        {
            int scroll = viewPager.getScrollY() - page.getTop();
            page.setTranslationY(scroll);
        }
        page.setTranslationZ(1f);
    }

    private void setSize(View page, float position, float percentage) {
        page.setScaleX((position != 0 && position != 1) ? percentage : 1);
        page.setScaleY((position != 0 && position != 1) ? percentage : 1);
    }

    private void setRotation(View page, float position, float percentage) {
        ViewPager2 viewPager = requireViewPager(page);
        if (viewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL)
        {
            if (position > 0) {
                page.setRotationY(-180 * (percentage + 1));
            } else {
                page.setRotationY(180 * (percentage + 1));
            }
        }
        else if (viewPager.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
        {
            if (position > 0) {
                page.setRotationX(180 * (percentage + 1));
            } else {
                page.setRotationX(-180 * (percentage + 1));
            }
        }
    }

    private ViewPager2 requireViewPager(@NonNull View page) {
        ViewParent parent = page.getParent();
        ViewParent parentParent = parent.getParent();

        if (parent instanceof RecyclerView && parentParent instanceof ViewPager2) {
            return (ViewPager2) parentParent;
        }

        throw new IllegalStateException(
                "Expected the page view to be managed by a ViewPager2 instance.");
    }

    //region Getters/Setters
    public float getScaleAmountPercent() {
        return scaleAmountPercent;
    }

    public void setScaleAmountPercent(float scaleAmountPercent) {
        this.scaleAmountPercent = scaleAmountPercent;
    }

    public boolean isEnableScale() {
        return enableScale;
    }

    public void setEnableScale(boolean enableScale) {
        this.enableScale = enableScale;
    }
}
