package com.disunit.supermarioodyssey.ui;

import android.opengl.GLES32;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ui.MaterialUI;


public class Button extends UI {

public Button(int w,int h){
  super(w,h);
}

public void render(){
  if(!material.compiled){
    material.compile();
  }else{
    material.use();
  }
  material.update();
  GLES32.glDrawArrays(GLES32.GL_TRIANGLE_STRIP,0,8);
}

}
