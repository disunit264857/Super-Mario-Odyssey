package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import com.disunit.supermarioodyssey.scene.Camera;
import com.disunit.supermarioodyssey.scene.GameObject;
import java.util.LinkedList;

public class SceneNode {

  public LinkedList<SceneNode> nodes;
  public GameObject gameObject;

  public SceneNode(GameObject object) {
    nodes = new LinkedList<SceneNode>();
    gameObject = object;
  }

  public void render(GameObject parent, Camera camera, DirectionalLight sun) {
    // useProgram above

    gameObject.applyTransform(parent);

    if (!gameObject.mesh.cullObject) {
      if (!gameObject.mesh.vaoUpdated) {
        gameObject.mesh.updateVAO();
      }
      if (!gameObject.material.compiled) {
        gameObject.material.compile();
      } else {
        gameObject.material.use();
      }
      if (!gameObject.material.upToDate) {
      gameObject.material.update();
      gameObject.material.upToDate = true;
      }
      /*MVP MATRIX*/

      GLES32.glUniformMatrix4fv(GLES32.glGetUniformLocation(gameObject.material.program,"V"), 1, false, camera.vMatrix, 0); // v
      GLES32.glUniformMatrix4fv(GLES32.glGetUniformLocation(gameObject.material.program,"P"), 1, false, camera.pMatrix, 0); // p
      GLES32.glUniformMatrix4fv(GLES32.glGetUniformLocation(gameObject.material.program,"M"), 1, false, gameObject.mMatrix, 0); // m
      GLES32.glUniformMatrix4fv(GLES32.glGetUniformLocation(gameObject.material.program,"S"), 1, false, sun.ovbMatrix, 0);

      GLES32.glUniform3f(19, sun.transform.rx, sun.transform.ry, sun.transform.rz);

      /*MVP MATRIX*/
      GLES32.glBindVertexArray(gameObject.mesh.VAO[0]);
      GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, gameObject.mesh.vp.length);
    }
    for (SceneNode node : nodes) {
      node.render(this.gameObject, camera, sun);
    }
  }
  
  
  public void renderShadowmap(GameObject parent, Camera camera, DirectionalLight sun) {
    gameObject.applyTransform(parent);

    if (!gameObject.mesh.cullObject) {
      if (!gameObject.mesh.vaoUpdated) {
        gameObject.mesh.updateVAO();
      }
      MaterialShadowmap.material.use();
      /*MVP MATRIX*/
      
      GLES32.glUniformMatrix4fv(3, 1, false, gameObject.mMatrix, 0);//m
      GLES32.glUniformMatrix4fv(4, 1, false, sun.vMatrix, 0); // v
      GLES32.glUniformMatrix4fv(5, 1, false, sun.oMatrix, 0); // p
      
      GLES32.glUniform3f(19, sun.transform.rx, sun.transform.ry, sun.transform.rz);

      /*MVP MATRIX*/
      GLES32.glBindVertexArray(gameObject.mesh.VAO[0]);
      GLES32.glDrawArrays(GLES32.GL_TRIANGLES, 0, gameObject.mesh.vp.length);
    }
    for (SceneNode node : nodes) {
      node.renderShadowmap(this.gameObject, camera, sun);
    }
  }
  

  public void add(GameObject object) {
    nodes.add(new SceneNode(object));
  }
}
