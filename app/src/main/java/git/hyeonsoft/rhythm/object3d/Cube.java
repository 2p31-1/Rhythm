package git.hyeonsoft.rhythm.object3d;

public class Cube extends GameObject3d{
    public Cube(float[] pos, float[] size, float[] color){
        super(pos, size);
        gameObjects = new GameObject[6];
        gameObjects[0] = new Square(new float[]{
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f
        }, color);
        gameObjects[1] = new Square(new float[]{
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f
        }, color);
        gameObjects[2] = new Square(new float[]{
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f
        }, color);
        gameObjects[3] = new Square(new float[]{
                0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f
        }, color);
        gameObjects[4] = new Square(new float[]{
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f
        }, color);
        gameObjects[5] = new Square(new float[]{
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f
        }, color);
    }
}
