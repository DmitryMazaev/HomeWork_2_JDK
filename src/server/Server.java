package server;
import client.AutorisationWindow;
import client.ClientInterface;
import db.Repository;
import java.util.ArrayList;

public class Server {
    /**
     * Список клиентов
     */
    private ArrayList<ClientInterface> clientList = new ArrayList<>();
    /**
     * Статус работы сервера
     */
    private boolean isServerStart = false;
    Repository fileStorage;
    public ArrayList<ClientInterface> getClientList() {
        return clientList;
    }
    public Server(Repository fileStorage) {
        this.fileStorage = fileStorage;
    }
    public void setClientList(ArrayList<ClientInterface> clientList) {
        this.clientList = clientList;
    }
    public boolean isServerStart() {
        return isServerStart;
    }
    public void setServerStart(boolean serverStart) {
        isServerStart = serverStart;
    }
    /**
     * Включение клиента в список клиентов
     */
    public void connectUser (ClientInterface clientWindow)
    {
        clientList.add(clientWindow);
    }

    /**
     * Метод рассылки сообщений подключенным клиентам
     */
    public void mailing()
    {
        for (ClientInterface clientInterface: clientList)
        {
            clientInterface.updateChat();
        }
    }

    /**
     * Метод отключения клиента от чата
     */
    public void disconnectFromChat(ClientInterface clientInterface)
    {
        clientInterface.setConnectStatus(false);
        fileStorage.whriteInLogFile(clientInterface.getLoginClient() + " покинул чат");
        //clientList.remove(clientInterface);
        mailing();
        clientInterface.setVisibleFalse();
        clientList.remove(clientInterface);
        AutorisationWindow autorisationWindow = new AutorisationWindow(this, fileStorage);
    }

    /**
     * Метод отключения всех клиентов от сервера
     */
    public void disconnectFromServer()
    {
        for (ClientInterface client: clientList)
        {
            disconnectFromChat(client);
        }
    }

}