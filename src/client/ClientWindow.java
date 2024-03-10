package client;

import db.Repository;
import server.Server;
import server.ServerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientWindow extends JFrame implements ClientInterface{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private JTextField messageField;
    private JButton sendMessage;
    private JPanel messagePanel;
    private TextArea chat;
    private JLabel disconnectNullLabel;
    private JButton disconnectButton;
    private JPanel disconnectPanel;

    private Server server;
    Repository fileStorage;

    ServerInterface serverWindow;
    private String loginClient;
    /**
     * Пароль клиента
     */
    private String passwordClient;
    private boolean connectStatus = true;
    public ClientWindow(Server server, String loginClient, String passwordClient, boolean connectStatus, Repository fileStorage){
        this.server = server;
        this.loginClient = loginClient;
        this.passwordClient = passwordClient;
        this.connectStatus = connectStatus;
        this.fileStorage = fileStorage;
        settingWindow();
        chatWindow();
        setVisible(true);
    }
    public String getLoginClient() {
        return loginClient;
    }
    public void setLoginClient(String loginClient) {
        this.loginClient = loginClient;
    }
    public String getPasswordClient() {
        return passwordClient;
    }
    public boolean isConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(boolean connectStatus) {
        this.connectStatus = connectStatus;
    }

    /**
     * Метод отключения клиента от сервера
     */
    @Override
    public void disconnectClient() {
        server.disconnectFromChat(this);
        //setVisible(false);

    }

    /**
     * Метод выключения видимости окна
     */
    @Override
    public void setVisibleFalse() {
        setVisible(false);
    }
    public void setPasswordClient(String passwordClient) {
        this.passwordClient = passwordClient;
    }

    /**
     * Метод возврата клиента
     */
    public ClientInterface getClient(){
        return this;
    }
    /**
     * Метод создания параметров окна
     */
    public void settingWindow() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat (" + getClient().getLoginClient() + ")");
        setLocation(WIDTH - 100, HEIGHT - 100);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    /**
     * Метод создания элементов чата
     */
    public void chatWindow() {
        messageField = new JTextField();
        sendMessage = new JButton("Отправить");
        messagePanel = new JPanel(new GridLayout(1, 2));
        disconnectPanel = new JPanel(new GridLayout(1, 2));
        disconnectNullLabel = new JLabel();
        disconnectButton = new JButton("Отключиться");
        disconnectPanel.add(disconnectNullLabel);
        disconnectPanel.add(disconnectButton);
        messagePanel.add(messageField);
        messagePanel.add(sendMessage);
        add(disconnectPanel, BorderLayout.NORTH);
        add(messagePanel, BorderLayout.SOUTH);
        try {
            chat = new TextArea(fileStorage.readFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        add(chat);
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnectClient();
            }
        });
        sendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.getClientList().contains(getClient()) && !messageField.getText().equals("") && server.isServerStart() && connectStatus){
                    fileStorage.whriteInLogFile( getClient().getLoginClient() + ": " + messageField.getText());
                    messageField.setText("");
                    updateChat();
                    server.mailing();
                }
            }

        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.getClientList().contains(getClient()) && !messageField.getText().equals("") && server.isServerStart() && connectStatus){
                    fileStorage.whriteInLogFile( getClient().getLoginClient() + ": " + messageField.getText());
                    messageField.setText("");
                    updateChat();
                    server.mailing();
                }
            }
        });
    }
    /**
     * Метод для обновления чата клиентов
     */
    public void updateChat(){
        chat.setText("");
        for (ClientInterface client1: server.getClientList())
        {
            try {
                chat.setText(fileStorage.readFile());
                chat.repaint();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
