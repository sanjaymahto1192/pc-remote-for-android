package hu.za.pc_remote.transport;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/22/11
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionHandlingService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }
}
