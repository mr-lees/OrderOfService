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
public class AddSongToOrder extends javax.swing.JFrame {
    private String Songs[][];
    private String Keys[][];
    private String Tags[][];
    private String SQLCount;
    private String SQLSelect;
    

    /**
     * Creates new form AddSongToOrder
     */
    public AddSongToOrder() {
        initComponents();
        
        //Fill the Songs combo box cbSong
        SQLCount = "select COUNT(*) from `Songs`";
        SQLSelect = "select `SongTitle`, `Key` from `Songs`";
        fillComboBoxes(SQLCount, SQLSelect);
        
        //Fill the Keys combo box cbKey
        SQLCount = "select COUNT(distinct `Key`) from `Songs`";
        SQLSelect = "select distinct `Key` from `Songs`";
        fillComboBoxes(SQLCount, SQLSelect);
        
        //Fill the Tags combo box cbTag
        SQLCount = "select COUNT(distinct `TagDescription`) from `Tags`";
        SQLSelect = "select distinct `TagDescription` from `Tags`";
        fillComboBoxes(SQLCount, SQLSelect);
    }
    
    
    /**
     * Method used to fill the combo boxes from this form
     * @param SQLCountStatement - a string that is the SQL count statement
     * @param SQLSelectStatement - a string that is the SQL select statement
     */
    public void fillComboBoxes(String SQLCountStatement, String SQLSelectStatement) {
        int thisError = 0;
        //Number of combobox items after "Choose Song"/"Filter Key"/"Filter Tag"
        int numItems = 0; 
        
        //Sets the fields countStatement and selectStatement to the parameter values
        String countStatement = SQLCountStatement;
        String selectStatement = SQLSelectStatement;
        
        //Sets the SQL string to countStatement
        OrderOfServiceMain.dbObject.sqlString = countStatement; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Sets the SQL string to selectStatement
        OrderOfServiceMain.dbObject.sqlString = selectStatement;
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                if(selectStatement.equals("select `SongTitle`, `Key` from `Songs`")) {
                    //Initialise the array Songs
                    Songs = new String[numItems][3];

                    //Variable i is used as a way of placing the song in the right location of array Songs
                    int i = 0;

                    while (OrderOfServiceMain.dbObject.rs.next()) {

                        String SongTitle = OrderOfServiceMain.dbObject.rs.getString ("SongTitle");
                        String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");

                        //Concatonate Title and Key into one line
                        String Item = SongTitle + ", " + Key;

                        //Add data to array Songs
                        Songs[i][0] = Item;
                        Songs[i][1] = SongTitle;
                        Songs[i][2] = Key;

                        //Add Item to combo box
                        cbSong.addItem(Item);

                        //Increment i so that next results will be placed in a different address of the array
                        i++;

                    }
                }
                if(selectStatement.equals("select distinct `Key` from `Songs`")) {
                    //Initialise the array Keys
                    Keys = new String[numItems][1];

                    //Variable i is used as a way of placing the key in the right location of array Keys
                    int i = 0;
                    
                    while (OrderOfServiceMain.dbObject.rs.next()) {
                        
                        String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                        
                        //Add Key to the array
                        Keys[i][0] = Key;
                        
                        //Add Key to cbKey
                        cbKey.addItem(Key);
                        
                        //Increment i so that next results will be placed in a different address of the array
                        i++;
                    }
                }
                if(selectStatement.equals("select distinct `TagDescription` from `Tags`")) {
                    //Initialise the array Tags
                    Tags = new String[numItems][1];

                    //Variable i is used as a way of placing the tag in the right location of array Keys
                    int i = 0;
                    
                    while (OrderOfServiceMain.dbObject.rs.next()) {
                        
                        String Tag = OrderOfServiceMain.dbObject.rs.getString ("TagDescription");
                        
                        //Add Tag to the array
                        Tags[i][0] = Tag;
                        
                        //Add Key to cbTag
                        cbTag.addItem(Tag);
                        
                        //Increment i so that next results will be placed in a different address of the array
                        i++;
                    }
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
     * Filters the items in the combo box relating to either a Tag or a Key
     * @param sqlStatement1 - an SQL Statement to count number of results that fit the criteria
     * @param sqlStatement2 - an SQL Statement to retrieve these results
     */
    public void filterResults(String sqlStatement1, String sqlStatement2) {
        int thisError = 0;
        //Number of combobox items after "Choose Song"
        int numItems = 0; 
        
        //Sets the SQL string to count from Songs
        OrderOfServiceMain.dbObject.sqlString = sqlStatement1; 
        thisError = OrderOfServiceMain.dbObject.getCountBySelect ();
        
        numItems = OrderOfServiceMain.dbObject.NumberOfRecords;
        
        //Create SQL Statement to retrieve SongTitle and Key from every record
        OrderOfServiceMain.dbObject.sqlString = sqlStatement2;
        
        //Execute statement
        thisError = OrderOfServiceMain.dbObject.getRecordSetBySelect();
        if (thisError == 0) {
            try {
                //Array Songs is made new by initialising it again
                Songs = new String[numItems][3];
                
                //Delete previous items in combobox
                int maxI = cbSong.getItemCount();
                for(int i = maxI - 1; i > 0; i--) {
                    cbSong.removeItemAt(i);
                }
                
                //Variable i is used as a way of placing the song in the right location of array Songs
                int i = 0;
                
                while (OrderOfServiceMain.dbObject.rs.next()) {
                    String SongTitle = OrderOfServiceMain.dbObject.rs.getString ("SongTitle");
                    String Key = OrderOfServiceMain.dbObject.rs.getString ("Key");
                    
                    //Concatonate Title and Key into one line
                    String Item = SongTitle + ", " + Key;
                    
                    //Add data to array Songs
                    Songs[i][0] = Item;
                    Songs[i][1] = SongTitle;
                    Songs[i][2] = Key;
                    
                    cbSong.addItem(Item);
                    
                    //Increment i so that next data is placed in a different array location
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
        cbSong = new javax.swing.JComboBox();
        lFilterBy = new javax.swing.JLabel();
        lKey = new javax.swing.JLabel();
        lTag = new javax.swing.JLabel();
        lEnterSong = new javax.swing.JLabel();
        tfSong = new javax.swing.JTextField();
        lLogo = new javax.swing.JLabel();
        bSubmit = new javax.swing.JButton();
        cbTag = new javax.swing.JComboBox();
        cbKey = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GWBC Order of Service Manager - Create Service");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(218, 227, 243));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(68, 114, 196), 2));

        lHeading.setFont(new java.awt.Font("Calibri", 3, 36)); // NOI18N
        lHeading.setForeground(new java.awt.Color(31, 56, 100));
        lHeading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lHeading.setText("GWBC Create Service");

        cbSong.setBackground(new java.awt.Color(142, 170, 219));
        cbSong.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbSong.setForeground(new java.awt.Color(31, 56, 100));
        cbSong.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose Song" }));
        cbSong.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSongItemStateChanged(evt);
            }
        });
        cbSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSongActionPerformed(evt);
            }
        });

        lFilterBy.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lFilterBy.setForeground(new java.awt.Color(31, 56, 100));
        lFilterBy.setText("Filter by...");

        lKey.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lKey.setForeground(new java.awt.Color(31, 56, 100));
        lKey.setText("Key");

        lTag.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lTag.setForeground(new java.awt.Color(31, 56, 100));
        lTag.setText("Tag");

        lEnterSong.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        lEnterSong.setForeground(new java.awt.Color(31, 56, 100));
        lEnterSong.setText("Enter Song:");

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

        cbTag.setBackground(new java.awt.Color(142, 170, 219));
        cbTag.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbTag.setForeground(new java.awt.Color(31, 56, 100));
        cbTag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filter Tag" }));
        cbTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTagActionPerformed(evt);
            }
        });

        cbKey.setBackground(new java.awt.Color(142, 170, 219));
        cbKey.setFont(new java.awt.Font("Calibri Light", 1, 16)); // NOI18N
        cbKey.setForeground(new java.awt.Color(31, 56, 100));
        cbKey.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filter Key" }));
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
            .addComponent(lHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lLogo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lEnterSong)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfSong))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lKey)
                                        .addComponent(lTag))
                                    .addGap(65, 65, 65)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cbTag, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbKey, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(lFilterBy))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bSubmit)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbSong, 0, 400, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lHeading, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lFilterBy))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lKey)
                    .addComponent(cbKey, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTag)
                    .addComponent(cbTag, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lEnterSong)
                    .addComponent(tfSong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    //Ignore
    private void cbSongItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSongItemStateChanged
        
    }//GEN-LAST:event_cbSongItemStateChanged

    
    
    /**
     * Listener that adds the selected song to the order of service
     * @param evt 
     */
    private void bSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSubmitActionPerformed
        int i = 0;
        int songIndex = -1; 
        boolean found = false;
        
        //Does a linear search through ArrayList Songs to find the selected song
        while(i < Songs.length && found == false) {
            if(Songs[i][0].equals(tfSong.getText())) {
                songIndex = i;
                found = true;
            }
            i++;
        }
        
        if(found == true) {
            //Return the current table model for the Order of Service table
            DefaultTableModel model = (DefaultTableModel) OrderOfService.CreateServiceManual.tblOrderOfService.getModel();
            int index = model.getRowCount();
            
            //Add a new row containing the Index, Item Type ("Song") and the Title and key of the song to the model
            model.addRow(new Object[]{index, "Song", Songs[songIndex][1], Songs[songIndex][2]});
            
            //Set the model of the Order of Service table to the updated version
            OrderOfService.CreateServiceManual.tblOrderOfService.setModel(model);
            
            //Dispose the AddSongToOrder form
            dispose();
        }
        else {
            UserMessageBox MyMessage = new UserMessageBox ();
            MyMessage.setMessage ("Unnacceptable song: \n"
                    + "Must choose a song from the list. \n"
                    + "Song has not been submitted.");
            MyMessage.setVisible (true);
        }
    }//GEN-LAST:event_bSubmitActionPerformed

    /**
     * Event Listener that filters the results by the selected Tag if not the "Filter Tag"
     * @param evt 
     */
    private void cbTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTagActionPerformed
        //If it's not "Filter Tag" then run the filter method
        if(cbTag.getSelectedIndex() != 0) {
            String tag = cbTag.getSelectedItem().toString();
            
            //Set the count and select statements
            SQLCount = "select COUNT(*) from `Songs` where `SongID` in ("
                    + "select `SongID` from `SongTags` where `TagID` = ("
                    + "select `TagID` from `Tags` where `TagDescription` = '" + tag + "'))";
            
            System.out.println(SQLCount);
            
            SQLSelect = "select `SongTitle`, `Key` from `Songs` where `SongID` in ("
                    + "select `SongID` from `SongTags` where `TagID` = ("
                    + "select `TagID` from `Tags` where `TagDescription` = '" + tag + "'))";
            
            System.out.println(SQLSelect);
            
            //Run filterResults method
            filterResults(SQLCount, SQLSelect);
        }
        //Returns the Songs combo box to full size
        else {
            //Empty results from previous filters
            int maxI = cbSong.getItemCount();
            for(int i = maxI - 1; i > 0; i--) {
                cbSong.removeItemAt(i);
            }
            //Fill the Songs combo box cbSong
            SQLCount = "select COUNT(*) from `Songs`";
            SQLSelect = "select `SongTitle`, `Key` from `Songs`";
            fillComboBoxes(SQLCount, SQLSelect);
        }
    }//GEN-LAST:event_cbTagActionPerformed

    //Ignore
    private void cbKeyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKeyItemStateChanged
        
    }//GEN-LAST:event_cbKeyItemStateChanged

    /**
     * Event Listener that filters the results by the selected Key if not the "Filter Key"
     * @param evt 
     */
    private void cbKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKeyActionPerformed
        //If it's not "Filter Key" then run the filter method
        if(cbKey.getSelectedIndex() != 0) {
            String key = cbKey.getSelectedItem().toString();
            
            //Set the count and select statements
            SQLCount = "select COUNT(*) from `Songs` where `Key` = '" + key + "'";
            SQLSelect = "select `SongTitle`, `Key` from `Songs` where `Key` = '" + key + "'";
            
            //Run filterResults method
            filterResults(SQLCount, SQLSelect);
        }
        //Returns the Songs combo box to full size
        else {
            //Empty results from previous filters
            int maxI = cbSong.getItemCount();
            for(int i = maxI - 1; i > 0; i--) {
                cbSong.removeItemAt(i);
            }
            //Fill the Songs combo box cbSong
            SQLCount = "select COUNT(*) from `Songs`";
            SQLSelect = "select `SongTitle`, `Key` from `Songs`";
            fillComboBoxes(SQLCount, SQLSelect);
        }
    }//GEN-LAST:event_cbKeyActionPerformed

    /**
     * Listener that sets the text field to the selected item in "Choose Song" combo box
     * @param evt 
     */
    private void cbSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSongActionPerformed
        //Sets the tfSong text to the currently chosen song given that it is NOT 'Choose Song'
        if(cbSong.getSelectedIndex() != 0) {       
            String Song;
            Song = cbSong.getSelectedItem().toString();

            tfSong.setText(Song);
        }
        //Sets the tfSong text to null when 'Choose Song' is pressed
        else {
            tfSong.setText("");
        }
    }//GEN-LAST:event_cbSongActionPerformed

    
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
            java.util.logging.Logger.getLogger(AddSongToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSongToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSongToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSongToOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddSongToOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bSubmit;
    private javax.swing.JComboBox cbKey;
    private javax.swing.JComboBox cbSong;
    private javax.swing.JComboBox cbTag;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lEnterSong;
    private javax.swing.JLabel lFilterBy;
    private javax.swing.JLabel lHeading;
    private javax.swing.JLabel lKey;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel lTag;
    private javax.swing.JTextField tfSong;
    // End of variables declaration//GEN-END:variables
}
