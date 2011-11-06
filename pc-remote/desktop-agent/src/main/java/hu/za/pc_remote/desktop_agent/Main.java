package hu.za.pc_remote.desktop_agent;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.apache.log4j.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/27/11
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    private TrayIcon trayIcon;
    private BTManager manager;

    public Main() {
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.jpg"));

            MouseListener mouseListener = new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    logger.debug("Tray Icon - Mouse clicked!");
                }

                public void mouseEntered(MouseEvent e) {
                    logger.debug("Tray Icon - Mouse entered!");
                }

                public void mouseExited(MouseEvent e) {
                    logger.debug("Tray Icon - Mouse exited!");
                }

                public void mousePressed(MouseEvent e) {
                    logger.debug("Tray Icon - Mouse pressed!");
                }

                public void mouseReleased(MouseEvent e) {
                    logger.debug("Tray Icon - Mouse released!");
                }
            };

            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    logger.debug("Exiting...");
                    System.exit(0);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            trayIcon = new TrayIcon(image, "Tray Demo", popup);

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Action Event",
                            "An Action Event Has Been Performed!",
                            TrayIcon.MessageType.INFO);
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                logger.debug("TrayIcon could not be added.");
            }

        } else {

        }

        manager = new BTManager(trayIcon);
        manager.initialize();
        manager.start();
    }

    public static void main(String[] args) {
        new Main();
    }

}
