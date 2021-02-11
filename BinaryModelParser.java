package com.disunit.supermarioodyssey.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.disunit.supermarioodyssey.Constants;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.scene.GameObject;
import com.disunit.supermarioodyssey.scene.MaterialPhong;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;

public class BinaryModelParser {
        
        public static LinkedList<GameObject> parseModel(Context ctx,String base,String url){
          BufferedReader reader = null;
  LinkedList<GameObject> output = new LinkedList<GameObject>();
  
  try{
    reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open("models/"+base+url),"UTF-8"));
    String line="";
    while((line = reader.readLine())!=null){
      GameObject object = new GameObject();
      InputStream is = ctx.getAssets().open("models/"+base+line+".data");
      object.material = importMaterial(ctx,base,line+".mat");
      int size = is.available();
      byte[] buffer = new byte[size/3];
      object.mesh.vp = new float[size/12];
      object.mesh.vt = new float[size/12];
      object.mesh.vn = new float[size/12];
      is.read(buffer);
      FloatBuffer tmp = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).asFloatBuffer();
      tmp.position(0);
      tmp.get(object.mesh.vp);
      
      is.read(buffer);
            tmp = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).asFloatBuffer();
      tmp.position(0);
      tmp.get(object.mesh.vt);
      
      is.read(buffer);
            tmp = ByteBuffer.wrap(buffer).order(ByteOrder.BIG_ENDIAN).asFloatBuffer();
      tmp.position(0);
      tmp.get(object.mesh.vn);
      
      output.add(object);
    }
    
    }catch(IOException e){
      MainActivity.tv.append("IOEXCEPTION:"+e.getMessage());
    }
    return output;
        }
       public static MaterialPhong importMaterial(Context ctx,String base,String url){
  BufferedReader reader = null;
  MaterialPhong mat = new MaterialPhong();
  try{
    reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open("models/"+base+url),"UTF-8"));
    String line="";
    int l = 0;
    while((line = reader.readLine())!=null){
    switch(l++){
      case 0:
      mat.Kar = Float.parseFloat(line);
      break;
      case 1:
      mat.Kag = Float.parseFloat(line);
      break;
      case 2:
      mat.Kab = Float.parseFloat(line);
      break;
      
      case 3:
      mat.Kdr = Float.parseFloat(line);
      break;
      case 4:
      mat.Kdg = Float.parseFloat(line);
      break;
      case 5:
      mat.Kdb = Float.parseFloat(line);
      break;
      
      case 6:
      mat.Ksr = Float.parseFloat(line);
      break;
      case 7:
      mat.Ksg = Float.parseFloat(line);
      break;
      case 8:
      mat.Ksb = Float.parseFloat(line);
      break;
      
      case 9:
      mat.Ns = Float.parseFloat(line);
      break;
      
      case 10:
      mat.map_Ka = importTexture(ctx,"models/"+base+line);
      break;
      case 11:
      mat.map_Kd = importTexture(ctx,"models/"+base+line);
      break;
    }
    }
    }catch(IOException e){
      
    }
    return mat;
  }
  public static Bitmap importTexture(Context ctx,String url){
  InputStream istr;
    Bitmap bitmap = Constants.whiteT;
    try {
        istr = ctx.getAssets().open(url);
        bitmap = BitmapFactory.decodeStream(istr);
    } catch (IOException e) {
        MainActivity.tv.append("Message:"+e.getMessage()+"\n");
    }
    
    return bitmap;
}

}
