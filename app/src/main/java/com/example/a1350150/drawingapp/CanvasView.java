package com.example.a1350150.drawingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;

public class CanvasView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private boolean mEraseAll;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 3;
    private Random mRng;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        mEraseAll = false;
        mRng = new Random();

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(12f);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        invalidate();
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        // draw the mPath with the mPaint on the canvas when onDraw
        if (mEraseAll) {
            mCanvas.drawColor(Color.WHITE);
            mEraseAll = false;
        } else {
            mCanvas.drawPath(mPath, mPaint);
            canvas.drawBitmap(mBitmap, 0f, 0f, null);
        }
        super.onDraw(canvas);
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        mPaint.setARGB(125, mRng.nextInt(255), mRng.nextInt(255), mRng.nextInt(255));
        mPaint.setStrokeWidth((float)mRng.nextInt(49) + 1f);
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mEraseAll = true;
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.reset();
    }

    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }
}