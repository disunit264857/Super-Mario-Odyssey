package com.disunit.supermarioodyssey.scene;

import com.disunit.supermarioodyssey.scene.Camera;
import java.util.LinkedList;


public class SceneGraph {

public LinkedList<SceneNode> nodes;

public SceneGraph(){
  nodes = new LinkedList<SceneNode>();
}

public void renderShadowmap(Camera camera,DirectionalLight light){
  
  for(SceneNode node : nodes){
    node.renderShadowmap(null,camera,light);
  }
}

public void render(Camera camera,DirectionalLight light){
  for(SceneNode node : nodes){
    node.render(null,camera,light);
  }
}

public void add(GameObject object){
  nodes.add(new SceneNode(object));
}
public void add(SceneNode node){
  nodes.add(node);
}

}