package com.haitran.filemanager.manager;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.haitran.filemanager.adapter.ItemAdapter;
import com.haitran.filemanager.model.FileItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class FileManager {
    private Context context;
    public static String PATH_EXTERNAL = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATH_STORAGE = "/storage/";
    public static String name = null;

    public FileManager(Context context) {
        this.context = context;
    }

    public ArrayList<FileItem> readFile(String path) {
        File file = new File(path);
        ArrayList<FileItem> arrFileItem = new ArrayList<>();
        if (file.isFile()) {
            arrFileItem.add(getFileItem(file, -1));
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                int number = -1;
                if (!files[i].isFile()) {
                    if (files[i].listFiles() == null) {
                        number = -1;
                    } else {
                        number = files[i].listFiles().length;
                    }
                }
                arrFileItem.add(getFileItem(files[i], number));
            }
        }
        return arrFileItem;
    }

    public String getSizeInternal() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        long freeSize = statFs.getFreeBlocks() * blockSize;
        float total = (float) totalSize / (float) 1073741824;
        String a = new DecimalFormat("##.##").format(total);
        float free = (float) freeSize / (float) 1073741824;
        float used = total - free;
        String b = new DecimalFormat("##.##").format(used);
        String result = a + "_" + b;
        return result;
    }

    public String getSdCard() {
        File file = new File(PATH_STORAGE);
        if (!file.isFile()) {
            File[] files = file.listFiles();
            name = files[0].getAbsolutePath();
        }
//        String secStore = System.getenv("SECONDARY_STORAGE");
        File path = new File(name);
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalSize = stat.getBlockCount() * blockSize;
        long freeSize = stat.getFreeBlocks() * blockSize;
        float total = (float) totalSize / (float) 1073741824;
        String a = new DecimalFormat("##.##").format(total);
        float free = (float) freeSize / (float) 1073741824;
        float used = total - free;
        String b = new DecimalFormat("##.##").format(used);
        String result = a + "_" + b;
        return result;
    }


    public FileItem getFileItem(File file, int number) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String name = file.getName();
        String pathFile = file.getPath();
        String date = sdf.format(file.lastModified());
        boolean isFile = file.isFile();
        boolean isDirectory = file.isDirectory();
        boolean select = ItemAdapter.NOT_SELECTED;
        FileItem fileItem = new FileItem(name, pathFile, date, number, isFile, isDirectory, select);
        return fileItem;
    }

    public void deleteDirectory(String folder) {
        File file = new File(folder);
        ArrayList<File> arrFiles = new ArrayList<>();
        if (file.isDirectory()) {
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files == null) {
                    return;
                }
                for (int i = 0; i < files.length; i++) {
                    deleteDirectory(files[i].getPath());
                    arrFiles.add(files[i]);
                }
            }
        } else {
            file.delete();
            return;
        }
        arrFiles.add(file);
        for (File file1 : arrFiles) {
            file1.delete();
        }
    }

    public void renameFile(String path, String nameChange) {
        File file = new File(path);
        String name = path.substring(0, path.lastIndexOf("/") + 1) + nameChange;
        file.renameTo(new File(name));
    }

    public void copyDirectory(File sourceLocation, File targetLocation) {
        try {
            if (sourceLocation.isDirectory()) {
                File fileTarget = new File(targetLocation.getPath() + "/" + sourceLocation.getName());
                if (!fileTarget.exists()) {
                    fileTarget.mkdirs();
                }

                String[] children = sourceLocation.list();
                for (int i = 0; i < children.length; i++) {
                    copyDirectory(new File(children[i]), fileTarget);
                }

            } else {
                String fileTarget = sourceLocation.getName();
                File file = new File(targetLocation.getPath() + "/" + fileTarget);
                if (!file.exists()) {
                    file.createNewFile();
                }
                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
