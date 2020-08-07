package git.hyeonsoft.rhythm.object3d;

import android.opengl.Matrix;
import android.util.Log;

public class GameObject3d {
    protected float[] pos=null;
    protected float[] size = null;
    protected GameObject[] gameObjects = null;
    protected float[] transform = new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    public void setTransform(float x, float y, float z){
        Matrix.translateM(transform, 0, x, y, z);
        for(int i=0;i<16;i++)
        Log.i("trans"+i, "setTransform: "+transform[i]);
    }
    public void draw(float[] mvpMatrix){
        for(GameObject x:gameObjects){
            x.draw(mvpMatrix);
        }
    }
}
