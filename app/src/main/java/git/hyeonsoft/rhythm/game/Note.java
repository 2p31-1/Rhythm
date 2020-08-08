package git.hyeonsoft.rhythm.game;

import git.hyeonsoft.rhythm.object3d.Cube;
import git.hyeonsoft.rhythm.object3d.GameObject3d;

public class Note {
    public float time;
    public static float NoteSpeed;
    GameObject3d m3dObject;
    public Note(float time, float noteSize, float notePosX, float distance){
        m3dObject = new Cube(new float[]{notePosX, 0.06f, distance}, new float[]{noteSize, 0.1f, 0.1f}, new float[]{1f, 1f, 1f, 1f});
        this.time = time;
    }
    public void update(float deltaTime){
        m3dObject.doTranslate(0, 0, -NoteSpeed * deltaTime);
    }
    public void draw(float[] mvpMatrix){
        m3dObject.draw(mvpMatrix);
    }
    boolean isTimeOver(double currentTime)
    {
        return currentTime - time > 0.1;
    }
}

