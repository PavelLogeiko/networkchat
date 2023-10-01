package networkchat.server.common;

import networkchat.server.gui.ServerWindow;
import networkchat.share.Logger;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ServerController {
    private final String ip;
    private final Integer port;
    private Logger logger;
    private final ServerWindow window;

    public ServerController() {
        logger = new Logger("srv");
        ServerConfig serverConfig = getServerConfig();
        this.ip = serverConfig.getIp();
        this.port = serverConfig.getPort();
        this.window = new ServerWindow(this,logger);
        logger.put("Application start");
    }


    public void btStartPressed(){
        //здесь код запуска потоков сервера
        window.out("Server started");
        window.out(String.format("Socket: %s:%d",ip,port));
        window.setOnlineTheme();
    }
    public void btStopPressed(){
        //здесь код остановки потоков сервера
        window.out("Server stopped");
        window.setOfflineTheme();
    }

    public void btExitPressed(){
        logger.put("Application exit");
        System.exit(0);
    }

    @NotNull
    private ServerConfig getServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        try {
            serverConfig.load("./config/server.yml");
        }catch (Exception e) {
            showMessageDialog(null, "Ошибка при загрузке конфига сервера.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            logger.put("Ошибка при получении конфига.");
            System.exit(1);
        }
        return serverConfig;
    }
}