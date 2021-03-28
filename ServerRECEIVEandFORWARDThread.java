import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * Created by Soumit on 11/17/2016.
 */
public class ServerRECEIVEandFORWARDThread extends Thread {

    DatagramPacket pack1;
    DatagramSocket sock;
    String servername;
    private Hashtable<String, Infos> table;

    public ServerRECEIVEandFORWARDThread(Hashtable table, String name) {
        this.servername = name;
        this.table = table;
        this.start();
    }

    public void run() {
        while (true) {
            byte data[] = new byte[10000];
            pack1 = new DatagramPacket(data, data.length);
            try {
                sock = new DatagramSocket(50500);
                sock.receive(pack1);

                ToChecking();
                sock.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Exception in setting recipient's port extracting from Infos" + e);
            }

            sock.close();
        }
    }

    public void ToChecking() throws Exception {
        byte[] data = pack1.getData();
        String dataString = new String(data, 0, pack1.getLength());
        StringTokenizer token = new StringTokenizer(dataString, "/");
        InetAddress clientaddress = pack1.getAddress();
        String viastring = token.nextToken();
        String recipientname = token.nextToken();
        String sendername = token.nextToken();


        if (viastring.equals(servername)) {
            if (recipientname.equals(servername)) {
                int senderport = Integer.parseInt(token.nextToken());
                Infos clientinfos = new Infos(sendername, senderport, clientaddress);
                table.put(sendername, clientinfos);
            } else {
                if (!table.containsKey(recipientname)) {
                    System.out.println("Warning: Unknown recipient. Message dropped.");
                } else {
                    Infos recipientinfo = table.get(recipientname);
                    int recipientport = recipientinfo.port;
                    DatagramSocket s = new DatagramSocket();
                    pack1.setPort(recipientport);
                    pack1.setAddress(recipientinfo.address);
                    s.send(pack1);
                }
            }
        } else {
            System.out.println("Warning: Server name mismatch. Message dropped.");
        }
    }
}
