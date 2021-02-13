package com.disunit.supermarioodyssey;

import android.content.Context;
import com.disunit.supermarioodyssey.MainActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ShaderStorage {

public static String phong_vertex;
public static String phong_fragment;

public static String default_vertex;
public static String default_fragment;

public static String ui_vertex;
public static String ui_fragment;

public static String shadowmap_vertex;
public static String shadowmap_fragment;

public static void loadShader(Context ctx){
  phong_vertex = readFile("shaders/phong_vertex.glsl",ctx);
  phong_fragment = readFile("shaders/phong_fragment.glsl",ctx);
  default_vertex = readFile("shaders/default_vertex.glsl",ctx);
  default_fragment = readFile("shaders/default_fragment.glsl",ctx);
  ui_vertex = readFile("shaders/ui/ui_vertex.glsl",ctx);
  ui_fragment = readFile("shaders/ui/ui_fragment.glsl",ctx);
  shadowmap_vertex = readFile("shaders/shadowmap/shadowmap_vertex.glsl",ctx);
  shadowmap_fragment = readFile("shaders/shadowmap/shadowmap_fragment.glsl",ctx);
}

public static String readFile(String url,Context ctx){
  BufferedReader reader = null;
  try{
    reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(url),"UTF-8"));
    String line;
    String content = "";
    while((line = reader.readLine())!=null){
      content += line+"\n";
    }
    return content;
  }catch(IOException e){
    MainActivity.tv.append(e.getMessage());
  }
  return "";
}


}
