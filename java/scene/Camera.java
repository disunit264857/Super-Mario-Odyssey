package com.disunit.supermarioodyssey.scene;

import android.opengl.Matrix;
import com.disunit.supermarioodyssey.Constants;


public class Camera {

public Transform transform;
public float fov;
public float aspectRatio;
public float near,far;

public float[] pMatrix;
public float[] vMatrix;
public float[] mvpMatrix;

public Camera(){
  pMatrix = new float[16];
  vMatrix = new float[16];
  mvpMatrix = new float[16];
  fov = 60;
  aspectRatio = 1;
  near = 0.3f;
  far = 1000f;
  Matrix.perspectiveM(pMatrix,0,fov,aspectRatio,near,far);
  transform = new Transform();
}

public void updatePMatrix(){
  Matrix.perspectiveM(pMatrix,0,fov,aspectRatio,near,far);
}
public void updateVMatrix(){
  Matrix.setLookAtM(vMatrix,0,transform.x,transform.y,transform.z,transform.x+(float)(Math.sin(-transform.ry*Constants.Deg2Rad)*Math.cos(transform.rx*Constants.Deg2Rad)),transform.y+(float)(Math.sin(-transform.rx*Constants.Deg2Rad)),transform.z+(float)(Math.cos(-transform.ry*Constants.Deg2Rad)*Math.cos(transform.rx*Constants.Deg2Rad)),0,1,0);
  //Matrix.setLookAtM(vMatrix,0,0,10,-100,0,0,60,0,1,0);
}


}
