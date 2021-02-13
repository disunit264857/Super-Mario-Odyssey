package com.disunit.supermarioodyssey.scene;

import android.graphics.RectF;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.kingdom.Kingdom;
import com.disunit.supermarioodyssey.ui.*;


public class SceneManager {

public Camera camera;

public Joystick joystick;
public Touchpad touchpad;
public HealthMeter hp;

public Kingdom kingdom;

public SceneManager(Camera camera){
  this.camera = camera;
}

public void start(){

    }

public void update(){
  camera.transform.ry += touchpad.ix*45;
  camera.transform.rx += -touchpad.iy*45;
  camera.transform.x += -joystick.ix;
  camera.transform.z += joystick.iy;
  
  
  
}

public void createUI(){
    joystick = new Joystick(128,128);
  joystick.extent = new RectF(-UI.aspectRatio, -1, 0, 1);
    joystick.transform.x = -0.5f;
    joystick.transform.y = -0.5f;
    joystick.transform.sx = 0.8f;
    joystick.transform.sy = 0.8f;
    MainActivity.uiLayer[0].uiObjects.add(joystick);

touchpad = new Touchpad(256, 256);
  touchpad.extent = new RectF(0, -1, UI.aspectRatio, 1);
  touchpad.transform.sx = touchpad.transform.sy = 1;
MainActivity.uiLayer[0].uiObjects.add(touchpad);

hp = new HealthMeter(256,256);

hp.transform.sx = hp.transform.sy = 0.3f;
hp.transform.x = UI.aspectRatio-0.4f;
hp.transform.y = 0.6f;
MainActivity.uiLayer[0].uiObjects.add(hp);
hp.setHealth();
}

}