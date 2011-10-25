package hu.za.pc_remote.desktop_agent;

import hu.za.pc_remote.common.KeyCode;
import hu.za.pc_remote.common.RCAction;
import org.apache.log4j.Logger;


import static hu.za.pc_remote.common.RCAction.Type.*;


/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/24/11
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventSender {

    private static Logger logger = Logger.getLogger(EventSender.class);

    private Mover mover = null;

    static{
        System.load("C:\\Users\\Andor\\diploma\\git\\pc-remote\\desktop-agent\\src\\main\\resources\\inputsender_x64.dll");
    }



    native void sendMouseMove(int x, int y);

    native void sendMouseClick(int button);

    native void sendKeyPress(int key);

    public void send(RCAction action) {
        if(mover != null){
            mover.running = false;
        }
        switch (action.type) {
            case MOUSE_MOVE:
                float x = (Float)action.arguments[0];
                float y = (Float)action.arguments[1];
                float vx = (Float)action.arguments[2];
                float vy = (Float)action.arguments[3];
/*                if(vy == 0 && vy == 0){*/
                    sendMouseMove((int)x, (int)y);
/*                }
                else{
                    mover = new Mover(vx, vy);
                    mover.start();
                }*/
                break;
            case MOUSE_CLICK:
                sendMouseClick((Integer)action.arguments[0]);
                break;
            case KEY_PRESS:
                sendKeyPress(((KeyCode)action.arguments[0]).getCode());
                break;
            case COMMAND:
                logger.debug("Command arived " + action);
                break;
        }
    }

    private class Mover extends Thread {
        public boolean running = true;
        private float vx,vy;
        private static final float a = 50;

        public Mover(float vx, float vy){
            this.vx = vx;
            this.vy = vy;
        }

        public void run() {
            while (running && vx != 0 && vy != 0) {
                sendMouseMove((int)(vx / 100),(int) (vy / 100));

                vx = nextValue(vx);
                vy = nextValue(vy);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    logger.debug(e);
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
