package com.disunit.supermarioodyssey.ui;

import android.graphics.Bitmap;
import android.opengl.GLES32;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ShaderStorage;
import com.disunit.supermarioodyssey.scene.Transform;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class MaterialUI {

public int program;
public int[] texture;
public ByteBuffer data;
public int w,h;
public boolean compiled;
public boolean isUpToDate;

public MaterialUI(int w,int h){
  this.w = w;
  this.h = h;
  compiled = false;
  isUpToDate = false;
  data = ByteBuffer.allocateDirect(w*h*4).order(ByteOrder.nativeOrder());
}

public void compile(){
  program = GLES32.glCreateProgram();
  int vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER);
  int fs = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER);
  GLES32.glShaderSource(vs,ShaderStorage.ui_vertex);
  GLES32.glShaderSource(fs,ShaderStorage.ui_fragment);
  GLES32.glCompileShader(vs);
  GLES32.glCompileShader(fs);
  GLES32.glAttachShader(program,vs);
  GLES32.glAttachShader(program,fs);
  GLES32.glLinkProgram(program);
  MainActivity.tv.append(GLES32.glGetShaderInfoLog(vs));
  MainActivity.tv.append(GLES32.glGetShaderInfoLog(fs));
  texture = new int[1];
  //GLES32.glUseProgram(program);
  GLES32.glGenTextures(1,texture,0);
  GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,texture[0]);
  
  GLES32.glTexStorage2D(GLES32.GL_TEXTURE_2D,1,GLES32.GL_RGBA8,w,h);
  
  GLES32.glTexSubImage2D(GLES32.GL_TEXTURE_2D,0,0,0,w,h,GLES32.GL_RGBA,GLES32.GL_UNSIGNED_BYTE,data);
  
  GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, GLES32.GL_NEAREST);
  GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER, GLES32.GL_NEAREST);
  /*TEXTURE UNIFORM*/
  GLES32.glUniform1i(3, 0);//UI IMAGE
  /*TEXTURE UNIFORM*/
  compiled = true;
}

public void use(){
  GLES32.glUseProgram(program);
}

public void updateTexture(){
  GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,texture[0]);
  data.position(0);
  GLES32.glTexSubImage2D(GLES32.GL_TEXTURE_2D,0,0,0,w,h,GLES32.GL_RGBA,GLES32.GL_UNSIGNED_BYTE,data);
  GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,0);
}

public void updateTextureData(Bitmap bmp){
  data.position(0);
  bmp.copyPixelsToBuffer(data);
}

public void update(){
  /*UI TEXTURE*/
  GLES32.glActiveTexture(GLES32.GL_TEXTURE0);
  GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,texture[0]);
}

}
