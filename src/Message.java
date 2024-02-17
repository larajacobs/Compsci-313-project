package src;

/**
 * Message Object
 */
public class Message {
    private Client receiver;
    private Client sender;
    private String msg;

    public Message(Client receiver, Client sender, String msg) {
        this.receiver = receiver;
        this.sender = sender;
        this.msg = msg;
    }

    public Client getReceiver() {
        return receiver;
    }

    public Client getSender() {
        return sender;
    }

    public String getMsg() {
        return msg;
    }
}