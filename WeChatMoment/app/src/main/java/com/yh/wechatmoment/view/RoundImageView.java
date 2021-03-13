package com.yh.wechatmoment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    private int width;
    private int height;

    public RoundImageView(@NonNull Context context) {
        super(context);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if (width > 12 && height > 12) {
            Path path = new Path();
            path.moveTo(0, 12);
            path.quadTo(0, 0, 12, 0);
            path.lineTo(width - 12, 0);

            path.quadTo(width, 0, width, 12);
            path.lineTo(width, height - 12);

            path.quadTo(width, height, width - 12, height);
            path.lineTo(12, height);

            path.quadTo(0, height, 0, height - 12);
            path.lineTo(0, 12);

            canvas.clipPath(path);
        }
        super.draw(canvas);
    }
}
