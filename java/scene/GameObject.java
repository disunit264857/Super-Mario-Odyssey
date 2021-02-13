package com.disunit.supermarioodyssey.scene;

import android.opengl.Matrix;



public class GameObject {

public String name = "";

public GameObject parent;
public Transform transform;
public Mesh mesh;
public Material material;
public float[] mMatrix;
public boolean transformApplied;

public GameObject(){
  mMatrix = new float[16];
  parent = null;
  transform = new Transform();
  mesh = new Mesh();
  material = new MaterialPhong();
  applyTransform(null);
}

//public void apply
public void applyTransform(GameObject parent){
  Matrix.setIdentityM(mMatrix,0);
  
    Matrix.translateM(mMatrix,0,transform.x,transform.y,transform.z);
  
  Matrix.scaleM(mMatrix,0,transform.sx,transform.sy,transform.sz);
  
  Matrix.rotateM(mMatrix,0,transform.ry,0,1,0);
  Matrix.rotateM(mMatrix,0,transform.rx,1,0,0);
  Matrix.rotateM(mMatrix,0,transform.rz,0,0,1);
  if(parent!=null){
  Matrix.multiplyMM(mMatrix,0,parent.mMatrix,0,mMatrix,0);
  }
}


}