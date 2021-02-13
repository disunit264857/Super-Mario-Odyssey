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

public class Touchpad extends UI {
public int id;
  public boolean isTouched;
  public float ix, iy;
  private float x, y;
  private float sx, sy;

  public Touchpad(int w, int h) {
    super(w, h);
    id = -1;
    isTouched = false;
    ix = iy = 0;
    x = y = 0;
    sx = sy = 0;
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
            this.sx = this.x;
            this.sy = this.y;
            this.x = (float) event.getX(i) / UI.h * 2 - UI.aspectRatio;
            this.y = 1 - (float) event.getY(i) / UI.h * 2;
            ix = (this.x - this.sx)/transform.sx*2;
            iy = (this.y - this.sy)/transform.sy*2;
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
  }
}
