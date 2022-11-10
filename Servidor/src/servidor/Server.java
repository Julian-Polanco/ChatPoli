package servidor;

import data.SendData;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * EL servidor como tal, ejecuta un hilo donde se mantiene toda la lógica, asi como una lista de
 * las Ips conectadas y lleva el log.
 * Se utiliza el puerto 5002 por defecto para escucahr las peticiones.
 * Se asume que los clientes usaran el puerto 9999.
 */
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
                    String TargetUser = null;
                    for (String ipTarget : ipList) {
                        System.out.println(ipTarget);
                        String[] ipWithoutUser = ipTarget.split(";");
                        if (ipWithoutUser[0].equals(ip)) {
                            TargetUser = ipWithoutUser[1];
                        }
                    }
                    serverLog.writeInLog(user, message, TargetUser);
                    try {
                        InetAddress localizacion2 = client.getInetAddress();
                        String ipRemota = localizacion2.getHostAddress();
                        if (message.equalsIgnoreCase("chao")) {
                            recievedData.setMessage("El usuario " + user + " abandono el chat");
                            serverLog.writeInLog(recievedData.getIpAddres());
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
                    InetAddress localizacion2 = client.getInetAddress();
                    String ipRemota = localizacion2.getHostAddress();
                    ipList.add(ipRemota + ";" + recievedData.getUser());

                    recievedData.setIpList(ipList);

                    InetAddress ipCl = client.getInetAddress();
                    String ipClient = ipCl.getHostAddress();

                    OutputStream outputToClient1 = client.getOutputStream();
                    DataOutputStream dataToClient1 = new DataOutputStream(outputToClient1);
                    dataToClient1.writeBoolean(true);
                    for (String i : ipList) {
                        String[] ipWithoutUser = i.split(";");
                        try {
                            Socket sentClient = new Socket(ipWithoutUser[0], 9999);
                            OutputStream outputToClient = sentClient.getOutputStream();
                            ObjectOutputStream dataToClient = new ObjectOutputStream(outputToClient);
                            dataToClient.writeObject(recievedData);
                            dataToClient.close();
                            outputToClient.close();
                            sentClient.close();
                        } catch (Exception ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "ex1", ex);
                        }
                    }
                    objectInput.close();
                    outputToClient1.close();
                    dataToClient1.close();
                    client.close();
                    serverLog.writeInLog(message, user);

                }
                client.close();
                objectInput.close();
            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "ex1", ex);
        } catch (ClassNotFoundException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "ex2", ex);
        }
    }

}

/***
 * Mantiene los datos del cliente. Nombre del mismo e Ip usada en la conexión
 */
class ipList {

    private String ip;

    private String User;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String toString() {
        return User;
    }
}
