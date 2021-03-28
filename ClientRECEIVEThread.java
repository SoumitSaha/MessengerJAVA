import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;

/**
 * Created by Soumit on 11/17/2016.
 */
public class ClientRECEIVEThread extends Thread {

    DatagramPacket pack;
    DatagramSocket sock_in;
    int listeningport;
    String recipient, clientname;

    public void receive() throws Exception {
        byte data[] = new byte[10000];
        pack = new DatagramPacket(data, data.length);
        sock_in.receive(pack);
        data = pack.getData();
        String dataString = new String(data, 0, pack.getLength());
        StringTokenizer token = new StringTokenizer(dataString, "/");
        token.nextToken();
        recipient = token.nextToken();
        if (recipient.equals(clientname)) {
            String sender = token.nextToken();
            String body = token.nextToken();
            System.out.println(sender + " says: " + body);
        }
    }

    public ClientRECEIVEThread(String listeningport, String clientname, DatagramSocket sock_in) {
        this.listeningport = Integer.parseInt(listeningport);
        this.clientname = clientname;
        this.sock_in = sock_in;
        this.start();
    }

    public void run() {
        while (true) {
            try {
                receive();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
