package git.hyeonsoft.rhythm.object3d;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.view.View;

import git.hyeonsoft.rhythm.MainActivity;
import git.hyeonsoft.rhythm.R;

public class SquareTexture extends GameObject{
    int[] textures = new int[1];
    Bitmap bitmap;
    public SquareTexture(float[] coords, float[] color, Bitmap bitmap){
        super();
        vertexShaderCode="#version 300 es\n" +
                "layout (location = 0) in vec3 aPos;" +
                "layout (location = 1) in vec3 aColor;" +
                "layout (location = 2) in vec2 aTexCoord;" +
                "" +
                "out vec3 ourColor;" +
                "out vec2 TexCoord;" +
                "" +
                "" +
                "" +
                "void main()" +
                "{" +
                "    gl_Position = vec4(aPos, 1.0);" +
                "    ourColor = aColor;" +
                "    TexCoord = aTexCoord;" +
                "}";
        fragmentShaderCode = "#version 300 es\n" +
                "out vec4 FragColor;" +
                "" +
                "in vec3 ourColor;" +
                "in vec2 TexCoord;" +
                "" +
                "uniform sampler2D ourTexture;" +
                "" +
                "void main()" +
                "{" +
                "    color = texture(ourTexture, TexCoord);" +
                "}";
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        int a = R.drawable.combo;
        this.coords = coords;
        this.color = color;
        this.bitmap = bitmap;
        this.mDrawOrder = new short[]{0, 1, 2, 0, 2, 3};
        buildCode();
    }

    @Override
    public void draw(float[] mvpMatrix) {
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);


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
}
