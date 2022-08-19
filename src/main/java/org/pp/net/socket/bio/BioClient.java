package org.pp.net.socket.bio;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        socket.getOutputStream().write(str.getBytes(StandardCharsets.UTF_8));
        socket.close();
    }
}
