package hw6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int PORT = 5555;

    public static void main(String[] args) {
        WriteThread writeThread = new WriteThread();
        writeThread.start();

        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                ) {
                    System.out.println("Клиент подключился");

                    ReadThread readThread = new ReadThread(in, "Клиент");
                    readThread.start();

                    writeThread.setOutputSream(out);

                    try {
                        readThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Клиент отключился");

                    writeThread.setOutputSream(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}