package org.pp.net.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost", 8080));
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        InputStream in = null;
        try {
            out = socket.getOutputStream();

            out.write("鹏鹏".getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.shutdownOutput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in = socket.getInputStream();
            byte[] bytes = in.readAllBytes();
            String str = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.shutdownInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
