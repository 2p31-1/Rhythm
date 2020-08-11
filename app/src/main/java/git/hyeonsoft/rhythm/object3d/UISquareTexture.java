package git.hyeonsoft.rhythm.object3d;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import git.hyeonsoft.rhythm.MainActivity;
import git.hyeonsoft.rhythm.MainGLRenderer;
import git.hyeonsoft.rhythm.R;

public class UISquareTexture extends GameObject{
    int[] textures = new int[1];
    Bitmap bitmap;
    float[] texture;
    public float[] size;
    public float[] pos;
    FloatBuffer textureBuffer;
    public UISquareTexture(float[] pos, float[] size, float[] color, Bitmap bitmap){
        vertexShaderCode="#version 300 es\n" +
                "layout (location = 0) in vec2 inPos;" +
                "layout (location = 1) in vec2 aTexCoord;" +
                "" +
                "out vec2 TexCoord;" +
                "out vec4 Ccolor;" +
                "uniform mat4 mTransform;" +
                "uniform vec4 ourColor;" +
                "" +
                "void main()" +
                "{" +
                "    gl_Position = mTransform * vec4(inPos.xy, 0, 1);" +
                "    Ccolor=outColor;" +
                "    TexCoord = aTexCoord;" +
                "}";
        fragmentShaderCode = "#version 300 es\n" +
                "precision mediump float;" +
               // "uniform sampler2D ourTexture;" +
                "in vec2 TexCoord;" +
                "in vec4 Ccolor;" +
                "out vec4 color;" +
                "void main()" +
                "{" +
                "    " +//color = texture(ourTexture, TexCoord) * vec4(ourColor, 1.0);
                "    color = CColor;" +
                "}";
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        this.coords = new float[]{
                0.5f,  0.5f,     // 우측 상단
                0.5f, -0.5f,     // 우측 하단
                -0.5f, -0.5f,    // 좌측 하단
                -0.5f,  0.5f,    // 좌측 상단
        };
        this.texture = new float[]{
                1.0f, 0.0f,   // 우측 상단
                1.0f, 1.0f,   // 우측 하단
                0.0f, 1.0f,   // 좌측 하단
                0.0f, 0.0f    // 좌측 상단
        };

        this.pos = pos;
        this.size = size;
        this.color = color;
        this.bitmap = bitmap;
        this.mDrawOrder = new short[]{0, 1, 2, 0, 2, 3};
        buildCode();
    }
    public UISquareTexture(float[] pos, float[] size, float[] color, Bitmap bitmap, float[] texture){
        vertexShaderCode="#version 300 es\n" +
                "layout (location = 0) in vec2 inPos;" +
                "layout (location = 1) in vec2 aTexCoord;" +
                "" +
                "out vec2 TexCoord;" +
                "" +
                "uniform mat4 mTransform;" +
                "" +
                "void main()" +
                "{" +
                "    gl_Position = mTransform * vec4(inPos.xy, 0, 1.0);" +
                "    TexCoord = aTexCoord;" +
                "}";
        fragmentShaderCode = "#version 300 es\n" +
                "precision mediump float;" +
                "out vec4 color;" +
                "" +
                "in vec2 TexCoord;" +
                "" +
                "uniform sampler2D ourTexture;" +
                "uniform vec3 ourColor;" +
                "" +
                "void main()" +
                "{" +
                "    //color = texture(ourTexture, TexCoord) * vec4(ourColor, 1.0);" +
                "    color = vec4(1, 1, 1, 1);" +
                "}";

        GLES30.glGenTextures(1, textures, 0);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        this.coords = new float[]{
                0.5f,  0.5f,    // 우측 상단
                0.5f, -0.5f,    // 우측 하단
                -0.5f, -0.5f,   // 좌측 하단
                -0.5f,  0.5f,   // 좌측 상단
        };
        this.pos = pos;
        this.size = size;
        this.texture = texture;
        this.color = color;
        this.bitmap = bitmap;
        this.mDrawOrder = new short[]{0, 1, 2, 0, 2, 3};
        buildCode();
    }

    @Override
    public void buildCode() {

        int vertexShader = MainGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);

        int fragmentShader = MainGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        ByteBuffer tb = ByteBuffer.allocateDirect(texture.length*4);
        tb.order(ByteOrder.nativeOrder());
        textureBuffer = tb.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

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

    @Override
    public void draw(float[] mvpMatrix) {
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);


        GLES30.glEnableVertexAttribArray(0);
        GLES30.glEnableVertexAttribArray(1);
        vertexBuffer.position(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 0, textureBuffer);

        float[] mMatrix = new float[]{1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
        Matrix.scaleM(mMatrix, 0, size[0]/ MainGLRenderer.ratio, size[1], 0);
        Log.e("dr", "draw: "+MainGLRenderer.ratio);
        Matrix.translateM(mMatrix, 0, pos[0], pos[1], 0);


        GLES30.glUniformMatrix4fv(GLES30.glGetUniformLocation(mProgram, "mTransform"), 1, false, mMatrix, 0);

        GLES30.glUniform4fv(GLES30.glGetUniformLocation(mProgram, "ourColor"), 1, color, 0);

       // GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, BitmapFactory.decodeResource(MainActivity.resources, R.drawable.combo), 0);

        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mDrawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);

        GLES30.glDisableVertexAttribArray(0);

    }
}
