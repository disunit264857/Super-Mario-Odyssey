package com.disunit.supermarioodyssey.ui;

import android.graphics.RectF;
import android.opengl.GLES32;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.View;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.scene.Transform;
import com.disunit.supermarioodyssey.ui.MaterialUI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class UI {

  public static int[] uiVAO;
  public static int[] uiVBO;

  public MaterialUI material;

  public Transform transform;
  public float[] mMatrix;
  
  public static int w,h;
  public static float aspectRatio;
  
  public RectF extent;// TOUCH EXTENT NOT UI EXTENT
  

  public static void initialize() {
    uiVAO = new int[1];
    uiVBO = new int[2];

    FloatBuffer tmp = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder()).asFloatBuffer();

    tmp.put(
        new float[] {
          1.0f, 1.0f,
          0.0f, 1.0f,
          1.0f, 0.0f,
          0.0f, 0.0f
        });
    tmp.position(0);

    GLES32.glGenVertexArrays(1, uiVAO, 0);
    GLES32.glGenBuffers(2, uiVBO, 0);
    GLES32.glBindVertexArray(uiVAO[0]);

    /*POSITION*/
    GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, uiVBO[0]); // vertex pos
    GLES32.glBufferData(
        GLES32.GL_ARRAY_BUFFER, 32, tmp, GLES32.GL_STATIC_DRAW); // 4*2*(4bytes)_position

    GLES32.glEnableVertexAttribArray(0); // vertex pos
    GLES32.glVertexAttribPointer(0, 2, GLES32.GL_FLOAT, false, 8, 0);

    /*POSITION*/
    tmp.position(0);
    /*UV*/

    GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, uiVBO[1]); // vertex uv
    GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, 32, tmp, GLES32.GL_STATIC_DRAW); // 4*2*(4bytes)_UV
    GLES32.glEnableVertexAttribArray(1);
    GLES32.glVertexAttribPointer(1, 2, GLES32.GL_FLOAT, false, 8, 0);
    /*UV*/

    GLES32.glBindVertexArray(0);
  }
  
  public UI(int w, int h) {
    material = new MaterialUI(w, h);
    mMatrix = new float[16];
    transform = new Transform();
    extent = new RectF(0, 0, 1, 1);
    transform.sx = transform.sy = 0.5f;
    
  }

  public void applyTransform() {
    Matrix.setIdentityM(mMatrix, 0);

    Matrix.translateM(mMatrix, 0, transform.x, transform.y, transform.z);

    Matrix.scaleM(mMatrix, 0, transform.sx, transform.sy, 1);
  }

  public void onTouch(MotionEvent event) {}
  public void render(){}
}
