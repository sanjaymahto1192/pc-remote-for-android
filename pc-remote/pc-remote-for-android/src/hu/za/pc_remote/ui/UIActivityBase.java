package hu.za.pc_remote.ui;

import android.app.Activity;
import android.app.ActivityManager;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/26/11
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class UIActivityBase extends Activity {

    protected boolean isConHandServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("hu.za.pc_remote.transport.ConnectionHandlingService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
