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
        float x = e.getX()/getWidth();
        float y = e.getY()/getHeight();
        mRenderer.mGamePlay.onTouch(x, y);
        mRenderer.cameyeY = x*8-4;
        mRenderer.cameyeZ = y*8-8;
        return true;
    }
}
