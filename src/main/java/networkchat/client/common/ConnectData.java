package networkchat.client.common;

public record ConnectData(
        String address,
        int port,
        String login,
        String password) {

    public ConnectData(String address, int port, String login, String password) {
        this.address = address;
        this.port = port;
        this.login = login;
        this.password = password;
    }
}