package git.hyeonsoft.rhythm.object3d;

public class Square extends GameObject{
    public Square(float[] coords, float[] color){
        super();
        this.coords = coords;
        this.color = color;
        this.mDrawOrder = new short[]{0, 1, 2, 0, 2, 3};
        buildCode();
    }
}
