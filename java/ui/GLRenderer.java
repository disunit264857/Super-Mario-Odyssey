package com.disunit.supermarioodyssey.scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.MailTo;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.scene.SceneManager;
import com.disunit.supermarioodyssey.ui.*;
import com.disunit.supermarioodyssey.ui.UILayer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {

  public MainActivity ctx;

  public Camera camera;
  public DirectionalLight sun;
  public SceneManager manager;

  public GLRenderer(MainActivity ctx) {
    this.ctx = ctx;
    camera = new Camera();
    camera.transform.y = 10;
    manager = new SceneManager(camera);
    sun = new DirectionalLight();
    sun.transform.ry = -1;
  }

  // public void

  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    GLES32.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
    GLES32.glEnable(GLES32.GL_DEPTH_TEST);
    GLES32.glEnable(GLES32.GL_CULL_FACE);
    GLES32.glBlendFunc(GLES32.GL_SRC_ALPHA, GLES32.GL_ONE_MINUS_SRC_ALPHA);
    UI.initialize();
    sun.initialize();
    sun.transform.y = 60;
    manager.start();
    MaterialShadowmap.material.compile();
  }

  public void onDrawFrame(GL10 gl) {
  //GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER,0);

    manager.update();

    GLES32.glEnable(GLES32.GL_DEPTH_TEST);
    GLES32.glDisable(GLES32.GL_BLEND);
    renderScene();
    /*UNBINDED*/

    GLES32.glEnable(GLES32.GL_BLEND);
    GLES32.glDisable(GLES32.GL_DEPTH_TEST);
    renderUI();
    // GLES32.glUseProgram(0);
  }

  public void renderUI() {
    for (int i = 0; i < 32; i++) {
      if (((MainActivity.uiBit >> i) & 1) == 1) {
        MainActivity.uiLayer[i].render(camera);
      }
    }
  }

  public void renderScene() {

    // camera.transform.ry+=1;
    // camera.transform.ry +=2;
    camera.updateVMatrix();
    sun.transform.rx = (float) Math.sin(((double) System.currentTimeMillis()) / 180 * Math.PI*0.03) * 1;
    sun.transform.ry = -1;
    sun.updateVMatrix();
    sun.updateMVPMatrix();
    // GLES32.glGetUniformLocation(gameObject.material.program,"sunDir")

    /*SUN DIRECTION*/
    GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER,sun.frameBuffer[0]);
    GLES32.glColorMask(false,false,false,false);
    GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT);

    ctx.sceneGraph.renderShadowmap(camera, sun);
   GLES32.glActiveTexture(GLES32.GL_TEXTURE2);
    GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,sun.depthTexture[0]);
   GLES32.glColorMask(true,true,true,true);
    GLES32.glBindFramebuffer(GLES32.GL_FRAMEBUFFER,0);
    GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);
    ctx.sceneGraph.render(camera, sun);

    /*UNBINDING*/
    GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, 0);
    GLES32.glBindVertexArray(0);
  }

  public void onSurfaceChanged(GL10 gl, int w, int h) {
    GLES32.glViewport(0, 0, w, h);
    camera.aspectRatio = UI.aspectRatio = (float) w / h;
    camera.updatePMatrix();
    UI.w = w;
    UI.h = h;
    for (int i = 0; i < 32; i++) {
      MainActivity.uiLayer[i] = new UILayer();
    }
    manager.createUI();
  }
}
