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
 * @author natha
 */
public class ReviewSongs extends javax.swing.JFrame {

    /**
     * Creates new form ReviewSongs
     */
    public ReviewSongs() {
        initComponents();
        
        //Add Songs to the table
        String SongCount = "select COUNT(*) from `Songs`";
        String SelectSongs = "select * from `Songs`";
        getSongs(SongCount, SelectSongs);
        
        //Add keys to the combo box
        fillComboBox();
    }
    
    /**
     * Method that retrieves the songs and their data 
     * and adds it to the table
     * @param SongCount is the count statement (SQL)
     * @param SelectSongs is the select statement (SQL)
     */
    public void getSongs(String SongCount, String SelectSongs) {
        int thisError = 0;
        //Number of records in the table
        int numItems = 0;
        //Array holding all of the SongIDs
        String[] SongIDs;
        
        //SQL Statement that counts the number of records in table `Songs` of DB
        String songCount = SongCount;
        
        //SQL Statement that gets all the information in the Song's record 
        String selectSongs = SelectSongs;
        
        //Sets the SQL string to songCount
        OrderOfServiceMain.dbObject.sqlString = songCount; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        SongIDs = new String[numItems];
        
        //Sets the SQL string to selectSongs
        OrderOfServiceMain.dbObject.sqlString = selectSongs;
        
        //Execute the statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if(thisError == 0) {
            try { 
                //Variable i is used to not overwrite data in the array SongIDs
                int i = 0;
                
                while(OrderOfServiceMain.dbObject.rs.next()) {
                    //Get the results from the result set
                    String SongID = OrderOfServiceMain.dbObject.rs.getString ("SongID");
                    String SongTitle = OrderOfServiceMain.dbObject.rs.getString ("SongTitle");
                    String Artist = OrderOfServiceMain.dbObject.rs.getString ("Artist");
                    String Year = OrderOfServiceMain.dbObject.rs.getString ("SongYear");
                    String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                    
                    DefaultTableModel model = (DefaultTableModel) tblSongs.getModel();
                    
                    model.addRow(new Object[]{SongID, SongTitle, Artist, Year, Key});
                    
                    //Add the ID to array SongIDs
                    SongIDs[i] = SongID;
                    
                    //Increment i
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
        
        
        for(int i = 0; i < tblSongs.getRowCount(); i++) {
            int SongID = Integer.parseInt(SongIDs[i]);
            //SQL Statement that gets the number of tags for a particular song
            String numTags = "select COUNT(*) from `SongTags` where `SongID` = " + SongID;
        
            //SQLStatement that gets the number of uses for a song
            String numUses = "select COUNT(*) from `Contents` where `SongID` = " + SongID;
            
            //For testing purposes
            System.out.println(numTags);
            System.out.println(numUses);
            
            OrderOfServiceMain.dbObject.sqlString = numTags;
            
            
            //Execute the statement
            OrderOfServiceMain.dbObject.getCountBySelect();
            
            int NumTags = OrderOfServiceMain.dbObject.NumberOfRecords;
            
            
            OrderOfServiceMain.dbObject.sqlString = numUses;
            
            //Execute the statement
            OrderOfServiceMain.dbObject.getCountBySelect();
            
            int NumUses = OrderOfServiceMain.dbObject.NumberOfRecords;
            
            //Get the table model so that NumTags and NumUses can be added
            DefaultTableModel model = (DefaultTableModel) tblSongs.getModel();
            
            //Add NumTags and NumUses to table
            model.setValueAt(NumTags, i, 5);
            model.setValueAt(NumUses, i, 6);
        }
        
        
    }
    
    
    /**
     * Method that fills the combo box cbKey
     */
    public void fillComboBox() {
        int thisError = 0;
        //Number of items in the combo box
        int numItems = 0;
        
        
        //Sets the SQL string to the count statement
        OrderOfServiceMain.dbObject.sqlString = "select COUNT(distinct `Key`) from `Songs`"; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Set the sqlString to the select statement
        OrderOfServiceMain.dbObject.sqlString = "select distinct `Key` from `Songs`";
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if(thisError == 0) {
            try {
                while(OrderOfServiceMain.dbObject.rs.next()) {
                    String Key = OrderOfServiceMain.dbObject.rs.getString("Key");
                    
                    cbKey.addItem(Key);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSongs = new javax.swing.JTable();
        lLogo = new javax.swing.JLabel();
        lFilter = new javax.swing.JLabel();
        lKey = new javax.swing.JLabel();
        bExit = new javax.swing.JButton();
        lSongID = new javax.swing.JLabel();
        tfSongID = new javax.swing.JTextField();
        bSongDetails = new javax.swing.JButton();
        cbKey = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Review Songs");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lTitle.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lTitle.setForeground(new java.awt.Color(31, 56, 100));
        lTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lTitle.setText("GWBC Review Songs");

        tblSongs.setBackground(new java.awt.Color(218, 227, 243));
        tblSongs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));
        tblSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SongID", "Title", "Artist", "Year", "Key", "Number of Tags", "Number of Uses"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSongs);

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/OrderOfService/GWBC Logo.png"))); // NOI18N

        lFilter.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lFilter.setForeground(new java.awt.Color(31, 56, 100));
        lFilter.setText("Filter by...");

        lKey.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lKey.setForeground(new java.awt.Color(31, 56, 100));
        lKey.setText("Key:");

        bExit.setBackground(new java.awt.Color(180, 199, 231));
        bExit.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bExit.setForeground(new java.awt.Color(31, 56, 100));
        bExit.setText("Exit to Main Menu");
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });

        lSongID.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lSongID.setForeground(new java.awt.Color(31, 56, 100));
        lSongID.setText("To see one song, enter its SongID:");

        tfSongID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfSongIDKeyTyped(evt);
            }
        });

        bSongDetails.setBackground(new java.awt.Color(180, 199, 231));
        bSongDetails.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        bSongDetails.setForeground(new java.awt.Color(31, 56, 100));
        bSongDetails.setText("Show Song Details");
        bSongDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSongDetailsActionPerformed(evt);
            }
        });

        cbKey.setBackground(new java.awt.Color(142, 170, 219));
        cbKey.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbKey.setForeground(new java.awt.Color(31, 56, 100));
        cbKey.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Show All" }));
        cbKey.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbKeyItemStateChanged(evt);
            }
        });
        cbKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lFilter)
                    .addComponent(lKey))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bExit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lLogo)
                        .addGap(37, 37, 37))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cbKey, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lSongID)
                            .addComponent(bSongDetails)
                            .addComponent(tfSongID, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(128, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lFilter)
                    .addComponent(lSongID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbKey, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lKey))
                    .addComponent(tfSongID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bSongDetails)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lLogo)
                    .addComponent(bExit))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the form but not the whole application
     * @param evt 
     */
    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        dispose();
    }//GEN-LAST:event_bExitActionPerformed

    /**
     * Ignore this
     */
    private void cbKeyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKeyItemStateChanged

    }//GEN-LAST:event_cbKeyItemStateChanged

    /**
     * Method that is run when the state of the combo box is changed
     * If the index is 0 nothing happens
     * @param evt 
     */
    private void cbKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKeyActionPerformed
        int selectedIndex = cbKey.getSelectedIndex();
        
        //If the user has chosen 'Show All' display original results
        if(selectedIndex == 0) {
            //Empty the table
            
            //Change the model to have a row count of 0
            DefaultTableModel model = (DefaultTableModel) tblSongs.getModel();
            model.setRowCount(0);
            
            //set the model of the table to the change version
            tblSongs.setModel(model);
            
            //Add Songs to the table
            String SongCount = "select COUNT(*) from `Songs`";
            String SelectSongs = "select * from `Songs`";
            getSongs(SongCount, SelectSongs);
        }
        else {
            //Empty the table
            
            //Change the model to have a row count of 0
            DefaultTableModel model = (DefaultTableModel) tblSongs.getModel();
            model.setRowCount(0);
            
            //set the model of the table to the change version
            tblSongs.setModel(model);
            
            //Add Songs that only have that particular key
            String key = cbKey.getSelectedItem().toString();
            
            String SongCount = "select COUNT(*) from `Songs` where `Key` = '" + key + "'";
            String SelectSongs = "select * from `Songs` where `Key` = '" + key + "'";
            getSongs(SongCount, SelectSongs);
        }
    }//GEN-LAST:event_cbKeyActionPerformed

    /**
     * Key Listener that consumes inputs if they are not numeric
     * It also consumes inputs if the length of the text is 11
     * @param evt 
     */
    private void tfSongIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSongIDKeyTyped
        char inChar = evt.getKeyChar();
        
        if(Character.isDigit(inChar) == false || tfSongID.getText().length() == 11) {
            evt.consume();
        }
    }//GEN-LAST:event_tfSongIDKeyTyped

    /**
     * Action Listener that creates a new ReviewSongDetails form and disposes this form
     * @param evt 
     */
    private void bSongDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSongDetailsActionPerformed
        //Set String SongID to the text in tfSongID
        String SongID = tfSongID.getText();
        
        //Only create a new form if the SongID length is greater than zero
        if(SongID.length() > 0) {
            //Create a ReviewSongDetails form and set it visible
            ReviewSongDetails reviewSongDetails = new ReviewSongDetails(SongID);
            reviewSongDetails.setVisible(true);
        
            //Dispose this form
            dispose();
        }
        else {
            UserMessageBox myMessage = new UserMessageBox();
            myMessage.setTitle("Error!");
            myMessage.setMessage("There seems to be no inputted SongID.");
            myMessage.setVisible(true);
        }
    }//GEN-LAST:event_bSongDetailsActionPerformed

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
            java.util.logging.Logger.getLogger(ReviewSongs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReviewSongs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReviewSongs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReviewSongs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReviewSongs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bExit;
    private javax.swing.JButton bSongDetails;
    private javax.swing.JComboBox cbKey;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lFilter;
    private javax.swing.JLabel lKey;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lSongID;
    private javax.swing.JLabel lTitle;
    private javax.swing.JTable tblSongs;
    private javax.swing.JTextField tfSongID;
    // End of variables declaration//GEN-END:variables
}
