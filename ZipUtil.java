package com.fotobuch.android.util;

import rs.webnet.util.blog.BLog;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by kursulla on 4/25/14.
 * <p/>
 * Class purpose:
 */
public class ZipUtil {

    public static final int BUFFER_SIZE = 1024;
    private static final String TAG = "ZipUtil";

    /**
     * Method that get input file path, zip it and return File based on passed outputFilePath.
     * @param inputFilePath Path to file for compression.
     * @param outputFilePath Path to output compressed file.
     * @return Compressed file.
     */
    public static File zipFile(String inputFilePath, String outputFilePath) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputFilePath)));
            ZipEntry entry = new ZipEntry(outputFilePath);
            zos.putNextEntry(entry);
            FileInputStream in = new FileInputStream(inputFilePath);

            int len;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(outputFilePath);
    }
    /**
     * Method that get input directory path, zip it and return File based on passed outputFilePath.
     * @param pathToDirectory Path to directory for compression.
     * @param outputFilePath Path to output compressed file.
     * @return Compressed file.
     */
    public static File zipDirectory(String pathToDirectory, String outputFilePath) {
        ZipOutputStream zos = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputFilePath)));

            File[] fileList = new File(pathToDirectory).listFiles();
            for (int i=0;i<fileList.length;i++) {
                BLog.d(TAG, ""+fileList[i].getAbsolutePath());
                ZipEntry entry = new ZipEntry(fileList[i].getAbsolutePath());
                zos.putNextEntry(entry);
                FileInputStream in = new FileInputStream(fileList[i].getAbsolutePath());
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new File(outputFilePath);
    }
public static File unZip(String pathToZip, String pathToOutputDir) {
        byte[] buffer = new byte[1024];
        File outputDirectory = null;
        try {

            outputDirectory = new File(pathToOutputDir);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdir();
            }

            ZipInputStream zis = new ZipInputStream(new FileInputStream(pathToZip));
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(pathToOutputDir + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return outputDirectory;
    }
    
}
