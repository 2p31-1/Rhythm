package git.hyeonsoft.rhythm.game;

import git.hyeonsoft.rhythm.object3d.Cube;
import git.hyeonsoft.rhythm.object3d.GameObject3d;

public class NoteBar {
    public float time;
    GameObject3d m3dObject;
    public NoteBar(float time, float noteSize, float distance){
        m3dObject = new Cube(new float[]{0 , 0.05f, distance}, new float[]{noteSize, 0.05f, 0.05f}, new float[]{0.5f,0.5f,0.5f,0.5f});
        this.time = time;
    }
    public synchronized void update(float deltaTime){
        m3dObject.doTranslate(0, 0, -Note.NoteSpeed * deltaTime);
    }
    public synchronized void draw(float[] mvpMatrix){
        m3dObject.draw(mvpMatrix);
    }
    public synchronized boolean isTimeOver(double currentTime){return currentTime > time;}
}
