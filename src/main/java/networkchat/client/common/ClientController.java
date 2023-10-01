package networkchat.client.common;

import networkchat.client.gui.ClientWindow;
import networkchat.share.Logger;

public class ClientController {

    private ClientWindow window;
    private String serverAddress;
    private int serverPort;
    private String login;
    private String password;

    public ClientController() {
        Logger logger = new Logger("cl");
        this.window = new ClientWindow(this,logger);
        window.setOfflineTheme();
    }

    public void btSend(String msg) {
        //отправка сообщения
        window.out(msg);
    }

    public void connect(ConnectData connectInfo) {
        serverAddress = connectInfo.address();
        serverPort = connectInfo.port();
        login = connectInfo.login();
        password = connectInfo.password();

        // блок соединения с сервером
        window.out("Соединяемся с " + serverAddress + ":" + serverPort);

        // авторизуемся
        window.out("Попытка авторизации: " + login);

        //соединились
        window.setOnlineTheme();
    }

    public void disconnect() {
        //отсоединение от сервера
        window.out("переход в оффлайн");
        window.setOfflineTheme();
    }
}