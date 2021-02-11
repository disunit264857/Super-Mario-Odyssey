package com.disunit.supermarioodyssey.ui;

import android.opengl.GLES32;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ui.MaterialUI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public abstract class UI {

public static int[] uiVAO;
public static int[] uiVBO;

public MaterialUI material;

public static void initialize(){
  uiVAO = new int[1];
  uiVBO = new int[2];
  
  FloatBuffer tmp = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();
  
  tmp.put(new float[]{
  1.0f,1.0f,
  0.0f,1.0f,
  1.0f,0.0f,
  0.0f,0.0f
});
  tmp.position(0);
  
  GLES32.glGenVertexArrays(1,uiVAO,0);
  GLES32.glGenBuffers(2,uiVBO,0);
  GLES32.glBindVertexArray(uiVAO[0]);
  
  /*POSITION*/
  GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,uiVBO[0]);//vertex pos
  GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,32,tmp,GLES32.GL_STATIC_DRAW);//4*2*(4bytes)_position
  
  GLES32.glEnableVertexAttribArray(0);//vertex pos
  GLES32.glVertexAttribPointer(0,2,GLES32.GL_FLOAT,false,8,0);
  
  /*POSITION*/
  tmp.position(0);
  /*UV*/
  
  GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,uiVBO[1]);//vertex uv
  GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,32,tmp,GLES32.GL_STATIC_DRAW);//4*2*(4bytes)_UV
  GLES32.glEnableVertexAttribArray(1);
   GLES32.glVertexAttribPointer(1,2,GLES32.GL_FLOAT,false,8,0);
  /*UV*/
  
  GLES32.glBindVertexArray(0);
}

public UI(int w,int h){
  material = new MaterialUI(w,h);
}

public abstract void render();

}
