
package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import android.opengl.GLUtils;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ShaderStorage;

public class MaterialShadowmap extends Material {

  public static MaterialShadowmap material;
  
  static{
    material = new MaterialShadowmap();
  }
  
  public void compile() {
    program = GLES32.glCreateProgram();
    int vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER);
    int fs = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER);
    GLES32.glShaderSource(vs, ShaderStorage.shadowmap_vertex);
    GLES32.glShaderSource(fs, ShaderStorage.shadowmap_fragment);
    GLES32.glCompileShader(vs);
    GLES32.glCompileShader(fs);
    GLES32.glAttachShader(program, vs);
    GLES32.glAttachShader(program, fs);
    GLES32.glLinkProgram(program);
    MainActivity.tv.append(GLES32.glGetShaderInfoLog(vs));
    MainActivity.tv.append(GLES32.glGetShaderInfoLog(fs));
    compiled = true;
    GLES32.glUseProgram(program);
  }

  public void use() {
    GLES32.glUseProgram(program);
  }
  public void update(){}
}
