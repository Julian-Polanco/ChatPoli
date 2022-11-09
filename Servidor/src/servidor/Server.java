package servidor;

import data.SendData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private Set<String> ipList = new HashSet<>();

    private static ServerLog serverLog = new ServerLog();

    public static void main(String[] args) {

        try {
            Server obj = new Server();
            obj.run(args);
            serverLog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// instance variables here
    public void run(String[] args) throws Exception {
        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {

        try {
            ServerSocket server = new ServerSocket(5002);

            String user, ip, message, conn;
            boolean connected;

            SendData recievedData;

            while (true) {
                Socket client = server.accept();

                ObjectInputStream objectInput = new ObjectInputStream(client.getInputStream());

                recievedData = (SendData) objectInput.readObject();
                user = recievedData.getUser();
                ip = recievedData.getIpAddres();
                message = recievedData.getMessage();
                connected = recievedData.isConnected();
                if (!message.equals("connection")) {
                    client.close();
                    serverLog.writeInLog(user, message, ip);
                    try {
                        InetAddress localizacion2 = client.getInetAddress();
                        String ipRemota = localizacion2.getHostAddress();
                        if (message.equalsIgnoreCase("chao")) {
                            recievedData.setMessage("El usuario " + user + " abandono el chat");
                            recievedData.setConnected(false);
                            Socket sentClient = new Socket(ipRemota, 9999);
                            OutputStream outputToClient = sentClient.getOutputStream();
                            ObjectOutputStream dataToClient = new ObjectOutputStream(outputToClient);
                            dataToClient.writeObject(recievedData);
                            dataToClient.close();
                            sentClient.close();

                            recievedData.setConnected(true);
                            Socket sentClient2 = new Socket(ip, 9999);
                            OutputStream outputToClient2 = sentClient2.getOutputStream();
                            ObjectOutputStream dataToClient2 = new ObjectOutputStream(outputToClient2);
                            dataToClient2.writeObject(recievedData);
                            dataToClient2.close();
                            sentClient2.close();

                        } else {
                            Socket sentClient = new Socket(ip, 9999);
                            OutputStream outputToClient = sentClient.getOutputStream();
                            ObjectOutputStream dataToClient = new ObjectOutputStream(outputToClient);
                            dataToClient.writeObject(recievedData);
                            dataToClient.close();
                            sentClient.close();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else {
                    //------------------obtener los clientes conectados---------------------///
                    InetAddress localizacion2 = client.getInetAddress();
                    String ipRemota = localizacion2.getHostAddress();
                    ipList.add(ipRemota);

                    recievedData.setIpList(ipList);

                    InetAddress ipCl = client.getInetAddress();
                    String ipClient = ipCl.getHostAddress();

                    OutputStream outputToClient1 = client.getOutputStream();
                    DataOutputStream dataToClient1 = new DataOutputStream(outputToClient1);
                    dataToClient1.writeBoolean(true);
                    for (String i : ipList) {
                        try {
                            Socket sentClient = new Socket(i, 9999);
                            OutputStream outputToClient = sentClient.getOutputStream();
                            ObjectOutputStream dataToClient = new ObjectOutputStream(outputToClient);
                            dataToClient.writeObject(recievedData);
                            dataToClient.close();
                            outputToClient.close();
                            sentClient.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    ////---------------------------------------------------------------------///
                    objectInput.close();
                    outputToClient1.close();
                    dataToClient1.close();
                    client.close();
                    serverLog.writeInLog(message, ipRemota);

                }
                client.close();
                objectInput.close();
                //DataInputStream inputStream = new DataInputStream(client.getInputStream());
                //String msg = inputStream.readUTF();
                //serverLog.writeInLog(msg);
            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "ex1", ex);
        } catch (ClassNotFoundException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "ex2", ex);
        }
    }

}
