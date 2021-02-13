package com.disunit.supermarioodyssey;

import android.app.Activity;
import android.graphics.*;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.disunit.supermarioodyssey.scene.*;
import com.disunit.supermarioodyssey.scene.RenderQueue;
import com.disunit.supermarioodyssey.ui.*;
import com.disunit.supermarioodyssey.util.*;
import java.util.LinkedList;

public class MainActivity extends Activity {

  public RenderQueue renderQueue;
  public SceneGraph sceneGraph;
  public static TextView tv;
  public static ImageView iv;
  public static int uiBit;

  public static UILayer[] uiLayer;

  public static final int UI_ONE = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    tv = new TextView(this);
    iv = new ImageView(this);
    uiBit = UI_ONE;

    uiLayer = new UILayer[32];
    for (int i = 0; i < 32; i++) {
      uiLayer[i] = new UILayer();
    }

    

    ShaderStorage.loadShader(this);

    SceneNode model = BinaryModelParser.parseModel(this, "Test/", "Ground002.mesh");
    sceneGraph = new SceneGraph();
    model.gameObject.transform.x = 50;
    model.gameObject.transform.rx = 90;
    //model.gameObject.applyTransform(null);
    sceneGraph.add(model);

    GLSurfaceView glView = new GLSurfaceView(this);
    glView.setEGLContextClientVersion(3);
    GLRenderer renderer = new GLRenderer(this);
    TouchManager touchManager = new TouchManager();
    glView.setRenderer(renderer);
    glView.setOnTouchListener(touchManager);
    FrameLayout fl = new FrameLayout(this);

    fl.addView(glView);
    fl.addView(tv);
    fl.addView(iv);
    setContentView(fl);
  }

  public void onWindowFocusChanged(boolean hasFocus) {
    if (hasFocus) {
      getWindow()
          .getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                  | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                  | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                  | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                  | View.SYSTEM_UI_FLAG_FULLSCREEN
                  | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
  }
}
