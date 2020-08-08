package git.hyeonsoft.rhythm;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import git.hyeonsoft.rhythm.game.*;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    private final float[]mMVPMatrix = new float[16];
    private final float[]mProjectionMatrix = new float[16];
    private final float[]mViewMatrix = new float[16];
    public GamePlay mGamePlay;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        mGamePlay = new GamePlay(this);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width/height;
        //3차원 공간의 점을 2차원 화면에 보여주기 위한 projection matrix의 정의
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 200);
    }

    float cameyeY=0, cameyeZ=0;
    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT|GLES30.GL_DEPTH_BUFFER_BIT);
        //카메라 위치
        Matrix.setLookAtM(mViewMatrix, 0, 0, 1.5f, -4f, 0, 0,10, 0, 1, 0);
        //Matrix.setLookAtM(mViewMatrix, 0, 0, 2.637207f, -7.7922363f, 0, 0,10, 0, 1, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0); //행렬곱 저장
        mGamePlay.draw(mMVPMatrix);
    }

    public static float[] vec3ToScreenPos(float[] mMVPMatrix, float x, float y, float z){
        float[] pos = new float[]{-2f, 0, 0, 1};
        float[] screenPos;
        screenPos = new float[4];
        //Matrix.multiplyMV(screenPos, 0, mViewMatrix, 0, pos, 0);
        Matrix.multiplyMV(screenPos, 0, mMVPMatrix, 0, pos, 0);
        screenPos[0]/=screenPos[3];
        screenPos[1]/=screenPos[3];//동차좌표 w 위치가 1이 아니므로 w 값으로 나누어준다. (normalize)
        return screenPos;
    }

    public static int loadShader(int type, String shaderCode){
        //type : GLES30.GL_VERTEX_SHADER : vertex shader type
        //     : GLES30.GL_FRAGMENT_SHADER) : fragment shader type
        int shader = GLES30.glCreateShader(type);

        GLES30.glShaderSource(shader, shaderCode);

        GLES30.glCompileShader(shader);

        //Log.e("에러", GLES30.glGetShaderInfoLog(shader));

        return shader;
    }

}
