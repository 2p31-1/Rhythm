package git.hyeonsoft.rhythm;

import android.content.Context;
import android.opengl.GLSurfaceView;
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
        if((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN)mRenderer.mGamePlay.touch(e.getX()/getWidth(), e.getY()/getHeight());
        return true;
    }
}
