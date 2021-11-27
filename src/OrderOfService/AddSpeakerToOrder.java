/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OrderOfService;

import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 7-NDeverill
 */
public class AddSpeakerToOrder extends javax.swing.JFrame {
    private String Speakers[][];

    
    /**
     * Creates new form AddSongToOrder
     */
    public AddSpeakerToOrder() {
        initComponents();
        fillComboBoxes();
    }
    
    
    /**
     * Method used to fill the combo boxes from this form
     */
    public void fillComboBoxes() {
        int thisError = 0;
        //Number of combobox items after "Choose Song"/"Filter Key"/"Filter Tag"
        int numItems = 0; 
        
        
        //Sets the SQL string to countStatement
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Speakers`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Sets the SQL string to selectStatement
        OrderOfServiceMain.dbObject.sqlString = "select * from `Speakers`";
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                //Initialise the array Speakers
                Speakers = new String[numItems][2];

                //Variable i is used as a way of placing the song in the right location of array Speakers
                int i = 0;

                while (OrderOfServiceMain.dbObject.rs.next()) {
                    String Speaker = OrderOfServiceMain.dbObject.rs.getString ("SpeakerName");
                    String SpeakerID = OrderOfServiceMain.dbObject.rs.getString ("SpeakerID");

                    //Add names of speakers into array Speakers
                    Speakers[i][0] = SpeakerID;
                    Speakers[i][1] = Speaker;

                    //Add the Speaker to the combo box
                    cbSpeaker.addItem(Speaker);
                    
                    //Add the IDs to the combo box
                    cbSpeakerID.addItem(SpeakerID);

                    //Increment i so that next speaker will be placed in a different address of the array
                    i++;
                }
            }
            catch (SQLException ex) {
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
    }
    
    
    /**
     * Filters the items in the combo box relating to a SpeakerID
     */
    public void filterResults(String SpeakerID) {
        int thisError = 0;
        //Number of combobox items after "Choose Speaker"
        int numItems = 0; 
        //String of the SpeakerID number that has been typed into tfID
        String speakerID = SpeakerID;
        
        //Sets the SQL string to count from Speakers
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Speakers` where `SpeakerID` = " + speakerID; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Create SQL Statement to retrieve SpeakerName from every record
        OrderOfServiceMain.dbObject.sqlString = "select * from `Speakers` where `SpeakerID` = " + speakerID;
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                //Array Speakers is made new by initialising it again
                Speakers = new String[numItems][2];
                
                //Delete previous items in combobox
                int maxI = cbSpeaker.getItemCount();
                for(int i = maxI - 1; i > 0; i--) {
                    cbSpeaker.removeItemAt(i);
                }
                
                //Variable i is used as a way of placing the speaker in the right location of array Speakers
                int i = 0;
                
                while (OrderOfServiceMain.dbObject.rs.next()) {
                    String Speaker = OrderOfServiceMain.dbObject.rs.getString ("SpeakerName");
                    String ID = OrderOfServiceMain.dbObject.rs.getString ("SpeakerID");
                    
                    //Add names of speakers to array Speakers
                    Speakers[i][0] = ID;
                    Speakers[i][1] = Speaker;
                    
                    cbSpeaker.addItem(Speaker);
                    
                    //Increment i so that next speaker is placed in a different array location
                    i++;
                }
            }
            catch (SQLException ex) {
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
        lHeading = new javax.swing.JLabel();
        cbSpeaker = new javax.swing.JComboBox();
        lSpeakerID = new javax.swing.JLabel();
        lSpeakerName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lLogo = new javax.swing.JLabel();
        bSubmit = new javax.swing.JButton();
        cbSpeakerID = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Create Service");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lHeading.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lHeading.setForeground(new java.awt.Color(31, 56, 100));
        lHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lHeading.setText("GWBC Create Service");

        cbSpeaker.setBackground(new java.awt.Color(142, 170, 219));
        cbSpeaker.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbSpeaker.setForeground(new java.awt.Color(31, 56, 100));
        cbSpeaker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Speaker" }));
        cbSpeaker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSpeakerItemStateChanged(evt);
            }
        });

        lSpeakerID.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSpeakerID.setForeground(new java.awt.Color(31, 56, 100));
        lSpeakerID.setText("Speaker ID:");

        lSpeakerName.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSpeakerName.setForeground(new java.awt.Color(31, 56, 100));
        lSpeakerName.setText("Speaker Name:");

        tfName.setEditable(false);

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        bSubmit.setBackground(new java.awt.Color(180, 199, 231));
        bSubmit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSubmit.setForeground(new java.awt.Color(31, 56, 100));
        bSubmit.setText("Submit");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        cbSpeakerID.setBackground(new java.awt.Color(142, 170, 219));
        cbSpeakerID.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbSpeakerID.setForeground(new java.awt.Color(31, 56, 100));
        cbSpeakerID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show All" }));
        cbSpeakerID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSpeakerIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lHeading, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lLogo)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lSpeakerID)
                            .addComponent(lSpeakerName))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfName, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(cbSpeakerID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bSubmit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSpeakerID)
                    .addComponent(cbSpeakerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lSpeakerName)
                    .addComponent(bSubmit))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lLogo)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * Sets the text on tfName to the selected item in cbSpeaker
     * @param evt 
     */
    private void cbSpeakerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSpeakerItemStateChanged
        //Sets the tfSpeaker text to the currently chosen song given that it is NOT 'Choose Song'
        if(cbSpeaker.getSelectedIndex() != 0) {       
            String Speaker;
            Speaker = cbSpeaker.getSelectedItem().toString();

            tfName.setText(Speaker);
        }
        //Sets the tfSong text to null when 'Choose Song' is pressed
        else {
            tfName.setText("");
        }
    }//GEN-LAST:event_cbSpeakerItemStateChanged

    /**
     * Listener that adds the selected speaker to the order of service
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        int i = 0;
        int speakerIndex = -1; 
        boolean found = false;
        
        //Does a linear search through ArrayList Speakers to find the selected speaker
        while(i < Speakers.length && found == false) {
            if(Speakers[i][1].equals(tfName.getText())) {
                speakerIndex = i;
                found = true;
            }
            i++;
        }
        
        if(found == true) {
            //Return the current table model for the Order of Service table
            DefaultTableModel model = (DefaultTableModel) OrderOfService.CreateServiceManual.tblOrderOfService.getModel();
            int index = model.getRowCount();
            
            //Add a new row containing the Index, Item Type ("Speaker") and the name of the speaker
            model.addRow(new Object[]{index, "Speaker", Speakers[speakerIndex][1], null});
            
            //Set the model of the Order of Service table to the updated version
            OrderOfService.CreateServiceManual.tblOrderOfService.setModel(model);
            
            //Dispose the AddSpeakerToOrder form
            dispose();
        }
        else {
            UserMessageBox MyMessage = new UserMessageBox ();
            MyMessage.setMessage ("Unnacceptable speaker: \n"
                    + "Must choose a speaker from the list. \n"
                    + "Speaker has not been submitted.");
            MyMessage.setVisible (true);
        }
    }//GEN-LAST:event_bSubmitActionPerformed

    /**
     * Event Listener that filters by SpeakerID
     * @param evt 
     */
    private void cbSpeakerIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSpeakerIDActionPerformed
        if(cbSpeakerID.getSelectedIndex() != 0) {
            String selectedID = cbSpeakerID.getSelectedItem().toString();
            //Filter the results using method and selected SpeakerID as parameter
            filterResults(selectedID);
        }
        //Returns the Songs combo box to full size
        else {
            //Empty Speaker combo box
            int maxI = cbSpeaker.getItemCount();
            for(int i = maxI - 1; i > 0; i--) {
                cbSpeaker.removeItemAt(i);
            }
            
            //Empty SpeakerID combo box
            maxI = cbSpeakerID.getItemCount();
            for(int i = maxI - 1; i > 0; i--) {
                cbSpeakerID.removeItemAt(i);
            }
            
            //Use method to fill the combo boxes
            fillComboBoxes();
        }
    }//GEN-LAST:event_cbSpeakerIDActionPerformed

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
            java.util.logging.Logger.getLogger(AddSpeakerToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSpeakerToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSpeakerToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSpeakerToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddSpeakerToOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSubmit;
    private javax.swing.JComboBox cbSpeaker;
    private javax.swing.JComboBox cbSpeakerID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lHeading;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lSpeakerID;
    private javax.swing.JLabel lSpeakerName;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
