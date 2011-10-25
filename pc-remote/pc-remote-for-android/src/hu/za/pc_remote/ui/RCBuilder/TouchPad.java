package hu.za.pc_remote.ui.RCBuilder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.*;
import hu.za.pc_remote.common.RCAction;
import hu.za.pc_remote.transport.ConnectionHandlingService;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/12/11
 * Time: 7:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class TouchPad extends SurfaceView implements SurfaceHolder.Callback {
    Context context;
    GestureDetector gestureDetector;
    Drawer drawer;

    float x;
    float y;
    float vx;
    float vy;
    static final float a = 50;
    Paint paint;

    public TouchPad(Context context) {
        super(context);
        this.context = context;

        gestureDetector = new GestureDetector(new MyGestureListener());
        x = 100;
        y = 100;
        paint = new Paint();
        paint.setColor(Color.BLUE);

        getHolder().addCallback(this);
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawer = new Drawer();
        drawer.start();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        drawer.running = false;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            vx += velocityX;
            vy += velocityY;

 /*           Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            RCAction extra = new RCAction();
            extra.type = RCAction.Type.MOUSE_MOVE;
            extra.arguments = new Float[]{new Float(0), new Float(0), velocityX, velocityY};
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);
            context.sendBroadcast(i);*/

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            x -= dx;
            y -= dy;

            Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            RCAction extra = new RCAction();
            extra.type = RCAction.Type.MOUSE_MOVE;
            extra.arguments = new Float[]{-dx, -dy, new Float(0), new Float(0)};
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);
            context.sendBroadcast(i);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            x = e.getX();
            y = e.getY();

            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            RCAction extra = new RCAction();
            extra.type = RCAction.Type.MOUSE_CLICK;
            extra.arguments = new Integer[]{new Integer(1)};
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);
            context.sendBroadcast(i);

            return true;
        }
    }

    private class Drawer extends Thread {
        public boolean running = true;

        public void run() {
            while (running) {
                x += (vx / 100);
                y += (vy / 100);

                vx = nextValue(vx);
                vy = nextValue(vy);

                Canvas c = getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawCircle(x, y, 20, paint);
                getHolder().unlockCanvasAndPost(c);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Log.w("hello", "Drawer thread interrupted");
                }
            }
        }

        private float nextValue(float f) {
            if (f == 0)
                return 0;
            float i = f > 0 ? 1 : -1;

            f = f - (a * i);
            if ((f * i) < 0)
                f = 0;
            return f;
        }
    }

}

