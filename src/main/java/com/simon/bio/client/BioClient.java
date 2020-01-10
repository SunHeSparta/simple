package com.simon.bio.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Create by SunHe on 2020/1/10
 */
public class BioClient {

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        OutputStream outputStream = null;

        try {
            socket = new Socket("127.0.0.1", 9999);
            Socket finalSocket = socket;
            new Thread(() -> {
                while (true) {
                    try {
                        InputStream inputStream;
                        inputStream = finalSocket.getInputStream();
                        byte[] bytes = new byte[1024];
                        int n;
                        while ((n = inputStream.read(bytes)) != -1) {
                            String line = new String(bytes, 0, n, StandardCharsets.UTF_8);
                            System.out.println("client get server msg:" + line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println("请输入:\r\n");
            Scanner scanner = new Scanner(System.in);
            outputStream = socket.getOutputStream();
            while (true) {
                String s = scanner.nextLine();
                if ("exit".equalsIgnoreCase(s)) {
                    break;
                }
                outputStream.write(s.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }

}
