package com.disunit.supermarioodyssey.ui;

import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import com.disunit.supermarioodyssey.MainActivity;

public class Button extends UI {

  public int id;
  public boolean isTouched;

  public Button(int w, int h) {
    super(w, h);
    id = -1;
    isTouched = false;
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
          }
        }
        break;

        // TOUCH MOVE
      case MotionEvent.ACTION_MOVE:
        break;

        // TOUCH UP
      case MotionEvent.ACTION_POINTER_UP:
        index =
            (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
      case MotionEvent.ACTION_UP:
        if (id == event.getPointerId(index)) {
          isTouched = false;
          id = -1;
        }
        break;
    }
  }
}
