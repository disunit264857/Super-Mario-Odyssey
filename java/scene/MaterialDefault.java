package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ShaderStorage;


public class MaterialDefault extends Material {

public void compile(){
  program = GLES32.glCreateProgram();
  int vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER);
  int fs = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER);
  GLES32.glShaderSource(vs,ShaderStorage.default_vertex);
  GLES32.glShaderSource(fs,ShaderStorage.default_fragment);
  GLES32.glCompileShader(vs);
  GLES32.glCompileShader(fs);
  GLES32.glAttachShader(program,vs);
  GLES32.glAttachShader(program,fs);
  GLES32.glLinkProgram(program);
  MainActivity.tv.append(GLES32.glGetShaderInfoLog(vs));
  MainActivity.tv.append(GLES32.glGetShaderInfoLog(fs));
}

public void use(){
  GLES32.glUseProgram(program);
}

public void update(){
  
}

}
