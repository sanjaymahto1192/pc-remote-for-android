package hu.za.pc_remote.desktop_agent;

import hu.za.pc_remote.common.RCAction;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/2/11
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class BTManager extends Thread {
    private static Logger logger = Logger.getLogger(BTManager.class);

    private TrayIcon trayIcon;
    public final UUID uuid = new UUID("0123456700001000800000805F9B34FB", false);
    public final String name = "Echo Server"; // the name of the service
    public final String url = "btspp://localhost:" + uuid + ";name=" + name + ";authenticate=true;encrypt=true;";
    LocalDevice local = null;
    StreamConnectionNotifier server = null;
    StreamConnection conn = null;
    ObjectInputStream inStream = null;

    public BTManager(){}
    public BTManager(TrayIcon trayIcon){
        this.trayIcon = trayIcon;
    }

    public void initialize() {
        try {
            logger.debug("Setting device to be discoverable...");
            local = LocalDevice.getLocalDevice();
            logger.debug(local.getBluetoothAddress());
            local.setDiscoverable(DiscoveryAgent.GIAC);
            logger.debug("Start advertising service...");
            server = (StreamConnectionNotifier) Connector.open(url);
            logger.debug("Waiting for incoming connection...");

        } catch (Exception e) {
            logger.debug("Exception Occured: " + e.toString());

            try {
                inStream.close();
            } catch (IOException e3) {
                logger.error(e3);
            }

            try {
                conn.close();
            } catch (IOException e2) {
                logger.error(e2);
            }

            try {
                server.close();
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }


            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e1) {
                logger.error(e1);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                conn = server.acceptAndOpen();
                logger.debug("Client Connected...");

                if(trayIcon != null){
                    trayIcon.displayMessage("Client Connected!", "", TrayIcon.MessageType.INFO);
                }

                inStream = new ObjectInputStream(conn.openInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            while (true) {
                try {
                    RCAction a = (RCAction) inStream.readObject();

                    logger.debug(a.toString());

                } catch (IOException e) {
                    logger.error(e);
                } catch (ClassNotFoundException e) {
                    logger.error(e);
                }
            }
        }
    }
}
