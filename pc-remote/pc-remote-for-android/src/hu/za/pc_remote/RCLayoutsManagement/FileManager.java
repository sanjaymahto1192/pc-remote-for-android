package hu.za.pc_remote.RCLayoutsManagement;

import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Andor
 * Date: 10/20/11
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileManager {
    private static final String APP_ROOT_DIR = "pcremote";

    public static void saveToFile(String fileName, String xml) throws IOException {
        String path = new StringBuilder(Environment.getExternalStorageDirectory().getPath())
                .append(File.separator)
                .append(APP_ROOT_DIR)
                .toString();

        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
            fw.append(xml);
            fw.flush();
        } catch (IOException e) {
            Log.e("Save File", "Failed to Save File", e);
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
    }

    public static FileReader getReader(String fileName) {
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new StringBuilder(Environment.getExternalStorageDirectory().getPath())
                            .append(File.separator)
                            .append(APP_ROOT_DIR)
                            .append(File.separator)
                            .append(fileName)
                            .toString());
        } catch (FileNotFoundException e) {
            Log.e("getReader", "Failed to get FileReader", e);
        }
        return fr;
    }

    public static List<String> listFiles() {
        File root = new File(
                new StringBuilder(Environment.getExternalStorageDirectory().getPath())
                        .append(File.separator)
                        .append(APP_ROOT_DIR).toString()
        );

        List<String> result = new ArrayList<String>();
        for(File f : root.listFiles()){
            result.add(f.getName());
        }
        return result;
    }
}
