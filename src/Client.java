package src;

/**
 * Client Object
 */
public class Client {
    private String username;
    private String IPaddress;
    private boolean online;

    public Client(String username, String IPaddress, boolean online) {
        this.username = username;
        this.IPaddress = IPaddress;
        this.online = online;
    }

    public String getUsername() {
        return username;
    }

    public String getIPaddress() {
        return IPaddress;
    }

    public boolean isOnline() {
        return online;
    }
}