package client;

public interface ClientInterface {
    void settingWindow();
    void chatWindow();
    void updateChat();
    String getLoginClient();
    boolean isConnectStatus();
    void setConnectStatus(boolean connectStatus);
    void disconnectClient();
    void setVisibleFalse();

}
