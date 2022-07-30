package org.pp.concurrent.synchronize.lock;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 文件锁
 * Lock阻塞
 * tryLock非阻塞
 */
public class FileLockTest {
    private static final String userDir = System.getProperty("user.dir");
    private static final File lockFile = new File(userDir + File.separator + "sock.lock");

    public static void main(String[] args) {
        hook();
        if (locking()) {
            System.out.println("文件被其他进程锁定...");
            return;
        }
        final ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            System.out.println("新建实例启动运行...");
            try {
                TimeUnit.SECONDS.sleep(8);
                System.out.println("运行完成...");
            } catch (InterruptedException e1) {
                System.out.println("线程中断...");
            } finally {

            }
        });
        service.shutdown();
    }

    public static boolean locking() {
//                System.getProperties().list(System.out);
        if (lockFile.exists()) {
            try (FileInputStream in = new FileInputStream(lockFile)) {
                String content = new String(in.readAllBytes());
                String pid = String.valueOf(getPid());
                if (!pid.equals(content)) {
                    return true;
                }
                return false;
            } catch (IOException e) {
                System.out.println("读文件异常：" + e.getMessage());
            }
        } else {
            try (FileOutputStream out = new FileOutputStream(lockFile)) {
                lockFile.createNewFile();
                String pid = String.valueOf(getPid());
//                String len = String.valueOf(pid.length());
//                out.write(len.getBytes(StandardCharsets.UTF_8));
//                out.write(".".getBytes(StandardCharsets.UTF_8));
                out.write(pid.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.out.println("文件写入失败：" + e.getMessage());
            }
        }
        return false;
    }

    private static long getPid() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long pid = runtimeMXBean.getPid();
        return pid;
    }

    public static void hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("hook exec...");
            FileChannel fileChannel = null;
            FileLock fileLock = null;
            boolean canDel = false;
            try {
                fileChannel = new RandomAccessFile(lockFile, "rw").getChannel();
                fileLock = fileChannel.tryLock();
                if (fileLock != null) {
                    System.out.println("比较是否同一进程sock.lock?");
                    String pid = String.valueOf(getPid()); // 当前进程id
                    byte[] bytes = new byte[pid.length()];
                    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
                    fileChannel.read(byteBuffer);
//                    int len = byteBuffer.get();
//                    System.out.println("len = " + len);
                    String content = new String(byteBuffer.array());
                    if (pid.equals(content)) {
                        canDel = true;
                    } else {
                        System.out.println("有进程删除文件失败,pid=" + pid);
                    }
                }
            } catch (Exception e) {
                System.out.println("忽略其他异常...");
            } finally {
                try {
                    if (fileLock != null) {
                        fileLock.release();
                    }
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (canDel) {
                    System.out.println("当前进程pid=" + getPid() + " 删除 = " + lockFile.delete());
                }
            }
        }));
    }
}
