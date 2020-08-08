package git.hyeonsoft.rhythm.object3d;

public class Cube extends GameObject3d{
    public Cube(float[] pos, float[] size, float[] color){
        super();
        gameObjects = new GameObject[6];
        gameObjects[0] = new Square(new float[]{
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2]
        }, color);
        gameObjects[1] = new Square(new float[]{
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2]
        }, color);
        gameObjects[2] = new Square(new float[]{
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2]
        }, color);
        gameObjects[3] = new Square(new float[]{
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2]
        }, color);
        gameObjects[4] = new Square(new float[]{
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]-0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2]
        }, color);
        gameObjects[5] = new Square(new float[]{
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]+0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]+0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]-0.5f*size[2],
                pos[0]+0.5f*size[0], pos[1]-0.5f*size[1], pos[2]+0.5f*size[2]
        }, color);
    }
}
