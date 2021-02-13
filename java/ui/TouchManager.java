package com.disunit.supermarioodyssey.ui;

import android.view.MotionEvent;
import android.view.View;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ui.UILayer;

public class TouchManager implements View.OnTouchListener {

  public TouchManager() {}

  public boolean onTouch(View view, MotionEvent event) {
    int pointer_count = event.getPointerCount();
    for (int i = 0; i < 32; i++) {
      if (((MainActivity.uiBit >> i) & 1) == 1) {
        MainActivity.uiLayer[i].onTouch(event);
      }
    }
    return true; // comsume touch event
  }
}
