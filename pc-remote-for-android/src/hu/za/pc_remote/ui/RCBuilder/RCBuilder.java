package hu.za.pc_remote.ui.RCBuilder;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import hu.za.pc_remote.ui.UIActivityBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.logging.Log.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 9/22/11
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class RCBuilder extends UIActivityBase {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getRCDirectories() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }

        File dataDir = Environment.getExternalStorageDirectory();
        File appRootDir = new File(dataDir, "pcremote");
        appRootDir.mkdir();
        File f = new File(appRootDir, "file");
        Log.i("file Path", f.getPath());

        try {
            FileWriter fw = new FileWriter(f);
            fw.write("HelloWorld");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}