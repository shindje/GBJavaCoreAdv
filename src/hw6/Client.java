package hw6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    private final static String SERVER = "localhost";

    public static void main(String[] args) {
        try(Socket socket = new Socket(SERVER, Server.PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Подключено к серверу");

            ReadThread readThread = new ReadThread(in, "Сервер");
            readThread.start();

            WriteThread writeThread = new WriteThread(out);
            writeThread.setDaemon(true);
            writeThread.start();

            try {
                readThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Отключено от сервера. Завершение работы");
        } catch (ConnectException e) {
            System.out.println("Ошибка подключения к серверу. Завершение работы");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}