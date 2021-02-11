package com.disunit.supermarioodyssey.ui;

import com.disunit.supermarioodyssey.ui.UI;
import java.util.ArrayList;


public class UILayer {

public ArrayList<UI> uiObjects;

public UILayer(){
  uiObjects = new ArrayList<UI>();
}

public void render(){
  for(UI uiObject : uiObjects){
    uiObject.render();
  }
}

}