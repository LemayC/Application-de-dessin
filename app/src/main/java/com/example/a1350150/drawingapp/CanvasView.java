package com.example.a1350150.drawingapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Collection;
import java.util.Random;

public class CanvasView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private boolean mResetCanvas;
    private Path mPath;
    Context context;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 3;
    private Bitmap mBackgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.image_luigi);

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        mResetCanvas = true;

        // we set a new Path
        mPath = new Path();

        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(16f);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //Change bg image size
        mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, w, h, false);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawBitmap(mBackgroundImage, 0f, 0f, null);

        invalidate();
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        // draw the mPath with the mPaint on the canvas when onDraw
        if (mResetCanvas) {
            //Draw luigi on the canvas :)
            mCanvas.drawBitmap(mBackgroundImage, 0f, 0f, null);
            mResetCanvas = false;
        } else {
            mCanvas.drawPath(mPath, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0f, 0f, null);
        super.onDraw(canvas);
    }

    public void setBackgroudImage(Resources res, int id) {
        int w = mBitmap.getWidth();
        int h = mBitmap.getHeight();
        //Create bitmap
        mBackgroundImage = BitmapFactory.decodeResource(getResources(), id);
        //Resize bitmap
        mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, w, h, false);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawBitmap(mBackgroundImage, 0f, 0f, null);
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
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

    // effaceles couleurs du dessin
    public void clearCanvas() {
        Random rng = new Random();
        int i = rng.nextInt(3);
        int resId = 0;
        switch (i) {
            case 0:
                resId = R.drawable.image_yoshi;
                break;
            case 1:
                resId = R.drawable.image_mario;
                break;
            default:
                resId = R.drawable.image_luigi;
                break;
        }
        setBackgroudImage(getResources(), resId);
        mResetCanvas = true;
        invalidate();
    }

    // change la couleur du pinceau selon le bouton
    public void changeDrawingColor(View v){
        ColorDrawable colorDrawableVar = (ColorDrawable) v.getBackground();
        mPaint.setColor(colorDrawableVar.getColor());
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