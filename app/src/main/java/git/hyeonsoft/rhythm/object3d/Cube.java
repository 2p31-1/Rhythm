package git.hyeonsoft.rhythm.object3d;

public class Cube extends GameObject3d{
    Cube(float[] pos, float[] size, float[] color){
        this.pos=pos;
        this.size = size;
        gameObjects = new GameObject[4];
    }
}
