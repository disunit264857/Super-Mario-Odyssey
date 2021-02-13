package com.disunit.supermarioodyssey.scene;

import android.graphics.Bitmap;
import com.disunit.supermarioodyssey.Constants;

public abstract class Material {

public int program;
public boolean compiled;
public boolean upToDate;

public float Kar,Kag,Kab;
public float Kdr,Kdg,Kdb;
public float Ksr,Ksg,Ksb;

public float Ns;

public Bitmap map_Ka;
public Bitmap map_Kd;

public int[] textures;

{
  compiled = false;
  upToDate = false;
  Kar = Kag = Kab = 0;
  Kdr = Kdg = Kdb = 0;
  Ksr = Ksg = Ksb = 0;
  Ns = 60;
  map_Ka = Constants.whiteT;
  map_Kd = Constants.whiteT;
  textures = new int[2];
}


public abstract void use();
public abstract void compile();
public abstract void update();

}
