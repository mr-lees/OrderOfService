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
public class EditSong extends javax.swing.JFrame {

    /**
     * Creates new form EditSong
     */
    public EditSong() {
        initComponents();
    }
    
    
    /**
     * Class that edits the song in the database
     */
    public void EditSongInDatabase() {
        boolean hasArtist = false;
        boolean hasYear = false;
        boolean error = false;
        int thisError = 0;
        String SQLStatement = "";
        
        //Check to see if the user has entered an ID
        int ID = Integer.parseInt(tfSongID.getText());
        int numItems = 0;
        
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(*) from `Songs`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Creates an array that holds all of the IDs in the table Songs
        String[] IDs = new String[numItems];
        boolean FoundID = false;
        
        OrderOfServiceMain.dbObject.sqlString = "select `SongID` from `Songs`"; 
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        
        if(thisError == 0) {
            try {
                //Variable i is used as a flag for the adding of Song IDs to the array IDs
                int i = 0;
                
                while(OrderOfServiceMain.dbObject.rs.next()) {
                    String SongID = OrderOfServiceMain.dbObject.rs.getString ("SongID");
                    
                    //Add the Song ID to the array
                    IDs[i] = SongID;
                    
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
        
        
        
        //Check to see if the user has given a Title and if it fits the length
        if(tfTitle.getText().length() <= 0 || tfTitle.getText().length() > 50) {
            error = true;
        }
        
        //Check to see if the user has given a key and if it fits the length
        if(tfKey.getText().length() <= 0 || tfKey.getText().length() > 20) {
            error = true;
        }
        
        if(error == false) {
            //Check to see whether the user has given an artist
            if(tfArtist.getText().length() > 0) {
                hasArtist = true;
            }
            
            //Check to see whether the user has given an (acceptable) year
            if(tfYear.getText().length() == 4) {
                hasYear = true;
            }
            
            //Make the SQL statement using the variable ID from earlier
            String Title = tfTitle.getText();
            String Artist = tfArtist.getText();
            String Year = tfYear.getText();
            String Key = tfKey.getText();
            
            SQLStatement = "update `Songs` "
                    + "set `SongTitle` = '" + Title + "', ";
            
            //If the user has inputted an Artist, add a line to the SQL Statement
            if(hasArtist == true) {
                SQLStatement = SQLStatement + "`Artist` = '" + Artist + "', ";
            }
            else {
                SQLStatement = SQLStatement + "`Artist` = null, ";
            }
            
            //If the user has inputted a year, add a line to the SQL Statement
            if(hasYear == true) {
                SQLStatement = SQLStatement + "`SongYear` = " + Year + ", ";
            }
            else {
                SQLStatement = SQLStatement + "`SongYear` = null, ";
            }
            
            SQLStatement = SQLStatement + "`Key` = '" + Key + "' "
                    + "where `SongID` = " + ID;
            
            //For testing purposes
            System.out.println(SQLStatement);
            
            //Edit the Song in the database
            OrderOfServiceMain.dbObject.sqlString = SQLStatement;
            OrderOfServiceMain.dbObject.updateRecord();
            
            if(thisError == 0) {
                UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Success!");
                myMessage.setMessage("The Song has been successfully added to the database!");
                myMessage.setVisible(true);
            }
        }
        else {
            //Inform the user of an input error
            UserMessageBox myMessage = new UserMessageBox();
                myMessage.setTitle("Error!");
                myMessage.setMessage("There has been an error with the inputs. \n"
                        + "Please make sure that you have: \n"
                        + "1. Entered an eligible ID \n"
                        + "2. Entered a Title \n"
                        + "3. Entered a Key");
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
        bSubmit = new javax.swing.JButton();
        lLogo = new javax.swing.JLabel();
        lSongID = new javax.swing.JLabel();
        lArtist = new javax.swing.JLabel();
        lYear = new javax.swing.JLabel();
        lKey = new javax.swing.JLabel();
        tfSongID = new javax.swing.JTextField();
        tfArtist = new javax.swing.JTextField();
        tfYear = new javax.swing.JTextField();
        tfKey = new javax.swing.JTextField();
        bExit = new javax.swing.JButton();
        lSongTitle = new javax.swing.JLabel();
        tfTitle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Edit Song");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Edit Song");

        bSubmit.setBackground(new java.awt.Color(180, 199, 231));
        bSubmit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSubmit.setForeground(new java.awt.Color(31, 56, 100));
        bSubmit.setText("Submit Changes");
        bSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSubmitActionPerformed(evt);
            }
        });

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        lSongID.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSongID.setForeground(new java.awt.Color(31, 56, 100));
        lSongID.setText("Enter SongID*:");

        lArtist.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lArtist.setForeground(new java.awt.Color(31, 56, 100));
        lArtist.setText("Enter Artist:");

        lYear.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lYear.setForeground(new java.awt.Color(31, 56, 100));
        lYear.setText("Enter Year:");

        lKey.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lKey.setForeground(new java.awt.Color(31, 56, 100));
        lKey.setText("Enter Key*:");

        tfSongID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSongIDKeyTyped(evt);
            }
        });

        tfYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfYearActionPerformed(evt);
            }
        });
        tfYear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfYearKeyTyped(evt);
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

        lSongTitle.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSongTitle.setForeground(new java.awt.Color(31, 56, 100));
        lSongTitle.setText("Enter Title*:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lLogo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lYear)
                            .addComponent(lArtist)
                            .addComponent(lKey)
                            .addComponent(lSongTitle))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfKey, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(tfYear, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfArtist, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfTitle)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bExit, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(bSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lSongID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfSongID, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSongID)
                    .addComponent(tfSongID, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lSongTitle))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfArtist, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lArtist))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfYear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lYear))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfKey, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lKey))
                .addGap(18, 30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
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
     * Action Listener for Submit button
     * Runs the EditSongInDatabase method
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        EditSongInDatabase();
    }//GEN-LAST:event_bSubmitActionPerformed

    /**
     * Key Listener that consumes non numeric inputs
     * Also consumes when the size of the string in text field is 4
     * @param evt 
     */
    private void tfYearKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfYearKeyTyped
        char inChar = evt.getKeyChar();
        
        if(Character.isDigit(inChar) == false || tfYear.getText().length() == 4) {
            evt.consume();
        }
    }//GEN-LAST:event_tfYearKeyTyped

    private void tfYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfYearActionPerformed

    /**
     * Key Listener that consumes non numeric inputs
     * Also consumes when the size of the string in text field is 11
     * @param evt 
     */
    private void tfSongIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSongIDKeyTyped
        char inChar = evt.getKeyChar();
        
        if(Character.isDigit(inChar) == false || tfSongID.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_tfSongIDKeyTyped

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
            java.util.logging.Logger.getLogger(EditSong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditSong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditSong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditSong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditSong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bSubmit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lArtist;
    private javax.swing.JLabel lKey;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lSongID;
    private javax.swing.JLabel lSongTitle;
    private javax.swing.JLabel lTitle;
    private javax.swing.JLabel lYear;
    private javax.swing.JTextField tfArtist;
    private javax.swing.JTextField tfKey;
    private javax.swing.JTextField tfSongID;
    private javax.swing.JTextField tfTitle;
    private javax.swing.JTextField tfYear;
    // End of variables declaration//GEN-END:variables
}
