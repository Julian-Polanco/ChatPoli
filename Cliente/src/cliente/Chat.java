package cliente;

import data.SendData;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat extends javax.swing.JFrame implements Runnable {

    public Client client;

    public Chat() {
        initComponents();
        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    public void setUser(String user) {
        this.user.setText(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        ipTarget = new javax.swing.JComboBox<>();
        user = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chat = new javax.swing.JTextArea();
        msg = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SendMessage = new javax.swing.JButton();
        user1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("-Chat Poli-");

        user.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        user.setText("nick");

        chat.setColumns(20);
        chat.setRows(5);
        jScrollPane1.setViewportView(chat);

        msg.setMinimumSize(new java.awt.Dimension(350, 30));
        msg.setVerifyInputWhenFocusTarget(false);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Escribe tu mensaje");

        SendMessage.setText("Enviar");
        SendMessage.setPreferredSize(new java.awt.Dimension(90, 30));
        SendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendMessageActionPerformed(evt);
            }
        });

        user1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        user1.setText("online");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(user)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(user1)
                .addGap(18, 18, 18)
                .addComponent(ipTarget, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 31, Short.MAX_VALUE)
                .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(SendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(ipTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(user)
                            .addComponent(user1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(458, 458, 458)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SendMessage.getAccessibleContext().setAccessibleName("sendMessage");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendMessageActionPerformed
        ipList ipSeleccionada = (ipList) ipTarget.getSelectedItem();

        SendData sendData = new SendData();
        sendData.setIpAddres(ipSeleccionada.getIp());
        sendData.setUser(user.getText());

        sendData.setMessage(msg.getText());
        sendData.setConnected(true);
        this.SetMsg(user.getText() + " --> " + msg.getText() + "\n");
        msg.setText("");
        client.SendData(sendData);
    }//GEN-LAST:event_SendMessageActionPerformed

    public void SetMsg(String msg) {
        chat.append(msg);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SendMessage;
    private javax.swing.JTextArea chat;
    private javax.swing.JComboBox<ipList> ipTarget;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField msg;
    private javax.swing.JLabel user;
    private javax.swing.JLabel user1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket socketCliente;
            SendData recievedData;
            while (true) {
                socketCliente = serverSocket.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());
                recievedData = (SendData) flujoEntrada.readObject();
                if (!recievedData.getMessage().equals("connection")) {
                    if (recievedData.isConnected()) {
                        this.SetMsg(recievedData.getUser() + " --> " + recievedData.getMessage() + "\n");
                    } else {
                        this.SetMsg("Abandonaste el chat");
                        SendMessage.setEnabled(false);
                    }
                } else {
                    ipTarget.removeAllItems();
                    for (String i : recievedData.getIpList()) {
                        String[] ipWithoutUser = i.split(";");
                        ipList list = new ipList();
                        list.setIp(ipWithoutUser[0]);
                        list.setUser(ipWithoutUser[1]);
                        ipTarget.addItem(list);
                    }
                }
            }
        } catch (EOFException e) {

        } catch (IOException e) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, "IO", e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, "NF", ex);
        }

    }
}

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
