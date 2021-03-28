import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Soumit on 11/17/2016.
 */
public class ClientSENDThread extends Thread {
    DatagramPacket pack1;
    DatagramSocket sock_out, sock_in;
    String ClientName, Servername, serverIP;

    public void send() throws Exception {
        Scanner sc = new Scanner(System.in);
        String data1 = sc.nextLine();
        StringTokenizer token = new StringTokenizer(data1, "$");
        String recipientname = token.nextToken();
        String msgbody = token.nextToken();
        String data2 = Servername + "/" + recipientname + "/" + ClientName + "/" + msgbody;
        byte data[] = data2.getBytes();
        pack1 = new DatagramPacket(data, data.length);
        InetAddress add = InetAddress.getByName(serverIP);
        pack1.setAddress(add);
        pack1.setPort(50500);
        sock_out.send(pack1);
    }

    public ClientSENDThread(String ClientName, String serverIP, String Servername, DatagramSocket sock_out, DatagramSocket sock_in) {
        //this.ClientListeningPort = ClientListeningPort;
        this.ClientName = ClientName;
        this.Servername = Servername;
        this.serverIP = serverIP;
        this.sock_in = sock_in;
        this.sock_out = sock_out;
        this.start();
    }

    public void run() {
        while (true) {
            try {
                send();
            } catch (Exception e) {
                System.out.println("Exception in SENDING message of Client " + ClientName);
            }
        }
    }
}
