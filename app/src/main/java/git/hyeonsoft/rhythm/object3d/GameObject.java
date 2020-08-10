package git.hyeonsoft.rhythm.object3d;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import git.hyeonsoft.rhythm.*;

public class GameObject {
    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;
    protected int mProgram;
    protected short[] mDrawOrder = {0, 1, 2};

    protected String vertexShaderCode =
            "#version 300 es\n" +
                    "uniform mat4 uMVPMatrix;" +
                    "uniform mat4 uTransform;"+
                    "layout (location=0) in vec3 aPos;" +
                    "out vec4 vertexColor;" +
                    "void main(){" +
                    " gl_Position = uMVPMatrix * uTransform * vec4(aPos, 1.0);" +
                    "}";

    protected String fragmentShaderCode =
            "#version 300 es\n" +
                    "precision mediump float;" +
                    "uniform vec4 vertexColor;" +
                    "out vec4 color;" +
                    "void main(){" +
                    " color = vertexColor;" +
                    "}";
    public GameObject(){}
    public GameObject(float[] coords, float[] color){
        this.coords = coords;
        this.color = color;
        buildCode();
    }

    public void buildCode(){

        int vertexShader = MainGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);

        int fragmentShader = MainGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        ByteBuffer drawByteBuffer = ByteBuffer.allocateDirect(mDrawOrder.length*2);
        drawByteBuffer.order(ByteOrder.nativeOrder());
        drawListBuffer = drawByteBuffer.asShortBuffer();
        drawListBuffer.put(mDrawOrder);
        drawListBuffer.position(0);

        mProgram = GLES30.glCreateProgram();

        GLES30.glAttachShader(mProgram, vertexShader);

        GLES30.glAttachShader(mProgram, fragmentShader);

        GLES30.glLinkProgram(mProgram);
    }



    public void draw(float[] mvpMatrix){
        GLES30.glUseProgram(mProgram);

        GLES30.glEnableVertexAttribArray(0);
        vertexBuffer.position(0);
        GLES30.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(mProgram, "uMVPMatrix"), 1, false, mvpMatrix, 0);
        float[] transform = new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(mProgram, "uTransform"), 1, false, transform, 0);
        GLES30.glUniform4fv(GLES30.glGetUniformLocation(mProgram, "vertexColor"), 1, color, 0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mDrawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES30.glDisableVertexAttribArray(0);
    }

    public void drawFromObject(float[] mvpMatrix, float[] transform){
        GLES30.glUseProgram(mProgram);

        GLES30.glEnableVertexAttribArray(0);
        vertexBuffer.position(0);
        GLES30.glVertexAttribPointer(0, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(mProgram, "uMVPMatrix"), 1, false, mvpMatrix, 0);
        GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(mProgram, "uTransform"), 1, false, transform, 0);
        GLES30.glUniform4fv(GLES30.glGetUniformLocation(mProgram, "vertexColor"), 1, color, 0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mDrawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES30.glDisableVertexAttribArray(0);

        GLES30.glGetProgramInfoLog(mProgram);
    }

    protected static final int COORDS_PER_VERTEX = 3;
    protected float[] coords;

    protected float[] color;
}
