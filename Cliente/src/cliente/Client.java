package cliente;

import data.SendData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client {

    Socket socket;
    private static ConnecServer cn = new ConnecServer();

    String ip;
    int port;

    Chat chat;

    public static void main(String[] args) {
        cn.setVisible(true);
    }

    public boolean connectServer(String IpAdress, int portNumber, String usr) {
        try {

            socket = new Socket(IpAdress, portNumber);
            SendData sendata = new SendData();
            sendata.setIpAddres(getIpAdress());
            sendata.setMessage("connection");
            sendata.setUser(usr);
            sendata.setConnected(false);

            ObjectOutputStream outputToServer = new ObjectOutputStream(socket.getOutputStream());
            outputToServer.writeObject(sendata);

            DataInputStream inputData;
            inputData = new DataInputStream(socket.getInputStream());
            Boolean conn = inputData.readBoolean();
            if (conn) {
                ip = IpAdress;
                port = portNumber;
            }
            outputToServer.close();
            inputData.close();
            return conn;

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "error", ex);
        }
        return false;
    }

    public void SendData(SendData sendChat) {
        try {
            socket = new Socket(ip, port);
            ObjectOutputStream chat = new ObjectOutputStream(socket.getOutputStream());
            chat.writeObject(sendChat);
            
            ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());
            
            chat.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String getIpAdress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip.getHostAddress();
    }

}
