package servidor;

/***
 * Permite mostrar las entregadas del log que se vayan registrando.
 */
public class ServerLog extends javax.swing.JFrame {
    public ServerLog() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Log = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 500));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Log del servidor");

        Log.setColumns(20);
        Log.setRows(5);
        jScrollPane1.setViewportView(Log);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerLog().setVisible(true);
            }
        });
    }
    
    /***
     * Escribe una entrada en el log
     * @param user El usuario que envio el mensaje
     * @param msg El mensaje enviado
     * @param ip La IP usada por el usuario en la conexci??n al servidor
     */
    public void writeInLog(String user, String msg,String ip){
        Log.append(user+": "+msg+" -> "+ip+"\n");
    }
    
    /***
     * Escribe una entrada en el log
     * @param msg Este mensaje es descartado y no usado en el log ya que se usa un mesaje est??tico
     * @param user El usuario que se est?? conectando
     */
    public void writeInLog(String msg,String user){
        Log.append("usuario \""+user+"\" Conectado\n");
    }
    
    /***
     * Escribe una entrada en el log
     * @param msg El mensaje a registrar en el log
     */
    public void writeInLog(String msg){
        Log.append(msg+"\n");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Log;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
