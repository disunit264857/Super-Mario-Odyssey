package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import android.opengl.Matrix;
import com.disunit.supermarioodyssey.scene.Transform;


public class DirectionalLight {
public Transform transform;
public float r,g,b;

public float[] oMatrix;
public float[] vMatrix;
public float[] bMatrix;
public float[] ovbMatrix;

public int[] frameBuffer;
public int[] depthTexture;


public DirectionalLight(){
  transform = new Transform();
  oMatrix = new float[16];
  vMatrix = new float[16];
  bMatrix = new float[]{
    0.5f,0.0f,0.0f,0.0f,
    0.0f,0.5f,0.0f,0.0f,
    0.0f,0.0f,0.5f,0.0f,
    0.5f,0.5f,0.5f,1.0f
  };
  
  ovbMatrix = new float[16];
  frameBuffer = new int[1];
  depthTexture = new int[1];
  Matrix.orthoM(oMatrix,0,-50,50,-50,50,0.3f,500);
}

public void initialize(){
  GLES32.glGenFramebuffers(1,frameBuffer,0);
  GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER,frameBuffer[0]);
  
  GLES32.glGenTextures(1,depthTexture,0);
  GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,depthTexture[0]);
  
  
  GLES32.glTexStorage2D(GLES32.GL_TEXTURE_2D,1,GLES32.GL_DEPTH_COMPONENT24,1024,1024);
 
  
  GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER,GLES32.GL_NEAREST);
GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER,GLES32.GL_NEAREST);
GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_WRAP_S,GLES32.GL_CLAMP_TO_EDGE);
GLES32.glTexParameteri(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_WRAP_T,GLES32.GL_CLAMP_TO_EDGE);

GLES32.glFramebufferTexture2D(GLES32.GL_FRAMEBUFFER,GLES32.GL_DEPTH_ATTACHMENT,GLES32.GL_TEXTURE_2D,depthTexture[0],0);
}

public void updateVMatrix(){
  Matrix.setLookAtM(vMatrix,0,transform.x,transform.y,transform.z,transform.x+transform.rx,transform.y+transform.ry,transform.z+transform.rz,0,1,0);
}

public void updateMVPMatrix(){
  Matrix.multiplyMM(ovbMatrix,0,oMatrix,0,vMatrix,0);
  Matrix.multiplyMM(ovbMatrix,0,bMatrix,0,ovbMatrix,0);
}

}
