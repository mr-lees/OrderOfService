/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import java.sql.SQLException;

/**
 *
 * @author natha
 */
public class DeleteSpeaker extends javax.swing.JFrame {

    /**
     * Creates new form DeleteSpeaker
     */
    public DeleteSpeaker() {
        initComponents();
    }
    
    
    /**
     * Class that deletes a speaker from the database
     */
    public void DeleteSpeakerFromDatabase() {
        boolean error = false;
        int thisError = 0;
        String SQLStatement = "";
        
        //Check to see if the user has entered an ID
        int ID = Integer.parseInt(tfSpeakerID.getText());
        int numItems = 0;
        
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Speakers`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Creates an array that holds all of the IDs in the table Speakers
        String[] IDs = new String[numItems];
        boolean FoundID = false;
        
        OrderOfServiceMain.dbObject.sqlString = "select `SpeakerID` from `Speakers`"; 
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        
        if(thisError == 0) {
            try {
                //Variable i is used as a flag for the adding of Speaker IDs to the array IDs
                int i = 0;
                
                while(OrderOfServiceMain.dbObject.rs.next()) {
                    String SpeakerID = OrderOfServiceMain.dbObject.rs.getString ("SpeakerID");
                    
                    //Add the Speaker ID to the array
                    IDs[i] = SpeakerID;
                    
                    //Increment i 
                    i++;
                }
            }
            catch(SQLException ex) {
                // Show errors in console
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                thisError = ex.getErrorCode ();

                UserMessageBox MyMessage = new UserMessageBox ();
                MyMessage.setMessage ("SQLException: " + ex.getMessage());
                MyMessage.setVisible (true);
            }
        }
        
        //Look in array IDs for the correct ID
        for(int i = 0; i < numItems; i++) {
            int currentID = Integer.parseInt(IDs[i]);
            
            if(ID == currentID) {
                FoundID = true;
            }
        }
        
        //If the ID is not found, there is an error
        if(FoundID == false) {
            error = true;
        }
        
        
        
        if(error == false) {
            //Create the SQL Statement
            SQLStatement = "delete from `Speakers` where `SpeakerID` = " + ID;
            
            //For testing purposes
            System.out.println(SQLStatement);
            
            //Add the Song to the database
            OrderOfServiceMain.dbObject.sqlString = SQLStatement;
            OrderOfServiceMain.dbObject.deleteRecord();
            
            if(thisError == 0) {
                UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Success!");
                myMessage.setMessage("The Speaker has been successfully deleted from the database!");
                myMessage.setVisible(true);
            }
        }
        else {
            //Inform the user of an input error
            UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Error!");
                myMessage.setMessage("There has been an error with the inputs. \n"
                        + "Please make sure that you have: \n"
                        + "Entered an eligible speaker ID");
                myMessage.setVisible(true);
        }
        
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lTitle = new javax.swing.JLabel();
        bDelete = new javax.swing.JButton();
        lLogo = new javax.swing.JLabel();
        lSpeakerID = new javax.swing.JLabel();
        tfSpeakerID = new javax.swing.JTextField();
        bExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Delete Speaker");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Delete Speaker");

        bDelete.setBackground(new java.awt.Color(180, 199, 231));
        bDelete.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bDelete.setForeground(new java.awt.Color(31, 56, 100));
        bDelete.setText("Delete Speaker");
        bDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDeleteActionPerformed(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        lSpeakerID.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSpeakerID.setForeground(new java.awt.Color(31, 56, 100));
        lSpeakerID.setText("Enter SpeakerID*:");

        tfSpeakerID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSpeakerIDKeyTyped(evt);
            }
        });

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Menu");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lSpeakerID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfSpeakerID, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lLogo)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSpeakerID)
                    .addComponent(tfSpeakerID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lLogo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action Listener for exit button
     * Disposes the form, but doesn't stop the main application
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        dispose();
    }//GEN-LAST:event_bExitActionPerformed

    /**
     * Action Listener for Delete button
     * Runs the DeleteSpeakerFromDatabase method
     * @param evt 
     */
    private void bDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDeleteActionPerformed
        DeleteSpeakerFromDatabase();
    }//GEN-LAST:event_bDeleteActionPerformed

    /**
     * Key Listener that consumes non numeric inputs
     * Also consumes when the size of the string in text field is 11
     * @param evt 
     */
    private void tfSpeakerIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSpeakerIDKeyTyped
        char inChar = evt.getKeyChar();
        
        if(Character.isDigit(inChar) == false || tfSpeakerID.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_tfSpeakerIDKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DeleteSpeaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeleteSpeaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeleteSpeaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeleteSpeaker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeleteSpeaker().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bDelete;
    private javax.swing.JButton bExit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lSpeakerID;
    private javax.swing.JLabel lTitle;
    private javax.swing.JTextField tfSpeakerID;
    // End of variables declaration//GEN-END:variables
}
