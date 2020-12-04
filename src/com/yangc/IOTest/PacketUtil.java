package com.yangc.IOTest;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import sun.rmi.runtime.Log;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.*;

public class PacketUtil {
    /**
     * 缓冲大小
     */
    private static int BUFFERSIZE = 1024;

    /**
     * 解压缩 zip 或者 rar
     *
     * @param packetFile
     * @param targetDir
     */
    public static List<String> unPacket(String packetFile, String targetDir) {
        if (packetFile.endsWith(".zip")) {
            return unZip(packetFile, targetDir);
        } else if (packetFile.endsWith(".rar")) {
            return unRar(packetFile, targetDir);
        } else {
            throw new IllegalArgumentException(String.format("不是合法的解压文件:%s，需要zip或者rar压缩文件", packetFile));
        }
    }


    /**
     * 解压缩
     *
     * @param zipFile
     * @param targetPath
     */
    private static List<String> unZip(String zipFile, String targetPath) {
        // 判断是否rar文件
//        if (!zipFile.endsWith(".zip")) {
//            System.err.println("打开的文件不是zip文件！");
//            return;
//        }
        ZipEntry zipEntry = null;
        try (
                // ZipInputStream读取压缩文件  //压缩包名称
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile), Charset.forName("GBK"));
                // 写入到缓冲流中
                BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        ) {
            File fileOut = null;
            List<String> listFile = new LinkedList<>();
            // 读取压缩文件中的一个文件
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                // 若当前zipEntry是一个文件夹
                if (zipEntry.isDirectory()) {
                    fileOut = new File(targetPath + "/" + zipEntry.getName());
                    // 在指定路径下创建文件夹
                    if (!fileOut.exists()) {
                        fileOut.mkdirs();
                    }
                    // 若是文件
                } else {
                    // 原文件名与指定路径创建File对象(解压后文件的对象)
                    fileOut = new File(targetPath, zipEntry.getName());
                    // 在指定路径下创建文件夹
                    if (!fileOut.getParentFile().exists()) {
                        fileOut.getParentFile().mkdirs();
                    }
                    try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
                         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);) {
                        // 将文件写入到指定file中
                        int b = 0;
                        while ((b = bufferedInputStream.read()) != -1) {
                            bufferedOutputStream.write(b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listFile.add(fileOut.getAbsolutePath());
                }
            }
            return listFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param rarFile    rar文件路径
     * @param targetPath 要解压的路径
     */
    private static List<String> unRar(String rarFile, String targetPath) {
        try {
            FileOutputStream fos = null;
            List<String> listFile = new LinkedList<>();
            // 创建一个rar档案文件
            File file = new File(rarFile);
            try (InputStream inputStream = new FileInputStream(file); Archive rarArchive = new Archive(inputStream)) {
                // 判断是否有加密
                if (rarArchive != null) {
                    if (rarArchive.isEncrypted()) {
                        rarArchive.close();// 关闭资源
                        System.out.println(String.format("%s有加密 直接退出不处理", rarFile));
                        return null;
                    }
                    FileHeader fileHeader = rarArchive.nextFileHeader();
                    while (fileHeader != null) {
                        if (!fileHeader.isDirectory()) {
                            // 从压缩文件中解压出来的文件名，有可能带目录的文件名
                            // String name = fileHeader.getFileNameString().trim();
                            String name = fileHeader.getFileNameW().isEmpty() ? fileHeader
                                    .getFileNameString() : fileHeader.getFileNameW();
                            String outPutFileName = targetPath + "/" + name;
                            // 分离文件名，为了创建目录
                            File fe = new File(outPutFileName);
                            // 创建文件夹
                            if (!fe.getParentFile().exists()) {
                                fe.getParentFile().mkdirs();
                            }
                            fos = new FileOutputStream(fe);
                            // 保存解压的文件
                            rarArchive.extractFile(fileHeader, fos);
                            listFile.add(fe.getAbsolutePath());
                            // 关闭资源
                            fos.close();
                            fos = null;
                        }
                        fileHeader = rarArchive.nextFileHeader();
                    }
                }
            }
            return listFile;
        } catch (RarException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 压缩指定的文件
     *
     * @param files   目标文件
     * @param zipFile 生成的压缩文件
     * @throws IOException
     */
    public static void zip(Path[] files, Path zipFile) throws IOException {
        OutputStream outputStream = Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW);
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        try {
            for (Path file : files) {
                if (Files.isDirectory(file)) {
                    continue;
                }
                try (InputStream inputStream = Files.newInputStream(file)) {
                    // 创建一个压缩项，指定名称
                    ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
                    // 添加到压缩流
                    zipOutputStream.putNextEntry(zipEntry);
                    // 写入数据
                    int len = 0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((len = inputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                    zipOutputStream.flush();
                }
            }
            // 完成所有压缩项的添加
            zipOutputStream.closeEntry();
        } finally {
            zipOutputStream.close();
            outputStream.close();
        }
    }

    /**
     * 压缩指定的目录
     *
     * @param folder
     * @param zipFile
     * @throws IOException
     */
    public static void zip(Path folder, Path zipFile) throws IOException {
        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException(folder.toString() + " 不是合法的文件夹");
        }
        OutputStream outputStream = Files.newOutputStream(zipFile, StandardOpenOption.CREATE_NEW);
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        LinkedList<String> path = new LinkedList<>();

        try {
            Files.walkFileTree(folder, new FileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                    if (!dir.equals(folder)) {
                        // 开始遍历目录
                        String folder = dir.getFileName().toString();
                        path.addLast(folder);
                        // 写入目录
                        ZipEntry zipEntry = new ZipEntry(path.stream().collect(Collectors.joining("/", "", "/")));
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            zipOutputStream.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 开始遍历文件
                    try (InputStream inputStream = Files.newInputStream(file)) {

                        // 创建一个压缩项，指定名称
                        String fileName = path.size() > 0
                                ? path.stream().collect(Collectors.joining("/", "", "")) + "/" + file.getFileName().toString()
                                : file.getFileName().toString();

                        ZipEntry zipEntry = new ZipEntry(fileName);

                        // 添加到压缩流
                        zipOutputStream.putNextEntry(zipEntry);
                        // 写入数据
                        int len = 0;
                        byte[] buffer = new byte[1024 * 10];
                        while ((len = inputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, len);
                        }

                        zipOutputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // 结束遍历目录
                    if (!path.isEmpty()) {
                        path.removeLast();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            zipOutputStream.closeEntry();
        } finally {
            zipOutputStream.close();
            outputStream.close();
        }
    }

    public static String readTxtImageDots(String path) {
        File file = new File(path);
        List list = new LinkedList();
        double[] nums = null;
        try {
            BufferedReader bw = new BufferedReader(new FileReader(file));
            String line = null;
            //因为不知道有bai几行数据du，所以先存入list集合中zhi
            while((line = bw.readLine()) != null){
                list.add(line);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedHashMap<String, LinkedList<String>> linkedHashMap = new LinkedHashMap<>();
        //确定数组长度
        for(int i=0;i<list.size();i++){
            String s = (String) list.get(i);
            System.out.println(s);
            String[] arr = s.split(" ");
            if (arr.length > 0) {
                String key = arr[0].trim();
                if (linkedHashMap.containsKey(key)) {
                    linkedHashMap.get(key).add(String.format("{%s:%s}", arr[1].trim(), arr[2].trim()));
                } else {
                    LinkedList linkedList = new LinkedList();
                    linkedList.push(String.format("{%s:%s}", arr[1].trim(), arr[2].trim()));
                    linkedHashMap.put(key, linkedList);
                }
            }
        }

        List<String> lst = new LinkedList<>();
        Iterator<Map.Entry<String, LinkedList<String>>> it = linkedHashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, LinkedList<String>> entry = it.next();
            LinkedList<String> linkedList = entry.getValue();
            lst.add(String.format("[%s]", String.join(",", linkedList)));
        }
        return String.format("[%s]", String.join(",", lst));
    }

    public static void main(String[] args) {
        List<String> list = unPacket("E:\\华西医院（何阳林6月28）\\坐标文件.rar", "E:\\华西医院（何阳林6月28）\\test");
        for (String path : list) {
            String ss = readTxtImageDots(path);
            System.out.println(ss);
        }
    }
}
