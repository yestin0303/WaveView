package yestin.myviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;


/**
 * Created by yinlu on 2016/11/13.
 */

public class WaveView extends View {
    Path path;
    Paint paint;
    Path pathback;
    Paint paintback;
    int screen_width;
    int screen_height;
    int dx;
    int itemLength;
    int controlLength;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        screen_width = wm.getDefaultDisplay().getWidth();
        screen_height = wm.getDefaultDisplay().getHeight();
        itemLength = screen_height;
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.parseColor("#B9EBDF"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        pathback = new Path();
        paintback = new Paint();
        paintback.setColor(Color.parseColor("#00B388"));
        paintback.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        pathback.reset();
        controlLength = itemLength / 4;
        path.moveTo(-itemLength + dx, 500);
        pathback.moveTo((float) (-itemLength * 1.25+ dx), 500);

        drawWave(canvas, path, paint);
        drawWave(canvas, pathback, paintback);
    }

    public void drawWave(Canvas canvas, Path drawpath, Paint drawpaint) {
        for (int i = 0; i <= screen_width / itemLength + 2; i++) {
            drawpath.rQuadTo(controlLength, 30, itemLength / 2, 0);
            drawpath.rQuadTo(controlLength, -30, itemLength / 2, 0);
        }
        drawpath.lineTo(screen_width, 0);
        drawpath.lineTo(0, 0);
        drawpath.close();
        canvas.drawPath(drawpath, drawpaint);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, itemLength);
        animator.setDuration(25000);
        //设置动画无限循环
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
