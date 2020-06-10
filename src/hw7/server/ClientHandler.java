package hw7.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split(" ");

                            System.out.println(str);
                            if (token.length < 2) {
                                continue;
                            }

                            String newNick = server.getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);

                            if (newNick != null) {
                                ClientHandler client = server.getSubscribedClientByNick(newNick);
                                if (client == null) {
                                    sendMsg("/authok " + newNick);
                                    String nicks = server.getSubscribedNicks();
                                    if (nicks.equals("")) {
                                        nicks = "Других подключенных клиентов нет";
                                    } else {
                                        nicks = "Список других подключенных клиентов: " + nicks;
                                    }
                                    sendMsg("Вы подключиличь к чату\n" + nicks);
                                    nick = newNick;
                                    login = token[1];
                                    server.broadcastMsg("Подключился клиент " + nick);

                                    server.subscribe(this);
                                    System.out.println("Клиент: " + nick + " подключился");
                                    break;
                                } else {
                                    sendMsg("Клиент " +  newNick + " уже подключен");
                                }

                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/w ")) {
                            String[] tokens = str.split(" ", 3);
                            if (tokens.length < 3) {
                                sendMsg("Неверный формат /w");
                            } else {
                                String sendToNick = tokens[1];
                                ClientHandler sendToClient = server.getSubscribedClientByNick(sendToNick);
                                if (sendToClient == null) {
                                    sendMsg("Клиент " + sendToNick + " не подключен!");
                                } else {
                                    sendToClient.sendMsg(nick + ": " + tokens[2]);
                                }
                            }
                        } else if (str.equals("/end")) {
                            sendMsg("/end");
                            break;
                        } else {
                            server.broadcastMsg(nick + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    System.out.println("Клиент отключился");
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}
