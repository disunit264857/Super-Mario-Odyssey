package com.disunit.supermarioodyssey.scene;

import android.opengl.GLES32;
import android.opengl.GLUtils;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.ShaderStorage;

public class MaterialPhong extends Material {

  public void compile() {
    program = GLES32.glCreateProgram();
    int vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER);
    int fs = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER);
    GLES32.glShaderSource(vs, ShaderStorage.phong_vertex);
    GLES32.glShaderSource(fs, ShaderStorage.phong_fragment);
    GLES32.glCompileShader(vs);
    GLES32.glCompileShader(fs);
    GLES32.glAttachShader(program, vs);
    GLES32.glAttachShader(program, fs);
    GLES32.glLinkProgram(program);
    MainActivity.tv.append(GLES32.glGetShaderInfoLog(vs));
    MainActivity.tv.append(GLES32.glGetShaderInfoLog(fs));
    compiled = true;
    GLES32.glUseProgram(program);
    GLES32.glGenTextures(2, textures, 0);

    /*AMBIENT TEXTURE*/
    GLES32.glActiveTexture(GLES32.GL_TEXTURE0);
    GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, textures[0]);
    GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, GLES32.GL_NEAREST);
    GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER, GLES32.GL_NEAREST);

    GLUtils.texImage2D(GLES32.GL_TEXTURE_2D, 0, map_Ka, 0);
    /*AMBIENT TEXTURE*/

    /*DIFFUSE TEXTURE*/
    GLES32.glActiveTexture(GLES32.GL_TEXTURE1);
    GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, textures[1]);
    GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MIN_FILTER, GLES32.GL_NEAREST);
    GLES32.glTexParameterf(GLES32.GL_TEXTURE_2D, GLES32.GL_TEXTURE_MAG_FILTER, GLES32.GL_NEAREST);
    GLUtils.texImage2D(GLES32.GL_TEXTURE_2D, 0, map_Kd, 0);
    /*DIFFUSE TEXTURE*/

    /* TEXTURE UNIFORM*/
    GLES32.glUniform1i(20, 0); // AMBIENT
    GLES32.glUniform1i(21, 1); // DIFFUSE
    /*TEXTURE UNIFORM*/
  }

  public void use() {
    GLES32.glUseProgram(program);
    GLES32.glActiveTexture(GLES32.GL_TEXTURE0);
    GLES32.glBindTexture(GLES32.GL_TEXTURE_2D, textures[1]);
  }

  public void update() {

    GLES32.glUniform1f(25, Ns);
    GLES32.glUniform3f(23, Kar, Kag, Kab);
    GLES32.glUniform3f(24, Kdr, Kdg, Kdb);
    GLES32.glUniform3f(26, Ksr, Ksg, Ksb);
  }
}
