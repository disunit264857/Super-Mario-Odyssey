package com.disunit.supermarioodyssey.scene;

import android.opengl.Matrix;



public class GameObject {

public String name = "";

public GameObject parent;
public Transform transform;
public Mesh mesh;
public Material material;
public float[] rMatrix;
public float[] mMatrix;

public GameObject(){
  rMatrix = new float[16];
  mMatrix = new float[16];
  parent = null;
  transform = new Transform();
  mesh = new Mesh();
  material = new MaterialDefault();
  applyTransform();
}

//public void apply
public void applyTransform(){
  Matrix.setIdentityM(mMatrix,0);
  Matrix.setIdentityM(rMatrix,0);
  
  Matrix.translateM(mMatrix,0,transform.x,transform.y,transform.z);
  
  Matrix.scaleM(mMatrix,0,transform.sx,transform.sy,transform.sz);
  /*Matrix.setRotateEulerM(rMatrix,0,transform.rx,transform.ry,transform.rz);
  
  
  Matrix.multiplyMM(mMatrix,0,mMatrix,0,rMatrix,0);*/
  
  
  Matrix.rotateM(mMatrix,0,transform.ry,0,1,0);
  Matrix.rotateM(mMatrix,0,transform.rx,1,0,0);
  Matrix.rotateM(mMatrix,0,transform.rz,0,0,1);
  
  Matrix.rotateM(rMatrix,0,transform.ry,0,1,0);
  Matrix.rotateM(rMatrix,0,transform.rx,1,0,0);
  Matrix.rotateM(rMatrix,0,transform.rz,0,0,1);
  
  
}


}