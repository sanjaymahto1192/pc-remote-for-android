package hu.za.pc_remote.transport;

import android.content.Intent;
import android.test.ServiceTestCase;
import hu.za.pc_remote.common.RCAction;
import junit.framework.Assert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/26/11
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionHandlingServiceTest extends ServiceTestCase<ConnectionHandlingService> {
    public ConnectionHandlingServiceTest() {
        super(ConnectionHandlingService.class);
    }

    public void testObjectOutputStreams() throws Exception {

        RCAction extra = new RCAction();
        extra.type = RCAction.Type.MOUSE_MOVE;
        extra.arguments = new float[]{2, 5};

        this.startService(new Intent());
        ConnectionHandlingService service = getService();
        final List<Integer> data = new ArrayList<Integer>();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new OutputStream() {
                @Override
                public void write(int i) throws IOException {
                    data.add(i);
                }
            });

            service.setTransportManager(new TransportManager(oos), null, null);

            Intent i = new Intent(ConnectionHandlingService.RC_INTENT_ACTION);
            i.putExtra(ConnectionHandlingService.INTENT_DATA_EXTRA_KEY, extra);

            getSystemContext().sendBroadcast(i);
            getSystemContext().sendBroadcast(i);
            getSystemContext().sendBroadcast(i);
            getSystemContext().sendBroadcast(i);

            ObjectInputStream ois = new ObjectInputStream(new InputStream() {
                @Override
                public int read() throws IOException {
                    if (data.isEmpty())
                        return 0;
                    Integer result = data.get(0);
                    data.remove(result);
                    return result.intValue();
                }
            });


            checkResult((RCAction) ois.readObject());
            checkResult((RCAction) ois.readObject());
            checkResult((RCAction) ois.readObject());
            checkResult((RCAction) ois.readObject());


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void checkResult(RCAction rca) {
        if (!rca.type.equals(RCAction.Type.MOUSE_MOVE)) {
            Assert.fail("type not match");
        }
        if (!(rca.arguments[0] == 2)) {
            Assert.fail("first argument not match");
        }
        if (!(rca.arguments[1] == 5)) {
            Assert.fail("second argument not match");
        }
    }
}
