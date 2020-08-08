package git.hyeonsoft.rhythm.object3d;

import android.opengl.Matrix;

public class GameObject3d {
    protected GameObject[] gameObjects = null;
    protected float[] transform;
    public void resetTransform(){
        transform = new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    }
    public GameObject3d(){
        resetTransform();
    }
    public GameObject3d(float[] pos, float[] size){
        resetTransform();
        doScale(size);
        doTranslate(pos);
    }
    public void doTranslate(float x, float y, float z){
        //Scale first, Rotate next, translate finally.
        Matrix.translateM(transform, 0, x, y, z);
    }
    public void doTranslate(float[] vec){
        //Scale first, Rotate next, translate finally.
        Matrix.translateM(transform, 0, vec[0], vec[1], vec[2]);
    }
    public void doRotate(float angle, float x, float y, float z){
        Matrix.rotateM(transform, 0, angle, x, y, z);
    }
    public void doScale(float x, float y, float z){
        Matrix.scaleM(transform, 0, x, y, z);
    }
    public void doScale(float[] vec){
        Matrix.scaleM(transform, 0, vec[0], vec[1], vec[2]);
    }
    public void draw(float[] mvpMatrix){
        for(GameObject x:gameObjects){
            x.drawFromObject(mvpMatrix, transform);
        }
    }
}
