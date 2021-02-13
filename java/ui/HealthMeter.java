package com.disunit.supermarioodyssey.ui;

import android.graphics.*;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;


public class HealthMeter extends UI {

  public int health; // 0 to 3
  private int healthB;
  private int bw, bh;
  private Bitmap texture;
  private Bitmap heart;
  private Canvas canvas;
  
  private Paint hpPaint;
  private Paint emptyPaint;
  private Paint separaterPaint;

  private float[] hsv;
  
  private long startTime;

private float gap = 0.06f;

  public HealthMeter(int w, int h) {
    super(w,h);
    bw = w;
    bh = h;
    healthB = 0;
    health = 2;
    texture = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    canvas = new Canvas(texture);
    hpPaint = new Paint();
    hpPaint.setStyle(Paint.Style.STROKE);
    hpPaint.setAntiAlias(true);
    hpPaint.setStrokeWidth(bw*gap*2);
    
    emptyPaint = new Paint();
    emptyPaint.setStyle(Paint.Style.STROKE);
    emptyPaint.setAntiAlias(true);
    emptyPaint.setStrokeWidth(bw*gap*2);
    
    emptyPaint.setColor(Color.WHITE);
    
    separaterPaint = new Paint();
    separaterPaint.setStyle(Paint.Style.STROKE);
    separaterPaint.setAntiAlias(true);
    separaterPaint.setStrokeWidth(bw*0.02f);
    separaterPaint.setARGB(50,0,0,0);
    separaterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    hsv = new float[]{0,1,1};
    startTime = 0;
    
    
    
    
    
    
    heart = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas tmp = new Canvas(heart);
    Path hPath = new Path();
    hPath.moveTo(w*0.5f,h*0.25f);
    hPath.quadTo(w*0.35f,h*0.35f,w*0.3f,h*0.45f);
    
    hPath.lineTo(w*0.7f,h*0.45f);
    
    hPath.quadTo(w*0.65f,h*0.35f,w*0.5f,h*0.25f);
    hPath.close();
    Paint hPaint = new Paint();
    hPaint.setAntiAlias(true);
    hPaint.setColor(Color.WHITE);
    tmp.drawPath(hPath,hPaint);
    tmp.drawCircle(w*(0.5f-0.1f),h*(0.45f+0.1f),w*0.1f*1.4f,hPaint);
    tmp.drawCircle(w*(0.5f+0.1f),h*(0.45f+0.1f),w*0.1f*1.4f,hPaint);
  }
  

  public void setHealth() {
    
    startTime = System.currentTimeMillis();
  }

  public void render() {
    canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
  long delta;
  float clampedDelta;
  if((delta=System.currentTimeMillis()-startTime)<=600f){
  clampedDelta = (float)Math.min(delta,500);
  hsv[0] = 60*(healthB+(health-healthB)*(float)clampedDelta/500f)-60;
    hpPaint.setColor(Color.HSVToColor(hsv));
  float angle = 120*(healthB+(health-healthB)*clampedDelta/500f);
    
    
    canvas.drawArc(bw*gap,bh*gap,bw*(1-gap),bh*(1-gap),90,-angle,false,hpPaint);
    canvas.drawArc(bw*gap,bh*gap,bw*(1-gap),bh*(1-gap),90-angle,-360+angle,false,emptyPaint);
    
    canvas.drawLine(bw*0.5f,bh*0.5f,bw*0.5f,bh,separaterPaint);
    canvas.drawLine(bw*0.5f,bh*0.5f,bw*(0.5f+(float)Math.cos(210f/180*Math.PI)),bh*(0.5f+(float)Math.sin(210f/180*Math.PI)),separaterPaint);
    
    
    canvas.drawLine(bw*0.5f,bh*0.5f,bw*(0.5f+(float)Math.cos(330f/180*Math.PI)),bh*(0.5f+(float)Math.sin(330f/180*Math.PI)),separaterPaint);
    
    canvas.drawBitmap(heart,null,new RectF(0,0,bw,bh),null);
    
    material.updateTextureData(texture);
    material.isUpToDate = false;
    }
  }
}
