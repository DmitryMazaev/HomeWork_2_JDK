package server;

import client.ClientInterface;
import db.Repository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerInterface {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    JButton startChart = new JButton("Запустить");
    JButton stopChart = new JButton("Остановить");
    public JTextArea textAreaServer = new JTextArea();
    Repository fileStorage;
    Server server;
    public ServerWindow(Server server, Repository fileStorage)
    {
        this.server = server;
        this.fileStorage = fileStorage;
        settingServer();

        startChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverOn();
            }
        });

        stopChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverOff();
            }
        });
        setVisible(true);
    }
    /**
     * Запуск сервера
     */
    @Override
    public void serverOn() {
        if(!server.isServerStart()) {
            fileStorage.whriteInLogFile("Сервер запущен");
            server.setServerStart(true);
            textAreaServer.setText("");
            textAreaServer.setText("Сервер запущен");
        }
    }
    /**
     * Выключение сервера
     */
    @Override
    public void serverOff() {
        if(server.isServerStart()) {
            fileStorage.whriteInLogFile("Сервер остановлен");
            server.setServerStart(false);
            server.disconnectFromServer();
            textAreaServer.setText("");
            textAreaServer.setText("Сервер остановлен");
        }
    }
    /**
     * Метод создания параметров окна
     */
    @Override
    public void settingServer() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);
        JPanel panelServer = new JPanel(new GridLayout(1, 2, 10, 10));
        panelServer.add(startChart);
        panelServer.add(stopChart);
        add(textAreaServer);
        panelServer.revalidate();
        add(panelServer, BorderLayout.SOUTH);
    }

}
