package git.hyeonsoft.rhythm;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class MainGLSurfaceView extends GLSurfaceView {

    private final MainGLRenderer mRenderer;

    public MainGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(3);

        mRenderer = new MainGLRenderer();

        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 32;
    @Override
    public boolean onTouchEvent(MotionEvent e){
        float x = e.getX()/getWidth()*4;
        float y = e.getY()/getHeight()*4-4;
        //Log.i("터치", ""+x+","+y);
        mRenderer.camEyeY=x;
        mRenderer.camEyeZ=y;
        return true;
    }
}
