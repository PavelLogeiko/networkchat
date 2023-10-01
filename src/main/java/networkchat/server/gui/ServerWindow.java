package networkchat.server.gui;

import networkchat.server.common.ServerController;
import networkchat.share.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class ServerWindow extends JFrame {
    private final int WINDOW_HEIGHT = 417;
    private final int WINDOW_WIDTH = 280;
    private final Logger logger;

    JButton btStart = new JButton("Start");
    JButton btStop = new JButton("Stop");
    JButton btExit = new JButton("Exit");
    JTextArea txtArea = new JTextArea("", 21, 23);
    ServerController controller;

    public ServerWindow(ServerController controller,Logger logger) throws HeadlessException {
        super("Chat server");
        this.logger = logger;
        this.controller = controller;
        Image iconImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon-server.png"))).getImage();
        setIconImage(iconImage);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //ставим окно по центру
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - WINDOW_WIDTH) / 2;
        int y = (screenSize.height - WINDOW_HEIGHT) / 2;

        // Устанавливаем координаты окна
        setLocation(x, y);
        setResizable(false);

        // создание системного трей
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            // Создаем иконку и меню в трее
            TrayIcon trayIcon = getTrayIcon(iconImage);
            try {
                // Добавляем иконку в трей
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Не удалось добавить иконку в трей.");
            }
        } else {
            System.err.println("Трей не поддерживается на этой платформе.");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        // панель для кнопок окна
        JPanel pnlFooter = new JPanel();
        pnlFooter.add(btStart);
        pnlFooter.add(btStop);
        pnlFooter.add(btExit);
        // панель для текстового поля
        JPanel pnlText = new JPanel();
        pnlText.add(txtArea);

        add(pnlText, BorderLayout.NORTH);
        add(pnlFooter, BorderLayout.SOUTH);

        btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.btStartPressed();
            }
        });

        btStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.btStopPressed();
            }
        });

        btExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.btExitPressed();
            }
        });
        setVisible(true);
    }

    private TrayIcon getTrayIcon(Image iconImage) {
        PopupMenu popup = new PopupMenu();
        MenuItem showItem = new MenuItem("Показать окно");
        showItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Отображение окна при выборе опции "Показать окно"
                setVisible(true);
            }
        });
        popup.add(showItem);

        MenuItem exitItem = new MenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Выход из программы
                System.exit(0);
            }
        });
        popup.add(exitItem);
        // Создаем объект TrayIcon
        TrayIcon trayIcon = new TrayIcon(iconImage, "Chat server", popup);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount()==2){
                    setVisible(true);
                    setState(Frame.NORMAL);
                    toFront();
                }
            }
        });
        return trayIcon;
    }

    public void out(String text) {
        txtArea.append(text + "\n");
        logger.put(text);
    }

    public void setOfflineTheme(){
        btExit.setEnabled(true);
        btStart.setEnabled(true);
        btStop.setEnabled(false);
    }

    public void setOnlineTheme(){
        btExit.setEnabled(false);
        btStart.setEnabled(false);
        btStop.setEnabled(true);
    }



}
