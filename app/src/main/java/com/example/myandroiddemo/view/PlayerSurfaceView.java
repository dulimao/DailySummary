package com.example.myandroiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

public class PlayerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public PlayerSurfaceView(Context context) {
        super(context);
        init();
    }

    public PlayerSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.RED);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Surface surface = holder.getSurface();
        final float[] alpha = {0.1f};
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Canvas canvas = holder.lockCanvas();
                    if (canvas != null) {
                        int c = ColorUtils.blendARGB(Color.RED, Color.BLUE, alpha[0]);
                        alpha[0] += 0.1;
                        canvas.drawColor(c);
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }).start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
