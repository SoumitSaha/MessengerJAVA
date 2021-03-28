import java.net.InetAddress;

/**
 * Created by Soumit on 11/17/2016.
 */
public class Infos {
    String name;
    InetAddress address;
    int port;

    public Infos(String name) {
        this.name = name;
    }

    public Infos(String name, int port, InetAddress address) {
        this.name = name;
        this.port = port;
        this.address = address;
    }
}
