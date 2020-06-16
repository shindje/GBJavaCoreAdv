package hw8.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public VBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox msgPanel;
    @FXML
    public ListView<String> clientList;
    @FXML
    public CheckBox saveLoginAndPassCheckBox;

    Stage regStage;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;

    private boolean authenticated;
    private String nick;

    public void setAuthenticated(boolean authenticated) {
        authPanel.setVisible(!authenticated);
        authPanel.setManaged(!authenticated);
        msgPanel.setManaged(authenticated);
        msgPanel.setVisible(authenticated);
        clientList.setVisible(authenticated);
        clientList.setManaged(authenticated);
        if (!authenticated) {
            nick = "";
        }
        if (this.authenticated != authenticated) {
            textArea.clear();
        }
        setTitle(nick);
        this.authenticated = authenticated;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthenticated(false);
        loadLoginAndPass();

        regStage = createRegWindow();

        Platform.runLater(() -> {
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    System.out.println("bue");
                    if (socket != null && !socket.isClosed()) {
                        try {
                            out.writeUTF("/end");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });
    }

    private void connect() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(() -> {
                try {
                    //цикл аутентификации и регистрации
                    while (true) {
                        String str = in.readUTF();

                        System.out.println(str);

                        if (str.startsWith("/authok ")) {
                            nick = str.split(" ")[1];
                            setAuthenticated(true);
                            break;
                        } else if (str.startsWith("/reg")) {
                            Platform.runLater(() -> {
                                if (str.equals("/reg_ok")) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Регистрация");
                                    alert.setHeaderText("Регистрация прошла успешно");
                                    alert.showAndWait();
                                }
                                if (str.equals("/reg_error")) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Ошибка регистрации");
                                    alert.setHeaderText("Регистрация  не удалась. \n" +
                                            "Возможно логин уже занят, или данные содержат пробел");
                                    alert.showAndWait();
                                }
                            });
                        } else {
                            showText(str);
                        }
                    }

                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                break;
                            }

                            if (str.startsWith("/clientlist ")) {
                                String[] token = str.split(" ");
                                Platform.runLater(() -> {
                                    clientList.getItems().clear();
                                    for (int i = 1; i < token.length; i++) {
                                        clientList.getItems().add(token[i]);
                                    }
                                });
                            }

                        } else {
                            showText(str);
                        }
                    }
                } catch (EOFException e) {
                    System.out.println("Сокет отключен");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("мы отключились");
                    setAuthenticated(false);
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

    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {
        if (saveLoginAndPassCheckBox.isSelected()) {
            saveLoginAndPass();
        }

        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF("/auth " + loginField.getText().trim() + " " + passwordField.getText().trim());
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(String nick) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle("Super chat " + nick);
        });

    }

    public void clickClientList(MouseEvent mouseEvent) {
        System.out.println(clientList.getSelectionModel().getSelectedItem());
        String receiver = clientList.getSelectionModel().getSelectedItem();
        textField.setText("/w " + receiver + " ");
    }

    private Stage createRegWindow() {
        Stage stage = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reg.fxml"));
            Parent root = fxmlLoader.load();

            stage = new Stage();
            stage.setTitle("Registration ");
            stage.setScene(new Scene(root, 300, 200));
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);

            RegController regController = fxmlLoader.getController();
            regController.controller = this;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stage;
    }

    public void showRegWindow(ActionEvent actionEvent) {
        regStage.show();
    }

    public void tryRegistration(String login, String password ,String nickname){
        String msg = String.format("/reg %s %s %s", login, password ,nickname);

        if (socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exit(ActionEvent actionEvent) {
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showText(String text) {
        textArea.appendText(String.format("%tT %s\n", new Date(), text));
    }

    File getLoginAndPassFile() {
        String userHome = System.getProperty("user.home");
        File file = new File(userHome + File.separator + "superchat.save");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    void saveLoginAndPass() {
        try (OutputStream out =  new FileOutputStream(getLoginAndPassFile())) {
            Properties properties = new Properties();
            properties.setProperty("login", loginField.getText());
            properties.setProperty("password", passwordField.getText());
            properties.store(out, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void loadLoginAndPass() {
        try (InputStream in =  new FileInputStream(getLoginAndPassFile())) {
            Properties properties = new Properties();
            properties.load(in);
            loginField.setText(properties.getProperty("login"));
            passwordField.setText(properties.getProperty("password"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
