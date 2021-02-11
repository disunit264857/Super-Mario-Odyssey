package com.disunit.supermarioodyssey.scene;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.scene.SceneManager;
import com.disunit.supermarioodyssey.ui.Button;
import com.disunit.supermarioodyssey.ui.UI;
import com.disunit.supermarioodyssey.ui.UILayer;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer{

public MainActivity ctx;

public Camera camera;
public UILayer[] uiLayer;
public SceneManager manager;
public int uiBit;

public static final int UI_ONE = 1;

public GLRenderer(MainActivity ctx){
  this.ctx = ctx;
  camera = new Camera();
  uiLayer = new UILayer[32];
  for(int i=0;i<32;i++){
    uiLayer[i] = new UILayer();
  }
  Button btn = new Button(128,128);
  Bitmap bmp = Bitmap.createBitmap(128,128,Bitmap.Config.ARGB_8888);
  bmp.eraseColor(Color.RED);
  try{
  btn.material.updateTexture(bmp);
  MainActivity.tv.append(btn.material.data.get(1)+"");
  }catch(Exception e){
    MainActivity.tv.append(e.getMessage());
  }
  uiLayer[0].uiObjects.add(btn);
  manager = new SceneManager();
  uiBit = 0;
  uiBit = UI_ONE;
}
public void onSurfaceCreated(GL10 gl,EGLConfig config){
  GLES32.glClearColor(0.3f,0.3f,0.3f,1.0f);
  GLES32.glEnable(GLES32.GL_DEPTH_TEST);
  GLES32.glEnable(GLES32.GL_CULL_FACE);
  //GLES32.glEnable(GLES32.GL_BLEND);
  GLES32.glBlendFunc(GLES32.GL_ONE,GLES32.GL_ONE);
  UI.initialize();
}
public void onDrawFrame(GL10 gl){
  GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT|GLES32.GL_DEPTH_BUFFER_BIT);
  GLES32.glEnable(GLES32.GL_DEPTH_TEST);
  GLES32.glDisable(GLES32.GL_BLEND);
  renderScene();
  GLES32.glBindVertexArray(0);
  
  
  
  GLES32.glEnable(GLES32.GL_BLEND);
  GLES32.glDisable(GLES32.GL_DEPTH_TEST);
  GLES32.glBindVertexArray(UI.uiVAO[0]);
  renderUI();
}

public void renderUI(){
  for(int i = 0;i<32;i++){
    if(((uiBit>>i)&1)==1){
      uiLayer[i].render();
    }
  }
}

public void renderScene(){
  camera.transform.z = -100;
  camera.transform.y = 10;
  camera.transform.x = 80;
  //camera.transform.ry+=1;
  //camera.transform.ry +=2;
  camera.updateVMatrix();
  for(GameObject gameObject:ctx.renderQueue.queue){
    if(!gameObject.mesh.vaoUpdated){
      gameObject.mesh.updateVAO();
    }
    if(!gameObject.material.compiled){
      gameObject.material.compile();
    }else{
      gameObject.material.use();
    }
    //useProgram above
    if(!gameObject.material.upToDate){
      gameObject.material.update();
      gameObject.material.upToDate = true;
    }
    
    
    gameObject.transform.ry = 180;
    gameObject.transform.rx = 90;
    //gameObject.transform.sx = 0.01f;
    
    gameObject.applyTransform();
    
    /*MVP MATRIX*/
    
    Matrix.multiplyMM(camera.mvpMatrix,0,camera.vMatrix,0,gameObject.mMatrix,0);
    GLES32.glUniformMatrix4fv(5,1,false,camera.mvpMatrix,0);//mv
    
    Matrix.multiplyMM(camera.mvpMatrix,0,camera.pMatrix,0,camera.mvpMatrix,0);
    GLES32.glUniformMatrix4fv(3,1,false,camera.mvpMatrix,0);//mvp
    GLES32.glUniformMatrix4fv(4,1,false,gameObject.rMatrix,0);//r
    /*MVP MATRIX*/
    GLES32.glBindVertexArray(gameObject.mesh.VAO[0]);
    GLES32.glDrawArrays(GLES32.GL_TRIANGLES,0,gameObject.mesh.vp.length);
  }
}

public void onSurfaceChanged(GL10 gl,int w,int h){
  GLES32.glViewport(0,0,w,h);
  camera.aspectRatio = (float)w/h;
  camera.updatePMatrix();
}

}
