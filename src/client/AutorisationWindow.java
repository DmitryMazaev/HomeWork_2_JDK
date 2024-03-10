package client;
import db.Repository;
import server.Server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AutorisationWindow extends JFrame{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    private JLabel loginLabel;
    private JTextField loginTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JLabel auotorisationNullPanel;
    private JButton autorisationButton;
    private JPanel autorisationPanel;
    Server server;
    ClientInterface clientWindow;
    Repository fileStorage;

    public AutorisationWindow(Server server, Repository fileStorage){
        this.server = server;
        this.fileStorage = fileStorage;
        settingWindow();
        autorisationMenu();
        setVisible(true);
    }
    /**
     * Метод создания параметров окна
     */
    public void settingWindow() {
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Autorisation menu");
        setLocation(WIDTH - 100, HEIGHT - 100);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    /**
     * Метод создания элементов для авторизации клиента
     */
    public void autorisationMenu(){
        autorisationPanel = new JPanel(new GridLayout(3, 2));
        autorisationPanel.setBackground(Color.cyan);
        loginLabel = new JLabel("Логин: ");
        loginTextField = new JTextField();
        passwordLabel = new JLabel("Пароль");
        passwordTextField = new JTextField();
        auotorisationNullPanel = new JLabel();
        autorisationButton = new JButton("Войти");
        autorisationPanel.add(loginLabel);
        autorisationPanel.add(loginTextField);
        autorisationPanel.add(passwordLabel);
        autorisationPanel.add(passwordTextField);
        autorisationPanel.add(auotorisationNullPanel);
        autorisationPanel.add(autorisationButton);
        add(autorisationPanel, BorderLayout.NORTH);

        autorisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(server.isServerStart() && !loginTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
                    clientWindow = new ClientWindow(server, loginTextField.getText(), passwordTextField.getText(), true, fileStorage);
                    server.connectUser(clientWindow);
                    fileStorage.whriteInLogFile(clientWindow.getLoginClient() + " вошел в чат");
                    //clientWindow.updateChat();
                    server.mailing();
                    setTitle("Chat (" + clientWindow.getLoginClient() + ")");
                    autorisationPanel.setVisible(false);
                    setVisible(false);
                }
                else {
                    auotorisationNullPanel.setText("Сервер выключен!");
                }
            }
        });
    }
}
