package hu.za.pc_remote.ui.RCBuilder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import hu.za.pc_remote.R;
import hu.za.pc_remote.RCLayoutsManagement.FileManager;
import hu.za.pc_remote.common.RCAction;
import hu.za.pc_remote.transport.ConnectionHandlingService;
import hu.za.pc_remote.ui.ConnectionSettings;
import hu.za.pc_remote.ui.UIActivityBase;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/22/11
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCBuilder extends UIActivityBase {

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            ConnectionHandlingService connService = ((ConnectionHandlingService.LocalBinder) service).getService();
            if (!connService.hasTransportManager()) {
                //Show connection activity
                Intent i = new Intent(RCBuilder.this, ConnectionSettings.class);
                startActivity(i);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            //Nothing to do
        }

    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindService(new Intent(this, ConnectionHandlingService.class), mConnection, Context.BIND_AUTO_CREATE);

        List<View> views = getViews();
        Log.i("Views", String.format("views:%d", views.size()));
        LinearLayout layout = new LinearLayout(this);
        for (View v : views) {
            layout.addView(v);
        }
        //View.OnKeyListener keyListener = new RCKeyListener(this);
        TouchPad touchPad = new TouchPad(this);
        layout.addView(touchPad);
/*        layout.setOnKeyListener(keyListener);
        touchPad.setOnKeyListener(keyListener);*/
        setContentView(layout);
    }

    public List<View> getViews() {
        Log.i("getViews", "Started");
        List<View> views = new ArrayList<View>();

        boolean storageAvailable = false;
        boolean storageWritable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            storageAvailable = storageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            storageAvailable = true;
            storageWritable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            storageAvailable = storageWritable = false;
        }


        if (storageAvailable) {
            Log.i("getViews", "Storage avaliable");
            BufferedReader br = null;
            try {
                FileReader fr = FileManager.getReader(null);
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                Xml.parse(fr, new RCXmlParser(xr, this));

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (SAXException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ParserConfigurationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }
        }
        return views;
    }
}