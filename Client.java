import java.net.*;
import java.util.Scanner;

/**
 * Created by Soumit on 11/17/2016.
 */

public class Client implements Runnable {
    DatagramPacket pack, pack1;
    DatagramSocket sock_out, sock_in;
    String clientname, servername;
    String clientport;
    String serverIP;

    Client(String clientname, String clientport, String serverIP, String servername) {
        this.clientname = clientname;
        this.clientport = clientport;
        this.servername = servername;
        this.serverIP = serverIP;
        try {
            sock_out = new DatagramSocket();
            sock_in = new DatagramSocket(Integer.parseInt(clientport));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            sendInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    private void sendInfo() throws Exception {
        String data1 = servername + "/" + servername + "/" + clientname + "/" + clientport;
        byte data[] = data1.getBytes();
        pack1 = new DatagramPacket(data, data.length);
        InetAddress add = InetAddress.getByName(serverIP);
        pack1.setAddress(add);
        pack1.setPort(50500);
        //sock_out = new DatagramSocket();
        sock_out.send(pack1);
        //sock.close();
    }

    public void run() {
        try {
            new ClientSENDThread(clientname, serverIP, servername, sock_in, sock_out);
            new ClientRECEIVEThread(clientport, clientname, sock_in);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {

        new Client(args[0], args[1], args[2], args[3]);
    }
}
