package hu.za.pc_remote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.*;
import android.util.Log;
import hu.za.pc_remote.common.RCAction;
import hu.za.pc_remote.transport.ConnectionHandlingService;
import hu.za.pc_remote.ui.ConnectionSettings;

import java.lang.reflect.Type;

public class HelloActivity extends Activity {
    GestureDetector gestureDetector;
    MySurface surface;
    Drawer drawer;

    float x;
    float y;
    float vx;
    float vy;
    static final float a = 50;
    Paint paint;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(new MyGestureListener());
        surface = new MySurface();
        x = 100;
        y = 100;
        paint = new Paint();
        paint.setColor(Color.BLUE);

        setContentView(surface);

        surface.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        Intent i = new Intent(this, ConnectionHandlingService.class);
        startService(i);

        Intent i2 = new Intent(this, ConnectionSettings.class);
        startActivityForResult(i2, RESULT_OK);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            vx += velocityX;
            vy += velocityY;

            Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            RCAction extra = new RCAction();
            extra.type = RCAction.Type.MOUSE_MOVE;
            extra.arguments = new float[]{0, 0, velocityX, velocityY};
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);
            sendBroadcast(i);

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            x -= dx;
            y -= dy;

            Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            RCAction extra = new RCAction();
            extra.type = RCAction.Type.MOUSE_MOVE;
            extra.arguments = new float[]{dx, dy, 0, 0};
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);
            sendBroadcast(i);

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            x = e.getX();
            y = e.getY();
            return true;
        }
    }

    private class MySurface extends SurfaceView implements SurfaceHolder.Callback {

        public MySurface() {
            super(HelloActivity.this);
            getHolder().addCallback(this);
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
    }

    private class Drawer extends Thread {
        public boolean running = true;

        public void run() {
            while (running) {
                x += (vx / 100);
                y += (vy / 100);

                vx = nextValue(vx);
                vy = nextValue(vy);

                Canvas c = surface.getHolder().lockCanvas();
                c.drawColor(Color.BLACK);
                c.drawCircle(x, y, 20, paint);
                surface.getHolder().unlockCanvasAndPost(c);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Log.w("hello", "Drawer thread interrupted");
                }
            }
        }
    }

    private static float nextValue(float f) {
        if (f == 0)
            return 0;
        float i = f > 0 ? 1 : -1;

        f = f - (a * i);
        if ((f * i) < 0)
            f = 0;
        return f;
    }
}
