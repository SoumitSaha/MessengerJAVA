import java.util.Hashtable;
import java.net.*;

/**
 * Created by Soumit on 11/17/2016.
 */

public class Server implements Runnable {
    String ServerName;
    private Hashtable<String, Infos> table;

    Server(String str) {
        ServerName = str;
        table = new Hashtable<>();
        new Thread(this).start();
    }

    public void run() {
        new ServerRECEIVEandFORWARDThread(table, ServerName);
    }


    public static void main(String[] args) {
        new Server(args[0]);
    }
}
