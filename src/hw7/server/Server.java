package hw7.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;


public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket server = null;
        Socket socket;

        final int PORT = 8189;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился ");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String msg){
        for (ClientHandler c:clients) {
            c.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public ClientHandler getSubscribedClientByNick(String nick){
        for (ClientHandler c : clients) {
            if (c.getNick().equals(nick))
                return c;
        }
        return null;
    }

    public String getSubscribedNicks() {
        String nicks = "";
        for (ClientHandler c : clients) {
            if (!nicks.equals("")) {
                nicks += ", ";
            }
            nicks += c.getNick();
        }
        return nicks;
    }
}
