package com.disunit.supermarioodyssey.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import com.disunit.supermarioodyssey.MainActivity;

public class Joystick extends UI {

  
  public int id;
  public boolean isTouched;
  public float ix, iy;
  private float x, y;
  private float sx, sy;
  private long exitTime;

  private int bw, bh;
  private Bitmap texture;
  private Canvas canvas;
  private Paint outsidePaint,insidePaint;

  public Joystick(int w, int h) {
    super(w, h);
    bw = w;
    bh = h;
    id = -1;
    isTouched = false;
    ix = iy = 0;
    x = y = 0;
    sx = sy = 0;
    exitTime = 0;
    texture = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
    canvas = new Canvas(texture);
    outsidePaint = new Paint();
    outsidePaint.setStyle(Paint.Style.STROKE);
    outsidePaint.setAntiAlias(true);
    outsidePaint.setStrokeWidth(bw*0.02f);
    outsidePaint.setARGB(255, 255, 255, 255);
    insidePaint = new Paint();
    insidePaint.setStyle(Paint.Style.FILL);
    insidePaint.setAntiAlias(true);
    insidePaint.setARGB(255, 255, 255, 255);
  }

  public void onTouch(MotionEvent event) {
    int count = event.getPointerCount();
    int index = 0;
    int action = event.getAction();
    switch (action & MotionEvent.ACTION_MASK) {
        // TOUCH DOWN
      case MotionEvent.ACTION_POINTER_DOWN:
        index =
            (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
      case MotionEvent.ACTION_DOWN:
        float x = (float) event.getX(index) / UI.h * 2 - UI.aspectRatio;
        float y = 1 - (float) event.getY(index) / UI.h * 2;
        if (id == -1) {
          if (extent.contains(x, y)) {
            isTouched = true;
            id = event.getPointerId(index);
            this.x = x;
            this.y = y;
            this.sx = x;
            this.sy = y;
            transform.x = x-transform.sx*0.5f;
            transform.y = y-transform.sy*0.5f;
          }
        }
        break;
        // TOUCH MOVE
      case MotionEvent.ACTION_MOVE:
        for (int i = 0; i < count; i++) {
          if (id == event.getPointerId(i)) {
            this.x = (float) event.getX(i) / UI.h * 2 - UI.aspectRatio;
            this.y = 1 - (float) event.getY(i) / UI.h * 2;
            ix = (this.x - this.sx)/transform.sx*2;
            iy = (this.y - this.sy)/transform.sy*2;
            float d;
            
            if((d=(float)Math.hypot(ix,iy))>1f){
              ix = ix/d;
              iy = iy/d;
            }
            
          }
        }
        break;

        // TOUCH UP
      case MotionEvent.ACTION_POINTER_UP:
        index =
            (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
      case MotionEvent.ACTION_UP:
        if (id == event.getPointerId(index)) {
          id = -1;
          ix = iy = 0;
          isTouched = false;
        }
        break;
    }
  }

  public void render() {
    int alpha =
        (int) Math.min(Math.max((System.currentTimeMillis() - exitTime) / 4, 0), 255); // 1 sec
        
    canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
    if (isTouched) {
      exitTime = System.currentTimeMillis();
      canvas.drawCircle((ix*0.4f+0.5f) * bw, (iy*0.4f+0.5f) * bh, bw*0.1f, insidePaint);
    }
    outsidePaint.setARGB(255-alpha, 255, 255, 255);
    canvas.drawCircle(bw*0.5f,bh*0.5f, bw*0.4f, outsidePaint);
    material.updateTextureData(texture);
    material.isUpToDate = false;
  }
}
