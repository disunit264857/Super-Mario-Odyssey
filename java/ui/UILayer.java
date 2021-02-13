package com.disunit.supermarioodyssey.ui;

import android.opengl.GLES32;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.View;
import com.disunit.supermarioodyssey.scene.Camera;
import com.disunit.supermarioodyssey.ui.UI;
import java.util.ArrayList;

public class UILayer {

  public ArrayList<UI> uiObjects;

  public UILayer() {
    uiObjects = new ArrayList<UI>();
  }
  
  public void render(Camera camera) {
  GLES32.glBindVertexArray(UI.uiVAO[0]);
    for (UI uiObject : uiObjects) {
      if (!uiObject.material.compiled) {
        uiObject.material.compile();
      } else {
        uiObject.material.use();
      }
      uiObject.render();
      if (!uiObject.material.isUpToDate) {
        uiObject.material.updateTexture();
        uiObject.material.isUpToDate = true;
      }
      uiObject.material.update();
      uiObject.applyTransform();
      //Matrix.setIdentityM(camera.fMatrix,0);
      Matrix.multiplyMM(camera.mvpMatrix,0,camera.fMatrix,0,uiObject.mMatrix,0);
      
      GLES32.glUniformMatrix4fv(2,1,false,camera.mvpMatrix,0);
      GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP, 0, 8);
      GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,0);
    }
    GLES32.glBindVertexArray(0);
  }
  
  public void onTouch(MotionEvent event) {
    for (UI uiObject : uiObjects) {
    uiObject.onTouch(event);
    }
  }
  
}
