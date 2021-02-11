package com.disunit.supermarioodyssey;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.disunit.supermarioodyssey.util.BinaryModelParser;
import com.disunit.supermarioodyssey.MainActivity;
import com.disunit.supermarioodyssey.scene.*;
import com.disunit.supermarioodyssey.scene.RenderQueue;
import com.disunit.supermarioodyssey.util.OBJParser;
import java.util.LinkedList;

public class MainActivity extends Activity {
    
    
    public RenderQueue renderQueue;
    
    public static TextView tv;
    public static ImageView iv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        tv = new TextView(this);
        iv = new ImageView(this);
        
        ShaderStorage.loadShader(this);
        
        
        LinkedList<GameObject> model = BinaryModelParser.parseModel(this,"Test/","Ground002.mesh");
        renderQueue = new RenderQueue();
        
        for(GameObject object : model){
        object.transform.rx = 0;
        object.mesh.updateBuffer();
        //tv.append(object.mesh.vp[0]+"");
        renderQueue.queue.add(object);
        }
        
        
        GLSurfaceView glView = new GLSurfaceView(this);
        glView.setEGLContextClientVersion(3);
        GLRenderer renderer = new GLRenderer(this);
        
        
        
        glView.setRenderer(renderer);
        
        
        FrameLayout fl = new FrameLayout(this);
        
        fl.addView(glView);
        fl.addView(tv);
        fl.addView(iv);
        
        
        setContentView(fl);
        
    }

}
