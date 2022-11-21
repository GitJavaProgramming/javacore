package org.pp.io;

import org.junit.After;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/18 8:32
 * ************厚德载物************
 **/
public class IOStreamTest {

    private static final URL RESOURCE = ClassLoader.getSystemClassLoader().getResource("");
    private static final String CLASSPATH = RESOURCE.getFile();
    private static final String DOC = CLASSPATH + File.separator + "doc" + File.separator;
    InputStream in;
    OutputStream out;

    public static void main(String[] args) throws IOException {
//        new StreamTest().test2();
    }

    /**
     * RandomAccessFile 读写通道
     */
    @Test
    public void test11() throws IOException {
        String newJavaTxt = DOC + "java";
        RandomAccessFile raf = new RandomAccessFile(newJavaTxt, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(30);
        channel.read(buffer);
        System.out.println("===============");
        buffer.flip();
        while(buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char)b);
        }
        System.out.println("\n===============");
        buffer.flip();
        channel.write(buffer);
        raf.close();
    }

    /**
     * 文件通道 只读只写通道
     * 缓冲区操作 flip rewind
     */
    @Test
    public void test10() throws IOException {
        String newJavaTxt = DOC + "java"; // 文件内容 abcdefghijklmnopqrstuvwxyz1234567890
        FileInputStream in = new FileInputStream(newJavaTxt);
        ByteBuffer buffer = ByteBuffer.allocate(30/*小于文件字节数*/);
        FileChannel channel = in.getChannel(); // 输入流通道只读
        channel.read(buffer); // 从通道中读取数据
        buffer.flip(); // 翻转 limit=position
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) b);
        }
        System.out.println("\n=================================");
        buffer.rewind(); // 倒带 position=0 mark=-1
        buffer.put("12abcdefghijklmnopqrstuvwxyz".getBytes(StandardCharsets.UTF_8));
        buffer.rewind(); // 倒带
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.print((char) b);
        }
        channel.close();
        in.close();
        System.out.println("\n=================================");
        System.out.println("只打开输出流通道后关闭，文件内容被清空？？？");
        FileOutputStream out = new FileOutputStream(newJavaTxt);
        FileChannel channel1 = out.getChannel();
//        buffer.flip();
//        channel1.write(buffer);
        FileLock lock = channel1.lock();
        lock.release();
        channel1.close();
        out.close();
    }

    /**
     * nio 子缓冲区 数据共享 只读缓冲区（使用生成只读缓冲区子类方式，而不是在缓冲区基础上设置属性值？？）
     */
    @Test
    public void test9() {
        IntBuffer buf = IntBuffer.allocate(20);
        for (int i = 0; i < 10; i++) {
            buf.put(i * 2 + 1);
        }
//        buf = buf.asReadOnlyBuffer();
        int position = buf.position();
        System.out.println("操作缓冲区前设置position limit mark值");
        System.out.println("===================");
        buf.position(2);
        buf.limit(6);
        while (buf.hasRemaining()) {
            int i = buf.get();
            System.out.println(i);
        }
        System.out.println("===================");
        buf.position(2);
        buf.limit(6);
        IntBuffer sub = buf.slice(); // 开辟子缓冲区
        for (int i = 0; i < sub.capacity(); i++) {
            int i1 = sub.get(i);
            sub.put(i1 - 1);
        }
        sub.flip();
        while (sub.hasRemaining()) {
            int i = sub.get();
            System.out.println(i);
        }
        System.out.println("===================");
        buf.position(0);
        buf.limit(position);
        while (buf.hasRemaining()) {
            int i = buf.get();
            System.out.println(i);
        }
    }

    /**
     * nio缓冲区基本操作
     */
    @Test
    public void test8() {
        // buffer channel selector
        // capacity limit position mark
        // 字节 字符 整数 ==》数组与字符串
        IntBuffer buf = IntBuffer.allocate(100);
        // 写入整型数据
        buf.put(12);
        int[] arr = {1, 2, 3};
        buf.put(arr);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 读取
        buf.flip(); // 1、翻转缓冲区 limit=position position=0 mark=-1
        while (buf.hasRemaining()) { // 2、遍历
            int i = buf.get(); // 3、获取数据
            System.out.println(i);
        }
    }

    /**
     * 测试归并算法，两个有序数组合并
     */
    @Test
    public void test7() {
        int[] a = {1, 2, 3, 4, 5, 100};
        int[] b = {0, 6, 7, 8, 9};
        System.out.println(Arrays.toString(merge(a, b)));
    }

    private int[] merge(int[] a, int[] b) {
        int right1 = a.length;
        int right2 = b.length;
        int[] c = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < right1 && j < right2) {
            if (a[i] < b[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
            }
        }
        while (i < right1) {
            c[k++] = a[i++];
        }
        while (j < right2) {
            c[k++] = b[j++];
        }
        return c;
    }

    /**
     * 字符流 多次读取缓冲区
     */
    @Test
    public void test6() throws IOException {
        String newJavaTxt = DOC + "java";
        Reader reader = new BufferedReader(new FileReader(newJavaTxt)); // 字符流 文件缓冲区读取器
        final int LEN = 10;
        char[] chars = new char[LEN];
        int count = 0; // 读取缓冲区的次数
        char[] dest = new char[0];
        while (reader.read(chars) != -1) {
            System.out.println(Arrays.toString(chars));
            dest = Arrays.copyOf(dest, LEN * (count + 1));//扩容
            System.arraycopy(chars, 0, dest, LEN * (count++), chars.length);//将读取的字符数据拷贝到目的数组
            chars = new char[LEN]; // 重置缓冲区数组
        }
        System.out.println(Arrays.toString(dest));
    }

    /**
     * 二进制读字节流
     */
    @Test
    public void test5() throws IOException {
        String newJavaTxt = DOC + "java";
        FileInputStream fileInputStream = new FileInputStream(newJavaTxt);
        byte[] bytes = fileInputStream.readAllBytes();
        System.out.println(Arrays.toString(bytes)); // 文件包含的所有字节
        DataInput dataInput = new DataInputStream(new ByteArrayInputStream(bytes));
        // 读一个字节转换为字符
        byte readChar = dataInput.readByte();
        System.out.println((char) readChar);
        // 4个字节转换为整数
        byte[] bytes2 = {49, 49, 49, 49};
        int byte2Int = ByteUtil.byte2Int(bytes2);
        System.out.println(byte2Int);
    }


    /**
     * 测试日历api
     */
    @Test
    public void test4() {
        // older
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 1-9
        int minute = calendar.get(Calendar.MINUTE); // 0
        int second = calendar.get(Calendar.SECOND); // 0-9
        int millisecond = calendar.get(Calendar.MILLISECOND); // 毫秒数
        System.out.println(year + "-" + month + "-" + dayOfMonth + " " + hour + ":" + minute + ":" + second + "." + millisecond);

        // newer
        LocalDateTime localDateTime = LocalDateTime.now();
        int y = localDateTime.getYear();
        int m = localDateTime.getMonthValue();
        int d = localDateTime.getDayOfMonth();

        int h = localDateTime.getHour();
        int M = localDateTime.getMinute();
        int s = localDateTime.getSecond();
        System.out.println(y + "-" + m + "-" + d + " " + h + ":" + M + ":" + s);

        // 获取秒数
        Long second2 = localDateTime.toEpochSecond(ZoneOffset.UTC);
        System.out.println(second2);
        // 获取毫秒数
        Long milliSecond2 = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        long milliSeconds = Timestamp.valueOf(localDateTime).getTime();
        Long milliSecond3 = Instant.now().toEpochMilli();
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(milliSecond2);
        System.out.println(milliSeconds);
        System.out.println(milliSecond3);
        System.out.println(currentTimeMillis);
        // 纳秒
        int nano1 = localDateTime.getNano();
        int millisecond4 = nano1 / (1000 * 1000);
        System.out.println("纳秒：" + nano1);
        System.out.println("毫秒：" + millisecond4);

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
    }

    /**
     * 读写文件
     */
    @Test
    public void test1() throws IOException {
        String javaTxt = DOC + "Java.txt";
        in = new FileInputStream(javaTxt);
        int available = in.available();
        byte[] bytes = in.readNBytes(available);
        String newJavaTxt = DOC + "java";
        out = new FileOutputStream(newJavaTxt);
        out.write(bytes);
        out.flush();
    }

    /**
     * 从标准输入读一行字符串写入文件
     */
    @Test
    public void test2() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String newJavaTxt = DOC + "java";
        out = new FileOutputStream(newJavaTxt);
        out.write(line.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    /**
     * 可见字符集、中文字符所占字节数
     */
    @Test
    public void test3() throws UnsupportedEncodingException {
        // 所有支持的字符集
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        for (Map.Entry<String, Charset> stringCharsetEntry : stringCharsetSortedMap.entrySet()) {
            System.out.println(stringCharsetEntry.getKey() + "==>" + stringCharsetEntry.getValue().displayName());
        }
        // 中文字符 utf8编码时占3个字节 gbk时占2个字节
        System.out.println("中".getBytes("UTF-8").length);
        System.out.println("中".getBytes("GBK").length);
    }

    @After
    public void after() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }
}
