package com.sdmx.support.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public final class IOUtils {

    public static final String STORAGE_LOCATION = "files/";

    public static String storagePath(String path) {
        return STORAGE_LOCATION + path;
//        return (new File(STORAGE_LOCATION)).getAbsolutePath() + path;
    }

    public static List<String> glob(String location, String pattern) {
        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:"+ pattern);
        List<String> matches = new ArrayList<>();

        try {
            Files.walkFileTree(Paths.get(location), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (matcher.matches(path)) {
                        matches.add(path.toString().replace("\\", "/"));
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return matches;
        }
    }

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static String getUniqueName(String extension) {
        return StringUtils.uuid() + (extension != null ? "." + extension : "");
    }

    public static void upload(MultipartFile file, String dir) {
        upload(file, dir, file.getOriginalFilename());
    }

    public static void upload(MultipartFile file, String directoryPath, String filename) {
        try {
            byte[] bytes = file.getBytes();

            // Creating the directory to store file
            File dir = new File(directoryPath);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create the file on server
            File serverFile = new File(directoryPath + "/" + filename);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        }
        catch (Exception e) {
            System.out.println("File Upload Error: "+ e.getMessage());
        }
    }

    public static File filePut(String filepath, String content) {
        return filePut(filepath, content.getBytes());
    }

    public static File filePut(String filepath, byte[] dataByte) {
        File file = new File(filepath);

        try {
            File dir = file.getParentFile();

            if (!dir.exists()) {
                file.getParentFile().mkdirs();
            }

            RandomAccessFile stream = new RandomAccessFile(file, "rw");
            FileChannel channel = stream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(dataByte.length);
            buffer.put(dataByte);
            buffer.flip();
            channel.write(buffer);

            stream.close();
            channel.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    public static void delete(String filepath) {
        delete(new File(filepath));
    }

    public static void delete(File item) {
        if (item.isDirectory()) {
            String itemPath = item.getPath();

            for (String filename : item.list()) {
                File file = new File(itemPath, filename);

                if (file.isDirectory()) {
                    delete(file);
                }
                else {
                    file.delete();
                }
            }
        }

        item.delete();
    }
}
