package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Mesh {


public float[] vp;
public float[] vn;
public float[] vt;

public FloatBuffer vertexBuffer;
public FloatBuffer uvBuffer;
public FloatBuffer normalBuffer;

public boolean vaoUpdated;

public boolean cullObject;
public boolean castShadow,receiveShadow;

public int[] VAO;
private int[] VBO;

public Mesh(){
  vaoUpdated = false;
  VAO = new int[1];
  VBO = new int[3];
  vp = vn = vt = null;
  cullObject = true;
  //castShadow = false;
}

public void updateBuffer(){
  if(vp!=null){
  vertexBuffer = ByteBuffer.allocateDirect(vp.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
  vertexBuffer.put(vp);
  vertexBuffer.position(0);
  uvBuffer = ByteBuffer.allocateDirect(vt.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
  uvBuffer.put(vt);
  uvBuffer.position(0);
  normalBuffer = ByteBuffer.allocateDirect(vn.length*4).order(ByteOrder.nativeOrder()).asFloatBuffer();
  normalBuffer.put(vn);
  normalBuffer.position(0);
  cullObject = false;
  }
}

public void updateVAO(){
  GLES32.glGenVertexArrays(1,VAO,0);
  GLES32.glGenBuffers(3,VBO,0);
  GLES32.glBindVertexArray(VAO[0]);
  GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,VBO[0]);//pos
  GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,vp.length*4,vertexBuffer,GLES32.GL_STATIC_DRAW);
  
  GLES32.glEnableVertexAttribArray(0);//vertex pos
    GLES32.glVertexAttribPointer(0,3,GLES32.GL_FLOAT,false,12,0);
  
  
  
  GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,VBO[1]);//uv
  GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,vt.length*4,uvBuffer,GLES32.GL_STATIC_DRAW);
  
  GLES32.glEnableVertexAttribArray(1);//uv
    GLES32.glVertexAttribPointer(1,3,GLES32.GL_FLOAT,false,12,0);
  
  
  
  GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,VBO[2]);//normal
  GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,vn.length*4,normalBuffer,GLES32.GL_STATIC_DRAW);
    
  GLES32.glEnableVertexAttribArray(2);//normal
    GLES32.glVertexAttribPointer(2,3,GLES32.GL_FLOAT,false,12,0);
  
  
  vaoUpdated = true;
}

}
