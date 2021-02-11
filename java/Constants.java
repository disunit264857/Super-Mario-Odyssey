package com.disunit.supermarioodyssey;

import android.graphics.Bitmap;
import android.graphics.Color;


public class Constants {

public static float Deg2Rad = (float)(Math.PI/180);

public static Bitmap blackT = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
public static Bitmap whiteT = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
public static Bitmap noT = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);


static{
  blackT.eraseColor(Color.BLACK);
  whiteT.eraseColor(Color.WHITE);
  noT.eraseColor(Color.MAGENTA);
}

}
