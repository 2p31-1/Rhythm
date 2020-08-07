package git.hyeonsoft.rhythm;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import git.hyeonsoft.rhythm.object3d.*;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    private GameObject mGameObject;
    private final float[]mMVPMatrix = new float[16];
    private final float[]mProjectionMatrix = new float[16];
    private final float[]mViewMatrix = new float[16];


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glClearColor(0.5f, 0.4f, 0.3f, 1);

        mGameObject = new Square(new float[]{
                -2f,0, -0f,
                2f, 0, 0,
                2f, 0, 100,
                -2f, 0, 100
        },
                new float[] {0.9f, 0.9f, 0.9f, 1});
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        //3차원 공간의 점을 2차원 화면에 보여주기 위한 projection matrix의 정의
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 80);
    }

    public float camEyeY, camEyeZ;
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //카메라 위치
        Matrix.setLookAtM(mViewMatrix, 0, 0, 1.5f, -4f, 0, 0,10, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0); //행렬곱 저장

        mGameObject.draw(mMVPMatrix);
    }

    public static int loadShader(int type, String shaderCode){
        //type : GLES30.GL_VERTEX_SHADER : vertex shader type
        //     : GLES30.GL_FRAGMENT_SHADER) : fragment shader type
        int shader = GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader, shaderCode);

        GLES30.glCompileShader(shader);

        Log.e("에러", GLES30.glGetShaderInfoLog(shader));

        return shader;
    }

}
