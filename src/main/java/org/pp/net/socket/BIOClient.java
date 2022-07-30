package org.pp.net.socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        System.out.println("in==" + in.hashCode());
        System.out.println("out==" + out.hashCode());
        System.out.println("available=" + in.available());
        // 没有缓冲区，无法确定他们之间发送了多少字节数据
        // readAllBytes()方法将会造成阻塞
//        byte[] bytes = in.readNBytes(23); // 23为服务端发来的字节数，事先算好了，多余23个字节将会阻塞
//        System.out.println(bytes.length);
//        String str = new String(bytes);
//        System.out.println(str);
        OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(out));
        writer.write("你好...");
        System.out.println("len = " + "你好...".getBytes(StandardCharsets.UTF_8).length);
        writer.flush();
        in.close();
        out.close();
        socket.close();
    }
}
