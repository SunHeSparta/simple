package com.simon.bio.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by SunHe on 2020/1/10
 */
public class BioServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                read(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void read(Socket socket) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            byte[] bytes = new byte[1024];
            int n;
            String response;

            while ((n = inputStream.read(bytes)) != -1) {
                String line = new String(bytes, 0, n, StandardCharsets.UTF_8);
                System.out.println("server get client msg: " + line);
                response = "SJ".equalsIgnoreCase(line) ?
                        "当前时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                        : "客户端输入有误,请检查";
                outputStream.write(response.getBytes());
                outputStream.flush();
            }
        } catch (Exception e) {
            System.out.println("bio server error:" + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        }

    }

}
