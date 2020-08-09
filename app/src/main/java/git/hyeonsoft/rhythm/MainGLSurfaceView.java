package git.hyeonsoft.rhythm;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import javax.security.auth.login.LoginException;

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
        //Log.i(e.getActionMasked()+"as", ""+(e.getX()/getWidth()*2-1)+", "+(-e.getY()/getHeight()*2+1));
        switch(e.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mRenderer.mGamePlay.touch(e.getX(e.getPointerCount()-1)/getWidth()*2-1, -e.getY(e.getPointerCount()-1)/getHeight()*2+1);
                break;
        }
        return true;
    }
}
