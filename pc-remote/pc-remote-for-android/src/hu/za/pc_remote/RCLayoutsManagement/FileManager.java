package hu.za.pc_remote.RCLayoutsManagement;

import android.os.Environment;
import android.text.StaticLayout;
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

    public static void saveToFile(String name, int id, String xml) throws IOException {
        String fileName = String.format("%s_%d", name, id);

        String appRootDir = getAppRootDir();

        String path = new StringBuilder(appRootDir)
                .append(File.separator)
                .append(fileName)
                .toString();

        File appRoot = new File(appRootDir);
        if(!appRoot.exists()){
           appRoot.mkdir();
        }

        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }

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

    public static void deleteFile(LayoutListItem item){

        File file = new File(
                    new StringBuilder(getAppRootDir())
                            .append(File.separator)
                            .append(getFileName(item))
                            .toString());

        if(file.exists()){
            file.delete();
        }
    }

    public static FileReader getReader(LayoutListItem item) {
        FileReader fr = null;
        try {
            fr = new FileReader(
                    new StringBuilder(getAppRootDir())
                            .append(File.separator)
                            .append(getFileName(item))
                            .toString());
        } catch (FileNotFoundException e) {
            Log.e("getReader", "Failed to get FileReader", e);
        }
        return fr;
    }

    public static List<LayoutListItem> listFiles() {
        File root = new File(getAppRootDir());

        List<LayoutListItem> result = new ArrayList<LayoutListItem>();
        if (root != null && root.exists()){
            for (File f : root.listFiles()) {
                String fileName = f.getName();
                int index = fileName.lastIndexOf('_');
                String name = fileName.substring(0, index);
                int id = Integer.parseInt(fileName.substring(index+1, fileName.length()));

                LayoutListItem item = new LayoutListItem();
                item.setId(id);
                item.setName(name);
                result.add(item);
            }
        }
        return result;
    }

    private static String getFileName(LayoutListItem item){
        return String.format("%s_%d", item.getName(), item.getId());
    }

    private static String getAppRootDir(){
        return new StringBuilder(Environment.getExternalStorageDirectory().getPath())
                        .append(File.separator)
                        .append(APP_ROOT_DIR).toString();
    }
}
