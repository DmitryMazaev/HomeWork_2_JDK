import client.AutorisationWindow;
import client.ClientInterface;
import client.ClientWindow;
import db.FileStorage;
import db.Repository;
import server.Server;
import server.ServerInterface;
import server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        Repository fileStorage = new FileStorage();
        Server server = new Server(fileStorage);
        ServerInterface serverWindow = new ServerWindow(server, fileStorage);
        AutorisationWindow autorisationWindow1 = new AutorisationWindow(server, fileStorage);
        AutorisationWindow autorisationWindow2 = new AutorisationWindow(server, fileStorage);
    }
}