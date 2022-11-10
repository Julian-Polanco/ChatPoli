package cliente;

import data.SendData;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Implementa el cliente. Hace uso de las clases ConnecServer y Chat para la interfaz gráfica y
 * la lógica relacionada con las mismas.
 */
public class Client {

    Socket socket;
    private static ConnecServer cn = new ConnecServer();

    String ip;
    int port;

    Chat chat;

    public static void main(String[] args) {
        cn.setVisible(true);
    }

    /***
     * Intenta establecer la conexión con el servidor.
     * @param IpAdress La IP del servidor
     * @param portNumber EL puerto del servidor
     * @param usr El nombre de usuario usado para esta conexión
     * @return true si la conexión fue exitosa
     */
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

    /***
     * Envía un mensaje al servidor. Hace uso de la clase SendData.
     * @param sendChat Los datos a ser enviados ya serializados.
     */
    public void SendData(SendData sendChat) {
        try {
            socket = new Socket(ip, port);
            ObjectOutputStream chat = new ObjectOutputStream(socket.getOutputStream());
            chat.writeObject(sendChat);

            ObjectInputStream obj = new ObjectInputStream(socket.getInputStream());

            chat.close();
            socket.close();
        } catch (EOFException e) {
            // ... this is fine
        } catch (IOException e) {
            // handle exception which is not expected
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, "IO", e);
        }

    }

    /***
     * Obtiene la IP usada en la conexión por el cliente
     * @return La Ip del cliente
     */
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
